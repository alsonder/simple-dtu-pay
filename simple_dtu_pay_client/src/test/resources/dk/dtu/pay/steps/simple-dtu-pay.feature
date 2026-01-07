Feature: Simple DTU Pay

  Scenario: Successful Payment
    Given a customer with name "Susan"
    And the customer is registered with Simple DTU Pay
    Given a merchant with name "Daniel"
    And the merchant is registered with Simple DTU Pay
    When the merchant initiates a payment for 10 kr by the customer
    Then the payment is successful

  Scenario: Customer is not known
    Given a merchant with name "Daniel" who is registered with Simple DTU Pay
    When the merchant initiates a payment for 10 kr using customer id "non-existent-id"
    Then the payment is not successful

  Scenario: Merchant is not known
    Given a customer with name "Susan" who is registered with Simple DTU Pay
    When the merchant initiates a payment for 10 kr using merchant id "non-existent-id" by the customer
    Then the payment is not successful
