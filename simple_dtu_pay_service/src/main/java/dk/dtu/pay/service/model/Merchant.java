package dk.dtu.pay.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record Merchant(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name
) {
    public Merchant(String name) {
        this(UUID.randomUUID().toString(), name);
    }
}