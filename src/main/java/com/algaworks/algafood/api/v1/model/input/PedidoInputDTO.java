package com.algaworks.algafood.api.v1.model.input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PedidoInputDTO {

    @JsonIgnore
    private Long id;

    @NotNull
    @JsonProperty("restaurante")
    private Long restauranteId;

    @NotNull
    @JsonProperty("cliente")
    private Long clienteId;

    @NotNull
    @Valid
    private EnderecoInputDTO enderecoEntrega;

    @NotBlank
    private String status;

    @NotNull
    @Size(min = 1)
    @Valid
    private List<ItemPedidoInputDTO> itens;

    @NotNull
    @JsonProperty("formaPagamento")
    private Long formaPagamentoId;
}
