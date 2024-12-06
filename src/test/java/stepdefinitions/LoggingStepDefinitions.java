package stepdefinitions;

import io.cucumber.java.en.Given;
import utils.Log;

public class LoggingStepDefinitions {

    // Create a logger instance for logging

    @Given("I log various messages")
    public void logMessages() {
        // INFO log
        Log.info("This is an INFO level log message.");

        // DEBUG log
        Log.debug("This is a DEBUG level log message.");

        // WARN log
        Log.warn("This is a WARN level log message.");

        // ERROR log
        Log.error("This is an ERROR level log message.");

    }
}