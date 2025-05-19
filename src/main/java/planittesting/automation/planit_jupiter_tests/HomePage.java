package planittesting.automation.planit_jupiter_tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    // Link element to navigate to the Contact page
    @FindBy(linkText = "Contact")
    private WebElement contactLink;

    // Link element to navigate to the Shop page
    @FindBy(linkText = "Shop")
    private WebElement shopLink;

    // Constructor calls BasePage constructor to initialize WebDriver and elements
    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Clicks the Contact link on the home page and returns a new ContactPage object
    public ContactPage goToContactPage() {
        contactLink.click();
        return new ContactPage(driver);
    }

    // Clicks the Shop link on the home page and returns a new ShopPage object
    public ShopPage goToShopPage() {
        shopLink.click();
        return new ShopPage(driver);
    }
}
