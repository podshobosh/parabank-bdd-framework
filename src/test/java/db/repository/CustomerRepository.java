package db.repository;

import db.core.JdbcTemplate;
import db.core.SqlLoader;
import db.model.CustomerAccountSummary;
import db.model.CustomerRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerRepository {
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public Optional<CustomerRecord> findByUsername(String username) {
        return jdbcTemplate.queryForObject(
                SqlLoader.load("sql/customer_by_username.sql"),
                this::mapCustomer,
                username
        );
    }

    public Optional<CustomerAccountSummary> getAccountSummary(int customerId) {
        return jdbcTemplate.queryForObject(
                SqlLoader.load("sql/customer_account_summary.sql"),
                this::mapAccountSummary,
                customerId
        );
    }

    private CustomerRecord mapCustomer(ResultSet rs) throws SQLException {
        return new CustomerRecord(
                rs.getInt("ID"),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("USERNAME")
        );
    }

    private CustomerAccountSummary mapAccountSummary(ResultSet rs) throws SQLException {
        return new CustomerAccountSummary(
                rs.getInt("CUSTOMER_ID"),
                rs.getString("USERNAME"),
                rs.getInt("ACCOUNT_COUNT"),
                rs.getBigDecimal("TOTAL_BALANCE")
        );
    }
}
