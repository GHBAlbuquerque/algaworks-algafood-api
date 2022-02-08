package com.algaworks.algafood.api.model.entrada;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CidadeEntradaDTO {

    @NotBlank
    private String nome;

    @NotNull
    @JsonProperty("estado")
    private Long estadoId;

}
