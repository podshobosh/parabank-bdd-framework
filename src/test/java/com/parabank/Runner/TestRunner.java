package com.parabank.Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "classpath:features",
        glue = {"com.parabank.stepdefinitions", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json",
                "junit:target/cucumber.xml"
        },
        monochrome = true,
        tags = "@e2e",
        dryRun = false
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
