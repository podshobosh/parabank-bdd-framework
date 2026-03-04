package com.parabank.stepdefinitions;

import factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import utils.ConfigReader;
import utils.Log;

import static org.junit.Assert.assertTrue;

public class SmokeSteps {

    private final WebDriver driver;
    private HomePage homePage;

    public SmokeSteps() {
        this.driver = DriverFactory.getDriver();
        homePage = new HomePage();
    }


    @Given("user opens parabank homepage")
    public void user_opens_parabank_homepage() {
        driver.get(ConfigReader.getProperty("home.page.url"));
        Log.info("User successfully oponed parabank homepage");
    }

    @Then("the login panel is visible")
    public void the_login_panel_is_visible() {
        assertTrue(homePage.getUsernameInputBox().isDisplayed());
        assertTrue(homePage.getPasswordInputBox().isDisplayed());
        assertTrue(homePage.getLoginButton().isDisplayed());
        Log.info("Verified that login panel is visible");
    }
}
