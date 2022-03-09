package com.algaworks.algafood.api.model.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "enderecos")
public class EnderecoDTO extends RepresentationModel<EnderecoDTO> {

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private CidadeDTO cidade;

    private String cep;
}
