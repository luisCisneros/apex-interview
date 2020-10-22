package org.example;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/features",
        glue = "stepdefs",
//        tags = "@wip",  // tag for 'work in progress'
        plugin = {
                "pretty",
                "html:target/cucumber/report.html",
                "json:target/cucumber/report.json"
        })
public class TestRunner {
}
