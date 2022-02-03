package com.algaworks.algafood.api.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CozinhaControllerTest {

    @LocalServerPort
    private int port;

    @Test
    public void buscar_Cozinhas_Retorna200(){
            given()
                .basePath("/cozinhas")
                .port(port)
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void buscar_Cozinhas_Retorna200E8Itens(){
            given()
                .basePath("/cozinhas")
                .port(port)
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
                .port(port)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("id", equalTo(1))
                .body("nome", equalTo("Tailandesa"))
                .statusCode(HttpStatus.OK.value());
    }
}
