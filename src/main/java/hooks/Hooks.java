package hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Log;

public class Hooks {

    private static final Logger failedLogger = LogManager.getLogger("failedScenarioLogger");

    @Before
    public void beforeScenario(Scenario scenario) {
        // Extract scenario name and tags
        String scenarioName = scenario.getName();
        String tags = String.join(", ", scenario.getSourceTagNames());

        // Log scenario details
        Log.info("====================== STARTING SCENARIO ======================");
        Log.info("Scenario: " + scenarioName);
        Log.info("Tags: " + tags);
        Log.info("===============================================================");
    }

    @After
    public void afterScenario(Scenario scenario) {
        // Log scenario status and end
        Log.info("====================== ENDING SCENARIO ======================");
        Log.info("Scenario Status: " + scenario.getStatus());
        Log.info("=============================================================");

        // Log failed scenarios to the failed logger
        if (scenario.getStatus().toString().equalsIgnoreCase("FAILED")) {
            failedLogger.error("Scenario failed: " + scenario.getName());
        }
    }
}
