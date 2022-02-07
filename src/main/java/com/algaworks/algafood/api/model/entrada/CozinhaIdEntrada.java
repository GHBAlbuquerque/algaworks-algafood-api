package com.algaworks.algafood.api.model.entrada;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CozinhaIdEntrada {

    @NotNull
    private Long id;
}
