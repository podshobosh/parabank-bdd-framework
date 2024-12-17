package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    private static final String SCREENSHOT_DIR = ConfigReader.getProperty("screenshot.dir");

    /**
     * Captures a screenshot of the current browser state and saves it with a timestamped filename.
     *
     * @param driver   WebDriver instance.
     * @param testName Descriptive name for the test or step being captured.
     * @return The absolute path of the saved screenshot file.
     */
    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            // Generate timestamp and file name
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotPath = SCREENSHOT_DIR + File.separator + testName + "_" + timestamp + ".png";

            // Take screenshot as a file
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(screenshotPath);

            // Ensure the directory exists
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }

            // Copy the screenshot to the destination
            FileUtils.copyFile(srcFile, destFile);

            Log.info("Screenshot saved at the designated directory");
            return destFile.getAbsolutePath();
        } catch (Exception e) {
            Log.error("Failed to capture screenshot for test: " + testName, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Captures a screenshot as a Base64-encoded string for embedding in reports.
     *
     * @param driver WebDriver instance.
     * @return Base64 encoded screenshot.
     */
    public static String takeScreenshotAsBase64(WebDriver driver) {
        try {
            // Take screenshot as Base64
            String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            Log.info("Screenshot captured as Base64.");
            return base64Screenshot;
        } catch (Exception e) {
            Log.error("Failed to capture Base64 screenshot.", e);
            throw new RuntimeException(e);
        }
    }
}