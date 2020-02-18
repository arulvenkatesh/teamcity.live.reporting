package teamcity.live.reporting;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import static org.junit.Assert.*;

public class StepDefinitions {
    @Given("First step is executed")
    public void first_step_is_executed() {
        System.out.println("Executing first_step_is_executed started");
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(true,true,"First assertion");
        Assert.assertEquals(true,true,"First assertion");
        Assert.assertEquals(true,true,"First assertion");
        System.out.println("Executing first_step_is_executed completed");
    }

    @Given("Second step is executed")
    public void second_step_is_executed() {
        System.out.println("Executing second_step_is_executed started");
        Assert.assertEquals(true,true,"First assertion");
        Assert.assertEquals(true,true,"First assertion");
        Assert.assertEquals(true,true,"First assertion");
        System.out.println("Executing second_step_is_executed finished");
    }

    @Then("Test should fail")
    public void third_step_is_executed() {
        System.out.println("Executing third_step_is_executed started");
        Assert.assertEquals(true,false,"Assertion will fail");
        System.out.println("Executing third_step_is_executed finished");
    }

    @Given("Do nothing")
    public void fourth_step_is_executed() {
        System.out.println("Executing fourth_step_is_executed started");
        System.out.println("Executing fourth_step_is_executed finished");
    }
}
