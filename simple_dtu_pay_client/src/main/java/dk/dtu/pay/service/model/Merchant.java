package dk.dtu.pay.service.model;

import java.util.UUID;

public record Merchant(String id, String name) {
    public Merchant(String name) {
        this(UUID.randomUUID().toString(), name);
    }
}