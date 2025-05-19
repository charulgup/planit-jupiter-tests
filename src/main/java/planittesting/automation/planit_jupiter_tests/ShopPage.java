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

    @FindBy(partialLinkText = "Cart")
    private WebElement cartLink;

    public ShopPage(WebDriver driver) {
        super(driver);
    }
    
    public void buyItem(String productName, int quantity) {
    	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	 
        for (int i = 0; i < quantity; i++) {
        	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h4[text()='" + productName + "']/following-sibling::p/a[text()='Buy']"))).click();
            //driver.findElement(By.xpath("//h4[text()='" + productName + "']/following-sibling::p/a[text()='Buy']")).click();
        }
    }
    
    public void waitForCheckout()
    {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Checkout']")));
}

    public void goToCart() {
    	waitForPageToLoad();
        cartLink.click();
    }

    public boolean verifyProductPrice(String productName) {
        try {
            WebElement priceElement = driver.findElement(By.xpath("//td[normalize-space(text())='" + productName + "']/following-sibling::td[1]2"));
            return priceElement.getText().matches("\\$\\d+\\.\\d{2}");
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean verifySubtotal(String productName, int quantity) {
        try {
            String unitPriceText = driver.findElement(By.xpath("//td[normalize-space(text())='" + productName + "']/following-sibling::td[1]")).getText().replace("$", "");
            logger.info("Unit price text for product '" + productName + "': " + unitPriceText);
            double unitPrice = Double.parseDouble(unitPriceText);

            String subtotalText = driver.findElement(By.xpath("//td[normalize-space(text())='" + productName + "']/following-sibling::td[3]")).getText().replace("$", "");
            logger.info("SubTotal text for product '" + productName + "': " + subtotalText);
            double expectedSubtotal = quantity * unitPrice;
            double actualSubtotal = Double.parseDouble(subtotalText);

            return Math.abs(expectedSubtotal - actualSubtotal) < 0.01;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyTotal() {
        try {
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

            WebElement totalElement = driver.findElement(
                By.xpath("//strong[contains(text(),'Total')]")
                
            );
           
           
            String displayedTotal = totalElement.getText().split(":")[1].trim();
            
            double actualTotal = Double.parseDouble(displayedTotal);
            logger.info("Displayed total in UI: $" + actualTotal);

            boolean result = Math.abs(total - actualTotal) < 0.01;
            logger.info("Total verification result: " + result);
            return result;

        } catch (Exception e) {
            //logger.error("Error while verifying total: ", e);
            return false;
        }
    
    
    }
}
