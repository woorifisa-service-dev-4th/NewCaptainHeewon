package com.guard.service;

import com.guard.dto.AuthResponseDTO;
import com.guard.dto.LoginRequestDTO;
import com.guard.model.User;
import com.guard.repository.AuthCodeRepository;
import com.guard.repository.UserRepository;
import com.guard.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.UUID;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final AuthenticationManager authenticationManager;

	private final UserRepository userRepository;

	private final AuthCodeRepository authCodeRepository;

	public String login(LoginRequestDTO request) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
		);
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();

		User user = userRepository.findByUsername(userDetails.getUsername())
			.orElseThrow(() -> new RuntimeException("User not found"));

		String authorizationCode = UUID.randomUUID().toString();

		 authCodeRepository.saveAuthCode(authorizationCode,user.getUsername());

		 return authorizationCode;
	}
}
