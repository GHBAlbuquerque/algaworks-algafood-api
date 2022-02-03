package com.algaworks.algafood.api.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CozinhaControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        flyway.migrate();
    }

    @Test
    public void listar_Cozinhas_Retorna200(){
            given()
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void listar_Cozinhas_Retorna200E8Itens(){
            given()
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                    .body("", hasSize(8))
                    .body("nome", hasItems("Tailandesa", "Indiana"))
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void buscar_Cozinha_Retorna200(){
            given()
                .basePath("/cozinhas/1")
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .body("id", equalTo(1))
                .body("nome", equalTo("Tailandesa"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void salvar_Cozinha_Retorna201(){
        given()
                .body("{ \"nome\": \"Chinesa\" }")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.CREATED.value());
    }
}
