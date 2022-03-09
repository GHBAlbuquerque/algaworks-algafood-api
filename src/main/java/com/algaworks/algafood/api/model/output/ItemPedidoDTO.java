package com.algaworks.algafood.api.model.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Getter
@Setter
@Relation(collectionRelation = "itensPedido")
public class ItemPedidoDTO extends RepresentationModel<ItemPedidoDTO> {

    private Long produtoId;

    private String produtoNome;

    private Integer quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal precoTotal;

    private String observacao;

}
