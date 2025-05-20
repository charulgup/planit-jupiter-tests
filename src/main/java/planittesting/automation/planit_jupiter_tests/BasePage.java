package planittesting.automation.planit_jupiter_tests;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    // Protected WebDriver instance available to subclasses (page classes)
    protected WebDriver driver;
	protected WebDriverWait wait;

    // Constructor accepts WebDriver instance and initializes WebElements with PageFactory
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    
}
