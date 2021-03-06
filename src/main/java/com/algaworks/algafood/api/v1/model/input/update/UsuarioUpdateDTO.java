package com.algaworks.algafood.api.v1.model.input.update;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioUpdateDTO {

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

}
