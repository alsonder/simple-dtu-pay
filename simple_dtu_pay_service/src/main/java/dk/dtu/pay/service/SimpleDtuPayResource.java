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
        Customer customer = new Customer(customerWithoutId.name());
        customers.put(customer.id(), customer);
        return Response.status(201).entity(customer).build();
    }

    @POST
    @Path("merchants")
    public Response registerMerchant(Merchant merchantWithoutId) {
        Merchant merchant = new Merchant(merchantWithoutId.name());
        merchants.put(merchant.id(), merchant);
        return Response.status(201).entity(merchant).build();
    }

    @POST
    @Path("payments")
    public Response pay(PaymentRequest request) {
        if (!customers.containsKey(request.customerId())) {
            return Response.status(404)
                    .entity("customer with id \"" + request.customerId() + "\" is unknown")
                    .build();
        }
        if (!merchants.containsKey(request.merchantId())) {
            return Response.status(404)
                    .entity("merchant with id \"" + request.merchantId() + "\" is unknown")
                    .build();
        }

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
        return customers.remove(id) != null ? Response.noContent().build() : Response.status(404).build();
    }

    @DELETE
    @Path("merchants/{id}")
    public Response unregisterMerchant(@PathParam("id") String id) {
        return merchants.remove(id) != null ? Response.noContent().build() : Response.status(404).build();
    }
}
