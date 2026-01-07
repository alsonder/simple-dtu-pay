package dk.dtu.steps;

import dk.dtu.exception.PersonServiceException;
import dk.dtu.model.Person;
import dk.dtu.service.PersonService;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class PersonServiceSteps {

    private final PersonService service = new PersonService();
    private Person responsePerson;
    private PersonServiceException caughtException;

    @When("I GET the person resource")
    public void iGetThePersonResource() {
        try {
            responsePerson = service.getPerson();
            caughtException = null;
        } catch (PersonServiceException e) {
            caughtException = e;
            responsePerson = null;
        }
    }

    @When("I PUT a new person with name {string} and address {string}")
    public void iPutNewPerson(String name, String address) {
        try {
            Person person = new Person(name, address);
            service.updatePerson(person);
            caughtException = null;
        } catch (PersonServiceException e) {
            caughtException = e;
        }
    }

    @Then("the response status is {int}")
    public void theResponseStatusIs(int expectedStatus) {
        if (caughtException != null) {
            String message = caughtException.getMessage();
            assertTrue("Exception message should contain status " + expectedStatus,
                       message.contains("HTTP " + expectedStatus));
        } else {
            fail("Expected exception with status " + expectedStatus + ", but no exception was thrown");
        }
    }

    @And("the response contains a person with name {string} and address {string}")
    public void theResponseContainsPerson(String expectedName, String expectedAddress) {
        assertNotNull("Response person should not be null", responsePerson);
        assertEquals(expectedName, responsePerson.getName());
        assertEquals(expectedAddress, responsePerson.getAddress());
        assertNull("No exception should be thrown", caughtException);
    }

    @And("an error is returned with message containing {string}")
    public void anErrorIsReturnedWithMessageContaining(String expectedSubstring) {
        assertNotNull("Exception should be thrown", caughtException);
        assertTrue("Error message should contain: " + expectedSubstring,
                   caughtException.getMessage().contains(expectedSubstring));
    }

    @After
    public void cleanup() {
        service.close();
    }
}
