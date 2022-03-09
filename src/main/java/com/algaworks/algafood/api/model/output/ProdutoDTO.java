package com.algaworks.algafood.api.model.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "produtos")
public class ProdutoDTO extends RepresentationModel<ProdutoDTO> {

    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Boolean ativo;

    private FotoProdutoDTO foto;
}
