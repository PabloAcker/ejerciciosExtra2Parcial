package api.apiTest.filaA;

import api.config.Configuration;
import api.factoryRequest.FactoryRequest;
import api.factoryRequest.RequestInfo;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.hamcrest.Matchers.equalTo;
public class Ejercicio1 {
    RequestInfo requestInfo = new RequestInfo();
    Response response;

    String auth;
    JSONObject projectBody = new JSONObject();
    JSONObject userBody = new JSONObject();

    @BeforeEach
    public void setup() {
        userBody.put("FullName", "PabloAcker");
        userBody.put("Email", Configuration.user1);
        userBody.put("Password", Configuration.password);
        projectBody.put("Content", "NameProject_Acker");
    }
    @Test
    public void verifyCreateUserAndProjectTest() {
        requestInfo.setHost(Configuration.host + "api/user.json").setBody(userBody.toString());
        response = FactoryRequest.make("post").send(requestInfo);
        response.then().log().all().statusCode(200)
                .body("Email", equalTo(userBody.get("Email")))
                .body("FullName", equalTo(userBody.get("FullName")));

        auth = Base64.getEncoder().encodeToString((userBody.get("Email")+":"+userBody.get("Password")).getBytes());

        requestInfo.setHost(Configuration.host + "api/projects.json").setBody(projectBody.toString()).setHeader("Authorization", "Basic " + auth);
        response = FactoryRequest.make("post").send(requestInfo);
        response.then().log().all().statusCode(200)
                .body("Content", equalTo(projectBody.get("Content")));

        requestInfo.setHost(Configuration.host + "api/user/0.json").setHeader("Authorization", "Basic " + auth);
        response = FactoryRequest.make("delete").send(requestInfo);
        response.then().log().all().statusCode(200)
                .body("Email", equalTo(userBody.get("Email")))
                .body("FullName", equalTo(userBody.get("FullName")));

        requestInfo.setHost(Configuration.host + "api/projects.json").setBody(projectBody.toString()).setHeader("Authorization", "Basic " + auth);
        response = FactoryRequest.make("post").send(requestInfo);
        response.then().log().all().statusCode(200)
                .body("ErrorMessage", equalTo("Account doesn't exist"));
    }
}
