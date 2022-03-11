package com.algaworks.algafood.api.model.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "pedidos")
public class PedidoSingletonDTO extends RepresentationModel<PedidoSingletonDTO> {

    private String codigo;

    private BigDecimal subtotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;

    private OffsetDateTime dataEntrega;

    private OffsetDateTime dataCancelamento;

    private RestauranteSimpleDTO restaurante;

    private UsuarioDTO cliente;

    private EnderecoDTO enderecoEntrega;

    private String status;

    private List<ItemPedidoDTO> itens;

    private FormaPagamentoDTO formaPagamento;



}
