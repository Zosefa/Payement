package com.example.apigateway.client;

import com.example.apigateway.model.RequestTransactionMvola;
import jakarta.ws.rs.core.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AirtelClient {
    private final WebClient webClient;


    public AirtelClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://airtel-service").build();
    }

    public Mono<Object> transaction(RequestTransactionMvola request) {
        return webClient.post()
                .uri("/airtel/transaction")
                .contentType(org.springframework.http.MediaType.valueOf(MediaType.APPLICATION_JSON))
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Object.class);
    }
}
