package api.apiTest.filaB;

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
    JSONObject body = new JSONObject();
    String auth;

    @BeforeEach
    public void setup() {
        auth = Base64.getEncoder().encodeToString((Configuration.user2+":"+Configuration.password).getBytes());
    }

    @Test
    public void verifyCreateUserAndProject() {

        body.clear();
        body.put("Email", Configuration.user2);
        body.put("Password", Configuration.password);
        body.put("FullName", "PabloAckerM");
        requestInfo.setHost(Configuration.host+"api/user.json").setBody(body.toString());
        response = FactoryRequest.make("post").send(requestInfo);
        response.then()
                .log().all()
                .statusCode(200)
                .body("Email", equalTo(body.get("Email")))
                .body("FullName", equalTo(body.get("FullName")));

        //Verify Token

        body.clear();
        requestInfo.setHost(Configuration.host+"api/authentication/token.json").setHeader("Authorization","Basic "+auth);
        response = FactoryRequest.make("get").send(requestInfo);
        response.then()
                .log().all()
                .statusCode(200)
                .body("UserEmail", equalTo(Configuration.user2));

        String token = response.then().extract().path("TokenString");


        //Verify Create Project
        body.put("Content", "ProjectPablo");
        body.put("Icon", 3);
        requestInfo.removeHeader("Authorization").setHost(Configuration.host+"api/projects.json").setBody(body.toString()).setHeader("Token",token);
        response = FactoryRequest.make("post").send(requestInfo);
        response.then()
                .log().all()
                .statusCode(200)
                .body("Content", equalTo(body.get("Content")))
                .body("Icon", equalTo(body.get("Icon")));


        //Verify Delete Token
        requestInfo.setHost(Configuration.host+"api/authentication/token.json").setHeader("Token",token);
        response = FactoryRequest.make("delete").send(requestInfo);
        response.then()
                .log().all()
                .statusCode(200)
                .body("TokenString", equalTo(token));


        //Verify Create Project deleted token
        body.clear();
        body.put("Content", "NuevoProjectPablo");
        body.put("Icon", 5);
        requestInfo.setHost(Configuration.host+"api/projects.json").setBody(body.toString()).setHeader("Token",token);
        response = FactoryRequest.make("post").send(requestInfo);
        response.then()
                .log().all()
                .statusCode(200)
                .body("ErrorCode", equalTo(102));
    }
}
