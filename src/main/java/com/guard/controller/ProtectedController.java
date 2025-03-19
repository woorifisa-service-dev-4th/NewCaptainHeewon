package com.guard.controller;

import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import io.jsonwebtoken.*;

import java.util.Date;

@RestController
@RequestMapping("/api/protected")
public class ProtectedController {

	private static final String SECRET_KEY = "여기에 아무거나 값 넣기";

	@GetMapping("/data")
	public ResponseEntity<String> getProtectedData(@RequestHeader("Authorization") String token) {
		try {
			// Bearer 제거 후 토큰 파싱된다
			token = token.replace("Bearer ", "");

			// JWT 검증 절차하고
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);

			// JWT 토큰 검증 후 데이터 반환한다
			return ResponseEntity.ok("Protected Data Accessed!");
		} catch (JwtException e) {
			return ResponseEntity.status(401).body("Invalid token");
		}
	}
}

