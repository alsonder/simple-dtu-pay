package dk.dtu.pay.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentRequest(
        @JsonProperty("amount") int amount,
        @JsonProperty("customerId") String customerId,
        @JsonProperty("merchantId") String merchantId
) {}