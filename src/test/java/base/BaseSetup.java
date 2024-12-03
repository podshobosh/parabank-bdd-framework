package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseSetup {
    private static WebDriver driver;
    private final Properties properties = new Properties();

    // Constructor to load properties file
    public BaseSetup() {
        try {
            String fileFilePath = System.getProperty("user.dir")
                    + "/src/test/resources/config/application-config.properties";
            File propertiesFile = new File(fileFilePath);
            FileInputStream propertyFileInputStream = new FileInputStream(propertiesFile);

            properties.load(propertyFileInputStream);
        } catch (IOException ex) {
            throw new RuntimeException("Cannot read or load config file " + ex.getMessage());
        }
    }

    // Method to open the browser by reading from the properties file
    public void openBrowser() {
        // Read browser type and headless configuration from properties file
        String browserType = this.properties.getProperty("browser.type");
        boolean isHeadless = Boolean.parseBoolean(this.properties.getProperty("browser.headless"));

        if (browserType.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            if (isHeadless) options.addArguments("--headless");
            driver = new ChromeDriver(options);
        } else if (browserType.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            if (isHeadless) options.addArguments("--headless");
            driver = new EdgeDriver(options);
        } else if (browserType.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            if (isHeadless) options.addArguments("--headless");
            driver = new FirefoxDriver(options);
        } else {
            throw new RuntimeException("Wrong browser type specified");
        }

        driver.manage().window().maximize();
        String url = this.properties.getProperty("app.url");
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    // Method to open the browser by accepting browser type as a parameter
    public void openBrowser(String browserType) {
        boolean isHeadless = Boolean.parseBoolean(this.properties.getProperty("browser.headless"));

        if (browserType.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            if (isHeadless) options.addArguments("--headless");
            driver = new ChromeDriver(options);
        } else if (browserType.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            if (isHeadless) options.addArguments("--headless");
            driver = new EdgeDriver(options);
        } else if (browserType.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            if (isHeadless) options.addArguments("--headless");
            driver = new FirefoxDriver(options);
        } else {
            throw new RuntimeException("Wrong browser type specified");
        }

        driver.manage().window().maximize();
        String url = this.properties.getProperty("app.url");
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public WebDriver getDriver() {
        return driver;
    }

    // Method to quit the browser
    public void quitBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}