package com.BrewMate.BrewMate.config;

import com.BrewMate.BrewMate.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /** Configures security settings, allowing JWT authentication. */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // ✅ Disable CSRF (re-enable later if needed)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/auth/register", "/users/auth/login", "/users/auth/refresh").permitAll() // Public endpoints
                        .anyRequest().authenticated() // 🔒 Require authentication for other requests
                )
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) // ✅ Validate JWT in requests
                .build();
    }




}
