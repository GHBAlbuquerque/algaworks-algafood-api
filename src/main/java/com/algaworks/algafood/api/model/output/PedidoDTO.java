package com.algaworks.algafood.api.model.output;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PedidoDTO {

    private Long id;

    private BigDecimal subtotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    private LocalDate dataCriacao;

    private LocalDate dataConfirmacao;

    private LocalDate dataEntrega;

    private LocalDate dataCancelamento;

    private RestauranteSimpleDTO restaurante;

    private UsuarioDTO cliente;

    private EnderecoDTO enderecoEntrega;

    private String status;

    private List<ItemPedidoDTO> itens;

    private FormaPagamentoDTO formaPagamento;
}
