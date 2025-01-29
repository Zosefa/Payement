package com.example.mvola.Client;

import com.example.mvola.Response.ResponseStatus;
import com.example.mvola.Response.ResponseToken;
import com.example.mvola.Response.ResponseTransaction;
import com.example.mvola.model.CorrelationId;
import com.example.mvola.model.RequestTransaction;
import com.example.mvola.model.Token;
import com.example.mvola.model.Transaction;
import jakarta.ws.rs.core.MediaType;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.stream.Collector;

@Component
public class MvolaClient {
    private final WebClient webClient;

    public MvolaClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://devapi.mvola.mg").build();
    }

    public ResponseToken getToken(Token token) {
        return webClient.post()
                .uri("/token")
                .header(HttpHeaders.AUTHORIZATION, "Basic WnhiSFZGRmhXNUZranpTejg3Z256R09Qc1NRYTpSY2tISEVsNVpmcWl1UVVXWkdRdlpFYkV3VXdh")
                .contentType(org.springframework.http.MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED))
                .bodyValue("grant_type=" + token.getGrantType() + "&scope=" + token.getScope())
                .retrieve()
                .bodyToMono(ResponseToken.class)
                .block();
    }

    public ResponseTransaction transaction(Transaction transaction, String response, String CorrelationID ,String numero) {
        return webClient.post()
                .uri("/mvola/mm/transactions/type/merchantpay/1.0.0")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+response)
                .header("Version", "1.0")
                .header("X-CorrelationID", CorrelationID)
                .header("UserLanguage", "FR")
                .header("UserAccountIdentifier", "msisdn;"+numero)
                .header("partnerName", "EQIMA")
                .contentType(org.springframework.http.MediaType.valueOf(MediaType.APPLICATION_JSON))
                .bodyValue(transaction)
                .retrieve()
                .bodyToMono(ResponseTransaction.class)
                .block();
    }

    public ResponseStatus getStatus(ResponseTransaction responseTransaction, String correlationId, String response, RequestTransaction REQUEST) {
        return webClient.get()
                .uri("/mvola/mm/transactions/type/merchantpay/1.0.0/status/"+responseTransaction.serverCorrelationId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+response)
                .header("Version", "1.0")
                .header("X-CorrelationID", correlationId)
                .header("UserLanguage", "FR")
                .header("UserAccountIdentifier", "msisdn;"+REQUEST.getCredit())
                .header("partnerName", "EQIMA")
                .retrieve()
                .bodyToMono(ResponseStatus.class)
                .block();

    }

}
