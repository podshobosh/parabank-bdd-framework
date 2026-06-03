package com.parabank.stepdefinitions;

import factory.DriverFactory;
import db.assertions.BankingDbAssertions;
import db.model.CustomerRecord;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.Customer;
import org.openqa.selenium.WebDriver;
import pages.AccountPage;
import pages.RegisterPage;
import utils.ConfigReader;
import utils.Log;
import utils.ScenarioContext;
import utils.TestDataFactory;

import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AuthStepDefs {
    private final WebDriver driver;
    private final RegisterPage registerPage;
    private final AccountPage accountPage;
    private final BankingDbAssertions bankingDbAssertions;

    public AuthStepDefs() {
        this.driver = DriverFactory.getDriver();
        this.registerPage = new RegisterPage();
        this.accountPage = new AccountPage();
        this.bankingDbAssertions = new BankingDbAssertions();
    }

    @Given("the customer is on the registration page")
    public void the_user_on_the_parabank_registration_page() {
        registerPage.openRegisterPage();
        assertTrue(registerPage.isOnRegistrationPage());
        Log.info("Verified user is on the registration page");
    }

    @When("the customer registers with valid generated profile information")
    public void user_registers_with_generated_valid_customer_details() {
        Customer customer = TestDataFactory.validRetailBankingCustomer();
        ScenarioContext.put("customer", customer);
        ScenarioContext.put("username", customer.getUsername());
        ScenarioContext.put("password", customer.getPassword());
        registerPage.register(customer);
        Log.info("Registered generated customer username=" + customer.getUsername());
    }

    @When("user registers with the valid customer details")
    public void user_registers_with_the_valid_customer_details(Map<String, String> credentials) {
        String uniqueUsername = "pb_" + System.currentTimeMillis();
        Customer customer = new Customer(
                credentials.get("firstName"),
                credentials.get("lastName"),
                credentials.get("address"),
                credentials.get("city"),
                credentials.get("state"),
                credentials.get("zip"),
                credentials.get("phone"),
                credentials.get("ssn"),
                uniqueUsername,
                credentials.get("password")
        );
        ScenarioContext.put("customer", customer);
        ScenarioContext.put("username", customer.getUsername());
        ScenarioContext.put("password", customer.getPassword());
        registerPage.register(customer);
        Log.info("User entered all required registration data");
    }

    @Then("the customer registration should be successful")
    public void registration_should_succeed() {
        assertTrue(accountPage.isWelcomeMessageVisible());
        assertTrue(accountPage.isWelcomeUserVisible());
        Log.info("Verified registration success page");
    }

    @Then("the customer profile should be persisted in the database")
    public void customer_should_exist_in_the_database() {
        Customer customer = (Customer) ScenarioContext.get("customer");
        assertNotNull("Customer object is null; registration step probably did not run.", customer);
        CustomerRecord customerRecord = bankingDbAssertions.assertCustomerWasPersisted(customer.getUsername());
        ScenarioContext.put("customerId", customerRecord.id());
        Log.info("Verified CUSTOMER table persistence through repository layer. customerId=" + customerRecord.id());
    }

    @When("the customer signs out")
    public void customer_logs_out() {
        driver.get(ConfigReader.getProperty("base.ui.url") + "/logout.htm");
        Log.info("Customer logged out");
    }

    @When("the customer signs in with the newly registered credentials")
    public void customer_logs_in_with_the_newly_registered_credentials() {
        accountPage.login(ScenarioContext.getString("username"), ScenarioContext.getString("password"));
        Log.info("Logged in with newly registered credentials");
    }

    @When("customer logs in with registered credentials")
    public void customer_logs_in_with_registered_credentials() {
        accountPage.login(ConfigReader.getProperty("test.user"), ConfigReader.getProperty("test.pass"));
        ScenarioContext.put("customerId", Integer.parseInt(ConfigReader.getProperty("test.customer.id")));
        Log.info("Logged in with configured smoke credentials");
    }

    @Then("account overview should be displayed")
    public void account_overview_should_be_displayed() {
        assertTrue(accountPage.isAccountOverviewTextDisplayed());
        Log.info("Verified account overview is displayed");
    }
}
