package api.apiTest.filaA;

import api.config.Configuration;
import api.factoryRequest.FactoryRequest;
import api.factoryRequest.RequestInfo;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.hamcrest.Matchers.equalTo;
public class Ejercicio2 {
    RequestInfo requestInfo = new RequestInfo();
    Response response;
    String auth;
    String[] projectsContent = {
            "Project_Acker_1",
            "Project_Acker_2",
            "Project_Acker_3",
            "Project_Acker_4"
    };

    @BeforeEach
    public void setup() {
        auth = Base64.getEncoder().encodeToString((Configuration.user+":"+ Configuration.password).getBytes());
    }

    @Test
    public void verifyCreate4ProjectsAndDeleteTest() {
        for(int i = 0; i<4; i++) {
            requestInfo.setHost(Configuration.host + "api/projects.json").setBody("{\"Content\":\""+projectsContent[i]+"\"}").setHeader("Authorization", "Basic " + auth);
            response = FactoryRequest.make("post").send(requestInfo);
            response.then().log().all().statusCode(200)
                    .body("Content", equalTo(projectsContent[i]));
        }
        requestInfo.setHost(Configuration.host + "api/projects.json").setHeader("Authorization", "Basic " + auth);
        response = FactoryRequest.make("get").send(requestInfo);
        response.then().log().all().statusCode(200);

        JSONArray jsonArray = new JSONArray(response.body().print());
        for(int i = 0; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.getInt("Id");
            requestInfo.setHost(Configuration.host + "api/projects/"+id+".json").setHeader("Authorization", "Basic " + auth);
            response = FactoryRequest.make("delete").send(requestInfo);
            response.then().log().all().statusCode(200)
                    .body("Id", equalTo(id))
                    .body("Deleted", equalTo(true));
        }
    }
}
