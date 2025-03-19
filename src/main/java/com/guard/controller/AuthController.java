package com.guard.controller;

import com.guard.dto.LoginRequestDTO;
import com.guard.service.LoginService;
import com.guard.dto.ClientAuthRequest;

import org.hibernate.annotations.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.webjars.NotFoundException;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.guard.service.ClientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final LoginService loginService;

	private final ClientService clientService;

	@PostMapping("/authorize")
	public void authorize(@RequestBody ClientAuthRequest request, HttpServletResponse response) throws IOException {

		boolean isValid = clientService.validateClient(request.getClientId(), request.getClientSecret(),
			request.getRedirectUri());

		if (!isValid) {
			new NotFoundException("클라이언트를 찾을 수 없음.");
		}

		String redirectUrl = "http://localhost:3000/login?client_id=" + request.getClientId();
		response.sendRedirect(redirectUrl);
	}

	// 로그인 API
	@PostMapping("/login")
	public void login(@RequestBody LoginRequestDTO request, HttpServletResponse response) throws IOException {
		String authCode = loginService.login(request);
		String redirectUrl = request.getRedirectUri() + "?client_id=" + request.getClientId() + "&code=" + authCode;
		response.sendRedirect(redirectUrl);
	}
}
