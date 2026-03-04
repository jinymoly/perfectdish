package com.dish.perfect.global.auth;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenProvider {

    private static final long ACCESS_TOKEN_VALID_TIME = 30 * 60 * 1000L; // 30 mins
    private static final long REFRESH_TOKEN_VALID_TIME = 7 * 24 * 60 * 60 * 1000L; // 7 days

    @Value("${jwt.secretKey}")
    private String secretKey;

    private SecretKey signatureVerificationKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.signatureVerificationKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(String phoneNumber, String role) {
        Claims claims = Jwts.claims().setSubject(phoneNumber);
        claims.put("role", role);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))
                .signWith(signatureVerificationKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String phoneNumber) {
        Claims claims = Jwts.claims().setSubject(phoneNumber);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME))
                .signWith(signatureVerificationKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signatureVerificationKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getPhoneNumberFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signatureVerificationKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signatureVerificationKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class);
    }
}
