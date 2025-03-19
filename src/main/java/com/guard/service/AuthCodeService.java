package com.guard.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthCodeService {
    private final ConcurrentHashMap<String, String> authCodeStore = new ConcurrentHashMap<>();

    public void saveCode(String username, String code) {
        authCodeStore.put(username, code);
    }

    public boolean validateCode(String code) {
        return authCodeStore.containsValue(code);
    }
}