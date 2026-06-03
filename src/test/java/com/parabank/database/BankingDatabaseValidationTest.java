package com.parabank.database;

import api.ParabankApiClient;
import db.model.AccountRecord;
import db.model.CustomerRecord;
import db.repository.AccountRepository;
import db.repository.CustomerRepository;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class BankingDatabaseValidationTest {
    private final CustomerRepository customerRepository = new CustomerRepository();
    private final AccountRepository accountRepository = new AccountRepository();
    private final ParabankApiClient apiClient = new ParabankApiClient();

    @Test(groups = {"db", "integration", "data-consistency"})
    public void configuredCustomerShouldExistInDatabase() {
        String username = ConfigReader.getProperty("test.user");
        int expectedCustomerId = Integer.parseInt(ConfigReader.getProperty("test.customer.id"));

        CustomerRecord customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new AssertionError("Expected configured customer to exist. username=" + username));

        assertEquals(customer.id(), expectedCustomerId, "Configured customer id should match the database record");
    }

    @Test(groups = {"db", "integration", "data-consistency"})
    public void customerAccountsReturnedByApiShouldMatchDatabaseOwnership() {
        int customerId = Integer.parseInt(ConfigReader.getProperty("test.customer.id"));

        List<AccountRecord> databaseAccounts = accountRepository.findAccountsByCustomerId(customerId);
        Response response = apiClient.getAccountsForCustomer(customerId);

        response.then().statusCode(200);
        List<Integer> apiAccountIds = response.jsonPath().getList("id");

        assertFalse(databaseAccounts.isEmpty(), "Database should contain accounts for the configured customer");
        assertEquals(apiAccountIds.size(), databaseAccounts.size(), "API and DB should return the same account count");

        for (AccountRecord databaseAccount : databaseAccounts) {
            response.then().body("find { it.id == " + databaseAccount.id() + " }.customerId", equalTo(customerId));
        }
    }

    @Test(groups = {"db", "integration", "data-consistency"})
    public void accountDetailApiShouldMatchPersistedAccountRecord() {
        int customerId = Integer.parseInt(ConfigReader.getProperty("test.customer.id"));
        AccountRecord databaseAccount = accountRepository.findAccountsByCustomerId(customerId).get(0);

        Response response = apiClient.getAccount(databaseAccount.id());

        response.then().statusCode(200);
        assertEquals(response.jsonPath().getInt("id"), databaseAccount.id());
        assertEquals(response.jsonPath().getInt("customerId"), databaseAccount.customerId());
        assertEquals(response.jsonPath().getString("type"), databaseAccount.type());

        BigDecimal apiBalance = new BigDecimal(response.jsonPath().getString("balance"));
        assertEquals(apiBalance.compareTo(databaseAccount.balance()), 0,
                "API balance should numerically match the persisted account balance");
    }
}
