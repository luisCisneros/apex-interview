package stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.WebDriverManager;

public class Hooks {

    private static final Logger log = LogManager.getLogger();
    private WebDriverManager webDriverManager;

    public Hooks(WebDriverManager webDriverManager) {
        this.webDriverManager = webDriverManager;
    }

    @Before
    public void setUp(Scenario scenario) {
        String padding = "==========";
        log.info("{} SCENARIO: {} {}", padding, scenario.getName(), padding);
        webDriverManager.setUpDriver();
    }

    @After
    public void teardown(Scenario scenario) {
        webDriverManager.teardown(scenario);
    }
}
