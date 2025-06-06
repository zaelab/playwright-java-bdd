package listeners;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.qameta.allure.Attachment;
import zaelab.driver.DriverBase;

import java.nio.file.Paths;
import java.nio.file.Files;

public class AllureListener {
    
    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                // Create screenshots directory if it doesn't exist
                Files.createDirectories(Paths.get("screenshots"));
                
                String screenshotPath = "screenshots/" + scenario.getName() + ".png";
                DriverBase.getDriver().screenshot(new com.microsoft.playwright.Page.ScreenshotOptions()
                    .setPath(Paths.get(screenshotPath)));
                attachScreenshot(screenshotPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] attachScreenshot(String screenshotPath) {
        try {
            return Files.readAllBytes(Paths.get(screenshotPath));
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] attachScreenshot(byte[] screenshot) {
        return screenshot;
    }
} 