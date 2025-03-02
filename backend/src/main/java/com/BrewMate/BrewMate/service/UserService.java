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
     * Saves a new user with password encoding (BCrypt) and generates JWT token
     */
    public Optional<UserDTO> saveUser(User user) {
        // Ensure unique userId by regenerating if needed
        while (userRepository.findByUserId(user.getUserID()).isPresent()) {
            // Change from generateRandomUserID() to generateUniqueUserID()
            user.setUserID(User.generateUniqueUserID());
        }

        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user
        User savedUser = userRepository.save(user);

        // Generate the JWT token
        String accessToken = JwtUtil.generateAccessToken(savedUser.getEmail());
        String refreshToken = JwtUtil.generateRefreshToken(savedUser.getEmail());

        // Return user details with JWT token in DTO
        return Optional.of(new UserDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail(),
                savedUser.getPassword(), savedUser.getUserID(), accessToken));
    }


    /**
     * Authenticates a user and returns JWT token along with user data
     */
    public Optional<UserDTO> authenticateUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Generate JWT tokens
                String accessToken = JwtUtil.generateAccessToken(user.getEmail());
                String refreshToken = JwtUtil.generateRefreshToken(user.getEmail());

                // Return user details with JWT token in DTO
                return Optional.of(new UserDTO(user.getId(), user.getName(), user.getEmail(),
                        user.getPassword(), user.getUserID(), accessToken));
            }
        }
        return Optional.empty();
    }
}
