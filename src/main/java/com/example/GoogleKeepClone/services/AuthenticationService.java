package com.example.GoogleKeepClone.services;

import com.example.GoogleKeepClone.entities.RegisteredUser;

public interface AuthenticationService {

    RegisteredUser getUserByEmail(String email);

}