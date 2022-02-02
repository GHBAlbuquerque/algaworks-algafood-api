package com.algaworks.algafood.integration;

import com.algaworks.algafood.domain.exception.ValidacaoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CadastroCidadeIntegrationTests {

    @Autowired
    private CadastroCidadeService service;

    private Estado estado = new Estado(1L, "Amapá", "AM");

    @Test
    public void salvar_cidadeCorreto_retornaSucesso(){
        // 1. Preparação
        var novaCidade = new Cidade();
        novaCidade.setNome("Macapá");
        novaCidade.setEstado(estado);

        // 2. Ação
        novaCidade = service.salvar(novaCidade);

        // 3. Validação
        Assertions.assertNotNull(novaCidade);
        Assertions.assertNotNull(novaCidade.getId());
    }

    @Test
    public void salvar_cidadeSemNome_retornaErro(){
        // 1. Preparação
        var novaCidade = new Cidade();
        novaCidade.setEstado(estado);

        // 2. Ação e 3. Validação
        Assertions.assertThrows(ValidacaoException.class, () ->
                service.salvar(novaCidade));
    }

    @Test
    public void salvar_cidadeSemEstado_retornaErro(){
        // 1. Preparação
        var novaCidade = new Cidade();
        novaCidade.setNome("Macapá");

        // 2. Ação e 3. Validação
        Assertions.assertThrows(ValidacaoException.class, () ->
                service.salvar(novaCidade));
    }



}
