package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertTrue;

import zaelab.driver.DriverBase;

public class ZaelabHeaderSteps {
    @Given("navigate to the landing page")
    public void navigate_to_the_landing_page() {
        DriverBase.getDriver().navigate("https://www.zaelab.com");
    }

    @Then("logo should be visible")
    public void logo_should_be_visible() {
        assertTrue(DriverBase.getDriver().locator("//a[@aria-label='home']/img").isVisible(),
            "Logo should be visible on the landing page");
    }
}

