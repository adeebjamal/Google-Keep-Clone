package com.example.GoogleKeepClone.services;

import com.example.GoogleKeepClone.entities.RegisteredUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthenticationService {

    RegisteredUser getUserByEmail(String email);

    ResponseEntity<Map<String, String>> register(String email, String name, String password, HttpServletResponse response);

    ResponseEntity<Map<String, String>> verifyOtp(Integer OTP, HttpServletRequest httpServletRequest);

}