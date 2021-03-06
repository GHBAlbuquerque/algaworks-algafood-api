package com.algaworks.algafood.api.v1.model.input.update;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoUpdateDTO {

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Boolean ativo;

}
