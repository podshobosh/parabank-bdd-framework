package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.DatabaseUtils;

public class DBHooks {

    // hooks/Hooks.java
    @Before(order = 0)
    public void initDbOnce() {
        DatabaseUtils.initDatabase();
    }

    @After(order = 0)
    public void cleanDb() {
        // If desired per-scenario cleanup (or do it @Before if you prefer)
        DatabaseUtils.cleanDatabase();
    }
}
