package com.guard.controller;

import com.guard.service.AuthCodeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthCodeService authCodeService;

	public AuthController(AuthCodeService authCodeService) {
		this.authCodeService = authCodeService;
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
