package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    // Initialize ExtentReports with Spark Reporter
    public static ExtentReports getExtentReports() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("Reports/SparkReport.html");
            spark.config().setDocumentTitle("Automation Test Report");
            spark.config().setReportName("Test Execution Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }

    // Create a new test and store in ThreadLocal for parallel runs
    public static ExtentTest createTest(String testName) {
        ExtentTest extentTest = getExtentReports().createTest(testName);
        test.set(extentTest);
        return extentTest;
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    // Attach Base64 screenshot to the report
    public static void attachScreenshot(String base64Image, String description) {
        getTest().info(description, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}


/*
 * Take a File-Based Screenshot:
 * String screenshotPath = ScreenshotUtils.takeScreenshot(driver, "TestStepName");
 */

/*
* Take a Base64 Screenshot (e.g., for ExtentReports):
*String base64Screenshot = ScreenshotUtils.takeScreenshotAsBase64(driver);
*/
