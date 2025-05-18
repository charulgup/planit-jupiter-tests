package planittesting.automation.planit_jupiter_tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(linkText = "Contact")
    private WebElement contactLink;

    @FindBy(linkText = "Shop")
    private WebElement shopLink;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public ContactPage goToContactPage() {
        contactLink.click();
        return new ContactPage(driver);
    }

    public ShopPage goToShopPage() {
        shopLink.click();
        return new ShopPage(driver);
    }
}
