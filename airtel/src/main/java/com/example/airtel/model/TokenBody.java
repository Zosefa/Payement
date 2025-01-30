package com.example.airtel.model;

public class TokenBody {
    private String client_id = "21eb207f-534d-4e80-939b-43d2515a9f7f";

    private String client_secret = "20f4c576-7d11-4474-b30f-c63b371bfb85";

    private String grant_type = "client_credentials";

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
