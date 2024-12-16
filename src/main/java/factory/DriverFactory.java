package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.ConfigReader;
import utils.Log;

import java.time.Duration;

public class DriverFactory {
    private static WebDriver driver;

    // Method to initialize the WebDriver
    public static WebDriver initializeDriver() {
        String browser = ConfigReader.getProperty("browser");
        String headless = ConfigReader.getProperty("headless");

        try {
            switch (browser) {
                case "chrome":
                    Log.info("Launching Chrome browser...");
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (headless.equals("true")) {
                        Log.info("Running Chrome in headless mode.");
                        chromeOptions.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080");
                    }
                    driver = new ChromeDriver(chromeOptions);
                    break;

                case "firefox":
                    Log.info("Launching Firefox browser...");
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (headless.equals("true")) {
                        Log.info("Running Firefox in headless mode.");
                        firefoxOptions.addArguments("--headless");
                    }
                    driver = new FirefoxDriver(firefoxOptions);
                    break;

                case "edge":
                    Log.info("Launching Edge browser...");
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (headless.equals("true")) {
                        Log.info("Running Edge in headless mode.");
                        edgeOptions.addArguments("--headless");
                    }
                    driver = new EdgeDriver(edgeOptions);
                    break;

                default:
                    throw new RuntimeException("Unsupported browser: " + browser);
            }
        } catch (Exception e) {
            Log.error("Failed to initialize the WebDriver for browser: " + browser, e);
            throw new RuntimeException(e);
        }
        return driver;
    }

    // Getter for the WebDriver
    public static WebDriver getDriver() {
        if (driver == null) {
            driver = initializeDriver();
        }
        return driver;
    }

    // Method to quit the driver
    public static void quitDriver() {
        if (driver != null) {
            Log.info("Tearing down the browser...");
            driver.quit();
            driver = null;
        }
    }
}