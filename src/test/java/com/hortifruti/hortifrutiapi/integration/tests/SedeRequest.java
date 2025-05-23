package com.hortifruti.hortifrutiapi.integration.tests;

import com.hortifruti.hortifrutiapi.integration.config.TestConfig;
import com.hortifruti.hortifrutiapi.integration.pojo.venda.SedeRequestResponse;
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

    public Response criaNova(RequestSpecification specification, SedeRequestResponse sede) {
        return given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .body(sede)
                .when()
                .post("/sedes");
    }
}
