package com.example.GoogleKeepClone.services;

import com.example.GoogleKeepClone.Repositories.AuthenticationRepo;
import com.example.GoogleKeepClone.Utilities.EmailUtility;
import com.example.GoogleKeepClone.Utilities.JwtUtility;
import com.example.GoogleKeepClone.Utilities.PasswordHashUtility;
import com.example.GoogleKeepClone.entities.RegisteredUser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationRepo authenticationRepo;

    @Autowired
    private EmailUtility emailUtility;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private PasswordHashUtility passwordHashUtility;

    @Override
    public RegisteredUser getUserByEmail(String email) {
        RegisteredUser existingUser = null;
        try {
            existingUser = this.authenticationRepo.findByEmail(email);
        }
        catch(Exception e) {
            System.out.println("AuthenticationServiceImpl : getUserByEmail");
            System.out.println("ERROR MESSAGE : " + e.getMessage());
        }
        return existingUser;
    }

    @Override
    public ResponseEntity<Map<String, String>> register(String email, String name, String password, HttpServletResponse response) {
        Map<String, String> responseBody = new HashMap<>();
        try {
            Random random = new Random();
            int OTP = random.nextInt(999999 - 100000 + 1) + 100000;

            // Generating a cookie
            String payload = String.valueOf(OTP) + "`" + email + "`" + name + "`" + password;
            Cookie otpCookie = new Cookie("OTP", this.jwtUtility.generateJwt(payload));
            otpCookie.setHttpOnly(true);    // Optional: makes the cookie inaccessible to JavaScript
            otpCookie.setMaxAge(20 * 60);    // Optional: set expiry time in seconds (20 minutes)

            // Adding cookie into the response
            response.addCookie(otpCookie);

            // Sending OTP to user's email
            String subject = "OTP for email verification.";
            String body = "Thank you for signing up for Google Keep Clone. Use this OTP to verify your email " + String.valueOf(OTP);
            this.emailUtility.sendEmail(email, subject, body);

            // responseBody.put("OTP", String.valueOf(OTP));
            responseBody.put("message", "OTP generated and sent to your email.");
            responseBody.put("next step", "Hit the OTP verification endpoint with the OTP in request payload.");
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
        catch(Exception e) {
            System.out.println("AuthenticationServiceImpl : register");
            System.out.println("ERROR MESSAGE : " + e.getMessage());
            responseBody.put("Message", "Internal Server Error...");
            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> verifyOtp(Integer OTP, HttpServletRequest request) {
        Map<String, String> responseBody = new HashMap<>();
        try {
            Cookie[] cookies = request.getCookies();
            for(Cookie cookie: cookies) {
                if("OTP".equals(cookie.getName())) {
                    String otpJwt = cookie.getValue();
                    String payload = this.jwtUtility.validateToken(otpJwt);
                    String[] userInfo = payload.split("`");
                    if(userInfo[0].equals(OTP.toString())) {
                        this.authenticationRepo.insertRegisteredUser(userInfo[1], userInfo[2], this.passwordHashUtility.hashPassword(userInfo[3]));
                        responseBody.put("Message", "Registration successful.");
                        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
                    }
                    else {
                        responseBody.put("Error", "Make sure that the OTP is correct.");
                        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
                    }
                }
            }
            responseBody.put("OTP expired", "The OTP has expired. Try again after some time.");
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
        catch(Exception e) {
            System.out.println("AuthenticationServiceImpl : verifyOtp");
            System.out.println(e.getMessage());
            responseBody.put("ERROR", "Internal Server Error...");
            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> login(String email, String password, HttpServletResponse response) {
        Map<String, String> responseBody = new HashMap<>();
        try {
            RegisteredUser registeredUser = this.getUserByEmail(email);
            if(registeredUser == null) {
                responseBody.put("Message", "User with entered email doesn't exists.");
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }
            if(!registeredUser.getPassword().equals(this.passwordHashUtility.hashPassword(password))) {
                responseBody.put("Message", "Invalid credentials.");
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            Cookie loggedInUser = new Cookie("loggedInUser", this.jwtUtility.generateJwt(String.valueOf(registeredUser.getId())));
            loggedInUser.setHttpOnly(true);    // Optional: makes the cookie inaccessible to JavaScript
            loggedInUser.setMaxAge(24 * 60 * 60);    // Optional: set expiry time in seconds (24 hours)
            loggedInUser.setPath("/");      // Now this cookie can be accessed in every endpoint of the application

            response.addCookie(loggedInUser);

            responseBody.put("Logged in user", registeredUser.getName());
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
        catch(Exception e) {
            System.out.println("AuthenticationServiceImpl : login");
            System.out.println(e.getMessage());
            responseBody.put("ERROR", "Internal Server Error...");
            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
