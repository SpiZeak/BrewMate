package com.BrewMate.BrewMate.service;

import com.BrewMate.BrewMate.model.User;
import com.BrewMate.BrewMate.repository.UserRepository;
import com.BrewMate.BrewMate.security.JwtUtil;
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

	/** Saves a new user with password encoding (BCrypt). */
	public User saveUser(User user) {
		// Ensure unique userId by regenerating if needed
		while(userRepository.findByUserId(user.getUserID()).isPresent()){
			user.setUserID(User.generateRandomUserID());
		}
		user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password using BCrypt
		return userRepository.save(user);
	}

	/** Authenticates a user and returns JWT token along with user data. */
	public Optional<Object> authenticateUser(String email, String password) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			if (passwordEncoder.matches(password, user.getPassword())) {
				// Generate JWT tokens
				String accessToken = JwtUtil.generateAccessToken(email);
				String refreshToken = JwtUtil.generateRefreshToken(email);

				// Return user data along with JWT token
				return Optional.of(new Object() {
					public Long id = user.getId();
					public String name = user.getName();
					public String email = user.getEmail();
					public String password = user.getPassword(); // Return the hashed password for security
					public String userID = user.getUserID();
					public String JWTtoken = accessToken; // Include JWT token in response
				});
			}
		}
		return Optional.empty();
	}
}
