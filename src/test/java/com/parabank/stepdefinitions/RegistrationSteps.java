package com.parabank.stepdefinitions;

import factory.DriverFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.AccountPage;
import pages.HomePage;
import pages.RegisterPage;
import utils.AppUrls;
import utils.CommonUtils;
import utils.DatabaseUtils;
import utils.HoldOn;

import java.util.HashMap;
import java.util.Map;

public class RegistrationSteps {

    private final WebDriver driver;
    RegisterPage registerPage;
    HomePage homePage;
    AccountPage accountPage;

    public RegistrationSteps() {
        this.driver = DriverFactory.getDriver();
        this.homePage = new HomePage();
        this.registerPage = new RegisterPage();
        this.accountPage = new AccountPage();

    }

    private Map<String, String> payload = new HashMap<>();
    private String createdUsername;


    @Given("user is on the Home Page")
    public void user_is_on_the_home_page() {
        CommonUtils.verifyUrl(driver, AppUrls.HOME_PAGE);
    }

    @When("user navigates to register page")
    public void user_navigates_to_register_page() {
        registerPage.navigateToRegisterPage();
    }

    @When("user provides registration information:")
    public void user_provides_registration_information(Map<String, String> dataTable) {
        registerPage.register(dataTable);
        payload = dataTable;
    }


    @When("user clicks register button")
    public void user_clicks_register_button() {
        registerPage.clickRegisterButton();
        HoldOn.waitForPageToLoad(driver);


    }

    @Then("user should see registration success message")
    public void user_should_see_registration_success_message() {
        Assert.assertTrue(accountPage.isWelcomeUserVisible());
        Assert.assertTrue(accountPage.isWelcomeMessageVisible());

    }


    @And("I mirror the user into DB and verify it exists")
    public void iMirrorTheUserIntoDBAndVerifyItExists() {

    }
}
