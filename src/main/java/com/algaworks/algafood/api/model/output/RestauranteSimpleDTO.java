package com.algaworks.algafood.api.model.output;

import com.algaworks.algafood.api.model.view.PedidoView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "restaurantes")
public class RestauranteSimpleDTO extends RepresentationModel<RestauranteSimpleDTO> {

    @JsonView(PedidoView.PedidoSimpleDTO.class)
    private Long id;

    private String nome;
}
