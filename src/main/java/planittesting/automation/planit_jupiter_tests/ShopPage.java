package planittesting.automation.planit_jupiter_tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

public class ShopPage extends BasePage {

    private static final Logger logger = Logger.getLogger(ShopPage.class.getName());

    // Link element to navigate to the Cart page
    @FindBy(partialLinkText = "Cart")
    private WebElement cartLink;

    // Constructor calls BasePage constructor to initialize driver and elements
    public ShopPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Buys a specified quantity of a product by clicking the 'Buy' button multiple times.
     * Uses explicit wait to ensure the Buy button is clickable before each click.
     * @param productName The exact name of the product to buy.
     * @param quantity The number of times to click Buy for the product.
     */
    public void buyItem(String productName, int quantity) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (int i = 0; i < quantity; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//h4[text()='" + productName + "']/following-sibling::p/a[text()='Buy']"))
            ).click();
        }
    }

    /**
     * Waits explicitly for the Checkout button to be clickable.
     * Useful to ensure page readiness before proceeding to checkout.
     */
    public void waitForCheckout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Checkout']")));
    }

    /**
     * Clicks the Cart link to navigate to the cart page.
     * Waits for the current page to load before clicking.
     */
    public void goToCart() {
        waitForPageToLoad();
        cartLink.click();
    }

    /**
     * Verifies that the price of the specified product is displayed and follows a currency format (e.g., $12.34).
     * @param productName The product name to verify.
     * @return true if price is found and matches expected format; false otherwise.
     */
    public boolean verifyProductPrice(String productName) {
        try {
            // Locate the price element next to the product name in the table
            WebElement priceElement = driver.findElement(By.xpath(
                "//td[normalize-space(text())='" + productName + "']/following-sibling::td[1]"
            ));
            // Check price matches currency format like $12.34
            return priceElement.getText().matches("\\$\\d+\\.\\d{2}");
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Verifies the subtotal for a product is correct based on unit price and quantity.
     * @param productName The product name.
     * @param quantity The quantity bought.
     * @return true if subtotal matches quantity * unit price within a small tolerance, false otherwise.
     */
    public boolean verifySubtotal(String productName, int quantity) {
        try {
            // Extract unit price text and parse to double
            String unitPriceText = driver.findElement(By.xpath(
                "//td[normalize-space(text())='" + productName + "']/following-sibling::td[1]"
            )).getText().replace("$", "");
            logger.info("Unit price text for product '" + productName + "': " + unitPriceText);
            double unitPrice = Double.parseDouble(unitPriceText);

            // Extract subtotal text and parse to double
            String subtotalText = driver.findElement(By.xpath(
                "//td[normalize-space(text())='" + productName + "']/following-sibling::td[3]"
            )).getText().replace("$", "");
            logger.info("SubTotal text for product '" + productName + "': " + subtotalText);
            double expectedSubtotal = quantity * unitPrice;
            double actualSubtotal = Double.parseDouble(subtotalText);

            // Compare expected vs actual subtotal with a small delta
            return Math.abs(expectedSubtotal - actualSubtotal) < 0.01;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifies that the total shown in the cart matches the sum of all subtotals.
     * @return true if total matches sum of subtotals within tolerance; false otherwise.
     */
    public boolean verifyTotal() {
        try {
            // Find all subtotal elements in the cart table
            List<WebElement> subtotalElements = driver.findElements(
                By.xpath("//td[@class='ng-binding' and starts-with(text(),'$') and preceding-sibling::td[3]]")
            );

            double total = 0.0;
            for (WebElement element : subtotalElements) {
                String text = element.getText().replace("$", "").trim();
                double value = Double.parseDouble(text);
                logger.info("Subtotal value found: $" + value);
                total += value;
            }
            logger.info("Subtotal value found TOTAL: $" + total);

            // Find the displayed total amount in the UI
            WebElement totalElement = driver.findElement(
                By.xpath("//strong[contains(text(),'Total')]")
            );

            // Extract numeric part of total text after colon, trim whitespace
            String displayedTotal = totalElement.getText().split(":")[1].trim();
            double actualTotal = Double.parseDouble(displayedTotal);
            logger.info("Displayed total in UI: $" + actualTotal);

            // Compare calculated total vs displayed total with tolerance
            boolean result = Math.abs(total - actualTotal) < 0.01;
            logger.info("Total verification result: " + result);
            return result;

        } catch (Exception e) {
            // In case of error (e.g., elements not found), return false
            return false;
        }
    }
}
