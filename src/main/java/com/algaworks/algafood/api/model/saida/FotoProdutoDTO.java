package com.algaworks.algafood.api.model.saida;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoDTO {

    private Long id;

    private String nome;

    private String descricao;

    private String contentType;

    private Long tamanho;

}
