
package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");

    private By productsTitle = By.className("title");

    private By errorMessage =
            By.cssSelector("[data-test='error']");

    // Constructor
    public LoginPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );
    }

    // Enter username
    public void enterUsername(String username) {

        WebElement usernameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        usernameField
                )
        );

        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    // Enter password
    public void enterPassword(String password) {

        WebElement passwordInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        passwordField
                )
        );

        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    // Click login button
    public void clickLogin() {

        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        ).click();
    }

    // Complete login operation
    public void login(String username, String password) {

        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // Get login error message
    public String getErrorMessage() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        errorMessage
                )
        ).getText();
    }

    // Get products page title
    public String getProductsTitle() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        productsTitle
                )
        ).getText();
    }
}
