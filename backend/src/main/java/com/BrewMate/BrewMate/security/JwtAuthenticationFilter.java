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

// This checks if the user's login token is valid for each request
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Helper class for working with JWT tokens
    private final JwtUtil jwtUtil;

    // Constructor - sets up the JWT helper
    @Autowired
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // This runs for every web request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get the token from cookies
        String token = jwtUtil.getTokenFromRequest(request, "access_token");

        // If we have a valid token
        if (token != null && jwtUtil.isTokenValid(token, false)) {
            try {
                // Get the user's email from the token
                String email = jwtUtil.extractEmail(token, false);

                // Create an authentication object for Spring Security
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());

                // Tell Spring the user is logged in
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JwtException e) {
                // If token is broken, tell user they're not authorized
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        // Move to the next step in processing the request
        filterChain.doFilter(request, response);
    }
}