package com.algaworks.algafood.api.model.saida;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    private String restauranteNome;

    private String clienteNome;

    private String status;

    private String formaPagamentoDescricao;
}
