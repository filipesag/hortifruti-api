package com.hortifruti.hortifrutiapi.integration.tests;

import com.hortifruti.hortifrutiapi.integration.config.TestConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class SedeRequest {

    public Response buscarTodos(RequestSpecification specification) {
        return given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .when()
                .get("/sedes/buscar-todos");
    }
}
