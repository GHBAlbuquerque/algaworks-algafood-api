package com.algaworks.algafood.api.model.saida;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CidadeDTO {

    private Long id;

    private String nome;

    @JsonProperty(value = "estado")
    private String estadoNome;

}
