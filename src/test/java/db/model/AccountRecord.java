package db.model;

import java.math.BigDecimal;

public class AccountRecord {
    private final int id;
    private final int customerId;
    private final String type;
    private final BigDecimal balance;

    public AccountRecord(int id, int customerId, String type, BigDecimal balance) {
        this.id = id;
        this.customerId = customerId;
        this.type = type;
        this.balance = balance;
    }

    public int id() { return id; }
    public int customerId() { return customerId; }
    public String type() { return type; }
    public BigDecimal balance() { return balance; }
}
