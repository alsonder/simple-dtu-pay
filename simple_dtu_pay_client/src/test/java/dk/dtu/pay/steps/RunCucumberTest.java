package dk.dtu.pay.steps;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = "dk.dtu.pay.steps"
)
public class RunCucumberTest {
    // Tom klasse – den starter bare Cucumber
}