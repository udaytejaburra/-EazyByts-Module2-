package com.app.stocktrading.service;

import com.app.stocktrading.entity.User;
import com.app.stocktrading.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public User registerUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public User loginUser(String username, String password) {
		return userRepository.findByUsername(username)
				.filter(user -> passwordEncoder.matches(password, user.getPassword())).orElse(null);
	}
}
