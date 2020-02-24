package teamcity.live.reporting;


import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestngPlugin implements ITestListener {

    @Override
    public void onFinish(ITestContext arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStart(ITestContext arg0) {
        System.out.println(String.format("##teamcity[testStarted name='%s' captureStandardOutput='true']", arg0.getName()));
        System.out.println("Came to Onstart");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTestFailure(ITestResult arg0) {
        // TODO Auto-generated method stub
        System.out.println(String.format("##teamcity[testFailed type='comparisonFailure' name='%s' message='failure message' details='message and stack trace' expected='expected value' actual='actual value']",arg0.getName()));

    }

    @Override
    public void onTestSkipped(ITestResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTestStart(ITestResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTestSuccess(ITestResult arg0) {
        // TODO Auto-generated method stub
        System.out.println(String.format("##teamcity[testFinished name='%s' duration='50']", arg0.getName()));


    }
}
