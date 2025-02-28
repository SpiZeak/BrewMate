package com.BrewMate.BrewMate.security;

import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // Define 256-bit secret keys (make sure to store them securely in production)
    private static final String SECRET_KEY_STRING = "this_is_a_very_secure_256_bit_secret_key_for_jwt_signing_1234567890abcde"; // 256-bit
    private static final String REFRESH_SECRET_KEY_STRING = "this_is_a_very_secure_256_bit_refresh_secret_key_for_jwt_1234567890abcde"; // 256-bit refresh key

    // Convert the string secret into Key objects
    private static final Key SECRET_KEY = new SecretKeySpec(SECRET_KEY_STRING.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    private static final Key REFRESH_SECRET_KEY = new SecretKeySpec(REFRESH_SECRET_KEY_STRING.getBytes(), SignatureAlgorithm.HS256.getJcaName());

    // Expiry times for access and refresh tokens
    private static final long ACCESS_EXPIRATION_TIME = 1000 * 60 * 15; // 15 minutes
    private static final long REFRESH_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7 days

    // Generates the access token (15 minutes expiry)
    public static String generateAccessToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // Sign with the 256-bit secret key
                .compact();
    }

    // Generates the refresh token (7 days expiry)
    public static String generateRefreshToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(REFRESH_SECRET_KEY, SignatureAlgorithm.HS256) // Sign with the refresh secret key
                .compact();
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
                .setSigningKey(isRefresh ? REFRESH_SECRET_KEY : SECRET_KEY) // Use the correct key for validation
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
