package com.parabank.stepdefinitions;

import api.ParabankApiClient;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import pages.AccountPage;
import utils.Log;
import utils.ScenarioContext;

import static org.hamcrest.Matchers.equalTo;

public class AccountServicesStepDefs {
    private final AccountPage accountPage;
    private final ParabankApiClient apiClient;

    public AccountServicesStepDefs() {
        this.accountPage = new AccountPage();
        this.apiClient = new ParabankApiClient();
    }

    @When("the customer navigates to Open New Account")
    public void customer_clicks_on_open_new_account() {
        accountPage.goToNewAccount();
        Log.info("Navigated to Open New Account page");
    }

    @Then("the Open New Account page should be displayed")
    public void customer_should_be_on_the_open_account_page() {
        accountPage.highlightMessage();
        Assert.assertTrue("Open New Account header is not displayed", accountPage.getOpenNewAccountMessage().isDisplayed());
    }

    @And("the customer selects Checking as the account type")
    public void customer_customer_selects_checking_account_dropdown() {
        accountPage.selectCheckingAccount();
    }

    @And("the customer selects an existing funding account")
    public void customer_selects_an_existing_account_to_fund_the_new_account() {
        accountPage.selectDepositAccount();
    }

    @And("the customer submits the open account request")
    public void customer_clicks_create_account_button() {
        accountPage.clickOpenAccountBttn();
    }

    @Then("the new checking account should be displayed in the UI")
    public void the_new_account_should_be_created_successfully() {
        Assert.assertTrue("Account Opened message is missing", accountPage.isConfirmationMessageVisible());
        Assert.assertTrue("Congratulations message is missing", accountPage.getCongratulationMessage().isDisplayed());
        int newAccountId = accountPage.getNewAccountId();
        ScenarioContext.put("newAccountId", newAccountId);
        Log.info("New checking account created with id=" + newAccountId);
    }

    @And("the new checking account should be retrievable from the accounts API")
    public void the_new_account_should_be_available_from_the_parabank_accounts_api() {
        Integer accountId = ScenarioContext.getInteger("newAccountId");
        Response response = apiClient.getAccount(accountId);
        response.then()
                .statusCode(200)
                .body("id", equalTo(accountId))
                .body("type", equalTo("CHECKING"));
        Log.info("Verified account through REST-Assured API, accountId=" + accountId);
    }
}
