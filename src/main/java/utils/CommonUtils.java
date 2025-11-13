package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CommonUtils {

    /**
     * Verifies that the current URL matches the expected URL.
     *
     * @param driver      The WebDriver instance.
     * @param expectedUrl The expected URL to verify against.
     * @throws RuntimeException if the URL does not match
     */
    public static void verifyUrl(WebDriver driver, String expectedUrl) {
        // Get the current URL from the browser
        String actualUrl = driver.getCurrentUrl();
        // Normalize both URLs: trim spaces and convert to lowercase for a tolerant comparison
        String normalizedExpectedUrl = expectedUrl.trim().toLowerCase();
        String normalizedActualUrl = actualUrl.trim().toLowerCase();

        // Log the action and the URLs
        Log.info("Verifying the page URL.");
        Log.info("Expected URL (normalized): " + normalizedExpectedUrl);
        Log.info("Actual URL (normalized): " + normalizedActualUrl);

        // Validate the URL (case-insensitive and trimmed)
        if (!normalizedActualUrl.equals(normalizedExpectedUrl)) {
            Log.error("The page URL is incorrect!");
            throw new RuntimeException("Expected URL: " + normalizedExpectedUrl + ", but got: " + normalizedActualUrl);
        }

        // Log success if URLs match
        Log.info("Page URL verified successfully: " + actualUrl);
    }

    /**
     * Verifies that the current page title matches the expected title (case-insensitive and trimmed).
     *
     * @param driver        The WebDriver instance.
     * @param expectedTitle The expected title to verify against.
     * @throws RuntimeException if the title does not match
     */
    public static void verifyTitle(WebDriver driver, String expectedTitle) {
        // Get the current page title from the browser
        String actualTitle = driver.getTitle().trim();

        // Normalize the expected title: trim spaces and convert to lowercase
        String normalizedExpectedTitle = expectedTitle.trim();

        // Log the action and the titles
        Log.info("Verifying the page title.");
        Log.info("Expected Title (normalized): " + normalizedExpectedTitle);
        Log.info("Actual Title (normalized): " + actualTitle);

        // Validate the title
        if (!actualTitle.equals(normalizedExpectedTitle)) {
            Log.error("The page title is incorrect!");
            throw new RuntimeException("Expected Title: \"" + normalizedExpectedTitle + "\", but got: \"" + actualTitle + "\"");
        }

        // Log success if titles match
        Log.info("Page title verified successfully: " + actualTitle);
    }

    /**
     * Gets the value of the specified attribute for a given WebElement.
     *
     * @param element   The WebElement from which to retrieve the attribute.
     * @param attribute The attribute name (e.g., "class", "id", "placeholder").
     * @return The value of the attribute, or null if the attribute is not found.
     */
    public static String getAttribute(WebElement element, String attribute) {
        try {
            return element.getAttribute(attribute);
        } catch (Exception e) {
            System.err.println("Failed to get attribute '" + attribute + "' from element: " + e.getMessage());
            return null;
        }
    }
}
