package dk.dtu.pay.steps;

import dk.dtu.pay.client.SimpleDtuPay;
import dk.dtu.pay.service.model.Customer;
import dk.dtu.pay.service.model.Merchant;
import io.cucumber.java.After;
import io.cucumber.java.en.*;
import static org.junit.Assert.*;

public class SimpleDTUPaySteps {

    private final SimpleDtuPay dtupay = new SimpleDtuPay();

    private Customer customer;
    private Merchant merchant;
    private String customerId;
    private String merchantId;
    private boolean paymentSuccessful;

    @Given("a customer with name {string}")
    public void a_customer_with_name(String name) {
        customer = new Customer(name);
    }

    @Given("the customer is registered with Simple DTU Pay")
    public void the_customer_is_registered_with_simple_dtu_pay() {
        customerId = dtupay.registerCustomer(customer);
        assertNotNull(customerId);
    }

    @Given("a merchant with name {string}")
    public void a_merchant_with_name(String name) {
        merchant = new Merchant(name);
    }

    @Given("the merchant is registered with Simple DTU Pay")
    public void the_merchant_is_registered_with_simple_dtu_pay() {
        merchantId = dtupay.registerMerchant(merchant);
        assertNotNull(merchantId);
    }

    @When("the merchant initiates a payment for {int} kr by the customer")
    public void the_merchant_initiates_a_payment_for_kr_by_the_customer(int amount) {
        paymentSuccessful = dtupay.pay(amount, customerId, merchantId);
    }

    @Then("the payment is successful")
    public void the_payment_is_successful() {
        assertTrue(paymentSuccessful);
    }

    @Given("a customer with name {string} who is registered with Simple DTU Pay")
    public void a_customer_with_name_who_is_registered(String name) {
        a_customer_with_name(name);
        the_customer_is_registered_with_simple_dtu_pay();
    }

    @Given("a merchant with name {string} who is registered with Simple DTU Pay")
    public void a_merchant_with_name_who_is_registered(String name) {
        a_merchant_with_name(name);
        the_merchant_is_registered_with_simple_dtu_pay();
    }

    @When("the merchant initiates a payment for {int} kr using customer id {string}")
    public void pay_with_bad_customer(int amount, String badId) {
        paymentSuccessful = dtupay.pay(amount, badId, merchantId);
    }

    @When("the merchant initiates a payment for {int} kr using merchant id {string} by the customer")
    public void pay_with_bad_merchant(int amount, String badId) {
        paymentSuccessful = dtupay.pay(amount, customerId, badId);
    }

    @Then("the payment is not successful")
    public void the_payment_is_not_successful() {
        assertFalse(paymentSuccessful);
    }

    @After
    public void cleanup() {
        dtupay.unregisterCustomer(customerId);
        dtupay.unregisterMerchant(merchantId);
        customerId = null;
        merchantId = null;
    }
}