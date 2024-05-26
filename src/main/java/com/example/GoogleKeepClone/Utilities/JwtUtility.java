package com.example.GoogleKeepClone.Utilities;

public interface JwtUtility {

    String generateJwt(String payload);

    String validateToken(String token);

}
