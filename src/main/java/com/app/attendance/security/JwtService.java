package com.app.attendance.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }
    
    private Claims extractAllClaims(String token) {

        Jws<Claims> claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);

        return claims.getPayload();
    }
    
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
    
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
    
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    public boolean isTokenValid(String token, String email) {

        String username = extractUsername(token);

        return username.equals(email) && !isTokenExpired(token);
    }
}