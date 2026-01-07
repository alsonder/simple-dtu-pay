package dk.dtu.pay.service.model;

import java.util.UUID;

public record Customer(String id, String name) {
    public Customer(String name) {
        this(UUID.randomUUID().toString(), name);
    }
}