package utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {
    // Singleton instance of ExtentReports for generating the report
    private static ExtentReports extent;
    
    // ExtentTest instance to log steps and results for individual tests
    private static ExtentTest test;

    /**
     * Initializes the ExtentReports instance if not already initialized.
     * Sets up the reporter output path to "test-output/ExtentReport.html".
     * This method should be called once before running tests to start the report.
     */
    public static void initReport() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
    }

    /**
     * Flushes the ExtentReports instance to write all logged information to the report file.
     * Should be called after all tests have completed.
     */
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    /**
     * Creates a new test entry in the Extent report with the specified test name.
     * This allows logging steps and results under this test.
     * @param testName The name of the test to create in the report.
     */
    public static void createTest(String testName) {
        test = extent.createTest(testName);
    }

    /**
     * Provides access to the current ExtentTest instance to log info, pass/fail, or other steps.
     * @return The ExtentTest instance for the currently active test.
     */
    public static ExtentTest getTest() {
        return test;
    }
}
