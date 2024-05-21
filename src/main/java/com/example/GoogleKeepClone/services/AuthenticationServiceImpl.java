package com.example.GoogleKeepClone.services;

import com.example.GoogleKeepClone.Repositories.AuthenticationRepo;
import com.example.GoogleKeepClone.Utilities.EmailUtility;
import com.example.GoogleKeepClone.entities.RegisteredUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            System.out.println("ERROR MESSAGE" + e.getMessage());
        }
        return existingUser;
    }

    @Override
    public void sendOtpToEmail(int OTP, String email) {
        try {
            String subject = "OTP for e-mail verification.";
            String body = "Thank you signing up to Google-Keep-Clone. Use this OTP to verify your email: " + String.valueOf(OTP);
            this.emailUtility.sendEmail(email, subject, body);
        }
        catch(Exception e) {
            System.out.println("AuthenticationServiceImpl : sendOtpToEmail");
            System.out.println(e.getMessage());
        }
    }
}
