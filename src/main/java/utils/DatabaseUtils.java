package utils;

import java.sql.*;
import java.sql.Connection;
import java.util.*;


public final class DatabaseUtils {

    private DatabaseUtils() {
        // Utility class: prevent instantiation
    }


    /**
     * Initialize database with banking tables
     */
    public static void initDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            Log.info("Initializing database...");

            // Create customers table
            stmt.execute("CREATE TABLE IF NOT EXISTS customers (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(50) UNIQUE NOT NULL, " +
                    "password VARCHAR(100) NOT NULL, " +
                    "first_name VARCHAR(50), " +
                    "last_name VARCHAR(50), " +
                    "email VARCHAR(100), " +
                    "ssn VARCHAR(11), " +
                    "phone VARCHAR(20), " +
                    "address VARCHAR(255), " +
                    "city VARCHAR(50), " +
                    "state VARCHAR(2), " +
                    "zip_code VARCHAR(10), " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            // Create accounts table
            stmt.execute("CREATE TABLE IF NOT EXISTS accounts (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "customer_id INT NOT NULL, " +
                    "account_type VARCHAR(20), " +
                    "balance DECIMAL(10,2) DEFAULT 0.00, " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (customer_id) REFERENCES customers(id))");

            // Create transactions table
            stmt.execute("CREATE TABLE IF NOT EXISTS transactions (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "account_id INT NOT NULL, " +
                    "type VARCHAR(20), " +
                    "amount DECIMAL(10,2), " +
                    "description VARCHAR(255), " +
                    "transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (account_id) REFERENCES accounts(id))");

            Log.info("Database initialized successfully");
        } catch (SQLException e) {
            Log.error("Failed to initialize database", e);
        }
    }

    /**
     * Get database connection
     */
    private static volatile boolean driverInitialized = false;

    public static Connection getConnection() throws SQLException {
        // Optionally load JDBC driver once if provided in config
        if (!driverInitialized) {
            synchronized (DatabaseUtils.class) {
                if (!driverInitialized) {
                    String driverClass = ConfigReader.getProperty("DB_DRIVER");
                    if (driverClass != null && !driverClass.trim().isEmpty()) {
                        try {
                            Class.forName(driverClass.trim());
                            Log.info("Loaded JDBC driver: " + driverClass.trim());
                        } catch (ClassNotFoundException e) {
                            Log.error("JDBC driver class not found: " + driverClass, e);
                            throw new RuntimeException("JDBC driver class not found: " + driverClass, e);
                        }
                    }
                    driverInitialized = true;
                }
            }
        }

        String url = Optional.ofNullable(ConfigReader.getProperty("DB_URL")).map(String::trim).orElse(null);
        String user = Optional.ofNullable(ConfigReader.getProperty("DB_USER")).map(String::trim).orElse(null);
        String pass = Optional.ofNullable(ConfigReader.getProperty("DB_PASS")).map(String::trim).orElse(null);

        if (url == null || url.isEmpty()) {
            throw new IllegalStateException("DB_URL is not configured");
        }
        return DriverManager.getConnection(url, user, pass);
    }

    /**
     * Execute SELECT query and return result
     */
    public static List<Map<String, Object>> executeQuery(String query, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            // Set parameters
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = statement.executeQuery()) {
                List<Map<String, Object>> results = new ArrayList<>();

                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Process each row
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        // Use column LABEL (respects SQL aliases and is JDBC best practice)
                        String label = metaData.getColumnLabel(i);
                        Object value = rs.getObject(i);
                        // Put exact label as returned by driver
                        row.put(label, value);
                        // Also put a lowercase version to allow case-insensitive access across drivers
                        String lower = label == null ? null : label.toLowerCase(java.util.Locale.ROOT);
                        if (lower != null && !lower.equals(label)) {
                            row.put(lower, value);
                        }
                    }
                    results.add(row);
                }

                Log.info("Query executed: " + results.size() + " rows returned");
                return results;
            }

        } catch (SQLException e) {
            Log.error("Query failed", e);
            throw new RuntimeException("Failed to execute query", e);
        }
    }

    /**
     * Insert customer and return generated ID (basic variant)
     */
    public static int insertCustomer(String username, String password, String firstName, String lastName, String email, String ssn) {
        // Delegate to extended variant with optional contact details as nulls for backward compatibility
        return insertCustomer(username, password, firstName, lastName, email, ssn, null, null, null, null, null);
    }

    /**
     * Insert customer with contact details (address, city, state, zipcode, phone) and return generated ID
     */
    public static int insertCustomer(String username, String password, String firstName, String lastName,
                                     String email, String ssn,
                                     String address, String city, String state, String zipcode, String phone) {
        String query = "INSERT INTO customers (username, password, first_name, last_name, email, ssn, address, city, state, zip_code, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.setString(5, email);
            statement.setString(6, ssn);
            statement.setString(7, address);
            statement.setString(8, city);
            statement.setString(9, state);
            statement.setString(10, zipcode);
            statement.setString(11, phone);

            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    Log.info("Customer inserted with ID: " + id);
                    return id;
                }
                throw new RuntimeException("Failed to retrieve generated key after insert");
            }

        } catch (SQLException e) {
            Log.error("Failed to insert customer", e);
            throw new RuntimeException("Failed to insert customer", e);
        }
    }

    /**
     * Execute INSERT, UPDATE, or DELETE query and return number of rows affected
     */
    public static int executeUpdate(String query, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            // Set parameters
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            int rowsAffected = statement.executeUpdate();
            Log.info("Update executed: " + rowsAffected + " rows affected");
            return rowsAffected;

        } catch (SQLException e) {
            Log.error("Update failed: " + e.getMessage());
            throw new RuntimeException("Failed to execute update: " + e.getMessage(), e);
        }
    }

    /**
     * Verify customer exists by username
     */
    public static boolean verifyCustomerExists(String username) {
        String query = "SELECT COUNT(*) as count FROM customers WHERE username = ?";
        List<Map<String, Object>> rows = executeQuery(query, username);
        if (rows.isEmpty()) {
            return false;
        }
        Map<String, Object> row = rows.get(0);
        Object v = row.get("count"); // alias, but some drivers uppercase it
        if (v == null) {
            v = row.get("COUNT"); // fallback for uppercase alias
        }
        if (v == null) {
            // as a last resort, try vendor-specific label
            v = row.get("COUNT(*)");
        }
        long count = (v instanceof Number) ? ((Number) v).longValue() : Long.parseLong(String.valueOf(v));
        return count > 0;

    }

    /**
     * Get customer ID by username
     */
    public static int getCustomerID(String username) {
        String query = "SELECT id FROM customers WHERE username = ?";
        List<Map<String, Object>> rows = executeQuery(query, username);
        if (rows.isEmpty()) {
            return -1;
        }
        Map<String, Object> row = rows.get(0);
        Object v = row.get("id");
        if (v == null) {
            v = row.get("ID");
        }
        if (v == null) {
            // if aliasing changes in future
            v = row.get("Id");
        }
        return (v instanceof Number) ? ((Number) v).intValue() : Integer.parseInt(String.valueOf(v));
    }

    /**
     * Clean all data from database
     */
    public static void cleanDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("DELETE FROM transactions");
            stmt.execute("DELETE FROM accounts");
            stmt.execute("DELETE FROM customers");
            Log.info("Database cleaned");

        } catch (SQLException e) {
            Log.error("Failed to clean database", e);
        }
    }

    /**
     * Close database connection
     */
    public static void closeConnection() {
        // No-op: connections are closed by try-with-resources
        Log.info("Connection management handled by try-with-resources");
    }
}


