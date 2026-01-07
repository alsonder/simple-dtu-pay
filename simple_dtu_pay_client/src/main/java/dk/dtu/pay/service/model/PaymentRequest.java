package dk.dtu.pay.service.model;

public record PaymentRequest(int amount, String customerId, String merchantId) {}