package com.example.GoogleKeepClone.entities;

public class LoginRequest {

    private String email;
    private String password;

    public String getEmail() {
        // get email
        return this.email;
    }

    public void setEmail(String email) {
        // set email
        this.email = email;
    }

    public String getPassword() {
        // get password
        return this.password;
    }

    public void setPassword(String password) {
        // set password
        this.password = password;
    }
}
