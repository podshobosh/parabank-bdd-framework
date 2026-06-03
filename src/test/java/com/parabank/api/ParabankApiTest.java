package com.parabank.api;

import api.ParabankApiClient;
import org.testng.annotations.Test;
import utils.ConfigReader;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class ParabankApiTest {
    private final ParabankApiClient apiClient = new ParabankApiClient();

    @Test(groups = {"api", "smoke"})
    public void demoCustomerShouldHaveAccounts() {
        int customerId = Integer.parseInt(ConfigReader.getProperty("test.customer.id", "12212"));

        apiClient.getAccountsForCustomer(customerId)
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].customerId", equalTo(customerId));
    }

    @Test(groups = {"api", "negative"})
    public void invalidLoginShouldReturnBadRequest() {
        apiClient.login("bad_user", "bad_password")
                .then()
                .statusCode(400);
    }
}
