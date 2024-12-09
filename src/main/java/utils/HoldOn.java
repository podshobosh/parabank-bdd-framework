package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HoldOn {

    private static int defaultWaitTime = ConfigReader.getIntProperty("default.wait.time");
    private static int pageLoadTimeout = ConfigReader.getIntProperty("page.load.timeout");
    private static int elementVisibilityTimeout = ConfigReader.getIntProperty("element.visibility.timeout");

    // Wait until element is visible
    public static void waitForElementToBeVisible(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(elementVisibilityTimeout));
        wait.until(ExpectedConditions.visibilityOf(element));  // Wait until the element is visible
    }

    // Wait for page to load completely
    public static void waitForPageToLoad(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(pageLoadTimeout));
        wait.until(webDriver -> ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    // Wait for an element to be clickable
    public static void waitForElementToBeClickable(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(defaultWaitTime));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    // Wait for element to be present
    public static void waitForElementToBePresent(WebDriver driver, By elementLocator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(defaultWaitTime));
        wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
    }
}
