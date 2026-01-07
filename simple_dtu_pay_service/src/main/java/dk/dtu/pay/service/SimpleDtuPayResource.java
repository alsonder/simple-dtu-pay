package dk.dtu.pay.service;

import dk.dtu.pay.service.model.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimpleDtuPayResource {

    private final Map<String, Customer> customers = new ConcurrentHashMap<>();
    private final Map<String, Merchant> merchants = new ConcurrentHashMap<>();
    private final List<Payment> payments = new CopyOnWriteArrayList<>();

    @POST
    @Path("customers")
    public Response registerCustomer(Customer customerWithoutId) {
        // Create a new Customer record using the name and generating a new UUID
        Customer customer = new Customer(customerWithoutId.name());
        customers.put(customer.id(), customer);
        return Response.status(201).entity(customer).build();
    }

    @POST
    @Path("merchants")
    public Response registerMerchant(Merchant merchantWithoutId) {
        // Create a new Merchant record using the name and generating a new UUID
        Merchant merchant = new Merchant(merchantWithoutId.name());
        merchants.put(merchant.id(), merchant);
        return Response.status(201).entity(merchant).build();
    }

    @POST
    @Path("payments")
    public Response pay(PaymentRequest request) {
        // Check if customer exists - returning JSON error message
        if (!customers.containsKey(request.customerId())) {
            return Response.status(404)
                    .entity(Map.of("message", "customer with id \"" + request.customerId() + "\" is unknown"))
                    .build();
        }

        // Check if merchant exists - returning JSON error message
        if (!merchants.containsKey(request.merchantId())) {
            return Response.status(404)
                    .entity(Map.of("message", "merchant with id \"" + request.merchantId() + "\" is unknown"))
                    .build();
        }

        // Process payment
        Payment payment = new Payment(request.amount(), request.customerId(), request.merchantId());
        payments.add(payment);
        return Response.status(201).entity(payment).build();
    }

    @GET
    @Path("payments")
    public List<Payment> getPayments() {
        return List.copyOf(payments);
    }

    @DELETE
    @Path("customers/{id}")
    public Response unregisterCustomer(@PathParam("id") String id) {
        if (customers.remove(id) != null) {
            return Response.noContent().build();
        }
        return Response.status(404)
                .entity(Map.of("message", "customer not found"))
                .build();
    }

    @DELETE
    @Path("merchants/{id}")
    public Response unregisterMerchant(@PathParam("id") String id) {
        if (merchants.remove(id) != null) {
            return Response.noContent().build();
        }
        return Response.status(404)
                .entity(Map.of("message", "merchant not found"))
                .build();
    }
}