package com.BrewMate.BrewMate.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    // Algorithm used for signing tokens
    private static final MacAlgorithm ALGORITHM = Jwts.SIG.HS256;

    // Secret keys for access and refresh tokens
    private static final SecretKey SECRET_KEY = (SecretKey) Keys.secretKeyFor(ALGORITHM);
    private static final SecretKey REFRESH_SECRET_KEY = (SecretKey) Keys.secretKeyFor(ALGORITHM);

    // Token expiration times (milliseconds)
    private static final long ACCESS_EXPIRATION_TIME = 1000 * 60 * 15; // 15 min
    private static final long REFRESH_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7 days

    /** Generates a short-lived access token (15 min). */
    public static String generateAccessToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
                .signWith(SECRET_KEY, ALGORITHM)
                .compact();
    }

    /** Generates a refresh token (7 days) used to obtain a new access token. */
    public static String generateRefreshToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(REFRESH_SECRET_KEY, ALGORITHM)
                .compact();
    }

    /** Validates a JWT (access or refresh token). */
    public static boolean isTokenValid(String token, boolean isRefresh) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(isRefresh ? REFRESH_SECRET_KEY : SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);

            return claims.getPayload().getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /** Extracts the email (subject) from a JWT. */
    public static String extractEmail(String token, boolean isRefresh) {
        return Jwts.parser()
                .verifyWith(isRefresh ? REFRESH_SECRET_KEY : SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /** Retrieves a JWT from a specific cookie in an HTTP request. */
    public static String getTokenFromRequest(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
