package hooks;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import zaelab.driver.DriverBase;
import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

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

    @After
    public void afterTest(Scenario scenario) {
        System.out.println("[TestHooks] After scenario: '" + scenario.getName() + "' on thread: " + Thread.currentThread().getId());
        // if (scenario.isFailed()) {
        //     captureScreenshot(scenario, "Scenario_Failure");
        // }
        DriverBase.cleanup();
    }

    private void captureScreenshot(Scenario scenario, String screenshotName) {
        try {
            byte[] screenshot = DriverBase.getDriver().screenshot(new Page.ScreenshotOptions().setFullPage(true));
            String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String screenshotTitle = scenario.getName() + "_" + screenshotName + "_" + timestamp;
            
            // Attach screenshot to Allure report
            Allure.addAttachment(
                screenshotTitle,
                "image/png",
                new ByteArrayInputStream(screenshot),
                "png"
            );
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
} 