package com.example.mvola.Response;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public record ResponseToken(String access_token, String scope, String token_type, Integer expires_in) {
}
