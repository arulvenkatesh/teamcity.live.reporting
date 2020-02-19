package teamcity.live.reporting;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        plugin = {
                "teamcity.live.reporting.TeamCityPlugin",
                "pretty",
                "json:target/report.json",
                "junit:target/cucumber-reports/Cucumber.xml",
                "de.monochromata.cucumber.report.PrettyReports:target/pretty-cucumber"},        
        monochrome = true
        )
public class RunCucumberTest extends AbstractTestNGCucumberTests {
        @Override
        @DataProvider(parallel = true)
        public Object[][] scenarios() {
                return super.scenarios();
        }
}
