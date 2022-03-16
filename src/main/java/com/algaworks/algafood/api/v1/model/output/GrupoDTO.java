package com.algaworks.algafood.api.v1.model.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@Getter
@Setter
@Relation(collectionRelation = "grupos")
public class GrupoDTO extends RepresentationModel<GrupoDTO> {

    private Long id;

    private String nome;

    private List<PermissaoDTO> permissoes;

}
