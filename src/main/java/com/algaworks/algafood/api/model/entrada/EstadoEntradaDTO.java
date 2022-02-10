package com.algaworks.algafood.api.model.entrada;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EstadoEntradaDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String sigla;
}
