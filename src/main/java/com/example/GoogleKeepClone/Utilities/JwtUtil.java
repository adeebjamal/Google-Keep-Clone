package com.example.GoogleKeepClone.Utilities;

public interface JwtUtil {

    String generateJwt(String payload);

    String validateToken(String token);

}
