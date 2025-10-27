package com.parabank;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.DatabaseUtils;

import java.util.List;
import java.util.Map;

public class DatabaseUtilsTest {

    @BeforeAll
    static void setUp() {
        // Ensure schema exists and DB is clean before tests
        DatabaseUtils.initDatabase();
        DatabaseUtils.cleanDatabase();
    }

    @AfterAll
    static void tearDown() {
        DatabaseUtils.cleanDatabase();
        DatabaseUtils.closeConnection();
    }

    @Test
    void jdbc_smoke_flow_should_insert_and_query_customer() {
        // Insert a customer
        int newId = DatabaseUtils.insertCustomer(
                "testuser",
                "password123",
                "Test",
                "User",
                "testuser@example.com",
                "123-45-6789",
                "123 Main St",
                "Metropolis",
                "NY",
                "12345",
                "+1-555-0100"
        );

        // Verify existence via COUNT(*)
        boolean exists = DatabaseUtils.verifyCustomerExists("testuser");
        Assertions.assertTrue(exists, "Customer should exist after insertion");

        // Verify ID retrieval API
        int fetchedId = DatabaseUtils.getCustomerID("testuser");
        Assertions.assertEquals(newId, fetchedId, "Fetched ID should match generated key");

        // Direct SELECT to verify column label mapping
        List<Map<String, Object>> rows = DatabaseUtils.executeQuery(
                "SELECT id AS id, username AS username FROM customers WHERE id = ?",
                newId
        );
        Assertions.assertEquals(1, rows.size(), "Exactly one row should be returned");
        Map<String, Object> row = rows.get(0);
        Assertions.assertEquals("testuser", String.valueOf(row.get("username")));
        Assertions.assertEquals(String.valueOf(newId), String.valueOf(row.get("id")));

        // Print full customer record using SELECT * so we can see every column that was added
        List<Map<String, Object>> fullRows = DatabaseUtils.executeQuery(
                "SELECT * FROM customers WHERE id = ?",
                newId
        );
        Assertions.assertEquals(1, fullRows.size(), "SELECT * should return exactly one row for the inserted customer");
        Map<String, Object> full = fullRows.get(0);

        // Assert newly added contact fields
        Assertions.assertEquals("123 Main St", String.valueOf(full.get("address")));
        Assertions.assertEquals("Metropolis", String.valueOf(full.get("city")));
        Assertions.assertEquals("NY", String.valueOf(full.get("state")));
        Assertions.assertEquals("12345", String.valueOf(full.get("zip_code")));
        Assertions.assertEquals("+1-555-0100", String.valueOf(full.get("phone")));

        System.out.println("[JDBC_PRINT] ===== Customer (SELECT *) =====");
        // Print only lowercase keys to avoid duplicates from case-insensitive mapping
        java.util.Set<String> keys = new java.util.TreeSet<>(full.keySet());
        for (String k : keys) {
            if (k.equals(k.toLowerCase(java.util.Locale.ROOT))) {
                Object v = full.get(k);
                System.out.println("[JDBC_PRINT] " + k + "=" + String.valueOf(v));
            }
        }
        System.out.println("[JDBC_PRINT] =============================");
    }
}
