package com.algaworks.algafood.integration;

import com.algaworks.algafood.domain.exception.ValidacaoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CadastroEstadoIntegrationTestsIT {
    
    @Autowired
    private CadastroEstadoService service;

    @Test
    public void salvar_estadoCorreto_retornaSucesso(){
        // 1. Preparação
        var novoEstado = new Estado();
        novoEstado.setNome("Mato Grosso");
        novoEstado.setSigla("MT");

        // 2. Ação
        novoEstado = service.salvar(novoEstado);

        // 3. Validação
        Assertions.assertNotNull(novoEstado);
        Assertions.assertNotNull(novoEstado.getId());
    }

    @Test
    public void salvar_estadoSemNome_retornaErro(){
        // 1. Preparação
        var novoEstado = new Estado();
        novoEstado.setSigla("MT");

        // 2. Ação e 3. Validação
        Assertions.assertThrows(ValidacaoException.class, () ->
                service.salvar(novoEstado));
    }

    @Test
    public void salvar_estadoSemSigla_retornaErro(){
        // 1. Preparação
        var novoEstado = new Estado();
        novoEstado.setNome("Mato Grosso");

        // 2. Ação e 3. Validação
        Assertions.assertThrows(ValidacaoException.class, () ->
                service.salvar(novoEstado));
    }

}
