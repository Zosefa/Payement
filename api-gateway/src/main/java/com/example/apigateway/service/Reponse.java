package com.example.apigateway.service;

public class Reponse {
    private String status = "réussit";
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String variable) {
        this.status = variable;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
