package com.algaworks.algafood.integration;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;

@SpringBootTest
public class CadastroRestauranteIntegrationTestsIT {

    @Autowired
    private CadastroRestauranteService service;

    /*
    @Test
    public void salvar_restauranteCorreto_retornaSucesso(){
        // 1. Preparação
        var novoRestaurante = new Restaurante();
        var cozinha = new Cozinha();
        cozinha.setId(1L);
        novoRestaurante.setNome("China In Box");
        novoRestaurante.setTaxaFrete(BigDecimal.TEN);
        novoRestaurante.setCozinha(cozinha);
        novoRestaurante.setDataCadastro(OffsetDateTime.now());
        novoRestaurante.setDataAtualizacao(OffsetDateTime.now());

        // 2. Ação
        novoRestaurante = service.salvar(novoRestaurante);

        // 3. Validação
        Assertions.assertNotNull(novoRestaurante);
        Assertions.assertNotNull(novoRestaurante.getId());
    }

    @Test
    public void salvar_restauranteSemNome_retornaErro(){
        // 1. Preparação
        var novoRestaurante = new Restaurante();

        // 2. Ação e 3. Validação
        Assertions.assertThrows(ConstraintViolationException.class, () ->
                service.salvar(novoRestaurante));
    }

    @Test
    public void salvar_restauranteSemTaxaFrete_retornaErro(){
        // 1. Preparação
        var novoRestaurante = new Restaurante();

        // 2. Ação e 3. Validação
        Assertions.assertThrows(ConstraintViolationException.class, () ->
                service.salvar(novoRestaurante));
    }*/

}
