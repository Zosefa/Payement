package com.example.airtel.model;

import java.util.List;

public class RequestBodyAirtel {
    private String reference;
    private List subscriber;
    private List transaction;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(List subscriber) {
        this.subscriber = subscriber;
    }

    public List getTransaction() {
        return transaction;
    }

    public void setTransaction(List transaction) {
        this.transaction = transaction;
    }
}
