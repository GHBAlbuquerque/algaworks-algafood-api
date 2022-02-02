package com.algaworks.algafood.integration;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.entitynotfound.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

@SpringBootTest
public class CadastroCozinhaIntegrationTestsIT {

    @Autowired
    private CadastroCozinhaService service;

    @Test
    public void salvar_cozinhaCorreta_retornaSucesso(){
        // 1. Preparação
        var novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        // 2. Ação
        novaCozinha = service.salvar(novaCozinha);

        // 3. Validação
        Assertions.assertNotNull(novaCozinha);
        Assertions.assertNotNull(novaCozinha.getId());
    }

    @Test
    public void salvar_cozinhaSemNome_retornaErro(){
        // 1. Preparação
        var novaCozinha = new Cozinha();

        // 2. Ação e 3. Validação
        Assertions.assertThrows(ConstraintViolationException.class, () ->
            service.salvar(novaCozinha));
    }

    @Test
    public void buscar_cozinhaExistente_retornaSucesso(){
        // 2. Ação
        var cozinhaEncontrada = service.buscar(1L);

        // 3. Validação
        Assertions.assertNotNull(cozinhaEncontrada);
        Assertions.assertEquals(1l, cozinhaEncontrada.getId());
    }

    @Test
    public void buscar_cozinhaInexistente_retornaErro(){
        // 1. Preparação, 2. Ação e 3. Validação
        Assertions.assertThrows(CozinhaNaoEncontradaException.class, () ->
                service.buscar(288L));
    }

    @Test
    public void remover_cozinhaEmUso_retornaErro(){
        // 1. Preparação, 2. Ação e 3. Validação
        Assertions.assertThrows(EntidadeEmUsoException.class, () ->
                service.remover(1L));
    }

    @Test
    public void remover_cozinhaInexistente_retornaErro(){
        // 1. Preparação, 2. Ação e 3. Validação
        Assertions.assertThrows(CozinhaNaoEncontradaException.class, () ->
                service.remover(288L));
    }




}
