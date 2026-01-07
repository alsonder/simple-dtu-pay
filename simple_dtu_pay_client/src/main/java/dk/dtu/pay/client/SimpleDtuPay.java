package dk.dtu.pay.client;

import dk.dtu.pay.service.model.*;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;

public class SimpleDtuPay {

    private final Client client = ClientBuilder.newClient()
            .register(ResteasyJackson2Provider.class);
    private final WebTarget base = client.target("http://localhost:8080");

    public String registerCustomer(Customer customer) {
        Response response = base.path("customers")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(customer, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 201) {
            return response.readEntity(Customer.class).id();
        } else {
            // Read as String to see the error message if registration fails
            String errorMsg = response.readEntity(String.class);
            throw new RuntimeException("Customer registration failed: " + errorMsg);
        }
    }

    public String registerMerchant(Merchant merchant) {
        Response response = base.path("merchants")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(merchant, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 201) {
            return response.readEntity(Merchant.class).id();
        } else {
            String errorMsg = response.readEntity(String.class);
            throw new RuntimeException("Merchant registration failed: " + errorMsg);
        }
    }

    public boolean pay(int amount, String customerId, String merchantId) {
        PaymentRequest request = new PaymentRequest(amount, customerId, merchantId);
        Response response = base.path("payments")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(request, MediaType.APPLICATION_JSON));

        // Return true only if the payment was actually created (201)
        return response.getStatus() == 201;
    }

    public void unregisterCustomer(String customerId) {
        if (customerId != null) {
            base.path("customers/" + customerId)
                    .request()
                    .delete();
        }
    }

    public void unregisterMerchant(String merchantId) {
        if (merchantId != null) {
            base.path("merchants/" + merchantId)
                    .request()
                    .delete();
        }
    }
}