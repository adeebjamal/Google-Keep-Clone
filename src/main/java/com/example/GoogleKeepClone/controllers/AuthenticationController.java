package com.example.GoogleKeepClone.controllers;

import com.example.GoogleKeepClone.entities.LoginRequest;
import com.example.GoogleKeepClone.entities.OtpVerificationRequest;
import com.example.GoogleKeepClone.entities.RegisteredUser;
import com.example.GoogleKeepClone.services.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("authentication")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisteredUser registerRequest, HttpServletResponse response) {
        Map<String, String> responseBody = new HashMap<>();
        try {
            if(registerRequest.getEmail() == null || registerRequest.getName() == null || registerRequest.getPassword() == null) {
                responseBody.put("Message", "Please fill all the required fields.");
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }
            if(registerRequest.getPassword().length() < 6) {
                responseBody.put("Weak password", "Make sure the length of password is at least 6.");
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }
            RegisteredUser existingUser = this.authenticationService.getUserByEmail(registerRequest.getEmail());
            if(existingUser != null) {
                responseBody.put("User already exists", "User with entered email already exists.");
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            return this.authenticationService.register(registerRequest.getEmail(), registerRequest.getName(), registerRequest.getPassword(), response);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println("POST : /authentication/register");
            responseBody.put("message", "Internal server error...");
            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("verifyOtp")
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestBody OtpVerificationRequest otpVerificationRequest, HttpServletRequest request) {
        Map<String, String> responseBody = new HashMap<>();
        try {
            if(otpVerificationRequest.getOTP() == null) {
                responseBody.put("Error", "Make sure to add the OTP in request body.");
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }
            return this.authenticationService.verifyOtp(otpVerificationRequest.getOTP(), request);
        }
        catch(Exception e) {
            System.out.println("AuthenticationController : verifyOtp");
            System.out.println(e.getMessage());
            responseBody.put("message", "Internal server error...");
            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> responseBody = new HashMap<>();
        try {
            Cookie[] cookies = request.getCookies();
            if(cookies != null) {
                for(Cookie cookie: cookies) {
                    if("loggedInUser".equals(cookie.getName())) {
                        responseBody.put("Message", "You are already logged in.");
                        return new ResponseEntity<>(responseBody, HttpStatus.OK);
                    }
                }
            }
            if(loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
                responseBody.put("Message", "Please fill all the required fields.");
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }
            return this.authenticationService.login(loginRequest.getEmail(), loginRequest.getPassword(), response);
        }
        catch(Exception e) {
            System.out.println("AuthenticationController : login");
            System.out.println(e.getMessage());
            responseBody.put("Error", "Internal server error...");
            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> responseBody = new HashMap<>();
        try {
            Cookie[] cookies = request.getCookies();
            if(cookies != null) {
                for(Cookie cookie: cookies) {
                    if("loggedInUser".equals(cookie.getName()) && !cookie.getValue().isEmpty()) {
                        cookie.setValue("");
                        cookie.setPath("/");
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                        responseBody.put("Message", "Logout successful");
                        return new ResponseEntity<>(responseBody, HttpStatus.OK);
                    }
                }
            }
            responseBody.put("Message", "You are not logged in.");
            return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
        }
        catch(Exception e) {
            System.out.println("AuthenticationController : logout");
            System.out.println(e.getMessage());
            responseBody.put("Error", "Internal server error...");
            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}