
package tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import utilities.ExtentReportListener;

@Listeners(ExtentReportListener.class)
public class LoginTest extends BaseTest {

    // Test 1: Successful login
    @Test
    public void verifySuccessfulLogin() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "User was not redirected to inventory page"
        );

        Assert.assertEquals(
                loginPage.getProductsTitle(),
                "Products",
                "Products page title does not match"
        );

        System.out.println("Successful login test passed!");
    }

    // Test 2: Locked-out user
    @Test
    public void verifyLockedOutUser() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("locked_out_user", "secret_sauce");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Sorry, this user has been locked out."
        );

        System.out.println("Locked-out user test passed!");
    }

    // Test 3: Invalid username
    @Test
    public void verifyInvalidUsername() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("wrong_user", "secret_sauce");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Username and password do not match any user in this service"
        );

        System.out.println("Invalid username test passed!");
    }

    // Test 4: Invalid password
    @Test
    public void verifyInvalidPassword() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("standard_user", "wrong_password");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Username and password do not match any user in this service"
        );

        System.out.println("Invalid password test passed!");
    }

    // Test 5: Empty username
    @Test
    public void verifyEmptyUsername() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("", "secret_sauce");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Username is required"
        );

        System.out.println("Empty username test passed!");
    }

    // Test 6: Empty password
    @Test
    public void verifyEmptyPassword() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("standard_user", "");

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Password is required"
        );

        System.out.println("Empty password test passed!");
    }
}
