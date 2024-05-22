package com.example.GoogleKeepClone.services;

import com.example.GoogleKeepClone.entities.RegisteredUser;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthenticationService {

    RegisteredUser getUserByEmail(String email);

    ResponseEntity<Map<String, String>> register(String email, HttpServletResponse response);

}