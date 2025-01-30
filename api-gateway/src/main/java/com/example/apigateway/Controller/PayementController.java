package com.example.apigateway.Controller;

import com.example.apigateway.client.MvolaClient;
import com.example.apigateway.model.RequestEnvoye;
import com.example.apigateway.model.RequestTransactionMvola;
import com.example.apigateway.model.ResponseTransactionMvola;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payement")
public class PayementController {
    @Autowired
    private MvolaClient mvolaClient;

    @PostMapping
    public Mono<ResponseEntity<ResponseTransactionMvola>> payement(@RequestBody RequestEnvoye request) {
        if ("Mvola".equals(request.getOperateur())) {
            RequestTransactionMvola transactionMvola = new RequestTransactionMvola();
            transactionMvola.setAmount(request.getAmount());
            transactionMvola.setCredit(request.getCredit());
            transactionMvola.setDebit(request.getDebit());
            transactionMvola.setDescription(request.getDescription());

            return mvolaClient.transaction(transactionMvola)
                    .map(response -> ResponseEntity.ok(response))
                    .defaultIfEmpty(ResponseEntity.badRequest().build());
        }

        if ("Airtel".equals(request.getOperateur())) {
            return Mono.just(ResponseEntity.badRequest().build());
        }

        return Mono.just(ResponseEntity.badRequest().build());
    }


}
