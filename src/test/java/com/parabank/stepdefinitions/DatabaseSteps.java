package com.parabank.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utils.DatabaseUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseSteps {

    private final Map<String, Object> ctx = new HashMap<>();

    @Given("the database is initialized and cleaned")
    public void theDatabaseIsInitializedAndCleaned() {
        DatabaseUtils.initDatabase();
        DatabaseUtils.cleanDatabase();
    }

    @When("I insert a customer with details")
    public void iInsertACustomerWithDetails(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Assert.assertFalse("DataTable should have at least one row", rows.isEmpty());
        Map<String, String> r = rows.get(0);

        String username = r.get("username");
        String password = r.get("password");
        String firstName = r.get("first_name");
        String lastName = r.get("last_name");
        String email = r.get("email");
        String ssn = r.get("ssn");
        String address = r.get("address");
        String city = r.get("city");
        String state = r.get("state");
        String zip = r.get("zip");
        String phone = r.get("phone");

        int id = DatabaseUtils.insertCustomer(
                username, password, firstName, lastName, email, ssn,
                address, city, state, zip, phone
        );
        ctx.put("username", username);
        ctx.put("insertedId", id);
        Assert.assertTrue("Generated id should be > 0", id > 0);
    }

    @Then("the customer {string} should exist in the database")
    public void theCustomerShouldExistInTheDatabase(String username) {
        boolean exists = DatabaseUtils.verifyCustomerExists(username);
        Assert.assertTrue("Expected customer to exist: " + username, exists);
    }

    @And("fetching the id for username {string} should return a valid id")
    public void fetchingTheIdForUsernameShouldReturnAValidId(String username) {
        int fetchedId = DatabaseUtils.getCustomerID(username);
        Assert.assertTrue("Fetched id should be > 0", fetchedId > 0);
        Object insertedId = ctx.get("insertedId");
        if (insertedId instanceof Integer) {
            Assert.assertEquals("Fetched id should match inserted id", ((Integer) insertedId).intValue(), fetchedId);
        }
    }

    @And("I print the full customer record for username {string}")
    public void iPrintTheFullCustomerRecordForUsername(String username) {
        List<Map<String, Object>> rows = DatabaseUtils.executeQuery("SELECT * FROM customers WHERE username = ?", username);
        Assert.assertEquals("Expected exactly one row for username", 1, rows.size());
        Map<String, Object> row = rows.get(0);

        System.out.println("[JDBC_PRINT] ===== Customer (SELECT *) =====");
        java.util.Set<String> keys = new java.util.TreeSet<>(row.keySet());
        for (String k : keys) {
            if (k.equals(k.toLowerCase(java.util.Locale.ROOT))) {
                Object v = row.get(k);
                System.out.println("[JDBC_PRINT] " + k + "=" + String.valueOf(v));
            }
        }
        System.out.println("[JDBC_PRINT] =============================");

        // Optional sanity checks for the newly added columns when present
        Assert.assertEquals(ctx.get("username"), String.valueOf(row.get("username")));
    }
}
