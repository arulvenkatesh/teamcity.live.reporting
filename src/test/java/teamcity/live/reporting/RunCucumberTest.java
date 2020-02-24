package teamcity.live.reporting;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

@CucumberOptions(
        plugin = {
                //"teamcity.live.reporting.TeamCityPlugin",
                "pretty",
                "json:target/report.json",
                "junit:target/cucumber-reports/Cucumber.xml",
                //"teamcity",
                "de.monochromata.cucumber.report.PrettyReports:target/pretty-cucumber"},        
        monochrome = true
        )

@Listeners(TestngPlugin.class)
public class RunCucumberTest extends AbstractTestNGCucumberTests {
        @Override
        @DataProvider(parallel = true)
        public Object[][] scenarios() {
                return super.scenarios();
        }

        @BeforeClass
        public void BeforeClass()
        {
                System.out.println(String.format("##teamcity[testSuiteStarted name='%s']", "teamcity.live.reporting"));
        }

        @AfterClass
        public void AfterClass()
        {
                System.out.println(String.format("##teamcity[testSuiteFinished name='%s']", "teamcity.live.reporting"));
        }
}
