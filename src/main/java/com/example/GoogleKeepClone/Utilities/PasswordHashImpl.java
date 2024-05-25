package com.example.GoogleKeepClone.Utilities;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;

@Service
public class PasswordHashImpl implements PasswordHash {

    @Override
    public String hashPassword(String plainTextPassword) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(plainTextPassword.getBytes());
            byte[] result = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for(byte b: result) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }
        catch(Exception e) {
            System.out.println("PasswordHashImpl : hashPassword");
            System.out.println(e.getMessage());
            return null;
        }
    }

}
