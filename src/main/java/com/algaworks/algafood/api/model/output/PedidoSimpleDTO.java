package com.algaworks.algafood.api.model.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PedidoSimpleDTO {

    private Long id;

    private BigDecimal subtotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    private LocalDate dataCriacao;

    private LocalDate dataEntrega;

    private LocalDate dataCancelamento;

    @JsonProperty("restaurante")
    private Long restauranteId;

    @JsonProperty("cliente")
    private Long clienteId;

    private String status;

    private String formaPagamentoDescricao;
}
