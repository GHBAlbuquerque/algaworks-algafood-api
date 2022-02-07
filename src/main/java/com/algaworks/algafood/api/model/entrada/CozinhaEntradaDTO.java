package com.algaworks.algafood.api.model.entrada;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CozinhaEntradaDTO {

    @NotBlank
    private String nome;
}
