package com.guard.controller;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import io.jsonwebtoken.Jwts;


import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/auth")
public class TokenController {

	private final ConcurrentHashMap<String, String> authCodeStore = new ConcurrentHashMap<>();

	private static final String SECRET_KEY = "my-secret-key-my-secret-key-my-secret-key"; // 충분한 길이의 비밀키 권장

	@PostMapping("/token")
	public ResponseEntity<String> getToken(@RequestParam String code) {
		// Authorization Code 검증
		if (!authCodeStore.containsValue(code)) {
			return ResponseEntity.status(400).body("Invalid authorization code");
		}

		// Access Token 생성 (JWT)

		String accessToken = Jwts.builder()
				.setSubject("user")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1시간 유효
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
				.compact();

		return ResponseEntity.ok("Access Token: " + accessToken);
	}
}

