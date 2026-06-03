package utils;

import models.Customer;

public final class TestDataFactory {
    private TestDataFactory() {
    }

    public static Customer validRetailBankingCustomer() {
        String unique = String.valueOf(System.currentTimeMillis());
        return new Customer(
                "Jordan",
                "Taylor",
                "100 Market Street",
                "Arlington",
                "VA",
                "22201",
                "703555" + unique.substring(unique.length() - 4),
                "123" + unique.substring(unique.length() - 6),
                "qa_user_" + unique,
                "Test@1234"
        );
    }
}
