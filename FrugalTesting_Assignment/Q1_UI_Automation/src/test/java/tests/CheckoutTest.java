
package tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.CartPage;
import pages.CheckoutPage;
import utilities.ExtentReportListener;

@Listeners(ExtentReportListener.class)
public class CheckoutTest extends BaseTest {

    @Test
    public void verifySuccessfulCheckout() {

        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        System.out.println("Login successful!");

        // Step 2: Add products
        CartPage cartPage = new CartPage(driver);
        cartPage.addBackpack();
        cartPage.addBikeLight();

        System.out.println("Products added successfully!");

        // Step 3: Open cart
        cartPage.openCart();

        // Step 4: Verify cart items
        Assert.assertEquals(
                cartPage.getCartItemCount(),
                2,
                "Cart should contain exactly two products"
        );

        System.out.println("Cart contains two products!");

        // Step 5: Checkout
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);

        // Step 6: Enter customer information
        checkoutPage.enterCustomerInformation(
                "Anil",
                "Kumar",
                "534202"
        );

        checkoutPage.clickContinue();

        System.out.println("Checkout overview opened!");

        // Step 7: Finish order
        checkoutPage.clickFinish();

        // Step 8: Verify confirmation
        String actualMessage = checkoutPage.getConfirmationMessage();

        Assert.assertEquals(
                actualMessage,
                "Thank you for your order!",
                "Order confirmation message does not match!"
        );

        System.out.println("Checkout successful!");
        System.out.println("Order confirmation verified!");
     
    }
}
