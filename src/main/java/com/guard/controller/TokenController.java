package com.guard.controller;

import com.guard.dto.AccessTokenRequestDTO;
import com.guard.dto.TokenResponseDTO;
import com.guard.service.TokenService;
import com.guard.utils.JwtUtil;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class TokenController {

	private final TokenService tokenService;
	@PostMapping("/token")
	public ResponseEntity<?> getToken(@RequestBody AccessTokenRequestDTO dto) {
		try {
			return ResponseEntity.ok(tokenService.getToken(dto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("AccessToken 발급 실패");
		}
	}
}
