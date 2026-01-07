package dk.dtu.steps;

import dk.dtu.service.HelloService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class HelloServiceSteps {

    private String result;
    private final HelloService service = new HelloService();

    @When("I call the hello service")
    public void iCallTheHelloService() {
        result = service.hello();
    }

    @Then("I get the answer {string}")
    public void iGetTheAnswer(String expected) {
        assertEquals(expected, result);
    }
}
