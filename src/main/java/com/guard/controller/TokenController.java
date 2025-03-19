package com.guard.controller;

import com.guard.service.AuthCodeService;
import com.guard.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/auth")
public class TokenController {


	private final JwtUtil jwtUtil;
	private final AuthCodeService authCodeService;

	// 생성자 주입으로 JwtUtil을 주입받음
	public TokenController(JwtUtil jwtUtil,AuthCodeService authCodeService) {
		this.jwtUtil = jwtUtil;
		this.authCodeService = authCodeService;
	}

	@PostMapping("/token")
	public ResponseEntity<String> getToken(@RequestParam String code) {
		// Authorization Code 검증
		if (!authCodeService.validateCode(code)) {
			return ResponseEntity.status(400).body("Invalid authorization code");
		}
		// JwtUtil을 사용하여 Access Token 생성
		String accessToken = jwtUtil.generateToken("user", List.of("ROLE_USER"));

		return ResponseEntity.ok("Access Token: " + accessToken);
	}
}
