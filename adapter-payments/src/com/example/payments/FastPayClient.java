package com.example.payments;

public class FastPayClient {
    public String payNow(String custId, int amountCents) {
        return "FP#"+ custId + ":" + amountCents;
    }
}

class FastPayAdapter implements PaymentGateway {
    private final FastPayClient client;

    FastPayAdapter(FastPayClient client) {
        this.client = client;
    }

    @Override
    public String charge(String customerId, int amountCents) {
        return client.payNow(customerId, amountCents);
    }
}