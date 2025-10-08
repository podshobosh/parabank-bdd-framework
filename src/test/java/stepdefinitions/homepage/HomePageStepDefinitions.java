package stepdefinitions.homepage;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import factory.DriverFactory;
import pages.HomePage;
import utils.CommonUtils;
import utils.Log;
import utils.ScreenshotUtils;
import utils.ExtentReportManager;

public class HomePageStepDefinitions {

    private WebDriver driver;
    private HomePage homePage;

    // Constructor to initialize WebDriver and HomePage Object
    public HomePageStepDefinitions() {
        this.driver = DriverFactory.getDriver();
        this.homePage = new HomePage(driver);
    }

    /**
     * Reusable method to log a message and take a screenshot.
     * @param message Description for the log and screenshot.
     */
    private void logAndCapture(String message) {
        Log.info(message); // Log the message
        String screenshotPath = ScreenshotUtils.takeScreenshot(driver, message.replace(" ", "_"));
        ExtentReportManager.attachScreenshot(screenshotPath, message);
    }

    // Step for verifying the HomePage title
    @Then("The page should have the title {string}")
    public void thePageShouldHaveTheTitle(String expectedTitle) {
        CommonUtils.verifyTitle(driver, expectedTitle);
        logAndCapture("HomePage Title Verification Completed");
    }

    // Step for verifying the HomePage URL
    @Then("The page URL should be {string}")
    public void thePageURLShouldBe(String expectedUrl) {
        CommonUtils.verifyUrl(driver, expectedUrl);
        logAndCapture("HomePage URL Verification Completed");
    }

    // Step for verifying the search button is present
    @Then("the top search button should be present")
    public void verifyTopSearchButtonIsPresent() {
        homePage.verifyTopSearchIconVisible();
        logAndCapture("Top Search Button is Present");
    }

    // Step to click the search button
    @When("I click on the search button")
    public void iClickOnTheSearchButton() {
        homePage.clickTopSearchIcon();
        logAndCapture("Top Search Button Clicked");
    }

    // Step to verify the search box expands
    @Then("the search box field should expand")
    public void topSearchBarState() {
        homePage.topSearchBoxShouldBeOpen(true);
        logAndCapture("Search Box Field Expanded");
    }

    // Step to verify the search box collapses
    @When("I click on the x button the search box field should collapse")
    public void theSearchBoxFieldShouldCollapse() {
        homePage.clickTopSearchIcon();
        homePage.topSearchBoxShouldBeOpen(false);
        logAndCapture("Search Box Field Collapsed");
    }

    @Then("the website logo should be present on the top left of the page")
    public void the_website_logo_should_be_present_on_the_top_left_of_the_page() {
        Assert.assertTrue("website logo not visible",homePage.isWebsiteLogoVisible());
    }
    @When("the user navigates to {string} page")
    public void the_user_navigates_to_page(String menuItem) {
        homePage.clickMainMenuItemByName(menuItem);

    }
    @When("user clicks on the website logo")
    public void user_clicks_on_the_website_logo() {
        homePage.clickSiteLogo();
    }
    @Then("user should be redirected to the HomePage")
    public void user_should_be_redirected_to_the_home_page() {
        Assert.assertTrue(homePage.isOnHomePage());
    }

}
