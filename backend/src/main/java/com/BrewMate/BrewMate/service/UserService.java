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
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Saves a new user with password encoding (BCrypt) WITHOUT generating JWT tokens.
     */
    public Optional<UserDTO> saveUser(User user) {
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
     * Authenticates a user and generates JWT tokens.
     */
    public Optional<UserDTO> authenticateUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {

                String accessToken = jwtUtil.generateAccessToken(user.getEmail());
                String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

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
        return Optional.empty();
    }
}
