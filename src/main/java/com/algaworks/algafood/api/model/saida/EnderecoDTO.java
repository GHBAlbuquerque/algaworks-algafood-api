package com.algaworks.algafood.api.model.saida;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDTO {

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private CidadeDTO cidade;

    private String cep;
}
