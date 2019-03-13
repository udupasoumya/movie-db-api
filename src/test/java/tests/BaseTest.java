package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.HelperMethods;
import utils.ResponseDetailsMethod;
import utils.RequestSetUpMethods;

import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;

public class BaseTest {
    RequestSetUpMethods apiReq;
    ResponseDetailsMethod apiRes;
    HelperMethods helper;
    protected String movieId;
    protected String sessionId;
    private Object[] ratingValue;
    private JSONObject req;
    public ExtentTest logger;
    public ExtentReports extent;
    public ExtentHtmlReporter htmlReporter;


    @BeforeTest
    public void setReports(){
        htmlReporter = new ExtentHtmlReporter("./Reports/test-output/ResultsExtentReport.html");
        htmlReporter.loadXMLConfig("./extent-config.xml");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host Name", "SauceDemo");
        extent.setSystemInfo("Environment", "Production");
        extent.setSystemInfo("User Name", "QA");        htmlReporter.config().setDocumentTitle("Test Report for Sauce Demo");
        // Name of the report
        htmlReporter.config().setReportName("Test Report");
        // Dark Theme
        htmlReporter.config().setTheme(Theme.STANDARD);
    }

    //Creating th request before every test
    @BeforeMethod
    public void setUp() throws IOException {
        String[] queryParam={"API_KEY"};
        helper=new HelperMethods();
        apiReq= new RequestSetUpMethods();
        apiRes = new ResponseDetailsMethod();
        apiReq.setQueryParams(queryParam);
        apiReq.setContentType();
        movieId=helper.getMovieIdRequest();
        sessionId=helper.createGuestSessionId();
        apiReq.setParams(sessionId);
    }


    public void setRatingRequestBody(String configValue){
        req=new JSONObject();
        ratingValue=(apiReq.readConfig(configValue)).split(":");
        req.put(String.valueOf(ratingValue[0]),ratingValue[1]);
        apiReq.setRequestBody(req);
    }


    public void setInvalidSessionId(){
        sessionId= apiReq.readConfig("INVALID_SESSION_ID");
        apiReq.setParams(sessionId);
    }

    @AfterMethod
    public void getResult(ITestResult result) throws Exception{
        if(result.getStatus() == ITestResult.FAILURE){
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
        }
        else if(result.getStatus() == ITestResult.SKIP){
            logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
        }
        else if(result.getStatus() == ITestResult.SUCCESS)
        {
            logger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
        }
        extent.flush();
    }



}
