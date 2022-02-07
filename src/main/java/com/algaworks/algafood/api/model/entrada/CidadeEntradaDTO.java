package com.algaworks.algafood.api.model.entrada;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CidadeEntradaDTO {

    @NotBlank
    private String nome;

    @NotNull
    @Valid
    private EstadoIdEntrada estado;

}
