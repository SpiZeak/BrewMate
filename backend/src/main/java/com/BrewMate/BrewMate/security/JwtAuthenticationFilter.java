package com.BrewMate.BrewMate.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * This filter runs before each request and checks if a valid JWT is provided.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Autowired
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extract access token from cookies
        String token = jwtUtil.getTokenFromRequest(request, "access_token");

        // If token is valid, authenticate the user
        if (token != null && jwtUtil.isTokenValid(token, false)) {
            try {
                String email = jwtUtil.extractEmail(token, false);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        // Continue processing the request
        filterChain.doFilter(request, response);
    }
}


/*The cookie validation happens in the `JwtAuthenticationFilter` which:
        1. Extracts the access token from the cookies
        2. Validates the token using `JwtUtil.isTokenValid()`
        3. If valid, creates an authentication token and sets it in the security context
        4. If invalid, returns a 401 Unauthorized response

This filter extends `OncePerRequestFilter`, meaning it runs before every request to validate the JWT cookie. This is a security best practice as it ensures that every protected endpoint is properly authenticated.
So while the `UserController` handles setting the cookies during login, the actual validation of those cookies happens automatically through Spring Security's filter chain via the `JwtAuthenticationFilter`.
        */