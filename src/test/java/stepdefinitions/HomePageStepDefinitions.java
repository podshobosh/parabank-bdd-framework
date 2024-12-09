package stepdefinitions;

import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import factory.DriverFactory;
import pages.HomePage;
import utils.Log;

public class HomePageStepDefinitions {

    private WebDriver driver;
    private HomePage HomePage;

    // Constructor to initialize the WebDriver and HomePage Object
    public HomePageStepDefinitions() {
        this.driver = DriverFactory.getDriver();
        this.HomePage = new HomePage(driver);
    }

    // Step for verifying the search button is present
    @Then("the search button should be present")
    public void theSearchButtonShouldBePresent() {
        HomePage.verifyTopSearchIconVisible();
        Log.info("Top search icon is verified to be visible.");
    }

    // Step to click the search button
    @When("I click on the search button")
    public void iClickOnTheSearchButton() {
        HomePage.clickTopSearchIcon();
        Log.info("Search button is clicked.");
    }

    @Then("the search box field should expand")
    public void theSearchBoxFieldShouldExpand() {
        HomePage.verifyTopSearchBoxIsVisible();
        Log.info("Search box expanded and verified.");
    }

    @When("I click on the x button the search box field should collapse")
    public void theSearchBoxFieldShouldCollapse() {
        HomePage.verifyTopSearchBoxIsClosed();
    }
}