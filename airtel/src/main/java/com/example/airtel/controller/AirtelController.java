package com.example.airtel.controller;

import com.example.airtel.Response.ResponseToken;
import com.example.airtel.client.AirtelClient;
import com.example.airtel.model.RequestBodyAirtel;
import com.example.airtel.model.TokenBody;
import com.example.airtel.service.RandomTextService;
import com.example.airtel.service.Subsriber;
import com.example.airtel.service.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/airtel")
public class AirtelController {
    @Autowired
    private AirtelClient airtelClient;

    @Autowired
    private RandomTextService randomTextService;

    @PostMapping("/transaction")
    public ResponseEntity<Object> transaction(@RequestBody com.example.airtel.model.RequestBody request){
        TokenBody token = new TokenBody();
        ResponseToken responseToken = airtelClient.getToken(token);

        Subsriber subscriber = new Subsriber();

        subscriber.setMsisdn(request.getCredit());

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setId(randomTextService.generateRandomText());

        RequestBodyAirtel bodyAirtel = new RequestBodyAirtel();
        bodyAirtel.setReference(request.getDescription());
        bodyAirtel.setSubscriber(List.of(subscriber));
        bodyAirtel.setTransaction(List.of(transaction));
//
        Object reponse = airtelClient.transaction(bodyAirtel,responseToken.access_token());
        return ResponseEntity.ok(reponse);
    }

}
