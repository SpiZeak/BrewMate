package com.BrewMate.BrewMate.service;

import com.BrewMate.BrewMate.model.User;
import com.BrewMate.BrewMate.repository.UserRepository;
import com.BrewMate.BrewMate.security.JwtUtil;
import com.BrewMate.BrewMate.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Marks this class as a Spring service bean
@Service
public class UserService {

    // Injects dependencies automatically
    @Autowired
    // Repository for database operations with user entities
    private final UserRepository userRepository;
    // Encoder for secure password hashing
    private final PasswordEncoder passwordEncoder;
    // Utility for JWT token generation and validation
    private final JwtUtil jwtUtil;

    // Constructor with dependency injection
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Retrieves all users from the database
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Registers a new user with secure password storage
     * 1. Encodes the password using BCrypt
     * 2. Saves the user to database
     * 3. Returns a DTO without authentication tokens
     */
    public Optional<UserDTO> saveUser(User user) {
        // Encrypt password before storing
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        return Optional.of(new UserDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                null,  // No access token at registration
                null,  // No refresh token at registration
                savedUser.getPassword()
        ));
    }

    /**
     * Authenticates a user and generates security tokens
     * 1. Finds user by email
     * 2. Verifies password matches
     * 3. Generates JWT tokens for authenticated session
     */
    public Optional<UserDTO> authenticateUser(String email, String password) {
        // Look up user by email
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Verify password matches stored hash
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Generate authentication tokens
                String accessToken = jwtUtil.generateAccessToken(user.getEmail());
                String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

                // Return user data with tokens
                return Optional.of(new UserDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        accessToken,
                        refreshToken,
                        user.getPassword()
                ));
            }
        }
        // Authentication failed
        return Optional.empty();
    }
}