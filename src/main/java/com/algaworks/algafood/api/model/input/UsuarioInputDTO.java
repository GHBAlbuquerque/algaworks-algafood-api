package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.api.model.input.update.UsuarioUpdateDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioInputDTO extends UsuarioUpdateDTO {

    @NotBlank
    private String senha;
}
