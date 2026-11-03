
package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Customer information fields
    private By firstName =
            By.id("first-name");

    private By lastName =
            By.id("last-name");

    private By postalCode =
            By.id("postal-code");

    // Buttons
    private By continueButton =
            By.id("continue");

    private By finishButton =
            By.id("finish");

    // Error and confirmation messages
    private By errorMessage =
            By.cssSelector("[data-test='error']");

    private By confirmationMessage =
            By.className("complete-header");

    // Constructor
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));
    }

    // Enter customer information
    public void enterCustomerInformation(
            String first, String last, String zip) {

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(firstName)
        ).sendKeys(first);

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(lastName)
        ).sendKeys(last);

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(postalCode)
        ).sendKeys(zip);
    }

    // Click Continue and verify navigation
    public void clickContinue() {

        wait.until(
            ExpectedConditions.elementToBeClickable(continueButton)
        ).click();

        try {
            wait.until(
                ExpectedConditions.urlContains(
                    "checkout-step-two.html")
            );
        } catch (org.openqa.selenium.TimeoutException e) {

            String error = driver.findElements(errorMessage)
                    .isEmpty()
                    ? "No checkout validation message found"
                    : driver.findElement(errorMessage).getText();

            throw new AssertionError(
                    "Checkout did not proceed: " + error, e);
        }
    }

    // Click Finish
    public void clickFinish() {

        wait.until(
            ExpectedConditions.elementToBeClickable(finishButton)
        ).click();
    }

    // Get order confirmation
    public String getConfirmationMessage() {

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    confirmationMessage)
        ).getText();
    }
}
