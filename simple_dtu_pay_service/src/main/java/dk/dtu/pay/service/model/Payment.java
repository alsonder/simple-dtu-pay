package dk.dtu.pay.service.model;

import java.util.UUID;

public record Payment(String id, int amount, String customerId, String merchantId) {
    public Payment(int amount, String customerId, String merchantId) {
        this(UUID.randomUUID().toString(), amount, customerId, merchantId);
    }
}