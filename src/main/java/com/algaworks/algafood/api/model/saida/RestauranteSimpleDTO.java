package com.algaworks.algafood.api.model.saida;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestauranteSimpleDTO {

    private Long id;

    private String nome;
}
