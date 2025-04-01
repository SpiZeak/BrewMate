package com.BrewMate.BrewMate.config;

import com.BrewMate.BrewMate.security.JwtAuthenticationFilter;
import com.BrewMate.BrewMate.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

// Marks this class as a configuration class
@Configuration
// Enables Spring Security's web security support
@EnableWebSecurity
public class SecurityConfig {
    // Creates our JWT authentication filter
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil) {
        return new JwtAuthenticationFilter(jwtUtil);
    }

    // Creates a password encoder for secure password storage
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configures security rules for our application
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                // Disable CSRF for simplicity (test mode)
                .csrf(csrf -> csrf.disable())

                // Security Headers
                .headers(headers -> headers
                        // Prevents XSS attacks by controlling what scripts can run
                        .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'; script-src 'self'"))
                        // Additional XSS protection
                        .addHeaderWriter(new StaticHeadersWriter("X-XSS-Protection", "1; mode=block"))
                        // Prevents clickjacking attacks by disallowing frames
                        .frameOptions(frame -> frame.deny())
                        // Prevents MIME type sniffing attacks
                        .addHeaderWriter(new StaticHeadersWriter("X-Content-Type-Options", "nosniff")))

                // Session management (stateless JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // URL Access Rules
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints that don't require login
                        .requestMatchers("/users/auth/register", "/users/auth/login", "/users/auth/logout", "/api/coffees/**").permitAll()
                        // Everyone can get user list
                        .requestMatchers(HttpMethod.GET, "/users").permitAll()
                        // All other requests need authentication
                        .anyRequest().authenticated())

                // JWT Authentication filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Creates an authentication manager
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}