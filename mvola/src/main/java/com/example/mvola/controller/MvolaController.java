package com.example.mvola.controller;

import com.example.mvola.Client.MvolaClient;
import com.example.mvola.RandomTextService;
import com.example.mvola.Response.ResponseStatus;
import com.example.mvola.Response.ResponseToken;
import com.example.mvola.Response.ResponseTransaction;
import com.example.mvola.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/mvola")
public class MvolaController {
    @Autowired
    private MvolaClient mvolaClient;
    @Autowired
    private RandomTextService random;

    private final Map<String, ResponseTransaction> transactionResponse = new ConcurrentHashMap<>();
    private final Map<String, String> correlatioIdResponse = new ConcurrentHashMap<>();

    CorrelationId correlationId = new CorrelationId();

    RequestTransaction request = new RequestTransaction();

    private static final Logger LOGGER = LoggerFactory.getLogger(MvolaController.class);

    @PostMapping("/transaction")
    public ResponseEntity<Object> transaction(@RequestBody RequestTransaction requestTransaction) {
        Token token = new Token();
        token.setGrantType("client_credentials");
        token.setScope("EXT_INT_MVOLA_SCOPE");

        request = requestTransaction;

        ResponseToken responseToken = mvolaClient.getToken(token);

        Transaction transaction = new Transaction();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String formattedDate = LocalDateTime.now().format(formatter);
        System.out.println(LocalDateTime.parse(formattedDate, formatter));

        transaction.setAmount(requestTransaction.getAmount());
        transaction.setCurrency("Ar");
        transaction.setDescriptionText(String.valueOf(requestTransaction.getDescription()));
        transaction.setRequestingOrganisationTransactionReference("teste");
        transaction.setRequestDate(LocalDateTime.parse(formattedDate, formatter));
        transaction.setOriginalTransactionReference("12345ede6");

        Data debitParty = new Data();
        debitParty.setKey("msisdn");
        debitParty.setValue(String.valueOf(requestTransaction.getDebit()));

        Data creditParty = new Data();
        creditParty.setKey("msisdn");
        creditParty.setValue(String.valueOf(requestTransaction.getCredit()));

        transaction.setDebitParty(List.of(debitParty));
        transaction.setCreditParty(List.of(creditParty));

        Data metadata = new Data();
        metadata.setKey("partnerName");
        metadata.setValue("Orinasa");
        transaction.setMetadata(List.of(metadata));

        correlationId.setCorrelationId(random.generateRandomText());
        ResponseTransaction reponse = mvolaClient.transaction(transaction, responseToken.access_token(),correlationId.getCorrelationId(),creditParty.getValue());
        transactionResponse.put("Reponse",reponse);
        correlatioIdResponse.put("correlation_Id",correlationId.getCorrelationId());

        return ResponseEntity.ok(reponse);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<Object> status(@PathVariable String id) {
        Token token = new Token();
        token.setGrantType("client_credentials");
        token.setScope("EXT_INT_MVOLA_SCOPE");

        ResponseToken responseToken = mvolaClient.getToken(token);

        ResponseTransaction reponse = transactionResponse.get("Reponse");
//        String Id = correlatioIdResponse.get("correlation_Id");

        ResponseStatus response = mvolaClient.getStatus(reponse, id,responseToken.access_token(), request);

        return ResponseEntity.ok(response);
    }


}
