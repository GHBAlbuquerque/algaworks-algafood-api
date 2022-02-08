package com.algaworks.algafood.api.model.entrada;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FormaPagamentoEntradaDTO {

    @NotNull
    private String descricao;
}
