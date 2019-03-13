package tests;

import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class VerifyGetCreditsDetails extends BaseTest{

        @BeforeMethod
        public void getCreditSetUp() throws IOException {
            movieId=helper.getMovieIdRequest();
        }

        //Just started with this to check the Credits list for a movie.
        @Test
        public void verify_valid_character_data() {
            String path=helper.setPath("MOVIE_CREDITS_PATH",movieId);
            Response getResponse=apiReq.getResponse(path,"updated");
            apiRes.checkStatus(getResponse,200);
            apiRes.validateData(getResponse,"id",550, "equal");
    }
}
