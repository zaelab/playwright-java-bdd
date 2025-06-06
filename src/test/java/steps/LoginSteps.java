package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.junit.Assert.assertTrue;

import zaelab.driver.DriverBase;

public class LoginSteps {

    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        DriverBase.getDriver().navigate("https://www.saucedemo.com/v1/index.html");
    }

    @When("the user enters valid username {string} and password {string}")
    public void the_user_enters_valid_username_and_password(String username, String password) {
        DriverBase.getDriver().locator("input#user-name").fill(username);
        DriverBase.getDriver().locator("input#password").fill(password);
    }

    @When("the user clicks the login button")
    public void the_user_clicks_the_login_button() {
        DriverBase.getDriver().locator("input.btn_action").click();
    }

    @Then("the user should be logged in successfully")
    public void the_user_should_be_logged_in_successfully() {
        assertTrue("User should be redirected to inventory page", 
            DriverBase.getDriver().url().contains("inventory.html"));
    }
}
