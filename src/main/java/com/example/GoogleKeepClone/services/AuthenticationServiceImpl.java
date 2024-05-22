package com.example.GoogleKeepClone.services;

import com.example.GoogleKeepClone.Repositories.AuthenticationRepo;
import com.example.GoogleKeepClone.Utilities.EmailUtility;
import com.example.GoogleKeepClone.entities.RegisteredUser;
import jakarta.servlet.http.Cookie;
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
    public ResponseEntity<Map<String, String>> register(String email, HttpServletResponse response) {
        Map<String, String> responseBody = new HashMap<>();
        try {
            Random random = new Random();
            int OTP = random.nextInt(999999 - 100000 + 1) + 100000;

            // Generating a cookie
            Cookie otpCookie = new Cookie("OTP", String.valueOf(OTP));
            otpCookie.setHttpOnly(true);    // Optional: makes the cookie inaccessible to JavaScript
            otpCookie.setMaxAge(5 * 60);    // Optional: set expiry time in seconds (5 minutes)

            // Adding cookie into the response
            response.addCookie(otpCookie);

            // Sending OTP to user's email
            String subject = "OTP for email verification.";
            String body = "Thank you for signing up for Google Keep Clone. Use this OTP to verify your email " + String.valueOf(OTP);
            this.emailUtility.sendEmail(email, subject, body);

            responseBody.put("OTP", String.valueOf(OTP));
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
}
