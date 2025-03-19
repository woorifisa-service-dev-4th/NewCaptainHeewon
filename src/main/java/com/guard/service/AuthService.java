package com.guard.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {
    private final ConcurrentHashMap<String, String> authCodes = new ConcurrentHashMap<>();
//
//    public void saveCode(String username, String authorizationCode) {
//        // 사용자별 Authorization Code 저장
//        authCodes.put(username, authorizationCode);
//    }
//
//    public String getCode(String username) {
//        // Authorization Code 가져오기
//        return authCodes.get(username);
//    }
}
