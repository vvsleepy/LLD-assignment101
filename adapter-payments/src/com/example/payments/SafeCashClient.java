package com.example.payments;

public class SafeCashClient {
    public SafeCashPayment createPayment(int amount, String user) {
        return new SafeCashPayment(amount, user);
    }
}

class SafeCashAdapter implements PaymentGateway {
    private final SafeCashClient client;

    SafeCashAdapter(SafeCashClient client) {
        this.client = client;
    }

    @Override
    public String charge(String customerId, int amountCents) {
        SafeCashPayment payment = client.createPayment(amountCents, customerId);
        return payment.confirm();
    }
}