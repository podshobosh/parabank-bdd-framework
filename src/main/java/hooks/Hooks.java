package hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import factory.DriverFactory;
import utils.Log;
import utils.ScreenshotUtils;
import utils.ExtentReportManager;

public class Hooks {

    private static final Logger failedLogger = LogManager.getLogger("failedScenarioLogger");
    private WebDriver driver;

    @Before
    public void beforeScenario(Scenario scenario) {
        String scenarioName = scenario.getName();
        String tags = String.join(", ", scenario.getSourceTagNames());

        Log.info("====================== STARTING SCENARIO ======================");
        Log.info("Scenario: " + scenarioName);
        Log.info("Tags: " + tags);
        Log.info("===============================================================");

        ExtentReportManager.createTest(scenarioName);
    }

    @After
    public void afterScenario(Scenario scenario) {
        driver = DriverFactory.getDriver();

        if (scenario.isFailed()) {
            Log.error("Scenario failed: " + scenario.getName());

            // Capture Base64 screenshot and attach to Extent Report
            String base64Screenshot = ScreenshotUtils.takeScreenshotAsBase64(driver);
            ExtentReportManager.getTest()
                    .fail("Scenario failed: " + scenario.getName(),
                            com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());

            failedLogger.error("Screenshot captured for failed scenario: " + scenario.getName());
        } else {
            Log.info("Scenario passed: " + scenario.getName());
            ExtentReportManager.getTest().pass("Scenario passed: " + scenario.getName());
        }

        Log.info("====================== ENDING SCENARIO ======================");
        Log.info("Scenario Status: " + scenario.getStatus());
        Log.info("=============================================================");

        ExtentReportManager.flush();
    }
}