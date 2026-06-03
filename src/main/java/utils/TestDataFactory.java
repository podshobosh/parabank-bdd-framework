package utils;

import models.Customer;

public final class TestDataFactory {
    private TestDataFactory() {
    }

    public static Customer validRetailBankingCustomer() {
        String unique = String.valueOf(System.currentTimeMillis());
        return new Customer(
                "Alex",
                "Morgan",
                "100 Main Street",
                "Arlington",
                "VA",
                "22201",
                "703555" + unique.substring(unique.length() - 4),
                "123" + unique.substring(unique.length() - 6),
                "pbcustomer" + unique,
                "Password123"
        );
    }
}
