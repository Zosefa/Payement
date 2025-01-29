package com.example.mvola.model;

public class Test {

    private Transaction trasaction;
    private String token;
    private String CorrelationId;
    private String creadit;

    public Transaction getTrasaction() {
        return trasaction;
    }

    public void setTrasaction(Transaction trasaction) {
        this.trasaction = trasaction;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCorrelationId() {
        return CorrelationId;
    }

    public void setCorrelationId(String correlationId) {
        CorrelationId = correlationId;
    }

    public String getCreadit() {
        return creadit;
    }

    public void setCreadit(String creadit) {
        this.creadit = creadit;
    }
}
