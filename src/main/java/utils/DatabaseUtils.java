package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public final class DatabaseUtils {
    private static volatile boolean driverInitialized = false;

    private DatabaseUtils() {
    }

    public static Connection getConnection() throws SQLException {
        initializeDriverOnce();

        String url = Optional.ofNullable(ConfigReader.getProperty("DB_URL")).map(String::trim).orElse(null);
        String user = Optional.ofNullable(ConfigReader.getProperty("DB_USER")).map(String::trim).orElse("sa");
        String pass = Optional.ofNullable(ConfigReader.getProperty("DB_PASS")).map(String::trim).orElse("");

        if (url == null || url.isEmpty()) {
            throw new IllegalStateException("DB_URL is not configured");
        }
        return DriverManager.getConnection(url, user, pass);
    }

    public static boolean isDatabaseReachable() {
        try (Connection ignored = getConnection()) {
            return true;
        } catch (SQLException e) {
            Log.error("Database connectivity check failed", e);
            return false;
        }
    }

    /**
     * DANGER: Wipes Parabank data in a local Docker instance only when explicitly requested by hooks.
     * Test validation should use repository classes under src/test/java/db instead of destructive cleanup.
     */
    public static void dangerouslyWipeParabankDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM TRANSACTION");
            stmt.execute("DELETE FROM ACCOUNT");
            stmt.execute("DELETE FROM CUSTOMER");
            Log.info("Parabank database wiped (CUSTOMER/ACCOUNT/TRANSACTION)");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to wipe Parabank database", e);
        }
    }

    private static void initializeDriverOnce() {
        if (!driverInitialized) {
            synchronized (DatabaseUtils.class) {
                if (!driverInitialized) {
                    String driverClass = ConfigReader.getProperty("DB_DRIVER");
                    if (driverClass != null && !driverClass.trim().isEmpty()) {
                        try {
                            Class.forName(driverClass.trim());
                            Log.info("Loaded JDBC driver: " + driverClass.trim());
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException("JDBC driver class not found: " + driverClass, e);
                        }
                    }
                    driverInitialized = true;
                }
            }
        }
    }
}
