package com.hortifruti.hortifrutiapi.integration.tests;

import com.hortifruti.hortifrutiapi.integration.pojo.venda.SedeRequestResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SedeIntegrationTests {

    private static RequestSpecification specification;
    private SedeRequest sedeRequest = new SedeRequest();

    @BeforeAll
    static void setup() {
        specification = BaseTests.getRequestSpecification();
    }

    @Test
    @DisplayName("Testanto buscar todas Sedes")
    void testeBuscaTodasSedes() throws IOException {
        Response response = sedeRequest.buscarTodos(specification);

        List<SedeRequestResponse> sedeResponse = Arrays.asList(
                response.then().statusCode(200).extract().body().as(SedeRequestResponse[].class)
        );

        assertNotNull(sedeResponse);
        assertEquals("Savassi",sedeResponse.get(0).getBairro());

    }

}
