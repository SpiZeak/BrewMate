package com.BrewMate.BrewMate.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

// Helper class for creating and checking login tokens
@Component
public class JwtUtil {

    // Secret key for normal login tokens
    private final SecretKey secretKey;
    // Secret key for "remember me" tokens
    private final SecretKey refreshSecretKey;
    // How long normal tokens last
    private final long accessExpirationMs;
    // How long "remember me" tokens last
    private final long refreshExpirationMs;

    // Constructor - gets settings from application properties
    public JwtUtil(
            @Value("${jwt.secret}") String secretKeyString,
            @Value("${jwt.refreshSecret}") String refreshSecretKeyString,
            @Value("${jwt.accessExpirationMs}") long accessExpirationMs,
            @Value("${jwt.refreshExpirationMs}") long refreshExpirationMs
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
        this.refreshSecretKey = Keys.hmacShaKeyFor(refreshSecretKeyString.getBytes(StandardCharsets.UTF_8));
        this.accessExpirationMs = accessExpirationMs;
        this.refreshExpirationMs = refreshExpirationMs;
    }

    // Creates a normal login token
    public String generateAccessToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessExpirationMs))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    // Creates a "remember me" token
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshExpirationMs))
                .signWith(refreshSecretKey, Jwts.SIG.HS256)
                .compact();
    }

    // Checks if a token is valid and not expired
    public boolean isTokenValid(String token, boolean isRefresh) {
        try {
            // Parse the token and verify its signature
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(isRefresh ? refreshSecretKey : secretKey)
                    .build()
                    .parseSignedClaims(token);

            // Check if token is expired
            return claims.getPayload().getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Gets the email from a token
    public String extractEmail(String token, boolean isRefresh) {
        return Jwts.parser()
                .verifyWith(isRefresh ? refreshSecretKey : secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Gets a token from browser cookies
    public String getTokenFromRequest(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            // Look through all cookies
            for (Cookie cookie : request.getCookies()) {
                // If we find the cookie we want
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}