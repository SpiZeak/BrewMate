package com.BrewMate.BrewMate.controller;

import com.BrewMate.BrewMate.model.User;
import com.BrewMate.BrewMate.security.JwtUtil;
import com.BrewMate.BrewMate.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/auth/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    /** Handles user login and returns user data with JWT token. */
    @PostMapping("/auth/login")
    public ResponseEntity<Object> loginUser(@RequestParam String email, @RequestParam String password) {
        Optional<Object> userOptional = userService.authenticateUser(email, password);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.status(401).body("Invalid email or password.");
        }
    }

    /** Refreshes access token using the refresh token stored in cookies. */
    @PostMapping("/auth/refresh")
    public ResponseEntity<String> refreshAccessToken(@CookieValue(name = "refresh_token", required = false) String refreshToken, HttpServletResponse response) {
        if (refreshToken != null && JwtUtil.isTokenValid(refreshToken, true)) {
            String email = JwtUtil.extractEmail(refreshToken, true);
            String newAccessToken = JwtUtil.generateAccessToken(email);
            addCookie(response, "access_token", newAccessToken, 900); // New 15 min token
            return ResponseEntity.ok("Token refreshed");
        }
        return ResponseEntity.status(401).body("Invalid or expired refresh token.");
    }

    /** Helper method to add JWTs as secure HTTP-only cookies. */
    private void addCookie(HttpServletResponse response, String name, String value, int expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(expiry);
        response.addCookie(cookie);
    }
}
