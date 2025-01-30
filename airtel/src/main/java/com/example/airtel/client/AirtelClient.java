package com.example.airtel.client;

import com.example.airtel.Response.ResponseToken;
import com.example.airtel.model.RequestBodyAirtel;
import com.example.airtel.model.TokenBody;
import jakarta.ws.rs.core.MediaType;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AirtelClient {
    private final WebClient webClient;

    public AirtelClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://openapiuat.airtel.africa").build();
    }

    public ResponseToken getToken(TokenBody body) {
        return webClient.post()
                .uri("/auth/oauth2/token")
                .header("Accept" ,"*/*")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(ResponseToken.class)
                .block();
    }

    public Object transaction(RequestBodyAirtel body, String token) {
        return webClient.post()
                .uri("/merchant/v1/payments/")
                .contentType(org.springframework.http.MediaType.valueOf(MediaType.APPLICATION_JSON))
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+token)
                .header("X-Country","MG")
                .header("X-Currency","MGA")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
}
