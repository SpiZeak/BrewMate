package com.BrewMate.BrewMate.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY_STRING = "detta-ar-en-mycket-lång-hemlig-nyckel-123456";
    private static final String REFRESH_SECRET_KEY_STRING = "detta-ar-en-mycket-lång-hemlig-nyckel-123456";

    private static final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));
    private static final SecretKey refreshSecretKey = Keys.hmacShaKeyFor(REFRESH_SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));

    private static final long ACCESS_EXPIRATION_TIME = 1000 * 60 * 15;
    private static final long REFRESH_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;

    public static String generateAccessToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public static String generateRefreshToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(refreshSecretKey, Jwts.SIG.HS256)
                .compact();
    }

    public static boolean isTokenValid(String token, boolean isRefresh) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(isRefresh ? refreshSecretKey : secretKey)
                    .build()
                    .parseSignedClaims(token);

            return claims.getPayload().getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public static String extractEmail(String token, boolean isRefresh) {
        return Jwts.parser()
                .verifyWith(isRefresh ? refreshSecretKey : secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

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