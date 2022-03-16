package com.algaworks.algafood.api.v1.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "cidades")
public class CidadeDTO extends RepresentationModel<CidadeDTO> {

    private Long id;

    private String nome;

    @JsonProperty(value = "estado")
    private String estadoNome;

}
