package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class ScreenshotUtil {

    /**
     * Captures a screenshot of the current browser window.
     * The screenshot is saved in the "test-output/screenshots" directory with a unique filename.
     *
     * @param driver   The WebDriver instance used to capture the screenshot.
     * @param testName The name of the test, used as part of the screenshot filename.
     * @return The full file path where the screenshot is saved.
     */
    public static String captureScreenshot(WebDriver driver, String testName) {
        // Generate a timestamp string in "yyyyMMdd_HHmmss" format to make filename unique
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        
        // Compose the screenshot filename using testName and timestamp
        String fileName = testName + "_" + timeStamp + ".png";

        // Get the current project directory path dynamically
        String projectPath = System.getProperty("user.dir");
        
        // Define the full path where screenshot will be saved
        String screenshotPath = projectPath + "/test-output/screenshots/" + fileName;

        // Take the screenshot and store it as a temporary file
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        
        // Create the destination file object for saving screenshot
        File destFile = new File(screenshotPath);

        try {
            // Copy the screenshot from the source temporary file to the destination path
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            // Print stack trace if an error occurs while saving the screenshot
            e.printStackTrace();
        }

        // Return the full path of the saved screenshot for logging or reporting purposes
        return screenshotPath;
    }
}
