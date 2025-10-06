package stepdefinitions.loginpage;


import factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.LoginPage;
import pages.MyNotesPage;
import utils.ExtentReportManager;
import utils.Log;
import utils.ScreenshotUtils;


public class LoginPageSteps {

    private WebDriver driver;
    private HomePage homePage;
    private MyNotesPage myNotesPage;
    private LoginPage loginPage;

    // Constructor to initialize WebDriver and LoginPage Object
    public LoginPageSteps() {
        this.driver = DriverFactory.getDriver();
        this.homePage = new HomePage(driver);
        this.myNotesPage = new MyNotesPage(driver);
        this.loginPage = new LoginPage(driver);
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

    @Given("user is one the login page")
    public void user_is_one_the_login_page() {
        loginPage.clickLoginLink();

    }
    @When("user enters {string} and {string}")
    public void user_enters_and(String user, String pass) {
        loginPage.login(user, pass);
    }
    @When("clicks Login button")
    public void clicks_login_button() {
;       loginPage.clickLogin();
    }
    @Then("user should see {string}")
    public void user_should_see(String message) {
    Assert.assertEquals(loginPage.getErrorText(), message);

    }


}