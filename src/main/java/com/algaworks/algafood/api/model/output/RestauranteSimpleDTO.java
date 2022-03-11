package com.algaworks.algafood.api.model.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "restaurantes")
public class RestauranteSimpleDTO extends RepresentationModel<RestauranteSimpleDTO> implements RestauranteModel {

    private Long id;

    private String nome;

    private boolean ativo;

    private boolean aberto;

    @Override
    public Long getId(){
        return this.id;
    }

    @Override
    public Boolean isAtivo(){
        return this.ativo;
    }

    @Override
    public Boolean isAberto(){
        return this.ativo;
    }
}
