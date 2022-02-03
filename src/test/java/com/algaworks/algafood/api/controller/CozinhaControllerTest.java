package com.algaworks.algafood.api.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import com.algaworks.algafood.util.DatabaseCleaner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CozinhaControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @BeforeEach
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        databaseCleaner.clearTables();
        prepararDados();
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
                    .body("", hasSize(2))
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

    private void prepararDados(){
        var cozinha = new Cozinha();
        cozinha.setNome("Tailandesa");
        cozinhaRepository.save(cozinha);

        var cozinha2 = new Cozinha();
        cozinha2.setNome("Indiana");
        cozinhaRepository.save(cozinha2);
    }
}
