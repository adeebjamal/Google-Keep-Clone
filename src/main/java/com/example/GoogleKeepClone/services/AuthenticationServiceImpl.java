package com.example.GoogleKeepClone.services;

import com.example.GoogleKeepClone.Repositories.AuthenticationRepo;
import com.example.GoogleKeepClone.entities.RegisteredUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    AuthenticationRepo authenticationRepo;

    @Override
    public RegisteredUser getUserByEmail(String email) {
        RegisteredUser existingUser = null;
        try {
            existingUser = this.authenticationRepo.findByEmail(email);
        }
        catch(Exception e) {
            System.out.println("AuthenticationServiceImpl : getUserByEmail");
            System.out.println("ERROR MESSAGE" + e.getMessage());
        }
        return existingUser;
    }
}
