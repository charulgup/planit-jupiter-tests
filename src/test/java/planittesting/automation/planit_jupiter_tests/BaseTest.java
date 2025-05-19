package planittesting.automation.planit_jupiter_tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class BaseTest {
    // Protected WebDriver instance accessible to subclasses
    protected WebDriver driver;
    
    // Setup method to initialize WebDriver before each test method
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        // Setup ChromeDriver using WebDriverManager to manage driver binaries automatically
        WebDriverManager.chromedriver().setup();
        
        // Instantiate ChromeDriver (open a new Chrome browser window)
        driver = new ChromeDriver();
        
        // Maximize the browser window for better visibility during tests
        driver.manage().window().maximize();
        
        // Navigate to the base URL of the application under test
        driver.get("http://jupiter.cloud.planittesting.com");
    }

    // Tear down method to quit WebDriver after each test method to clean up resources
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            // Close the browser and safely end the WebDriver session
            driver.quit();
        }
    }
    
    // Getter method to provide access to the WebDriver instance in subclasses if needed
    public WebDriver getDriver() {
        return driver;
    }
}
