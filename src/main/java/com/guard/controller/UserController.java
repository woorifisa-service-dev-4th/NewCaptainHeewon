package com.guard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.guard.model.User;
import com.guard.repository.UserRepository;
import com.guard.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;

	@GetMapping("/me")
	public ResponseEntity<User> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
		String username = jwt.getSubject();

		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

		return ResponseEntity.ok(user);
	}

}
