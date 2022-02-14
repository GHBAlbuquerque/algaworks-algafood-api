package com.algaworks.algafood.api.model.output;

import com.algaworks.algafood.api.model.view.PedidoView;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PedidoDTO {

    @JsonView({PedidoView.PedidoSimpleDTO.class,PedidoView.PedidoIdentificationDTO.class})
    private String codigo;

    @JsonView(PedidoView.PedidoSimpleDTO.class)
    private BigDecimal subtotal;

    @JsonView(PedidoView.PedidoSimpleDTO.class)
    private BigDecimal taxaFrete;

    @JsonView(PedidoView.PedidoSimpleDTO.class)
    private BigDecimal valorTotal;

    @JsonView(PedidoView.PedidoSimpleDTO.class)
    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;

    private OffsetDateTime dataEntrega;

    private OffsetDateTime dataCancelamento;

    @JsonView(PedidoView.PedidoSimpleDTO.class)
    private RestauranteSimpleDTO restaurante;

    @JsonView({PedidoView.PedidoSimpleDTO.class,PedidoView.PedidoIdentificationDTO.class})
    private UsuarioDTO cliente;

    private EnderecoDTO enderecoEntrega;

    @JsonView(PedidoView.PedidoSimpleDTO.class)
    private String status;

    private List<ItemPedidoDTO> itens;

    @JsonView(PedidoView.PedidoSimpleDTO.class)
    private FormaPagamentoDTO formaPagamento;
}
