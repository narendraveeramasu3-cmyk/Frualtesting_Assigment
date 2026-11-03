
package pages;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Product locators
    private By backpackButton =
            By.id("add-to-cart-sauce-labs-backpack");

    private By bikeLightButton =
            By.id("add-to-cart-sauce-labs-bike-light");

    // Remove product locators
    private By removeBackpackButton =
            By.id("remove-sauce-labs-backpack");

    private By removeBikeLightButton =
            By.id("remove-sauce-labs-bike-light");

    // Shopping cart locator
    private By cartButton =
            By.className("shopping_cart_link");

    // Cart item locators
    private By cartItems =
            By.className("cart_item");

    private By cartItemNames =
            By.className("inventory_item_name");

    // Checkout locators
    private By checkoutButton =
            By.id("checkout");

    private By firstNameField =
            By.id("first-name");

    // Constructor
    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );
    }

    // Add Backpack
    public void addBackpack() {
        wait.until(
                ExpectedConditions.elementToBeClickable(backpackButton)
        ).click();
    }

    // Add Bike Light
    public void addBikeLight() {
        wait.until(
                ExpectedConditions.elementToBeClickable(bikeLightButton)
        ).click();
    }

    // Open shopping cart
    public void openCart() {
        wait.until(
                ExpectedConditions.elementToBeClickable(cartButton)
        ).click();

        wait.until(
                ExpectedConditions.urlContains("cart.html")
        );

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        checkoutButton
                )
        );

        System.out.println("Cart page opened successfully!");
    }

    // Get cart item count (supports empty cart)
    public int getCartItemCount() {
        wait.until(
                ExpectedConditions.urlContains("cart.html")
        );

        return driver.findElements(cartItems).size();
    }

    // Get product names
    public List<String> getCartItemNames() {
        wait.until(
                ExpectedConditions.urlContains("cart.html")
        );

        return driver.findElements(cartItemNames)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    // Remove Backpack
    public void removeBackpack() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        removeBackpackButton
                )
        ).click();

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        removeBackpackButton
                )
        );
    }

    // Remove Bike Light
    public void removeBikeLight() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        removeBikeLightButton
                )
        ).click();

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        removeBikeLightButton
                )
        );
    }

    // Click Checkout
    public void clickCheckout() {
        wait.until(
                ExpectedConditions.elementToBeClickable(checkoutButton)
        ).click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        firstNameField
                )
        );

        System.out.println("Checkout information page opened!");
    }
}
