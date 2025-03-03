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

    /** Registers a new user and returns user data along with the JWT token */
    @PostMapping("/auth/register")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {
        Optional<UserDTO> savedUser = userService.saveUser(user);
        if (savedUser.isPresent()) {
            return ResponseEntity.ok(savedUser.get());  // Return UserDTO with JWT token
        }
        return ResponseEntity.status(500).body("Error saving user");
    }

    /** Handles user login and sets JWTs as cookies. */
    @PostMapping("/auth/login")
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password, HttpServletResponse response) {
        Optional<UserDTO> tokensOptional = userService.authenticateUser(email, password);
        if (tokensOptional.isPresent()) {
            UserDTO tokens = tokensOptional.get();
            String accessToken = tokens.getJWTtoken();  // Access JWT token from UserDTO
            addCookie(response, "access_token", accessToken, 900); // 15 min
            return ResponseEntity.ok("Logged in successfully");
        } else {
            return ResponseEntity.status(401).body("Invalid email or password.");
        }
    }

    /** Helper method to add JWTs as secure HTTP-only cookies. */
    private void addCookie(HttpServletResponse response, String name, String value, int expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setSameSite("Strict");
        cookie.setMaxAge(expiry);
        response.addCookie(cookie);
    }
}
