package utils;

import models.Customer;

public final class TestDataFactory {
    private TestDataFactory() {
    }

    public static Customer validRetailBankingCustomer() {
        String unique = String.valueOf(System.currentTimeMillis());
        return new Customer(
                "Freedom",
                "Tester",
                "100 Banking Way",
                "Leesburg",
                "VA",
                "20175",
                "703555" + unique.substring(unique.length() - 4),
                "123" + unique.substring(unique.length() - 6),
                "fb_qa_" + unique,
                "Test@1234"
        );
    }
}
