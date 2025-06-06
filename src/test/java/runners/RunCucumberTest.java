package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"steps", "hooks", "listeners"},
    plugin = {
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
        "pretty",
        "summary",
        "timeline:target/cucumber-reports/timeline",
        "html:target/cucumber-reports/cucumber-pretty.html",
        "json:target/cucumber-reports/cucumber.json"
    },
    tags = ""
)
public class RunCucumberTest extends AbstractTestNGCucumberTests {
} 