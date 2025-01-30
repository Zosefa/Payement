package com.example.apigateway.model;

import java.util.List;

public record ResponseGetMvola(
        String amount,
        String currency,
        String requestDate,
        List debitParty,
        List creditParty,
        List fees,
        List metadata,
        String transactionStatus,
        String creationDate,
        String transactionReference
) {
}
