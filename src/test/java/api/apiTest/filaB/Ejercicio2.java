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
public class Ejercicio2 {
    RequestInfo requestInfo = new RequestInfo();
    Response response;

    Response allItems;
    JSONObject body = new JSONObject();
    String auth;

    @BeforeEach
    public void setup() {
        auth = Base64.getEncoder().encodeToString((Configuration.user+":"+Configuration.password).getBytes());
    }

    @Test
    public void verifyCreate4ItemsAndDelete() {
        //Verify Create Item

        for(int i=0;i<4;i++){
            body.clear();
            body.put("Content", "Item_Acker"+i);
            requestInfo.setHost(Configuration.host+"api/items.json").setBody(body.toString()).setHeader("Authorization", "Basic " + auth);
            response = FactoryRequest.make("post").send(requestInfo);
            response.then()
                    .log().all()
                    .statusCode(200)
                    .body("Content", equalTo(body.get("Content")));
        }

        //Get all items

        requestInfo.setHost(Configuration.host+"api/items.json").setHeader("Authentication","Basic "+auth);
        allItems = FactoryRequest.make("get").send(requestInfo);
        allItems.then()
                .log().all()
                .statusCode(200);

        //Delete all items
        for(int i=0;i<4;i++){
            requestInfo.setHost(Configuration.host+"api/items/"+allItems.then().extract().path("Id["+i+"]")+".json").setHeader("Authentication","Basic "+auth);
            response = FactoryRequest.make("delete").send(requestInfo);
            response.then()
                    .log().all()
                    .statusCode(200)
                    .body("Id", equalTo(allItems.then().extract().path("Id["+i+"]")));
        }
    }
}
