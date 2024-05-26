package com.example.GoogleKeepClone.Utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;

import java.util.Date;

@Service
public class JwtUtilityImpl implements JwtUtility {

    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 900000; // 15 minutes

    @Override
    public String generateJwt(String payload) {
        return Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    @Override
    public String validateToken(String token) {
        try {
            Claims claim = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claim.getSubject();
        }
        catch(Exception e) {
            System.out.println("JwtUtilImpl : validateToken");
            System.out.println(e.getMessage());
            return null;
        }
    }
}
