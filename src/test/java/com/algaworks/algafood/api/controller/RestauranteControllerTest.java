package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.scenario.ScenarioFactory;
import com.algaworks.algafood.util.DatabaseCleaner;
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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RestauranteControllerTest {

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

    private final static int ID_INEXISTENTE = 100;

    //setup

    @BeforeEach
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurantes";

        databaseCleaner.clearTables();
        prepararDados();
    }

    private void prepararDados() {

        for(var cozinha : scenarioFactory.COZINHAS) {
            cozinhaRepository.save(cozinha);
        }

        estadoRepository.save(scenarioFactory.ESTADO);

        cidadeRepository.save(scenarioFactory.CIDADE);

        restauranteRepository.save(scenarioFactory.RESTAURANTE);
    }

    // listar

    @Test
    public void listar_Restaurantes_Retorna200(){
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }


    // buscar

    @Test
    public void buscar_Restaurante_Retorna200(){
        given()
            .pathParam("id", 1)
            .accept(ContentType.JSON)
        .when()
            .get("/{id}")
        .then()
            .body("id", equalTo(1))
            .body("nome", equalTo("Restaurante Tailandês"))
            .body("cozinha", notNullValue())
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void buscar_RestauranteInexistente_Retorna404(){
        given()
            .pathParam("id", ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get("/{id}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }


    // adicionar

    @Test
    public void adicionar_Restaurante_Retorna201() throws JsonProcessingException {
        var restaurante = scenarioFactory.RESTAURANTE;
        restaurante.setId(2L);
        var restauranteJSON = objectMapper.writeValueAsString(restaurante);

        given()
                .body(restauranteJSON)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .when()
                .post()
                .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void adicionar_RestauranteInvalido_Retorna400() throws JsonProcessingException{
        var restaurante = scenarioFactory.RESTAURANTE;
        restaurante.setNome("");
        var restauranteJSON = objectMapper.writeValueAsString(restaurante);

        given()
                .body(restauranteJSON)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void adicionar_RestauranteComCozinhaInvalida_Retorna400() throws JsonProcessingException{
        var restaurante = scenarioFactory.RESTAURANTE;
        restaurante.getCozinha().setId(100L);
        var restauranteJSON = objectMapper.writeValueAsString(restaurante);

            given()
                .body(restauranteJSON)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}
