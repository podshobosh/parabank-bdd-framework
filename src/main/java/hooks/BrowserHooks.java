package hooks;

import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;
import utils.HoldOn;
import utils.Log;
import utils.ScenarioContext;

public class BrowserHooks {
    private WebDriver driver;

    @Before("@UI or @Test")
    public void setUpBrowser() {
        Log.info("Initializing browser setup...");
        driver = DriverFactory.getDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        String baseUrl = ConfigReader.getProperty("home.page.url");
        Log.info("Navigating to: " + baseUrl);
        driver.get(baseUrl);
        HoldOn.waitForPageToLoad(driver);
        Log.info("Browser setup completed.");
    }

    @After("@UI or @Test")
    public void tearDownBrowser() {
        DriverFactory.quitDriver();
        ScenarioContext.clear();
    }
}
