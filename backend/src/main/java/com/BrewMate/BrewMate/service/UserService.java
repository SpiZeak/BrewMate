package com.BrewMate.BrewMate.service;

import com.BrewMate.BrewMate.model.User;
import com.BrewMate.BrewMate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User saveUser(User user) {
		// Ensure unique userId by regenerating if needed
		while (userRepository.findByUserId(user.getUserID()).isPresent()) {
			user.setUserID(User.generateUniqueUserID());
		}

		return userRepository.save(user);
	}
}
