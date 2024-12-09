package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import factory.DriverFactory;
import utils.Log;

public class LaunchApplication {

    private WebDriver driver = DriverFactory.getDriver(); // Get WebDriver from DriverFactory

    @Given("I am on the HomePage")
    public void iLaunchTheApplication() {
        // Log the action of launching the browser and navigating to the URL
        Log.info("Browser is launched and navigating to the application URL.");
    }

    @Then("I should see the homepage loaded with the title {string}")
    public void thePageTitleShouldBe(String expectedTitle) {
        // Get the actual title of the page
        String actualTitle = driver.getTitle();

        // Log both titles for better debugging
        Log.info("Expected title: " + expectedTitle);
        Log.info("Actual title: " + actualTitle);

        // Verify the page title
        Assert.assertEquals("Page title verification failed!", expectedTitle, actualTitle);

        // Log success if titles match
        Log.info("Page title verified successfully. Actual title: " + actualTitle);
    }
}