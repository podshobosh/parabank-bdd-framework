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

public final class DriverFactory {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static WebDriver initializeDriver() {
        String browser = ConfigReader.getProperty("browser", "chrome").toLowerCase();
        boolean headless = ConfigReader.getBooleanProperty("headless", true);

        WebDriver webDriver;
        switch (browser) {
            case "chrome":
                Log.info("Launching Chrome browser. headless=" + headless);
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                }
                webDriver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                Log.info("Launching Firefox browser. headless=" + headless);
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                webDriver = new FirefoxDriver(firefoxOptions);
                break;
            case "edge":
                Log.info("Launching Edge browser. headless=" + headless);
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless=new");
                }
                webDriver = new EdgeDriver(edgeOptions);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getIntProperty("page.load.timeout", 30)));
        webDriver.manage().timeouts().implicitlyWait(Duration.ZERO);
        DRIVER.set(webDriver);
        return webDriver;
    }

    public static WebDriver getDriver() {
        WebDriver webDriver = DRIVER.get();
        return webDriver == null ? initializeDriver() : webDriver;
    }

    public static WebDriver peekDriver() {
        return DRIVER.get();
    }

    public static void quitDriver() {
        WebDriver webDriver = DRIVER.get();
        if (webDriver != null) {
            Log.info("Tearing down the browser...");
            webDriver.quit();
            DRIVER.remove();
        }
    }
}
