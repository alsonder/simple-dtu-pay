Feature: person service

  Scenario: get person returns correct JSON object
    When I GET the person resource
    Then the response status is 200
    And the response contains a person with name "Susan" and address "USA"

  Scenario: update person with PUT and verify with GET
    When I PUT a new person with name "Peter" and address "Denmark"
    Then the response status is 200
    When I GET the person resource
    Then the response status is 200
    And the response contains a person with name "Peter" and address "Denmark"

  Scenario: trying to set address to -none- returns error
    When I PUT a new person with name "Invalid" and address "-none-"
    Then the response status is 400
    And an error is returned with message containing "Address cannot be \"-none-\""
