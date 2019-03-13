package utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

public class ResponseDetailsMethod extends HelperMethods {
    private JsonPath jp;

    //Validate Response Status code
    public void checkStatus (Response res, int code) {
        Assert.assertEquals(code, res.getStatusCode());
    }

    //Validate Response Status message
    public  void validateStatusMessage (Response res, String status) {
        jp=getPath(res);
        Assert.assertEquals(jp.get("status_message"), status);
    }

    //Validate Response Status Code
    public  void validateStatusCode (Response res, int status) {
        jp=getPath(res);
        Assert.assertEquals(jp.get("status_code"), status);
    }

    //Use Key value logic to validate specific data within the Json Response
    public  void validateData(Response res, String key, Object value, String type) {
        jp=getPath(res);
        if(type.equals("equal"))
            Assert.assertEquals(jp.get(key), value);
        else if(type.equals("not equal"))
            Assert.assertNotEquals(jp.get(key), value);
    }
}
