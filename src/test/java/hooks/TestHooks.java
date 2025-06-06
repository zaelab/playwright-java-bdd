package hooks;


import org.testng.ITestResult;
import zaelab.driver.DriverBase;
import com.microsoft.playwright.Page;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import listeners.AllureListener;

public class TestHooks {

    @Before
    public void setUp() {
        System.out.println("[TestHooks] TestNG BeforeMethod on thread: " + Thread.currentThread().getId());
        // Browser setup or other setup logic
        DriverBase.cleanup();
    }

    @AfterStep
    public void tearDown(Scenario scenario) {
        System.out.println("[TestHooks] TestNG AfterMethod on thread: " + Thread.currentThread().getId());
        // Attach screenshot if test failed
        if (scenario.isFailed()) {
            try {
                Page page = DriverBase.getDriver();
                if (page != null) {
                    byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
                    new AllureListener().attachScreenshot(screenshot);
                }
            } catch (Exception e) {
                System.err.println("[TestHooks] Failed to take screenshot in @AfterMethod: " + e.getMessage());
                e.printStackTrace();
            }
        }
        DriverBase.cleanup();
    }
} 