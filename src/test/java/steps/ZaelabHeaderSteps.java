package steps;

import static org.testng.Assert.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import zaelab.driver.DriverBase;

public class ZaelabHeaderSteps {
    @Given("navigate to the landing page")
    public void navigate_to_the_landing_page() {
        DriverBase.getDriver().navigate("https://www.zaelab.com");
    }

    @Then("logo should be visible")
    public void logo_should_be_visible() {
        assertTrue(DriverBase.getDriver().locator("//a[@aria-label='home']/img").isVisible());
    }

    
}

