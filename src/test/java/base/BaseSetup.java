package base;

import org.openqa.selenium.WebDriver;

public class BaseSetup {

    private static WebDriver driver;

    public void setupBrowser() {
        // Add browser setup code here
    }

    public void quitBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}