package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class RequestSetUpMethods extends HelperMethods{

    public static Properties prop;
    RequestSpecification request;

    //Set up Rest Assured base URL and Base Path, RequestSpecification
    public RequestSetUpMethods() throws IOException {
        prop=new Properties();
        File filePath= new File("./Configurations/env.properties");
        FileInputStream fs=new FileInputStream(filePath);
        prop.load(fs);
        RestAssured.baseURI= prop.getProperty("HOST");
        RestAssured.basePath=prop.getProperty("BASE_PATH");
        request= RestAssured.given();

    }

 //Reading config values from prop to form the request
    public static String retMovieName(){
        return prop.getProperty("MOVIE_NAME");

    }

    //Reading config value to form the request
    public String retApiToken(){
        return prop.getProperty("API_TOKEN");
    }


    //Reading config value to form the request. This is a generic one and the above ones can be substituted with this
    public String readConfig(String configValue){
        return prop.getProperty(configValue);
    }

    //Writing to config
    public void writeToConfig(String key,String value){
        prop.setProperty(key, value);
    }


    //method to set content type in request
    public  void setContentType (){
        request.header("Content-Type", "application/json");
    }

    //method to set query Params in request based on passed value
    public  void setParams(String params){
        String[] values=params.split(":");
        request.queryParam(values[0],values[1]);
    }

    //method to set query Params in request based
    public  void setQueryParams (String[] paramValues){
        HashMap<String, String> queryParams =new HashMap<String, String>();
        String[] val;
        for(String s:paramValues){
            val=(prop.getProperty(s)).split(":");
            queryParams.put(val[0],val[1]);
        }
        for(Map.Entry<String,String> entry: queryParams.entrySet())
        request.queryParam(entry.getKey(),entry.getValue());
    }


    //Sending a GET Request method
    public  Response getResponse(String path){
        return request.get(prop.getProperty(path));
    }

    //Overloading the GET request method based on the request
    public  Response getResponse(String path, String type){
            return request.get(path);
    }


    //Setting the request body based on the JSON Object created
    public void setRequestBody(JSONObject requestParams){
        request.body(requestParams.toString());
    }


    //Sending a POST and getting the response
    public Response postResponse(String path){
        request.log().all();
        return request.post(path);
    }

    //Sending a DELETE and getting the response
    public Response deleteResponse(String path){
        return request.delete(path);
    }

}
