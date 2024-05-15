package com.example.GoogleKeepClone.controllers;

import com.example.GoogleKeepClone.entities.RegisteredUser;
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

    @PostMapping("register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisteredUser registerRequest) {
        Map<String, String> response = new HashMap<>();
        try {
            response.put("message", "OTP generated and sent to your email.");
            response.put("next step", "Hit the OTP verification endpoint with the OTP in request payload.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println("POST : /authentication/register");
            response.put("message", "Internal server error.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
