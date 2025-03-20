package com.guard.controller;

import com.guard.dto.LoginRequestDTO;
import com.guard.service.LoginService;
import com.guard.dto.ClientAuthRequest;

import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.guard.service.ClientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final LoginService loginService;

	private final ClientService clientService;
	@Value("${JWT_KEY}")
	private String jwtKey;

	@PostMapping("/authorize")
	public ResponseEntity<Map<String, String>> authorize(@RequestBody ClientAuthRequest request) {
		System.out.println("request = " + request);

		boolean isValid = clientService.validateClient(request.getClientId(), request.getClientSecret(),
			request.getRedirectUri());

		if (!isValid) {
			throw new NotFoundException("클라이언트를 찾을 수 없음.");
		}

		String redirectUrl = "http://localhost:3000/login?client_id=" + request.getClientId()
			+ "&redirect_uri=" + request.getRedirectUri();

		Map<String, String> response = new HashMap<>();
		response.put("redirectUrl", redirectUrl);

		return ResponseEntity.ok(response);
	}

	// 로그인 API
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO request) throws IOException {
		String authCode = loginService.login(request);
		String redirectUrl = request.getRedirectUri() + "?client_id=" + request.getClientId() + "&code=" + authCode;

		Map<String, String> response = new HashMap<>();
		response.put("redirectUrl", redirectUrl);

		return ResponseEntity.ok(response);
	}

}
