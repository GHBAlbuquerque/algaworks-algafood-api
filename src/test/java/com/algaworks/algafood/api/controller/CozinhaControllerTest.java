package com.algaworks.algafood.api.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.scenario.ScenarioFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    private ScenarioFactory scenarioFactory = new ScenarioFactory();

    private ObjectMapper objectMapper = new ObjectMapper();

    private static int cozinhasCadastradas;

    private final static int ID_INEXISTENTE = 100;

    //setup

    @BeforeEach
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        databaseCleaner.clearTables();
        prepararDados();
    }

    private void prepararDados() {
        var cozinhas = scenarioFactory.COZINHAS;
        cozinhasCadastradas = cozinhas.size();

        for(var cozinha : cozinhas) {
            cozinhaRepository.save(cozinha);
        }

        estadoRepository.save(scenarioFactory.ESTADO);

        cidadeRepository.save(scenarioFactory.CIDADE);

        restauranteRepository.save(scenarioFactory.RESTAURANTE);
    }


    //listar

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
    public void listar_Cozinhas_Retorna200e2Itens(){
            given()
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                    .body("", hasSize(cozinhasCadastradas))
                    .body("nome", hasItems("Tailandesa", "Indiana"))
                    .statusCode(HttpStatus.OK.value());
    }

    //buscar

    @Test
    public void buscar_Cozinha_Retorna200(){
            given()
                .pathParam("id", 1)
                .accept(ContentType.JSON)
            .when()
                .get("/{id}")
            .then()
                .body("id", equalTo(1))
                .body("nome", equalTo("Tailandesa"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void buscar_CozinhaInexistente_Retorna404(){
        given()
                .pathParam("id", ID_INEXISTENTE)
                .accept(ContentType.JSON)
            .when()
                .get("/{id}")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    //buscarPorNome

    @Test
    public void buscarPorNome_Cozinha_Retorna200(){
        given()
                .queryParam("nome", "Tailandesa")
                .accept(ContentType.JSON)
            .when()
                .get("/por-nome")
            .then()
                .body("id", contains(1))
                .body("nome", contains("Tailandesa"))
                .statusCode(HttpStatus.OK.value());
    }

    //salvar

    @Test
    public void salvar_Cozinha_Retorna201() throws JsonProcessingException{
        var cozinhaJSON = objectMapper.writeValueAsString(Cozinha.builder().nome("Chinesa").build());

        given()
                .body(cozinhaJSON)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void salvar_CozinhaInvalida_Retorna400() throws JsonProcessingException{
        var cozinhaJSON = objectMapper.writeValueAsString(Cozinha.builder().nome("").build());

        given()
                .body(cozinhaJSON)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    //atualizar

    @Test
    public void atualizar_CozinhaValida_Retorna200() throws JsonProcessingException {
        var cozinhaJSON = objectMapper.writeValueAsString(Cozinha.builder().nome("Chinesa").build());

        given()
                .body(cozinhaJSON)
                .pathParam("id", 2)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .when()
                .put("/{id}")
            .then()
                .body("id", equalTo(2))
                .body("nome", equalTo("Chinesa"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void atualizar_CozinhaNaoExistente_Retorna404() throws JsonProcessingException {
        var cozinhaJSON = objectMapper.writeValueAsString(Cozinha.builder().nome("Chinesa").build());

        given()
                .body(cozinhaJSON)
                .pathParam("id", ID_INEXISTENTE)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .when()
                .put("/{id}")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    //deletar

    @Test
    public void deletar_CozinhaValida_Retorna204() {
            given()
                .pathParam("id", 2)
                .accept(ContentType.JSON)
            .when()
                .delete("/{id}")
            .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deletar_CozinhaEmUso_Retorna409() {
        given()
                .pathParam("id", 1)
                .accept(ContentType.JSON)
            .when()
                .delete("/{id}")
            .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    public void deletar_CozinhaInexistente_Retorna404() {
        given()
                .pathParam("id", ID_INEXISTENTE)
                .accept(ContentType.JSON)
            .when()
                .delete("/{id}")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }



}
