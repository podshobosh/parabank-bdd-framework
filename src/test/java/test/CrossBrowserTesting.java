package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CrossBrowserTesting {

    public static void main (String[] args){
        String browserType = "firefox";

        WebDriver driver = null;

            switch (browserType.toLowerCase()) {

                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                default:
                    System.out.println("Invalid browser type is selected");
                    return;
            }
        driver.manage().window().maximize();
        driver.get("https://seleniums.com/");
        System.out.println("Page title and URL: " + driver.getTitle() + " " + driver.getCurrentUrl());
        driver.quit();
    }
}