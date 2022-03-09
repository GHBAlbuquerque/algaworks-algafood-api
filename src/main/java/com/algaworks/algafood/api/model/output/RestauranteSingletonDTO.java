package com.algaworks.algafood.api.model.output;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFilter("filtroRestaurante")
public class RestauranteSingletonDTO {

    private Long id;

    private String nome;

    private BigDecimal taxaFrete;

    private CozinhaDTO cozinha;

    private boolean ativo;

    private EnderecoDTO endereco;
}
