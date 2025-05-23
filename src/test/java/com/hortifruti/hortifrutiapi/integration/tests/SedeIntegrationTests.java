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

    @Test
    @DisplayName("Testanto criar nova Sede")
    void testeCriarNovaSede() throws IOException {

        SedeRequestResponse sede = new SedeRequestResponse();
        sede.setBairro("Bairro Test");
        sede.setCidade("Cidade Test");
        sede.setEstado("Estado Test");
        sede.setRua("Rua Test");
        sede.setNumero("123");
        sede.setDescricao("Description Test");

        Response response = sedeRequest.criaNova(specification, sede);
        SedeRequestResponse novaSedeResponse = response.then().statusCode(200).extract().response().as(SedeRequestResponse.class);
        assertNotNull(novaSedeResponse);
        assertEquals("Bairro Test",novaSedeResponse.getBairro());
        assertEquals("Cidade Test",novaSedeResponse.getCidade());
        assertEquals("Estado Test",novaSedeResponse.getEstado());
        assertEquals("Rua Test",novaSedeResponse.getRua());
        assertEquals("234",novaSedeResponse.getNumero());
        assertEquals("Description Test",novaSedeResponse.setDescricao());

    }

}
