package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import java.sql.Connection;
import utils.DatabaseUtils;

public class DBHooks {

    // hooks/DBHooks.java
    @Before(order = 0)
    public void verifyDbConnectivity(Scenario scenario) {
        // Sanity check: fail fast if DB is unreachable.
        try (Connection ignored = DatabaseUtils.getConnection()) {
            // ok
        } catch (Exception e) {
            throw new RuntimeException("DB connectivity check failed. Is Parabank Docker running and DB_PORT mapped?", e);
        }
    }

    @After(order = 0)
    public void optionallyWipeDb(Scenario scenario) {
        // Industry practice: do NOT wipe shared/local integration DB per scenario by default.
        // Only wipe when scenario is explicitly tagged and an env flag is enabled.
        boolean tagged = scenario.getSourceTagNames().contains("@db_wipe");
        boolean allowed = "true".equalsIgnoreCase(System.getenv("ALLOW_DB_WIPE"));

        if (tagged && allowed) {
            DatabaseUtils.dangerouslyWipeParabankDatabase();
        }
    }
}
