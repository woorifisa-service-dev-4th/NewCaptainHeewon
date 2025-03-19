package com.guard.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginService {

    // 사용자 인증 메소드
    public String authenticate(String username, String password) {
        // 사용자 인증 로직 (임시로 인증 성공 가정)
        if ("admin".equals(username) && "password".equals(password)) {
            // 인증 성공 시 Authorization Code 생성
            return generateAuthorizationCode();
        }

        // 인증 실패 시 null 반환
        return null;
    }

    // Authorization Code 생성
    private String generateAuthorizationCode() {
        // 임시로 UUID를 사용하여 Authorization Code 생성
        return UUID.randomUUID().toString();
    }
}
