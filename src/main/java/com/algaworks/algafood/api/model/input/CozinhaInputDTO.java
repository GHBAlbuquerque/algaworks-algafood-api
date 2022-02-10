package com.algaworks.algafood.api.model.input;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CozinhaInputDTO {

    @NotBlank
    private String nome;
}
