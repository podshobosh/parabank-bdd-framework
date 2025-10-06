package stepdefinitions.homepage;

import factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.MyNotesPage;
import utils.ExtentReportManager;
import utils.HoldOn;
import utils.Log;
import utils.ScreenshotUtils;

public class myNotesPageStepDefinition {

    private WebDriver driver;
    private HomePage homePage;
    private MyNotesPage myNotesPage;

    // Constructor to initialize WebDriver and HomePage Object
    public myNotesPageStepDefinition() {
        this.driver = DriverFactory.getDriver();
        this.homePage = new HomePage(driver);
        this.myNotesPage = new MyNotesPage(driver);
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


    @Given("User click on My Notes Tab")
    public void user_click_on_my_notes_tab() {
//        System.out.println("[DEBUG] driver? " + DriverFactory.getDriver());
//        System.out.println("[DEBUG] myNotesPage? " + myNotesPage);
//        System.out.println("[DEBUG] homePage? " + homePage);
        myNotesPage.clickMyNotesLink();
    }
    @Given("Validate that topic of fields are present at the left side of the page")
    public void validate_that_topic_of_fields_are_present_at_the_left_side_of_the_page() {
        myNotesPage.validateTopicLinks();
    }
    @When("User clicks on Selenium topic")
    public void user_clicks_on_selenium_topic() {
        myNotesPage.clickOnSeleniumTopicLink();

    }
    @Then("Validate Selenium title is present")
    public void validate_selenium_title_is_present() {
        myNotesPage.validateSeleniumTitle();
    }

    @When("User clicks the {string} link from the sidebar")
    public void user_clicks_the_link_from_the_sidebar(String section) {
        myNotesPage.clickOnSidebarTopicLink(section);

    }
    @Then("User should see title {string}")
    public void user_should_see_title(String section) {
        myNotesPage.waitForTopicTitle(section);
        String actual = myNotesPage.getTopicTitle();
        Assert.assertEquals(section, actual);


    }



    /*
    One challenge I faced was dealing with StaleElementReferenceException when clicking sidebar links after opening a ‘My Notes’ tab. The DOM re-rendered, so even though the XPath looked the same, the cached WebElements were invalid. I solved it by switching from @FindBy List<WebElement> to a By locator and re-finding elements fresh each time with a helper method that retries safely. That eliminated the flakiness and made the framework more reliable.”
     */

}
