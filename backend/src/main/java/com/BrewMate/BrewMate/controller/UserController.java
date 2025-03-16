package com.BrewMate.BrewMate.controller;

import com.BrewMate.BrewMate.model.User;
import com.BrewMate.BrewMate.service.UserService;
import com.BrewMate.BrewMate.dto.UserDTO;
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

    /** Registers a new user WITHOUT generating JWT tokens */
    @PostMapping("/auth/register")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {
        Optional<UserDTO> savedUser = userService.saveUser(user);
        if (savedUser.isPresent()) {
            return ResponseEntity.ok(savedUser.get());  // Return user details, no JWT tokens
        }
        return ResponseEntity.status(500).body("Error saving user");
    }

    /** Handles user login and sets JWTs as cookies */
    @PostMapping("/auth/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest, HttpServletResponse response) {
        Optional<UserDTO> tokensOptional = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
        if (tokensOptional.isPresent()) {
            UserDTO tokens = tokensOptional.get();
            addCookie(response, "access_token", tokens.getAccessToken(), 3600); // 1 hour expiration
            addCookie(response, "refresh_token", tokens.getRefreshToken(), 604800); // 7 days expiration
            return ResponseEntity.ok("Logged in successfully");
        } else {
            return ResponseEntity.status(401).body("Invalid email or password.");
        }
    }


    /** Helper method to add JWTs as secure HTTP-only cookies */
    private void addCookie(HttpServletResponse response, String name, String value, int expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(expiry);
        response.addHeader("Set-Cookie", String.format("%s=%s; Max-Age=%d; Path=%s; HttpOnly; Secure; SameSite=Strict",
                name, value, expiry, cookie.getPath()));
    }
}
