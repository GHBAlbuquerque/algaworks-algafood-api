package com.algaworks.algafood.api.model.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "estados")
public class EstadoDTO extends RepresentationModel<EstadoDTO> {

    private Long id;

    private String nome;

    private String sigla;
}
