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

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Saves a new user with password encoding (BCrypt) and generates JWT tokens
     */
    public Optional<UserDTO> saveUser(User user) {
        // Ensure unique userId by regenerating if needed
        while (userRepository.findByUserId(user.getUserID()).isPresent()) {
            user.setUserID(User.generateUniqueUserID());
        }

        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user
        User savedUser = userRepository.save(user);

        // Generate JWT tokens
        String accessToken = JwtUtil.generateAccessToken(savedUser.getEmail());
        String refreshToken = JwtUtil.generateRefreshToken(savedUser.getEmail());

        // Return user details with both access and refresh tokens in DTO
        return Optional.of(new UserDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getUserID(),
                accessToken,
                refreshToken
        ));
    }

    /**
     * Authenticates a user and returns both access and refresh tokens along with user data
     */
    public Optional<UserDTO> authenticateUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Generate JWT tokens
                String accessToken = JwtUtil.generateAccessToken(user.getEmail());
                String refreshToken = JwtUtil.generateRefreshToken(user.getEmail());

                // Return user details with both access and refresh tokens in DTO
                return Optional.of(new UserDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getUserID(),
                        accessToken,
                        refreshToken
                ));

            }
        }
            return Optional.empty();
    }
}
