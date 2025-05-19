package planittesting.automation.planit_jupiter_tests;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    // Protected WebDriver instance available to subclasses (page classes)
    protected WebDriver driver;

    // Constructor accepts WebDriver instance and initializes WebElements with PageFactory
    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Returns the current page's title
    public String getPageTitle() {
        return driver.getTitle();
    }

    // Waits until the page's document.readyState is "complete" to ensure full page load
    public void waitForPageToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Using a JavaScript executor to poll the browser until the page loading is finished
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
            .executeScript("return document.readyState").equals("complete"));
    }
}
