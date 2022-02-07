package com.algaworks.algafood.api.model.entrada;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EstadoEntradaDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String sigla;
}
