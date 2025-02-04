package com.example.apigateway.Controller;

import com.example.apigateway.client.AirtelClient;
import com.example.apigateway.client.MvolaClient;
import com.example.apigateway.model.*;
import com.example.apigateway.service.IsoMessage;
import com.example.apigateway.service.Reponse;
import org.jpos.iso.ISOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payement")
public class PayementController {
    @Autowired
    private MvolaClient mvolaClient;

    @Autowired
    private AirtelClient airtelClient;

    @Autowired
    private IsoMessage isoMessage;

    @PostMapping
    public Mono<ResponseEntity<Object>> payement(@RequestBody Map<String, Object> request) throws ISOException {
        String transaction = "transaction";
        Reponse reponse = new Reponse();
        Object objet = reponse;
        reponse.setMessage("Votre transaction a éte éfféctué!");
        if (request == null || request.isEmpty()) {
            return Mono.just(ResponseEntity.badRequest().body("Les données ISO sont requises"));
        }
//
        String isoMessageHex = isoMessage.generateISOMessage(request);
//
        Map<String, Object> parsedData = isoMessage.parseISOMessage(isoMessageHex);


        if ("Mvola".equals(parsedData.get("field n62"))) {
            RequestTransactionMvola transactionMvola = new RequestTransactionMvola();
            transactionMvola.setAmount(String.valueOf(parsedData.get("field n4")));
            transactionMvola.setCredit(String.valueOf(parsedData.get("field n2")));
            transactionMvola.setDebit(String.valueOf(parsedData.get("field n61")));
            transactionMvola.setDescription(transaction);

            return mvolaClient.transaction(transactionMvola)
                    .map(response -> ResponseEntity.ok(response))
                    .defaultIfEmpty(ResponseEntity.badRequest().build());
        }

        if ("Airtel".equals(parsedData.get("field n62"))) {
            RequestTransactionMvola transactionMvola = new RequestTransactionMvola();
            transactionMvola.setAmount(String.valueOf(parsedData.get("field n4")));
            transactionMvola.setCredit(String.valueOf(parsedData.get("field n2")));
            transactionMvola.setDebit(String.valueOf(parsedData.get("field n61")));
            transactionMvola.setDescription(transaction);


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
