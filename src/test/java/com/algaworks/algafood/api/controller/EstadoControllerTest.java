package com.algaworks.algafood.api.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EstadoControllerTest {

    @LocalServerPort
    private int port;

    @Test
    public void buscar_Estados_Retorna200() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        given()
                .basePath("/estados")
                .port(port)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }


}
