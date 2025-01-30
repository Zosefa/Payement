package com.example.apigateway.Controller;

import com.example.apigateway.client.AirtelClient;
import com.example.apigateway.client.MvolaClient;
import com.example.apigateway.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payement")
public class PayementController {
    @Autowired
    private MvolaClient mvolaClient;

    @Autowired
    private AirtelClient airtelClient;

    @PostMapping
    public Mono<ResponseEntity<Object>> payement(@RequestBody RequestEnvoye request) {
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
            RequestTransactionMvola transactionMvola = new RequestTransactionMvola();
            transactionMvola.setAmount(request.getAmount());
            transactionMvola.setCredit(request.getCredit());
            transactionMvola.setDebit(request.getDebit());
            transactionMvola.setDescription(request.getDescription());

            return airtelClient.transaction(transactionMvola)
                    .map(response -> ResponseEntity.ok(response))
                    .defaultIfEmpty(ResponseEntity.badRequest().build());
        }

        return Mono.just(ResponseEntity.badRequest().build());
    }

    @GetMapping("/status/{ID}")
    public Mono<ResponseEntity<ResponseStatusMvola>> status(@PathVariable String ID){
        return mvolaClient.getStatus(ID)
                .map(response -> ResponseEntity.ok(response))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{ID}")
    public Mono<ResponseEntity<ResponseGetMvola>> about(@PathVariable String ID){
        return mvolaClient.getAbout(ID)
                .map(response -> ResponseEntity.ok(response))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
