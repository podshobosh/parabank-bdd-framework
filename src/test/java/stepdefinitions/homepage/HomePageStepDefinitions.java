package stepdefinitions.homepage;

import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import factory.DriverFactory;
import pages.HomePage;
import utils.CommonUtils;
import utils.Log;

public class HomePageStepDefinitions {

    private WebDriver driver;
    private HomePage homePage;

    // Constructor to initialize the WebDriver and HomePage Object
    public HomePageStepDefinitions() {
        this.driver = DriverFactory.getDriver();
        this.homePage = new HomePage(driver);
    }

    // Step for verifying the HomePage title
    @Then("The page should have the title {string}")
    public void thePageShouldHaveTheTitle(String expectedTitle) {
        CommonUtils.verifyTitle(driver, expectedTitle);
        Log.info("HomePage title verification completed");
    }

    // Step for verifying the HomePage URL
    @Then("The page URL should be {string}")
    public void thePageURLShouldBe(String expectedUrl) {
        CommonUtils.verifyUrl(driver, expectedUrl);
        Log.info("HomePage URL verification completed");
    }

    // Step for verifying the search button is present
    @Then("the top search button should be present")
    public void theTopSearchButtonShouldBePresent() {
        homePage.verifyTopSearchIconVisible();
        Log.info("Top search icon is verified to be visible.");
    }

    // Step to click the search button
    @When("I click on the search button")
    public void iClickOnTheSearchButton() {
        homePage.clickTopSearchIcon();
        Log.info("Search button is clicked.");
    }

    // Step to verify the search box expands
    @Then("the search box field should expand")
    public void theSearchBoxFieldShouldExpand() {
        homePage.verifyTopSearchBoxIsVisible();
        Log.info("Search box expanded and verified.");
    }

    // Step to verify the search box collapses
    @When("I click on the x button the search box field should collapse")
    public void theSearchBoxFieldShouldCollapse() {
        homePage.verifyTopSearchBoxIsClosed();
        Log.info("Search box collapsed and verified.");
    }
}