package utils;

import java.sql.*;
import java.util.*;


public final class DatabaseUtils {

    private DatabaseUtils() {
        // Utility class: prevent instantiation
    }


    // NOTE: This project connects to the Parabank Docker DB (HSQLDB) which already has tables like
    // CUSTOMER / ACCOUNT / TRANSACTION. Do NOT create schema here; only query/validate.

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
        String user = Optional.ofNullable(ConfigReader.getProperty("DB_USER")).map(String::trim).orElse("sa");
        String pass = Optional.ofNullable(ConfigReader.getProperty("DB_PASS")).map(String::trim).orElse("");

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
                    Map<String, Object> row = new LinkedHashMap<>();
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

                Log.info("Query executed: " + results.size() + " rows returned | SQL=" + query);
                return results;
            }

        } catch (SQLException e) {
            Log.error("Query failed", e);
            throw new RuntimeException("Failed to execute query", e);
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
            Log.info("Update executed: " + rowsAffected + " rows affected | SQL=" + query);
            return rowsAffected;

        } catch (SQLException e) {
            Log.error("Update failed: " + e.getMessage());
            throw new RuntimeException("Failed to execute update: " + e.getMessage(), e);
        }
    }

    /**
     * Execute a query expected to return a single row.
     * Returns null if no rows are found.
     */
    public static Map<String, Object> queryForRow(String query, Object... params) {
        List<Map<String, Object>> rows = executeQuery(query, params);
        return rows.isEmpty() ? null : rows.get(0);
    }

    /**
     * Execute a query expected to return a single integer value in the first column.
     * Returns null if no rows are found.
     */
    public static Integer queryForInt(String query, Object... params) {
        Map<String, Object> row = queryForRow(query, params);
        if (row == null || row.isEmpty()) return null;
        Object v = row.values().iterator().next();
        if (v == null) return null;
        return (v instanceof Number) ? ((Number) v).intValue() : Integer.parseInt(String.valueOf(v));
    }

    /**
     * Verify customer exists by username
     */
    public static boolean verifyCustomerExists(String username) {
        // Parabank schema uses table CUSTOMER and column USERNAME (uppercase identifiers)
        String query = "SELECT COUNT(*) AS CNT FROM CUSTOMER WHERE USERNAME = ?";
        Integer cnt = queryForInt(query, username);
        return cnt != null && cnt > 0;
    }

    /**
     * Get customer ID by username
     */
    public static int getCustomerID(String username) {
        // Parabank schema uses CUSTOMER.ID and CUSTOMER.USERNAME
        String query = "SELECT ID FROM CUSTOMER WHERE USERNAME = ?";
        Integer id = queryForInt(query, username);
        return id == null ? -1 : id;
    }

    /**
     * DANGER: Wipes Parabank data in your local Docker instance.
     * Use unique test data instead of wiping whenever possible.
     */
    public static void dangerouslyWipeParabankDatabase() {
        // WARNING: This wipes Parabank data in your local Docker instance.
        // Order matters due to FK relationships.
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("DELETE FROM TRANSACTION");
            stmt.execute("DELETE FROM ACCOUNT");
            stmt.execute("DELETE FROM CUSTOMER");
            Log.info("Parabank database wiped (CUSTOMER/ACCOUNT/TRANSACTION)");

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
