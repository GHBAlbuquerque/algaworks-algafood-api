package com.algaworks.algafood.api.model.saida;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestauranteDTO {

    private Long id;

    private String nome;

    private BigDecimal taxaFrete;

    private CozinhaDTO cozinha;

    private boolean ativo;
}