package com.example.mvola.Response;

import java.util.List;

public record ResponseGet(
        String amount,
        String currency,
        String requestDate,
        List debitParty,
        List creditParty,
        List fees,
        List metadata,
        String transactionStatus,
        String creationDate,
        String transactionReference) {
}
