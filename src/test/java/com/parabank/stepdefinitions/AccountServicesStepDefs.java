package com.parabank.stepdefinitions;

import factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.AccountPage;
import utils.Log;

public class AccountServicesStepDefs {

    private final WebDriver driver;
    private AccountPage accountPage;


    public AccountServicesStepDefs(){
        this.driver = DriverFactory.getDriver();
        this.accountPage = new AccountPage();
    }


    @Given("customer clicks on Open New Account")
    public void customer_clicks_on_open_new_account() {
        accountPage.goToNewAccount();

        Log.info("Successfully clicked and navigated to open account page");



    }

    @Then("customer should be on the open account page")
    public void customer_should_be_on_the_open_account_page() {
        accountPage.highlightMessage();
        Assert.assertTrue("'Open New Account' - text is not displayed!",accountPage.getOpenNewAccountMessage().isDisplayed());
        Log.info("Verified customer is on the Open New Account page");


    }

    @Then("customer customer selects checking account dropdown")
    public void customer_customer_selects_checking_account_dropdown() {
        accountPage.selectCheckingAccount();
        Log.info("Customer successfully selected Checking account from dropdown");
    }

    @Then("customer selects an existing account to fund the new account")
    public void customer_selects_an_existing_account_to_fund_the_new_account() {
        accountPage.selectDepositAccount();
        Log.info("Successfully selected the deposit account");
    }

    @When("customer clicks create account button")
    public void customer_clicks_create_account_button() {
        accountPage.clickOpenAccountBttn();
        System.out.println(" DEBUG: " + accountPage.getAccountOpenedMessage().getText());
    }

    @Then("the new account should be created successfully")
    public void the_new_account_should_be_created_successfully() {

        Assert.assertTrue("'Account Opened!' - text is missing",accountPage.isConfirmationMessageVisible());

        Assert.assertTrue("Congratulation message is missing",accountPage.getCongratulationMessage().isDisplayed());
        Log.info("Customer has successfully created new checking account");
    }

}
