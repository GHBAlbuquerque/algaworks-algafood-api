package com.algaworks.algafood.scenario;

import com.algaworks.algafood.domain.model.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class ScenarioFactory {

    public final List<Cozinha> COZINHAS = getCozinhas();
    public final Restaurante RESTAURANTE = getRestaurante();
    public final Cidade CIDADE = getCidade();
    public final Estado ESTADO = getEstado();

    private List<Cozinha> getCozinhas() {
        var data = new HashMap<Long, String>();
        data.put(1L, "Tailandesa");
        data.put(2L, "Indiana");

        var cozinhas = new ArrayList<Cozinha>();
        for(var dados : data.entrySet()) {
            cozinhas.add(Cozinha.builder().id(dados.getKey()).nome(dados.getValue()).build());
        }

       return cozinhas;
    }

    private Restaurante getRestaurante() {
        return Restaurante.builder()
                .nome("Restaurante Tailandês")
                .cozinha(getCozinhas().get(0))
                .taxaFrete(BigDecimal.TEN)
                .dataCadastro(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .endereco(new Endereco("Logradouro", "numero", "100", "bairro", getCidade(), "98706758" ))
                .build();
    }

    private Cidade getCidade(){
        return Cidade.builder()
                .id(1L)
                .nome("Palmas")
                .estado(getEstado())
                .build();
    }

    private Estado getEstado() {
        return Estado.builder()
                .id(1L)
                .nome("Tocantins")
                .sigla("TO")
                .build();
    }



}
