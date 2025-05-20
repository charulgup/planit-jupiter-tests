package planittesting.automation.planit_jupiter_tests;

import org.testng.annotations.Test;

public class ShoppingCartTests extends BaseTest {

	@Test
	public void testShoppingCartFunctionality() {
		// Step 1: From the home page, navigate to the shop page
		ShopPage shop = new HomePage(driver).goToShopPage();

		// Step 1: Buy 2 Stuffed Frogs
		shop.buyItem("Stuffed Frog", 2);

		// Step 1: Buy 5 Fluffy Bunnies
		shop.buyItem("Fluffy Bunny", 5);

		// Step 1: Buy 3 Valentine Bears
		shop.buyItem("Valentine Bear", 3);

		// Step 2: Navigate to the cart page to review purchases
		shop.goToCart();

		// Wait for the checkout/cart page to load completely
		shop.waitForCheckout();

		// Step 4: Verify the price for each product is displayed correctly
		assert shop.verifyProductPrice("Stuffed Frog");
		assert shop.verifyProductPrice("Fluffy Bunny");
		assert shop.verifyProductPrice("Valentine Bear");

		// Step 3: Verify the subtotal for each product matches quantity * price
		assert shop.verifySubtotal("Stuffed Frog", 2);
		assert shop.verifySubtotal("Fluffy Bunny", 5);
		assert shop.verifySubtotal("Valentine Bear", 3);

		// Verify the total price in cart equals the sum of all subtotals
		assert shop.verifyTotal() : "Cart total does not match sum of subtotals.";
	}
}
