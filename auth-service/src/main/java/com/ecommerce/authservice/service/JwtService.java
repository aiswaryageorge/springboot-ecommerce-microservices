package com.ecommerce.authservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
    public class JwtService {

    private final String SECRET =
            "thisIsAVerySecureSecretKeyForJwtAuthentication123456";

    public String generateToken(String username, String role) {

            return Jwts.builder()
                    .subject(username)
                    .claim("role", role)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                    .signWith(getKey())
                    .compact();
        }

    public String generateRefreshToken(String username, String role) {

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()
                        + 1000L * 60 * 60 * 24 * 7))
                .signWith(getKey())
                .compact();
    }

        private SecretKey getKey() {

            byte[] keyBytes =
                    SECRET.getBytes(StandardCharsets.UTF_8);

            return Keys.hmacShaKeyFor(keyBytes);
        }

        public String extractUsername(String token) {

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String extractRole(String token) {

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    public boolean validateToken(String token, String username) {

        String extractedUsername = extractUsername(token);

        return extractedUsername.equals(username);
    }
    }
