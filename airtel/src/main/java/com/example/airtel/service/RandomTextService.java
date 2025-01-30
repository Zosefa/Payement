package com.example.airtel.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class RandomTextService {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generateRandomText() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 19; i++) {
            if (i > 0 && i % 4 == 0) {
                sb.append('-');
            } else {
                sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
            }
        }
        return sb.toString();
    }
}
