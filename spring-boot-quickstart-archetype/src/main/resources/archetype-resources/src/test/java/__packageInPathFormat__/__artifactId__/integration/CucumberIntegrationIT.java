package ${package}.${artifactId}.integration;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by yujiaao.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/features"}, format = {"pretty", "html:target/reports/cucumber/html",
        "json:target/cucumber.json", "usage:target/usage.jsonx", "junit:target/junit.xml"})
public class CucumberIntegrationIT {
}
