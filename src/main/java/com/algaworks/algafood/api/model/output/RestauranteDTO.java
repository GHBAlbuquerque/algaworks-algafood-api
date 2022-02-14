package com.algaworks.algafood.api.model.output;

import com.algaworks.algafood.api.model.view.PedidoView;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestauranteDTO {

    @JsonView(PedidoView.PedidoSimpleDTO.class)
    private Long id;

    private String nome;

    private BigDecimal taxaFrete;

    private CozinhaDTO cozinha;

    private boolean ativo;

    private boolean aberto;
}
