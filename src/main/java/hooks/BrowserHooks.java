package hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;
import factory.DriverFactory;
import utils.ConfigReader;
import utils.HoldOn;
import utils.Log;

public class BrowserHooks {

    private WebDriver driver;

    @Before("@UI") // Executes before each scenario tagged with @UI

    public void setUpBrowser() {
        Log.info("Initializing browser setup...");
        driver = DriverFactory.getDriver(); // Initialize WebDriver
        driver.manage().deleteAllCookies(); // Clear cookies
        driver.manage().window().maximize(); // Maximize the browser window

        String baseUrl = ConfigReader.getProperty("url");
        Log.info("Navigating to: " + baseUrl);
        driver.get(baseUrl); // Navigate to the base URL

        HoldOn.waitForPageToLoad(driver); // Ensure page is fully loaded
        Log.info("Browser setup completed.");
    }

    @After("@UI") // Executes after each scenario tagged with @UI
    public void tearDownBrowser() {
        DriverFactory.quitDriver(); // Quit WebDriver and clean up
        Log.info("Browser teardown completed.");
    }
}
