package com.algaworks.algafood.api.v1.model.input.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SenhaUpdateDTO {

    private String senhaAtual;
    private String novaSenha;
}
