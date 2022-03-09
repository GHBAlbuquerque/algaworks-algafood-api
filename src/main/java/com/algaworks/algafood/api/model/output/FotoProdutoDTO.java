package com.algaworks.algafood.api.model.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "fotosProduto")
public class FotoProdutoDTO extends RepresentationModel<FotoProdutoDTO> {

    private String nome;

    private String descricao;

    private String contentType;

    private Long tamanho;

}
