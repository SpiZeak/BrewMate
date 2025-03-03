package com.BrewMate.BrewMate.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // Define the secret key for signing the JWT
    private static final String SECRET_KEY_STRING = "detta-ar-en-mycket-lång-hemlig-nyckel-123456"; // Secure key for signing
    private static final String REFRESH_SECRET_KEY_STRING = "detta-ar-en-mycket-lång-hemlig-nyckel-123456"; // Same secret for both tokens

    // Convert the string secret into Key objects using Keys.hmacShaKeyFor()
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());
    private static final Key REFRESH_SECRET_KEY = Keys.hmacShaKeyFor(REFRESH_SECRET_KEY_STRING.getBytes());

    // Expiry times for access and refresh tokens
    private static final long ACCESS_EXPIRATION_TIME = 1000 * 60 * 60; // 60 minutes
    private static final long REFRESH_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7 days

    // Generates the access token (60 minutes expiry)
    public static String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
                .signWith(SECRET_KEY) // No need to specify the algorithm, it's inferred from the Key
                .compact(); // Returns the token with signature included
    }

    // Generates the refresh token (7 days expiry)
    public static String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(REFRESH_SECRET_KEY) // No need to specify the algorithm, it's inferred from the Key
                .compact(); // Returns the token with signature included
    }

    // Verifies if the token is valid (expiration and signature)
    public static boolean isTokenValid(String token, boolean isRefresh) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(isRefresh ? REFRESH_SECRET_KEY : SECRET_KEY) // Use the correct key for validation
                    .build()
                    .parseClaimsJws(token);  // Parse and validate the JWT

            // Check if the token has expired
            return claims.getBody().getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false; // Return false if the token is invalid or expired
        }
    }

    // Extracts the email from the JWT token
    public static String extractEmail(String token, boolean isRefresh) {
        return Jwts.parser()
                .setSigningKey(isRefresh ? REFRESH_SECRET_KEY : SECRET_KEY) // Ensure correct key for validation
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // Extract the email (subject) from the token
    }

    // Retrieve the token from the request's cookies
    public static String getTokenFromRequest(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue(); // Return the token if found in the cookies
                }
            }
        }
        return null; // Return null if the token is not found
    }
}
