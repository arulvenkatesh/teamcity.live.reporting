package teamcity.live.reporting;


import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;

public class TestngPlugin implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("on test method " +  getTestMethodName(result) + " start");
        System.out.println(String.format("##teamcity[testStarted name='%s' captureStandardOutput='true']", getString(result)));

    }

    private String getString(ITestResult result) {
        return getParametersString(result.getParameters()).
                    replace("[","").
                    replace("\"","")
                    .replace("]","")
                    .replace(",","")
                    .replace(" ","_");
    }

    public String getParametersString(Object[] parameters) {
        String parametersString = "";
        if (parameters != null && parameters.length > 0){
            parametersString = Arrays.deepToString(parameters);
        }
        return parametersString;
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("on test method " + getTestMethodName(result) + " success");
        System.out.println(String.format("##teamcity[testFinished name='%s' duration='50']", getString(result)));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("on test method " + getTestMethodName(result) + " failure");
        System.out.println(String.format("##teamcity[testFailed type='comparisonFailure' name='%s' message='failure message' details='message and stack trace' expected='expected value' actual='actual value']",getString(result)));
        

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("test method " + getTestMethodName(result) + " skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("test failed but within success % " + getTestMethodName(result));
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("on start of test " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("on finish of test " + context.getName());
    }

    private static String getTestMethodName(ITestResult result) {
        return result.getMethod().getConstructorOrMethod().getName();
    }
}
