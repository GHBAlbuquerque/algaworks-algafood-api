package com.algaworks.algafood.api.v1.model.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CidadeInputDTO {

    @NotBlank
    private String nome;

    @NotNull
    @JsonProperty("estado")
    private Long estadoId;

}
