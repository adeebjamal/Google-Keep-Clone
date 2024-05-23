package com.example.GoogleKeepClone.controllers;

import com.example.GoogleKeepClone.entities.OtpVerificationRequest;
import com.example.GoogleKeepClone.entities.RegisteredUser;
import com.example.GoogleKeepClone.services.AuthenticationService;
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
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestBody OtpVerificationRequest otpVerificationRequest, HttpServletRequest httpServletRequest) {
        Map<String, String> responseBody = new HashMap<>();
        try {
            if(otpVerificationRequest.getOTP() == null) {
                responseBody.put("Error", "Make sure to add the OTP in request body.");
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }
            return this.authenticationService.verifyOtp(otpVerificationRequest.getOTP(), httpServletRequest);
        }
        catch(Exception e) {
            System.out.println("AuthenticationController : verifyOtp");
            System.out.println(e.getMessage());
            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}