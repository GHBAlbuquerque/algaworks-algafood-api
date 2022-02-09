package com.algaworks.algafood.api.model.entrada;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioNovoEntradaDTO extends UsuarioEntradaDTO {

    @NotBlank
    private String senha;
}
