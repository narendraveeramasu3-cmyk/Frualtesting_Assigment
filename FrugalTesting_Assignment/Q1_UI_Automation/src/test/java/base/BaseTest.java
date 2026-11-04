
package base;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    // Browser setup
    @BeforeMethod
    public void setUp() {

        ChromeOptions options = new ChromeOptions();

        // Read headless mode from Maven system property.
        // Default: false (normal visible Chrome).
        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );

        if (headless) {

            // Headless Chrome for GitHub Actions
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");

            System.out.println("Starting Chrome in HEADLESS mode.");

        } else {

            // Normal Chrome for local execution
            options.addArguments("--start-maximized");

            System.out.println("Starting Chrome in NORMAL mode.");
        }

        // Disable Chrome password manager
        options.setExperimentalOption(
                "prefs",
                Map.of(
                        "credentials_enable_service", false,
                        "profile.password_manager_enabled", false,
                        "profile.password_manager_leak_detection", false
                )
        );

        // Disable password-related browser prompts
        options.addArguments("--disable-save-password-bubble");

        // Initialize Chrome WebDriver
        driver = new ChromeDriver(options);

        // Open SauceDemo
        driver.get("https://www.saucedemo.com");

        System.out.println(
                "Browser initialized: " + driver.getWindowHandle()
        );

        System.out.println(
                "Current URL: " + driver.getCurrentUrl()
        );
    }

    // Capture screenshot on failure and close browser
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        if (driver == null) {
            return;
        }

        try {

            // Capture screenshot only if the test fails
            if (!result.isSuccess()) {

                System.out.println(
                        "Test failed. Capturing screenshot..."
                );

                // Create screenshot directory
                Path screenshotDirectory =
                        Paths.get("reports", "screenshots");

                Files.createDirectories(screenshotDirectory);

                // Generate unique screenshot filename
                String testName = result.getMethod()
                        .getMethodName()
                        .replaceAll("[^a-zA-Z0-9_-]", "_");

                String fileName = testName
                        + "_"
                        + System.currentTimeMillis()
                        + ".png";

                Path screenshotPath =
                        screenshotDirectory.resolve(fileName);

                // Capture screenshot before closing browser
                File screenshotFile =
                        ((TakesScreenshot) driver)
                                .getScreenshotAs(OutputType.FILE);

                // Save screenshot
                Files.copy(
                        screenshotFile.toPath(),
                        screenshotPath,
                        StandardCopyOption.REPLACE_EXISTING
                );

                // Pass screenshot path to Extent Report listener
                result.setAttribute(
                        "screenshotPath",
                        screenshotPath.toAbsolutePath().toString()
                );

                System.out.println(
                        "Screenshot saved successfully: "
                                + screenshotPath.toAbsolutePath()
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Screenshot capture issue: " + e.getMessage()
            );

        } finally {

            // Always close browser
            try {

                System.out.println("Closing browser...");

                driver.quit();

                System.out.println(
                        "Browser closed successfully."
                );

            } catch (Exception e) {

                System.out.println(
                        "Browser cleanup issue: " + e.getMessage()
                );

            } finally {

                driver = null;
            }
        }
    }

    // Provide WebDriver access to Extent Report listener
    public WebDriver getDriver() {
        return driver;
    }
}
