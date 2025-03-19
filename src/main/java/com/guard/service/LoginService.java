package com.guard.service;

import com.guard.model.User;
import com.guard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository; // DB에서 사용자 조회

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // 비밀번호 암호화

    // 사용자 인증 메소드
    public String authenticate(String username, String password) {
        // DB에서 사용자 조회
        User user = userRepository.findByUsername(username);

        // 사용자 존재 여부 확인
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
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