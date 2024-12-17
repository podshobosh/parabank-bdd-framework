//package stepdefinitions.homepage;
//
//import org.openqa.selenium.WebDriver;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import factory.DriverFactory;
//import pages.HomePage;
//import utils.CommonUtils;
//import utils.Log;
//import utils.ScreenshotUtils;
//
//public class HomePageStepDefinitions {
//
//    private WebDriver driver;
//    private HomePage homePage;
//
//    // Constructor to initialize WebDriver and HomePage Object
//    public HomePageStepDefinitions() {
//        this.driver = DriverFactory.getDriver();
//        this.homePage = new HomePage(driver);
//    }
//
//    // Step for verifying the HomePage title
//    @Then("The page should have the title {string}")
//    public void thePageShouldHaveTheTitle(String expectedTitle) {
//        CommonUtils.verifyTitle(driver, expectedTitle);
//        Log.info("HomePage title verification completed.");
//        ScreenshotUtils.takeScreenshot(driver, "HomePage_Title_Verification"); // Screenshot
//    }
//
//    // Step for verifying the HomePage URL
//    @Then("The page URL should be {string}")
//    public void thePageURLShouldBe(String expectedUrl) {
//        CommonUtils.verifyUrl(driver, expectedUrl);
//        Log.info("HomePage URL verification completed.");
//        ScreenshotUtils.takeScreenshot(driver, "HomePage_URL_Verification"); // Screenshot
//    }
//
//    // Step for verifying the search button is present
//    @Then("the top search button should be present")
//    public void verifyTopSearchButtonIsPresent() {
//        homePage.verifyTopSearchIconVisible();
//        Log.info("Top search button is present.");
//        ScreenshotUtils.takeScreenshot(driver, "Top_Search_Button_Present"); // Screenshot
//    }
//
//    // Step to click the search button
//    @When("I click on the search button")
//    public void iClickOnTheSearchButton() {
//        homePage.clickTopSearchIcon();
//        Log.info("Search button clicked.");
//        ScreenshotUtils.takeScreenshot(driver, "Top_Search_Button_Clicked"); // Screenshot
//    }
//
//    // Step to verify the search box expands
//    @Then("the search box field should expand")
//    public void topSearchBarState() {
//        homePage.topSearchBoxShouldBeOpen(true);
//        Log.info("Search box field is expanded.");
//        ScreenshotUtils.takeScreenshot(driver, "Search_Box_Expanded"); // Screenshot
//    }
//
//    // Step to verify the search box collapses
//    @When("I click on the x button the search box field should collapse")
//    public void theSearchBoxFieldShouldCollapse() {
//        homePage.clickTopSearchIcon();
//        homePage.topSearchBoxShouldBeOpen(false);
//        Log.info("Search box field is collapsed.");
//        ScreenshotUtils.takeScreenshot(driver, "Search_Box_Collapsed"); // Screenshot
//    }
//}

//+++++++++++++++++++++++++++++++++++++
//        package hooks;
//
//import io.cucumber.java.Before;
//import io.cucumber.java.After;
//import io.cucumber.java.Scenario;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import utils.Log;
//
//public class Hooks {
//
//    private static final Logger failedLogger = LogManager.getLogger("failedScenarioLogger");
//
//    @Before
//    public void beforeScenario(Scenario scenario) {
//        // Extract scenario name and tags
//        String scenarioName = scenario.getName();
//        String tags = String.join(", ", scenario.getSourceTagNames());
//
//        // Log scenario details
//        Log.info("====================== STARTING SCENARIO ======================");
//        Log.info("Scenario: " + scenarioName);
//        Log.info("Tags: " + tags);
//        Log.info("===============================================================");
//    }
//
//    @After
//    public void afterScenario(Scenario scenario) {
//        // Log scenario status and end
//        Log.info("====================== ENDING SCENARIO ======================");
//        Log.info("Scenario Status: " + scenario.getStatus());
//        Log.info("=============================================================");
//
//        // Log failed scenarios to the failed logger
//        if (scenario.getStatus().toString().equalsIgnoreCase("FAILED")) {
//            failedLogger.error("Scenario failed: " + scenario.getName());
//        }
//    }
//}
