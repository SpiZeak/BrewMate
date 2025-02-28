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

	/** Saves a new user with password encoding (BCrypt) and generates JWT token */
	public Optional<Object> saveUser(User user) {
		// Ensure unique userId by regenerating if needed
		while(userRepository.findByUserId(user.getUserID()).isPresent()){
			user.setUserID(User.generateRandomUserID());
		}

		// Hash the password before saving
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// Save the user
		User savedUser = userRepository.save(user);

		// Generate the JWT token
		String accessToken = JwtUtil.generateAccessToken(savedUser.getEmail());
		String refreshToken = JwtUtil.generateRefreshToken(savedUser.getEmail());

		// Return user details with JWT token
		return Optional.of(new Object() {
			public Long id = savedUser.getId();
			public String name = savedUser.getName();
			public String email = savedUser.getEmail();
			public String password = savedUser.getPassword(); // Return the hashed password for security
			public String userID = savedUser.getUserID();
			public String JWTtoken = accessToken; // Include JWT token in response
		});
	}

	/** Authenticates a user and returns JWT token along with user data */
	public Optional<Object> authenticateUser(String email, String password) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			if (passwordEncoder.matches(password, user.getPassword())) {
				// Generate JWT tokens
				String accessToken = JwtUtil.generateAccessToken(user.getEmail());
				String refreshToken = JwtUtil.generateRefreshToken(user.getEmail());

				// Return user details with JWT token
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
