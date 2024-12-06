package hooks;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.Log;  // Assuming you have a Log class for custom logging

public class Hooks {

    @Before
    public void beforeScenario(Scenario scenario) {
        // Get the tags associated with the scenario
        boolean hasSmokeTag = scenario.getSourceTagNames().contains("smoke");
        boolean hasRegressionTag = scenario.getSourceTagNames().contains("regression");

        // Log the scenario name and tags for visibility
        Log.info("Scenario: " + scenario.getName() + " | Tags: " + scenario.getSourceTagNames());

        // Check if both tags are present
        if (hasSmokeTag && hasRegressionTag) {
            // If both tags are present, generate separate logs for smoke and regression
            System.setProperty("log.tag", "smoke");
            System.setProperty("log.regression.tag", "regression");
            Log.info("Both @smoke and @regression tags found. Generating separate log files.");
        } else if (hasSmokeTag) {
            // If only the smoke tag is present
            System.setProperty("log.tag", "smoke");
            System.setProperty("log.regression.tag", "");  // Clear regression tag
            Log.info("Smoke tag found. Generating smoke log.");
        } else if (hasRegressionTag) {
            // If only the regression tag is present
            System.setProperty("log.tag", "");  // Clear smoke tag
            System.setProperty("log.regression.tag", "regression");
            Log.info("Regression tag found. Generating regression log.");
        } else {
            // For other tags, use a generic log file name
            System.setProperty("log.tag", "Test Logs");
            System.setProperty("log.regression.tag", "");  // Clear regression tag
            Log.info("No specific tag found. Using default log file name.");
        }
    }
}
