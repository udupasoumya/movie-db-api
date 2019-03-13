package tests;

import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import org.testng.annotations.*;

import java.io.IOException;


public class VerifyRatingUpdate extends BaseTest{

    //Verifying valid Rating Update and the Status Message. Making sure that if a Rating exists first delete it
    // Need to delete because the "Success" message is shown only when a User inserts rating the first time. Every attempt later is update
    @Test
    public void verify_valid_rating_insert() {
        logger=extent.createTest("Verify Valid Rating Insert ");
        String path=helper.setPath("MOVIE_RATING_PATH",movieId);
        logger.log(Status.INFO, "Delete the existing Rating");
        Response delResponse=apiReq.deleteResponse(path);
        apiRes.checkStatus(delResponse,200);
        logger.log(Status.INFO, "Set the API Request Body for Create Rating");
        setRatingRequestBody("VALID_RATING");
        logger.log(Status.INFO, "Call POST request for Create Rating");
        Response createResponse=apiReq.postResponse(path);
        logger.log(Status.INFO, "Validate Status of API response for Create Rating");
        apiRes.checkStatus(createResponse,201);
        logger.log(Status.INFO, "Validate status_message in API response for Create Rating");
        apiRes.validateStatusMessage(createResponse,"Success.");
        logger.log(Status.INFO, "Validate status_code in API response for Create Rating");
        apiRes.validateStatusCode(createResponse,1);

    }

    //Updating an existing rating test. API seems to behave slightly diff here and shows Success Message sometimes
    // and Update message some
    @Test
    public void verify_valid_rating_update() {
        logger=extent.createTest("Verify Valid Rating Update ");
        String path=helper.setPath("MOVIE_RATING_PATH",movieId);
        logger.log(Status.INFO, "Set the API Request Body for Create and Update Rating");
        setRatingRequestBody("VALID_RATING");
        logger.log(Status.INFO, "Call POST request for Create Rating");
        Response createResponse=apiReq.postResponse(path);
        logger.log(Status.INFO, "Validate Status of API response for Create Rating");
        apiRes.checkStatus(createResponse,201);
        logger.log(Status.INFO, "Call POST request for Update Rating");
        Response updateResponse=apiReq.postResponse(path);
        logger.log(Status.INFO, "Validate Status of API response for Create Rating");
        apiRes.checkStatus(updateResponse,201);
        logger.log(Status.INFO, "Validate status_message in API response for Create Rating");
        apiRes.validateStatusMessage(updateResponse,"Success.");
        logger.log(Status.INFO, "Validate status_code in API response for Create Rating");
        apiRes.validateStatusCode(updateResponse,1);

    }

    //Invalid Rating update scenario. Not Sure why rating cannot be 0.0 though!
    @Test
    public void verify_invalid_rating_zero() {
        logger=extent.createTest("Verify Invalid: 0 Rating Update ");
        setRatingRequestBody("INVALID_RATING");
        String path=helper.setPath("MOVIE_RATING_PATH",movieId);
        logger.log(Status.INFO, "Call POST request for Updating Invalid: 0 Rating");
        Response updateResponse=apiReq.postResponse(path);
        logger.log(Status.INFO, "Validate Status of API response for Invalid: 0 Rating");
        apiRes.checkStatus(updateResponse,400);
        logger.log(Status.INFO, "Validate status_message in API response for Invalid: 0 Rating");
        apiRes.validateStatusMessage(updateResponse,"Value too low: Value must be greater than 0.0.");
        logger.log(Status.INFO, "Validate status_code in API response for Invalid: 0 Rating");
        apiRes.validateStatusCode(updateResponse,18);

    }

    //10 is a valid Rating but 0.0 is not
    @Test
    public void verify_valid_rating_ten() {
        logger=extent.createTest("Verify Valid Rating: 10.0");
        String path=helper.setPath("MOVIE_RATING_PATH",movieId);
        logger.log(Status.INFO, "Delete the existing Rating");
        Response delResponse=apiReq.deleteResponse(path);
        apiRes.checkStatus(delResponse,200);
        logger.log(Status.INFO, "Set the API Request Body for Create Valid: 10.0 Rating");
        setRatingRequestBody("VALID_RATING_10");
        logger.log(Status.INFO, "Call POST request for Create Valid: 10.0 Rating");
        Response createResponse=apiReq.postResponse(path);
        logger.log(Status.INFO, "Validate Status of API response for Create Rating");
        apiRes.checkStatus(createResponse,201);
        logger.log(Status.INFO, "Validate status_message in API response for Create Rating");
        apiRes.validateStatusMessage(createResponse,"Success.");
        logger.log(Status.INFO, "Validate status_code in API response for Create Rating");
        apiRes.validateStatusCode(createResponse,1);
    }

    //Updating an invalid rating
    @Test
    public void verify_invalid_rating_nonnumeric() {
        logger=extent.createTest("Verify Invalid Rating: Non Numeric");
        String path=helper.setPath("MOVIE_RATING_PATH",movieId);
        logger.log(Status.INFO, "Set the API Request Body for Invalid Rating: Non Numeric");
        setRatingRequestBody("INVALID_RATING_NONNUMERIC");
        logger.log(Status.INFO, "Call POST request for Invalid Rating: Non Numeric");
        Response createResponse=apiReq.postResponse(path);
        logger.log(Status.INFO, "Validate Status of API response for Invalid Rating: Non Numeric");
        apiRes.checkStatus(createResponse,400);
        logger.log(Status.INFO, "Validate status_message in API response for Invalid Rating: Non Numeric");
        apiRes.validateStatusMessage(createResponse,"Value too low: Value must be greater than 0.0.");
        logger.log(Status.INFO, "Validate status_code in API response for Invalid Rating: Non Numeric");
        apiRes.validateStatusCode(createResponse,18);
    }

    //Test for invalid session
    @Test
    public void verify_invalid_session_id() {
        logger=extent.createTest("Verify Invalid Session Id in Request");
        String path=helper.setPath("MOVIE_RATING_PATH",movieId);
        setInvalidSessionId();
        setRatingRequestBody("VALID_RATING");
        Response createResponse=apiReq.postResponse(path);
        logger.log(Status.INFO, "Validate Status of API response for Invalid Session Id in Request");
        apiRes.checkStatus(createResponse,401);
        logger.log(Status.INFO, "Validate status_message in API response for Invalid Session Id in Request");
        apiRes.validateStatusMessage(createResponse,"Authentication failed: You do not have permissions to access the service.");
        apiRes.validateStatusCode(createResponse,3);
    }

    //Test for invalid Movie ID
    @Test
    public void verify_invalid_movie_id() {
        logger=extent.createTest("Verify Invalid Movie ID in Request");
        movieId=apiReq.readConfig("INVALID_MOVIE");
        String path=helper.setPath("MOVIE_RATING_PATH",movieId);
        setRatingRequestBody("VALID_RATING");
        Response createResponse=apiReq.postResponse(path);
        logger.log(Status.INFO, "Validate Status of API response for Invalid Movie ID in Request");
        apiRes.checkStatus(createResponse,404);
        logger.log(Status.INFO, "Validate status_message in API response for Invalid Movie ID in Request");
        apiRes.validateStatusMessage(createResponse,"The resource you requested could not be found.");
        apiRes.validateStatusCode(createResponse,34);
    }

    //Test for rating value in decimals


}
