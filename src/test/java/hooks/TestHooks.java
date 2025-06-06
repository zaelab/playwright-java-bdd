package hooks;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import zaelab.driver.DriverBase;
import com.microsoft.playwright.Page;
import net.serenitybdd.core.Serenity;

public class TestHooks {

    @Before
    public void beforeTest(Scenario scenario) {
        System.out.println("[TestHooks] Before scenario: '" + scenario.getName() + "' on thread: " + Thread.currentThread().getId());
        DriverBase.cleanup();
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            String stepName = scenario.getStatus().toString();
            captureScreenshot(scenario, "Step_" + stepName);
        }
    }

    // @After
    public void afterTest(Scenario scenario) {
        System.out.println("[TestHooks] After scenario: '" + scenario.getName() + "' on thread: " + Thread.currentThread().getId());
        if (scenario.isFailed()) {
            captureScreenshot(scenario, "Scenario_Failure");
        }
        DriverBase.cleanup();
    }

    private void captureScreenshot(Scenario scenario, String screenshotName) {
        try {
            byte[] screenshot = DriverBase.getDriver().screenshot(new Page.ScreenshotOptions().setFullPage(true));
            String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            java.nio.file.Path screenshotPath = java.nio.file.Paths.get("target/serenity-screenshots", 
                scenario.getName() + "_" + screenshotName + "_" + timestamp + ".png");
            java.nio.file.Files.createDirectories(screenshotPath.getParent());
            java.nio.file.Files.write(screenshotPath, screenshot);
            Serenity.recordReportData().withTitle("Screenshot: " + screenshotName)
                    .downloadable().fromFile(screenshotPath);
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
} 