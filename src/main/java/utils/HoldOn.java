package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HoldOn {

    private static int defaultWaitTime = ConfigReader.getIntProperty("default.wait.time");
    private static int pageLoadTimeout = ConfigReader.getIntProperty("page.load.timeout");
    private static int elementVisibilityTimeout = ConfigReader.getIntProperty("element.visibility.timeout");

    /**
     * @deprecated Avoid using Thread.sleep().
     * Use explicit waits instead.
     * This method exists ONLY for debugging and should not be used in framework logic.
     */
    @Deprecated
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Sleep interrupted: " + e.getMessage());
        }
    }

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

    public static void waitForElementsToBeVisible(WebDriver driver, List<WebElement> elements) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(elementVisibilityTimeout));
        for (WebElement e : elements) {
            wait.until(ExpectedConditions.visibilityOf(e));// Wait until the element is visible
        }

    }

    /**
     * Safely clicks a single element: waits clickable, then normal click, JS fallback if intercepted.
     */
    public static void safeClick(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(element));
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }

    }


    /**
     * Finds a list of elements by locator and clicks the one that matches the given text.
     * - Re-finds the list fresh each time to avoid stale elements.
     * - Retries automatically until the element is found and clicked, or timeout is reached.
     */
    public static void clickOnElementInList(WebDriver driver, By locator, String target) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(defaultWaitTime));
        wait.ignoring(StaleElementReferenceException.class)
                .ignoring(ElementClickInterceptedException.class)
                .until(driver1 -> {

                    List<WebElement> elements = driver1.findElements(locator);

                    for (WebElement e : elements) {
                        try {
                            String elementText = e.getText().trim();

                            if (elementText.equalsIgnoreCase(target)) {
                                // Scroll element into view
                                ((JavascriptExecutor) driver1).executeScript(
                                        "arguments[0].scrollIntoView({block: 'center'});", e);

                                // Try normal click first
                                try {
                                    e.click();
                                    return true; // Success
                                } catch (ElementClickInterceptedException ex) {
                                    // Fallback to JavaScript click
                                    ((JavascriptExecutor) driver1).executeScript("arguments[0].click();", e);
                                    return true; // Success
                                }
                            }
                        } catch (StaleElementReferenceException ex) {
                            // Element went stale, will retry in next iteration
                            return false;
                        }
                    }
                    return false; // Element not found, retry
                });


    }

    /**
     * This method is APP SPECIFIC - will not work as a universal method.
     * Only works for selenium UI
     */
    public static void waitForElementToDisappear(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(defaultWaitTime));
        try {
            // Wait for element to become invisible
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    locator
            ));
        } catch (TimeoutException e) {
            // Preloader might not exist on some pages, that's okay
            Log.info("Preloader not found or already disappeared");

        }
    }

    public static List<WebElement> waitForElementsToBePresent(WebDriver driver, By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public static void waitForTextToBePresent(
            WebDriver driver,
            WebElement element,
            String expectedText
    ) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(defaultWaitTime));
        wait.until(ExpectedConditions.textToBePresentInElementValue(element, expectedText));
    }


}
