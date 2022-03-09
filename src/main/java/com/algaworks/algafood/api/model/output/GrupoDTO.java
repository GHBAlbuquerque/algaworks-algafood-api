package com.algaworks.algafood.api.model.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;


@Getter
@Setter
@Relation(collectionRelation = "grupos")
public class GrupoDTO extends RepresentationModel<GrupoDTO> {

    private Long id;

    private String nome;

}
