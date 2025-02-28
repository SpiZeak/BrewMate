package com.BrewMate.BrewMate.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // Use SignatureAlgorithm for HMAC SHA-256 (not MacAlgorithm)
    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;

    private static final Key SECRET_KEY = Keys.secretKeyFor(ALGORITHM); // Use the HMAC secret key
    private static final Key REFRESH_SECRET_KEY = Keys.secretKeyFor(ALGORITHM); // Refresh token key

    private static final long ACCESS_EXPIRATION_TIME = 1000 * 60 * 15; // 15 minutes
    private static final long REFRESH_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7 days

    public static String generateAccessToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
                .signWith(SECRET_KEY, ALGORITHM) // Using SignatureAlgorithm.HS256
                .compact();
    }

    public static String generateRefreshToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(REFRESH_SECRET_KEY, ALGORITHM) // Using SignatureAlgorithm.HS256
                .compact();
    }

    public static boolean isTokenValid(String token, boolean isRefresh) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(isRefresh ? REFRESH_SECRET_KEY : SECRET_KEY) // Ensure correct key for validation
                    .build()
                    .parseClaimsJws(token);

            return claims.getBody().getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public static String extractEmail(String token, boolean isRefresh) {
        return Jwts.parser()
                .setSigningKey(isRefresh ? REFRESH_SECRET_KEY : SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static String getTokenFromRequest(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue(); // Return the token from the cookie
                }
            }
        }
        return null;
    }
}
