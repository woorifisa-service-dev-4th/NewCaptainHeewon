package com.guard.controller;

import com.guard.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private LoginService loginService;

	// Authorization Code 저장 (임시 저장소)
	private final ConcurrentHashMap<String, String> authCodeStore = new ConcurrentHashMap<>();

	// 로그인 API
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
		try {
			// LoginService 사용하여 인증 처리
			String authorizationCode = loginService.authenticate(username, password);

			if (authorizationCode != null) {
				// Authorization Code 저장 (임시 저장소)
				authCodeStore.put(username, authorizationCode);

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
