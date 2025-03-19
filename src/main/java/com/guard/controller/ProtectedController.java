package com.guard.controller;

import com.guard.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/protected")
public class ProtectedController {

	private final JwtUtil jwtUtil;

	// 생성자 주입을 통해 JwtUtil 빈을 사용
	public ProtectedController(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@GetMapping("/data")
	public ResponseEntity<String> getProtectedData(@RequestHeader("Authorization") String token) {
		// "Bearer " 접두사 제거
		token = token.replace("Bearer ", "");

		// JwtUtil을 사용하여 토큰 검증 (유효하면 subject 반환, 아니면 null)
		String subject = jwtUtil.validateToken(token);

		if (subject == null) {
			return ResponseEntity.status(401).body("Invalid token");
		}

		return ResponseEntity.ok("Protected Data Accessed! Subject: " + subject);
	}
}
