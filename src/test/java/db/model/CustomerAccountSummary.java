package db.model;

import java.math.BigDecimal;

public class CustomerAccountSummary {
    private final int customerId;
    private final String username;
    private final int accountCount;
    private final BigDecimal totalBalance;

    public CustomerAccountSummary(int customerId, String username, int accountCount, BigDecimal totalBalance) {
        this.customerId = customerId;
        this.username = username;
        this.accountCount = accountCount;
        this.totalBalance = totalBalance;
    }

    public int customerId() { return customerId; }
    public String username() { return username; }
    public int accountCount() { return accountCount; }
    public BigDecimal totalBalance() { return totalBalance; }
}
