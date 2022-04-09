package services;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.orderApi.OrderRequest;

import static io.restassured.RestAssured.given;

public class OrderApiService {
    private RequestSpecification requestSpecification;

    public Response createOrder(OrderRequest orderRequest, ResponseSpecification responseSpecification){

        this.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("http://localhost:8080/api")
                .setContentType("application/json; charset=utf8")
                .build();

    return given()
            .spec(requestSpecification)
            .body(orderRequest)
            .when()
            .post("/createOrder")
            .then()
            .assertThat()
            .spec(responseSpecification)
            .extract()
            .response();
    }
}
