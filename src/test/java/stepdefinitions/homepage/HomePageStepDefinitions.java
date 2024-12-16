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
    public void verifyTopSearchButtonIsPresent() {
        homePage.verifyTopSearchIconVisible();
    }

    // Step to click the search button
    @When("I click on the search button")
    public void iClickOnTheSearchButton() {
        homePage.clickTopSearchIcon();
    }

    // Step to verify the search box expands
    @Then("the search box field should expand")
    public void topSearchBarState() {
        homePage.topSearchBoxShouldBeOpen(true); // Expecting the search box to be open
    }

    // Step to verify the search box collapses
    @When("I click on the x button the search box field should collapse")
    public void theSearchBoxFieldShouldCollapse() {
        homePage.clickTopSearchIcon();
        homePage.topSearchBoxShouldBeOpen(false); // Expecting the search box to be closed
    }
}