package com.guard.repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

@Repository
public class AuthCodeRepository {
	private final ConcurrentHashMap<String, String> tokenStore = new ConcurrentHashMap<>();

	public void saveAuthCode(String code, String username) {
		tokenStore.put(code, username);
	}
	public Optional<String> findUsernameByCode(String code) {
		return Optional.ofNullable(tokenStore.get(code));
	}
	public String validateAuthCode(String code) {
		return tokenStore.remove(code); // 검증 후 바로 삭제
	}

	public void saveAccessToken(String accessToken, String username) {
		tokenStore.put(accessToken, username);
	}

	public String validateAccessToken(String accessToken) {
		return tokenStore.get(accessToken);
	}
}
