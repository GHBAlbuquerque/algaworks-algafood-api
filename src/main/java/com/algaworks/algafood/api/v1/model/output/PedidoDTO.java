package com.algaworks.algafood.api.v1.model.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "pedidos")
public class PedidoDTO extends RepresentationModel<PedidoSingletonDTO> {

    private String codigo;

    private BigDecimal subtotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    private RestauranteSimpleDTO restaurante;

    @JsonProperty(value = "cliente")
    private String clienteNome;

    private String status;

    private List<ItemPedidoDTO> itens;

    @JsonProperty(value = "formaPagamento")
    private String formaPagamentoDescricao;

}
