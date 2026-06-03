package db.repository;

import db.core.JdbcTemplate;
import db.core.SqlLoader;
import db.model.AccountRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AccountRepository {
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public List<AccountRecord> findAccountsByCustomerId(int customerId) {
        return jdbcTemplate.query(
                SqlLoader.load("sql/accounts_by_customer_id.sql"),
                this::mapAccount,
                customerId
        );
    }

    public Optional<AccountRecord> findById(int accountId) {
        return jdbcTemplate.queryForObject(
                SqlLoader.load("sql/account_by_id.sql"),
                this::mapAccount,
                accountId
        );
    }

    private AccountRecord mapAccount(ResultSet rs) throws SQLException {
        return new AccountRecord(
                rs.getInt("ID"),
                rs.getInt("CUSTOMER_ID"),
                normalizeAccountType(rs.getString("TYPE")),
                rs.getBigDecimal("BALANCE")
        );
    }

    private String normalizeAccountType(String databaseValue) {
        if ("0".equals(databaseValue)) {
            return "CHECKING";
        }
        if ("1".equals(databaseValue)) {
            return "SAVINGS";
        }
        return databaseValue;
    }
}
