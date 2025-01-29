package com.example.mvola.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

public class Transaction {
    private String amount;
    private String currency;
    private String descriptionText;
    private String requestingOrganisationTransactionReference;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime requestDate;
    private String originalTransactionReference;
    private List<Data> debitParty;
    private List<Data> creditParty;
    private List<Data> metadata;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getRequestingOrganisationTransactionReference() {
        return requestingOrganisationTransactionReference;
    }

    public void setRequestingOrganisationTransactionReference(String requestingOrganisationTransactionReference) {
        this.requestingOrganisationTransactionReference = requestingOrganisationTransactionReference;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public String getOriginalTransactionReference() {
        return originalTransactionReference;
    }

    public void setOriginalTransactionReference(String originalTransactionReference) {
        this.originalTransactionReference = originalTransactionReference;
    }

    public List<Data> getDebitParty() {
        return debitParty;
    }

    public void setDebitParty(List<Data> debitParty) {
        this.debitParty = debitParty;
    }

    public List<Data> getCreditParty() {
        return creditParty;
    }

    public void setCreditParty(List<Data> creaditParty) {
        this.creditParty = creaditParty;
    }

    public List<Data> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<Data> metadata) {
        this.metadata = metadata;
    }
}
