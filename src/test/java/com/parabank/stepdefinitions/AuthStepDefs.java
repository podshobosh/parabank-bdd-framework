package com.parabank.stepdefinitions;

import factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.Customer;
import org.openqa.selenium.WebDriver;
import pages.AccountPage;
import pages.RegisterPage;
import utils.CommonUtils;
import utils.ConfigReader;
import utils.DatabaseUtils;
import utils.Log;

import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AuthStepDefs {
    private Customer customer;

    private final WebDriver driver;
    private RegisterPage registerPage;
    private AccountPage accountPage;

    public AuthStepDefs() {
        this.driver = DriverFactory.getDriver();
        registerPage = new RegisterPage();
        accountPage = new AccountPage();
    }

    private String user;
    private String password;
    /*
    ----------------------------------------------------
     */

    @Given("the user on the Parabank registration page")
    public void the_user_on_the_parabank_registration_page() {
        registerPage.openRegisterPage();
        assertTrue(registerPage.isOnRegistrationPage());
        Log.info("Verified user is on the registration page");
    }


    @When("user registers with the valid customer details")
    public void user_registers_with_the_valid_customer_details(Map<String, String> credentials) {
        String uniqueUsername = "pb_" + System.currentTimeMillis();

        this.user = uniqueUsername;
        this.password = credentials.get("password");

        customer = new Customer(
                credentials.get("firstName"),
                credentials.get("lastName"),
                credentials.get("address"),
                credentials.get("city"),
                credentials.get("state"),
                credentials.get("zip"),
                credentials.get("phone"),
                credentials.get("ssn"),
                this.user,
                this.password
        );

        registerPage.register(customer);

        Log.info("User entered all the required data and clicked register button");
    }

    @Then("registration should succeed")
    public void registration_should_succeed() {
        assertTrue(accountPage.isWelcomeMessageVisible());
        assertTrue(accountPage.isWelcomeUserVisible());

        Log.info("Verified that user successfully created");
    }

    @Then("customer should exist in the database")
    public void customer_should_exist_in_the_database() {
        assertNotNull("Customer object is null (registration step probably didn't run).", customer);

        boolean exists = DatabaseUtils.verifyCustomerExists(customer.getUsername());
        assertTrue("Customer should exist in DB for username=" + customer.getUsername(), exists);
    }


    @When("customer logs in with registered credentials")
    public void customer_logs_in_with_registered_credentials() {
        accountPage.login(ConfigReader.getProperty("test.user"), ConfigReader.getProperty("test.pass"));
        Log.info("Enter login credentials and clicked login successfully");

    }

    @Then("account overview should be displayed")
    public void account_overview_should_be_displayed() {
        assertTrue(accountPage.isAccountOverviewTextDisplayed());
        Log.info("Verified that account overview is displayed");

    }

}
