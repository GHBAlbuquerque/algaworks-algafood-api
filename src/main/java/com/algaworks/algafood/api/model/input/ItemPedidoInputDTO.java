package com.algaworks.algafood.api.model.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class ItemPedidoInputDTO {

    @NotNull
    @Positive
    private Integer quantidade;

    private String observacao;

    @NotNull
    @JsonProperty("produto")
    private Long produtoId;
}
