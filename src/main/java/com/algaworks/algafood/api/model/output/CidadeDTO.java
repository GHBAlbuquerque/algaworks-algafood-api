package com.algaworks.algafood.api.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeDTO {

    private Long id;

    private String nome;

    @JsonProperty(value = "estado")
    private String estadoNome;

}
