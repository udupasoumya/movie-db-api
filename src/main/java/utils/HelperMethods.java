package utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.StringBuilder;

import static io.restassured.RestAssured.given;


public class HelperMethods {
    RequestSetUpMethods initialSetUp;
    private Response res;
    private JsonPath jp;
    private int movieId;
    private String sessionId;
    private String Url;

    //Change the Response type to JSON Path
    public  JsonPath getPath(Response res){
        String json=res.asString();
        return new JsonPath(json);
    }

    //Method to replace param movie_id in a URL to actual Movie ID. We can generate a generic method here
    public String setPath(String path,String movieId){
        Url=initialSetUp.readConfig(path);
        Url=Url.replaceFirst("movie_id",movieId);
        return Url;
    }

    //Instead of hard coding the MovieID(if the movie gets removed?),
    //wrote a method to call Search on Movie name and get the Id from it
    public String getMovieIdRequest() throws IOException {
        initialSetUp= new RequestSetUpMethods();
        String[] queryParam={"API_KEY","SEARCH_TERM"};
        initialSetUp.setQueryParams(queryParam);
        initialSetUp.setContentType();
        res = initialSetUp.getResponse("SEARCH_MOVIE_PATH");
        jp = getPath(res);
        return(getMovieId(jp,initialSetUp.retMovieName()));
    }


    //Parsing the response and extracting the Movie ID from it
    public static String getMovieId(JsonPath jp, String movieName) {
        Object movieId="";
        ArrayList<HashMap<String,String>> movieDetails=jp.get("results");
        for(int i=0;i<movieDetails.size();i++) {
            if (movieDetails.get(i).get("original_title").equals(movieName))
                movieId = movieDetails.get(i).get("id");
                return movieId.toString();
        }
        return movieId.toString();
    }

    //Method to create Valid Token based on API Key. This will be used to create a Session later
    public String createValidToken() throws IOException {
        initialSetUp= new RequestSetUpMethods();
        String[] queryParam={"API_KEY"};
        initialSetUp.setQueryParams(queryParam);
        initialSetUp.setContentType();
        res=initialSetUp.getResponse("CREATE_TOKEN_PATH");
        jp = getPath(res);
        StringBuilder request_token= new StringBuilder();
        String approval_url=initialSetUp.readConfig("HOST_AUTH");
        request_token.append("request_token:");
        request_token.append((String)jp.get("request_token"));
        initialSetUp.writeToConfig("API_TOKEN",request_token.toString());
        System.out.println(approval_url+jp.getString("request_token"));
        given().when().get(approval_url+jp.getString("request_token"));
        return request_token.toString();
    }


    //Request to create new Session ID based on the valid Token given above
    public String createSessionIdwithToken() throws IOException {
        initialSetUp= new RequestSetUpMethods();
        JSONObject requestParam=new JSONObject();
        String[] api_token=createValidToken().split(":");
        requestParam.put(api_token[0],api_token[1]);
        String[] queryParam={"API_KEY"};
        initialSetUp.setQueryParams(queryParam);
        initialSetUp.setContentType();
        initialSetUp.setRequestBody(requestParam);
        res=initialSetUp.postResponse("CREATE_SESSION_PATH");
        System.out.println(res.asString());
        jp = getPath(res);
        return("session_id:"+getSessionId(jp));

    }

    public static String getSessionId(JsonPath jp) {
        String sessionId=jp.get("session_id");
        return sessionId;

    }

    //Creating a valid Guest Session ID based on the API Key
    public String createGuestSessionId() throws IOException {
        initialSetUp= new RequestSetUpMethods();
        String[] queryParam={"API_KEY"};
        initialSetUp.setQueryParams(queryParam);
        initialSetUp.setContentType();
        res=initialSetUp.getResponse("GUEST_SESSION_PATH");
        jp = getPath(res);
        return("guest_session_id:"+getGuestSessionId(jp));
    }

    public static String getGuestSessionId(JsonPath jp) {
        String sessionId=jp.get("guest_session_id");
        return sessionId;
    }



}
