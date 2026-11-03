
package utilities;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportListener implements ITestListener {

    private static ExtentReports extent;

    private static final ThreadLocal<ExtentTest> test =
            new ThreadLocal<>();

    // Create HTML report
    public static synchronized ExtentReports getExtentReports() {

        if (extent == null) {

            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter(
                            "reports/ExtentReport.html"
                    );

            sparkReporter.config().setDocumentTitle(
                    "Q1 UI Automation Report"
            );

            sparkReporter.config().setReportName(
                    "FrugalTesting - Automation Results"
            );

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            extent.setSystemInfo("Project", "Q1 UI Automation");
            extent.setSystemInfo("Framework", "Selenium + TestNG");
            extent.setSystemInfo("Language", "Java");
        }

        return extent;
    }

    // Test starts
    @Override
    public void onTestStart(ITestResult result) {

        ExtentTest extentTest =
                getExtentReports().createTest(
                        result.getMethod().getMethodName()
                );

        test.set(extentTest);
    }

    // Test passes
    @Override
    public void onTestSuccess(ITestResult result) {

        ExtentTest extentTest = test.get();

        if (extentTest != null) {
            extentTest.pass("Test Passed Successfully");
        }

        test.remove();
    }

    // Test fails
    @Override
    public void onTestFailure(ITestResult result) {

        ExtentTest extentTest = test.get();

        if (extentTest == null) {

            extentTest = getExtentReports().createTest(
                    result.getMethod().getMethodName()
            );

            test.set(extentTest);
        }

        // Record failure
        extentTest.fail(result.getThrowable());

        // Read screenshot path captured by BaseTest
        String screenshotPath =
                (String) result.getAttribute("screenshotPath");

        if (screenshotPath != null) {

            try {

                Path screenshot =
                        Paths.get(screenshotPath);

                if (java.nio.file.Files.exists(screenshot)) {

                    extentTest.addScreenCaptureFromPath(
                            screenshot.toAbsolutePath().toString(),
                            "Failure Screenshot"
                    );

                    extentTest.info(
                            "Failure screenshot attached successfully."
                    );

                    System.out.println(
                            "Screenshot attached: " + screenshot
                    );

                } else {

                    extentTest.warning(
                            "Screenshot file does not exist: "
                                    + screenshotPath
                    );
                }

            } catch (Exception e) {

                extentTest.warning(
                        "Screenshot attachment failed: "
                                + e.getMessage()
                );
            }

        } else {

            extentTest.warning(
                    "Screenshot path was not provided by BaseTest."
            );
        }

        test.remove();
    }

    // Test skipped
    @Override
    public void onTestSkipped(ITestResult result) {

        ExtentTest extentTest = test.get();

        if (extentTest != null) {

            extentTest.skip(
                    result.getThrowable() != null
                            ? result.getThrowable()
                            : new Exception("Test Skipped")
            );
        }

        test.remove();
    }

    // Flush report
    @Override
    public void onFinish(
            org.testng.ITestContext context
    ) {

        if (extent != null) {
            extent.flush();
            System.out.println("Extent Report generated successfully.");
        }
    }
}
