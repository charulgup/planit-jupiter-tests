package planittesting.automation.planit_jupiter_tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class ShopPage extends BasePage {

	// Cart link to navigate to the cart page
	@FindBy(partialLinkText = "Cart")
	private WebElement cartLink;

	public ShopPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Step 1: Buy a specified quantity of a given product. Used for adding products
	 * like Stuffed Frog, Fluffy Bunny, and Valentine Bear.
	 *
	 * @param productName Name of the product to buy (must match visible text)
	 * @param quantity    Number of times to click the 'Buy' button
	 */
	public void buyItem(String productName, int quantity) {

		for (int i = 0; i < quantity; i++) {
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//h4[text()='" + productName + "']/following-sibling::p/a[text()='Buy']"))).click();
		}
	}

	/**
	 * Utility: Waits for the 'Checkout' button to be clickable. Ensures the page is
	 * fully loaded before proceeding.
	 */
	public void waitForCheckout() {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Checkout']")));
	}

	/**
	 * Step 2: Navigate to the cart page. Clicks on the 'Cart' link after ensuring
	 * the page is ready.
	 */
	public void goToCart() {
		//waitForPageToLoad();
		cartLink.click();
	}

	/**
	 * Step 4: Verifies the price format of a product in the cart. Ensures price
	 * follows the format like $10.99.
	 *
	 * @param productName Product name to locate in the cart.
	 * @return true if price is correctly formatted, false otherwise.
	 */
	public boolean verifyProductPrice(String productName) {
		try {
			WebElement priceElement = driver.findElement(
					By.xpath("//td[normalize-space(text())='" + productName + "']/following-sibling::td[1]"));
			return priceElement.getText().matches("\\$\\d+\\.\\d{2}");
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Step 3: Verifies that the subtotal for a product is correct. Calculates
	 * expected subtotal as unit price × quantity and compares with displayed value.
	 *
	 * @param productName Product name to verify.
	 * @param quantity    Quantity of the product added to cart.
	 * @return true if displayed subtotal is accurate, false otherwise.
	 */
	public boolean verifySubtotal(String productName, int quantity) {
		try {
			String unitPriceText = driver
					.findElement(
							By.xpath("//td[normalize-space(text())='" + productName + "']/following-sibling::td[1]"))
					.getText().replace("$", "");
			double unitPrice = Double.parseDouble(unitPriceText);

			String subtotalText = driver
					.findElement(
							By.xpath("//td[normalize-space(text())='" + productName + "']/following-sibling::td[3]"))
					.getText().replace("$", "");
			double expectedSubtotal = quantity * unitPrice;
			double actualSubtotal = Double.parseDouble(subtotalText);

			return Math.abs(expectedSubtotal - actualSubtotal) < 0.01;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Optional Utility: Verifies that the cart's total matches the sum of all
	 * product subtotals. This helps ensure no miscalculations across the cart.
	 *
	 * @return true if cart total is valid, false otherwise.
	 */
	public boolean verifyTotal() {
		try {
			List<WebElement> subtotalElements = driver.findElements(
					By.xpath("//td[@class='ng-binding' and starts-with(text(),'$') and preceding-sibling::td[3]]"));

			double total = 0.0;
			for (WebElement element : subtotalElements) {
				String text = element.getText().replace("$", "").trim();
				double value = Double.parseDouble(text);
				total += value;
			}

			WebElement totalElement = driver.findElement(By.xpath("//strong[contains(text(),'Total')]"));

			String displayedTotal = totalElement.getText().split(":")[1].trim();
			double actualTotal = Double.parseDouble(displayedTotal);

			return Math.abs(total - actualTotal) < 0.01;
		} catch (Exception e) {
			return false;
		}
	}
}
