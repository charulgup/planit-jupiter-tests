package TestListener;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

import planittesting.automation.planit_jupiter_tests.BaseTest;
import utils.ExtentReportManager;
import utils.ScreenshotUtil;

public class TestListener implements ITestListener {

    /**
     * Called before any test starts.
     * Initializes the ExtentReports report instance.
     */
    @Override
    public void onStart(ITestContext context) {
        ExtentReportManager.initReport();
    }

    /**
     * Called when each test method starts.
     * Creates a new test entry in the Extent report using the test method name.
     */
    @Override
    public void onTestStart(ITestResult result) {
        ExtentReportManager.createTest(result.getMethod().getMethodName());
    }

    /**
     * Called when a test method passes successfully.
     * Logs the pass status in the Extent report.
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.getTest().pass("Test passed");
    }

    /**
     * Called when a test method fails.
     * Logs the failure with exception details.
     * Captures a screenshot and attaches it to the report if WebDriver is available.
     */
    @Override
    public void onTestFailure(ITestResult result) {
        // Log failure with throwable details
        ExtentReportManager.getTest().log(Status.FAIL, "Test Failed: " + result.getThrowable());

        // Get the test class instance to retrieve WebDriver from BaseTest
        Object testInstance = result.getInstance();
        if (testInstance instanceof BaseTest) {
            WebDriver driver = ((BaseTest) testInstance).getDriver();

            // Capture screenshot using utility method and get the file path
            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getMethod().getMethodName());
            try {
                // Attach screenshot to Extent report
                ExtentReportManager.getTest().addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Warn if WebDriver is not accessible, so screenshot cannot be captured
            ExtentReportManager.getTest().log(Status.WARNING, "WebDriver not found. Screenshot not captured.");
        }
    }

    /**
     * Called when a test method is skipped.
     * Logs the skip status in the Extent report.
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest().skip("Test skipped");
    }

    /**
     * Called after all tests have finished.
     * Flushes and writes the Extent report to disk.
     */
    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flushReport();
    }
}
