package com.guard.service;

import java.util.Optional;

import org.hibernate.annotations.NotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.guard.dto.AccessTokenRequestDTO;
import com.guard.dto.AuthResponseDTO;
import com.guard.dto.TokenResponseDTO;
import com.guard.repository.AuthCodeRepository;
import com.guard.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
	private final JwtUtil jwtUtil;
	private final ClientService clientService;
	private final AuthCodeRepository authCodeRepository;

	public TokenResponseDTO getToken(AccessTokenRequestDTO dto) {
		boolean isValid = clientService.validateClient(dto.getClientId(), dto.getClientSecret(), dto.getRedirectUri());
		if (!isValid)
			throw new NotFoundException("client를 찾을 수 없음.");

		String code = dto.getCode();

		String userName = authCodeRepository.findUsernameByCode(code).orElseThrow(
			() -> new NotFoundException("authorization code 올바르지 않음")
		);

		// 인증 코드 검증 후 삭제
		authCodeRepository.validateAuthCode(code);

		// Access Token 발급
		String accessToken = jwtUtil.generateToken(userName);

		return new TokenResponseDTO(accessToken);
	}
}
