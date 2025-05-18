package planittesting.automation.planit_jupiter_tests;

import org.testng.annotations.Test;

public class ShoppingCartTests extends BaseTest {

    @Test
    public void testShoppingCartFunctionality() {
        ShopPage shop = new HomePage(driver).goToShopPage();
        shop.waitForPageToLoad();

        shop.buyItem("Stuffed Frog", 2);
        shop.buyItem("Fluffy Bunny", 5);
        shop.buyItem("Valentine Bear", 3);

        shop.goToCart();
        shop.waitForCheckout();

        assert shop.verifyProductPrice("Stuffed Frog");
        assert shop.verifyProductPrice("Fluffy Bunny");
        assert shop.verifyProductPrice("Valentine Bear");

        assert shop.verifySubtotal("Stuffed Frog", 2);
        assert shop.verifySubtotal("Fluffy Bunny", 5);
        assert shop.verifySubtotal("Valentine Bear", 3);

        assert shop.verifyTotal() : "Cart total does not match sum of subtotals.";
    }
}
