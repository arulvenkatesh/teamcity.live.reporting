package teamcity.live.reporting;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;

import static org.junit.Assert.*;

public class StepDefinitions {
    @Given("First step is executed")
    public void first_step_is_executed() {
        //System.out.println("Executing first_step_is_executed started");
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(true,true,"First assertion");
        Assert.assertEquals(true,true,"First assertion");
        Assert.assertEquals(true,true,"First assertion");
       // System.out.println("Executing first_step_is_executed completed");
    }

    @Given("Second step is executed")
    public void second_step_is_executed() {
        //System.out.println("Executing second_step_is_executed started");
        Assert.assertEquals(true,true,"First assertion");
        Assert.assertEquals(true,true,"First assertion");
        Assert.assertEquals(true,true,"First assertion");
        //System.out.println("Executing second_step_is_executed finished");
    }

    @Then("Test should fail")
    public void third_step_is_executed() {
        //System.out.println("Executing third_step_is_executed started");
        Assert.assertEquals(true,false,"Assertion will fail");
        //System.out.println("Executing third_step_is_executed finished");
    }

    @Given("Do nothing")
    public void fourth_step_is_executed() {
        //System.out.println("Executing fourth_step_is_executed started");
        //System.out.println("Executing fourth_step_is_executed finished");
        /*
        System.out.println("##teamcity[testSuiteStarted name='suiteName']");
        System.out.println("##teamcity[testSuiteStarted name='nestedSuiteName']");
        System.out.println("##teamcity[testStarted name='package_or_namespace.ClassName.TestName']");
        System.out.println("##teamcity[testFailed name='package_or_namespace.ClassName.TestName' message='The number must be 20000' details='junit.framework.AssertionFailedError: expected:<20000> but was:<10000>|n|r    at junit.framework.Assert.fail(Assert.java:47)|n|r    at junit.framework.Assert.failNotEquals(Assert.java:280)|n|r...']");
        System.out.println("##teamcity[testFinished name='package_or_namespace.ClassName.TestName']");
        System.out.println("##teamcity[testStarted name='package_or_namespace.ClassName.TestName2']");
        System.out.println("##teamcity[testFinished name='package_or_namespace.ClassName.TestName2']");
        System.out.println("##teamcity[testSuiteFinished name='nestedSuiteName']");
        System.out.println("##teamcity[testSuiteFinished name='suiteName']");
         */
    }
}
