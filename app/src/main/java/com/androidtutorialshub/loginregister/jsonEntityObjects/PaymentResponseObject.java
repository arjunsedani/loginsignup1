package com.androidtutorialshub.loginregister.jsonEntityObjects;

public class PaymentResponseObject {

    private client client;

    private response response;

    private String response_type;

    public PaymentResponseObject(client client, response response, String response_type) {
        this.client = client;
        this.response = response;
        this.response_type = response_type;
    }

    public client getClient() {
        return client;
    }

    public response getResponse() {
        return response;
    }

    public String getResponse_type() {
        return response_type;
    }
}
