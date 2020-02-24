package teamcity.live.reporting;

import io.cucumber.java.en.Given;
import org.testng.Assert;

public class StepDefinitionExtension {

    @Given("Second class ste")
    public void second_step_is_executed() {
        //System.out.println("Executing second_step_is_executed started");
        Assert.assertEquals(true,true,"First assertion");
        Assert.assertEquals(true,true,"First assertion");
        Assert.assertEquals(true,true,"First assertion");
        //System.out.println("Executing second_step_is_executed finished");
    }
}
