package dk.dtu.pay.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record Payment(
        @JsonProperty("id") String id,
        @JsonProperty("amount") int amount,
        @JsonProperty("customerId") String customerId,
        @JsonProperty("merchantId") String merchantId
) {
    public Payment(int amount, String customerId, String merchantId) {
        this(UUID.randomUUID().toString(), amount, customerId, merchantId);
    }
}