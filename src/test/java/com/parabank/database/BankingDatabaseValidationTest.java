package com.parabank.database;

import api.ParabankApiClient;
import db.assertions.BankingDbAssertions;
import db.model.AccountRecord;
import db.model.CustomerRecord;
import db.repository.AccountRepository;
import db.repository.CustomerRepository;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.math.BigDecimal;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class BankingDatabaseValidationTest {
    private final CustomerRepository customerRepository = new CustomerRepository();
    private final AccountRepository accountRepository = new AccountRepository();
    private final BankingDbAssertions bankingDbAssertions = new BankingDbAssertions();
    private final ParabankApiClient apiClient = new ParabankApiClient();

    @Test(groups = {"db", "backend", "smoke"})
    public void seedCustomerShouldHaveAccountsStoredInDatabase() {
        String username = ConfigReader.getProperty("test.user");
        int expectedCustomerId = Integer.parseInt(ConfigReader.getProperty("test.customer.id"));

        CustomerRecord customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new AssertionError("No DB customer found for username=" + username));
        List<AccountRecord> accounts = accountRepository.findAccountsByCustomerId(customer.id());

        assertEquals(customer.id(), expectedCustomerId, "Seed customer id should match config");
        assertFalse(accounts.isEmpty(), "Seed customer should have banking accounts in DB");
        assertTrue(accounts.stream().allMatch(account -> account.customerId() == customer.id()),
                "Every returned account should belong to the requested customer");
    }

    @Test(groups = {"db", "backend", "api-contract"})
    public void accountApiResponseShouldMatchDatabaseLedger() {
        int customerId = Integer.parseInt(ConfigReader.getProperty("test.customer.id"));
        AccountRecord databaseAccount = accountRepository.findAccountsByCustomerId(customerId).get(0);

        Response response = apiClient.getAccount(databaseAccount.id());

        response.then().statusCode(200);
        assertEquals(response.jsonPath().getInt("id"), databaseAccount.id());
        assertEquals(response.jsonPath().getInt("customerId"), databaseAccount.customerId());
        assertEquals(response.jsonPath().getString("type"), databaseAccount.type());
        BigDecimal apiBalance = new BigDecimal(response.jsonPath().getString("balance"));
        assertEquals(apiBalance.compareTo(databaseAccount.balance()), 0,
                "API balance should numerically match DB balance regardless of decimal scale");
    }

    @Test(groups = {"db", "backend", "business-rule"})
    public void dbAssertionLayerShouldValidateCustomerAccountOwnership() {
        int customerId = Integer.parseInt(ConfigReader.getProperty("test.customer.id"));
        int accountId = accountRepository.findAccountsByCustomerId(customerId).get(0).id();

        bankingDbAssertions.assertAccountBelongsToCustomer(accountId, customerId);
    }
}
