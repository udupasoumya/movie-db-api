package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerTestNG implements ITestListener {


        public void onTestStart(ITestResult Result) {

            System.out.println("Test Started:"+Result.getName());

        }

        public void onTestSuccess(ITestResult Result) {
            System.out.println("The name of the testcase passed is :"+Result.getName());
        }

        public void onTestFailure(ITestResult Result) {
            System.out.println("The name of the testcase failed is :"+Result.getName());

        }

        public void onTestSkipped(ITestResult Result) {
            System.out.println("The name of the testcase Skipped is :"+Result.getName());
        }

        public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

        }

        public void onStart(ITestContext iTestContext) {

        }

        public void onFinish(ITestContext iTestContext) {

        }
}
