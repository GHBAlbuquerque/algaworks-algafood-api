package com.algaworks.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoInputDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @NotBlank
    private String contentType;

    @NotNull
    private Long tamanho;
}
