package com.algaworks.algafood.api.model.output;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Relation(collectionRelation = "restaurantes")
public class RestauranteDTO extends RepresentationModel<RestauranteDTO> implements RestauranteModel{

    private Long id;

    private String nome;

    private BigDecimal taxaFrete;

    private CozinhaDTO cozinha;

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
