package com.guard.controller;

import com.guard.service.LoginService;
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

	private LoginService loginService;

	// Authorization Code 저장 (임시 저장소)
	private final ClientService clientService;

	@PostMapping("/authorize")
	public ResponseEntity<?> authorize(@RequestBody ClientAuthRequest request, HttpServletResponse response) {
		try {
			// 클라이언트 검증
			boolean isValid = clientService.validateClient(request.getClientId(), request.getClientSecret(),
				request.getRedirectUri());

			if (!isValid) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid client credentials");
			}

			String redirectUrl = "http://localhost:3000/login?client_id=" + request.getClientId();
			response.sendRedirect(redirectUrl);

			return ResponseEntity.ok().build();
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Redirection failed: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
		}
	}

	// 로그인 API
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
		try {
			// LoginService 사용하여 인증 처리
			String authorizationCode = loginService.authenticate(username, password);

			if (authorizationCode != null) {
				// Authorization Code 저장 (임시 저장소)
				authCodeService.saveCode(username, authorizationCode);

				// 성공적으로 인증되었을 경우, Authorization Code 반환
				return ResponseEntity.ok("Authorization Code: " + authorizationCode);
			}

			// 인증 실패 시 401 반환
			return ResponseEntity.status(401).body("Invalid credentials");

		} catch (Exception e) {
			// 예외 처리
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}
}
