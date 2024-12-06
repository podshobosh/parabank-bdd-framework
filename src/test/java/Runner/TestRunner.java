package Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = {"stepdefinitions", "hooks"},
        plugin = {                                  // Report and output plugins
                "pretty",                               // Prints Gherkin steps in the console
                "html:target/cucumber-reports.html",    // Generates an HTML report
                "json:target/cucumber.json",            // Generates a JSON report
                "junit:target/cucumber.xml"             // Generates a JUnit-compatible XML report
        },
//        monochrome = false,
        tags = "@regression",
        dryRun = false
)
public class TestRunner {

}