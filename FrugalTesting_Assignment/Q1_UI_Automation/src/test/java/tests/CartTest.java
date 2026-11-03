
package tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.CartPage;
import utilities.ExtentReportListener;

@Listeners(ExtentReportListener.class)
public class CartTest extends BaseTest {

    // Login and create CartPage object
    private CartPage loginAndGetCartPage() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("standard_user", "secret_sauce");

        return new CartPage(driver);
    }

    // Test 1: Add one product
    @Test
    public void verifySingleProductAddedToCart() {

        CartPage cartPage = loginAndGetCartPage();

        cartPage.addBackpack();
        cartPage.openCart();

        Assert.assertEquals(
                cartPage.getCartItemCount(),
                1,
                "Cart should contain one product."
        );

        System.out.println("Single product added successfully!");
    }

    // Test 2: Add multiple products
    @Test
    public void verifyTwoProductsAddedToCart() {

        CartPage cartPage = loginAndGetCartPage();

        cartPage.addBackpack();
        cartPage.addBikeLight();
        cartPage.openCart();

        Assert.assertEquals(
                cartPage.getCartItemCount(),
                2,
                "Cart should contain exactly two products."
        );

        System.out.println("Two products added successfully!");
    }

    // Test 3: Verify cart item count
    @Test
    public void verifyCartItemCount() {

        CartPage cartPage = loginAndGetCartPage();

        cartPage.addBackpack();
        cartPage.addBikeLight();
        cartPage.openCart();

        int actualCount = cartPage.getCartItemCount();

        Assert.assertEquals(
                actualCount,
                2,
                "Cart item count is incorrect."
        );

        System.out.println("Cart item count verified: " + actualCount);
    }

    // Test 4: Verify product names
    @Test
    public void verifyProductNamesInCart() {

        CartPage cartPage = loginAndGetCartPage();

        cartPage.addBackpack();
        cartPage.addBikeLight();
        cartPage.openCart();

        List<String> productNames = cartPage.getCartItemNames();

        Assert.assertTrue(
                productNames.contains("Sauce Labs Backpack"),
                "Backpack is missing from cart."
        );

        Assert.assertTrue(
                productNames.contains("Sauce Labs Bike Light"),
                "Bike Light is missing from cart."
        );

        System.out.println("Product names verified: " + productNames);
    }

    // Test 5: Remove one product
    @Test
    public void verifyRemoveProductFromCart() {

        CartPage cartPage = loginAndGetCartPage();

        cartPage.addBackpack();
        cartPage.addBikeLight();
        cartPage.openCart();

        cartPage.removeBackpack();

        Assert.assertEquals(
                cartPage.getCartItemCount(),
                1,
                "One product should remain after removal."
        );

        Assert.assertFalse(
                cartPage.getCartItemNames().contains("Sauce Labs Backpack"),
                "Backpack should be removed."
        );

        System.out.println("Product removal verified successfully!");
    }

    // Test 6: Remove all products
    @Test
    public void verifyEmptyCartAfterRemovingAllProducts() {

        CartPage cartPage = loginAndGetCartPage();

        cartPage.addBackpack();
        cartPage.addBikeLight();
        cartPage.openCart();

        cartPage.removeBackpack();
        cartPage.removeBikeLight();

        Assert.assertEquals(
                cartPage.getCartItemCount(),
                0,
                "Cart should be empty after removing all products."
        );

        System.out.println("Empty cart verified successfully!");
    }
}
