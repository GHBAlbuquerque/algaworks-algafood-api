package com.algaworks.algafood.api.model.entrada;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EstadoEntradaDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String sigla;
}
