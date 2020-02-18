package teamcity.live.reporting;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.junit.runner.RunWith;
import org.testng.annotations.DataProvider;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"teamcity",
                "pretty",
                "json:target/report.json",
                "junit:target/cucumber-reports/Cucumber.xml",
                "de.monochromata.cucumber.report.PrettyReports:target/pretty-cucumber"},
        features = "teamcity/live/reporting/*.feature",
        monochrome = true
        )
public class RunCucumberTest extends AbstractTestNGCucumberTests {
        @Override
        @DataProvider(parallel = true)
        public Object[][] scenarios() {
                return super.scenarios();
        }
}
