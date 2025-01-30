package com.example.apigateway.client;

import com.example.apigateway.model.RequestTransactionMvola;
import com.example.apigateway.model.ResponseTransactionMvola;
import jakarta.ws.rs.core.MediaType;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class MvolaClient {
    private final WebClient webClient;


    public MvolaClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://mvola-service").build();
    }

    public Mono<ResponseTransactionMvola> transaction(RequestTransactionMvola request) {
        return webClient.post()
                .uri("/mvola/transaction")
                .contentType(org.springframework.http.MediaType.valueOf(MediaType.APPLICATION_JSON))
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ResponseTransactionMvola.class);
    }
}
