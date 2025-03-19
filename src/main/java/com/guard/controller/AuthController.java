package com.guard.controller;

import com.guard.dto.ClientAuthRequest;
import com.guard.service.AuthCodeService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.guard.service.ClientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthCodeService authCodeService;

	private final ClientService clientService;

	@PostMapping("/authorize")
	public ResponseEntity<?> authorize(@RequestBody ClientAuthRequest request, HttpServletResponse response) {
		try {
			// 클라이언트 검증
			boolean isValid = clientService.validateClient(
				request.getClientId(), request.getClientSecret(), request.getRedirectUri()
			);

			if (!isValid) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid client credentials");
			}

			String redirectUrl = "http://localhost:3000/login?client_id=" + request.getClientId();
			response.sendRedirect(redirectUrl);

			return ResponseEntity.ok().build();
		}catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Redirection failed: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Unexpected error: " + e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
		// 사용자 인증 로직 (임시로 인증 성공 가정)
		if ("admin".equals(username) && "password".equals(password)) {
			// Authorization Code 생성
			String authorizationCode = UUID.randomUUID().toString();

			// 사용자별 Authorization Code 저장 (일회성)
			authCodeService.saveCode(username, authorizationCode);

			return ResponseEntity.ok("Authorization Code: " + authorizationCode);
		}

		return ResponseEntity.status(401).body("Invalid credentials");
	}
}
