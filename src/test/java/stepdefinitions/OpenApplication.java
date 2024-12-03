package stepdefinitions;

import base.BaseSetup;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class OpenApplication {

    private final BaseSetup baseSetup = new BaseSetup();

    @Given("I open the browser {string}")
    public void iOpenTheBrowser(String browserType) {
        baseSetup.openBrowser(browserType); // Passes browser type to BaseSetup
    }

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        baseSetup.getDriver().get(url);
    }

    @Then("The page title should be {string}")
    public void thePageTitleShouldBe(String expectedTitle) {
        String actualTitle = baseSetup.getDriver().getTitle();
        Assert.assertEquals("Page title verification failed!", expectedTitle, actualTitle);
    }
}
