package com.algaworks.algafood.api.model.entrada;

import com.algaworks.algafood.api.model.saida.CidadeDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EnderecoEntradaDTO {

    @NotBlank
    private String logradouro;

    @NotBlank
    private String numero;

    private String complemento;

    @NotBlank
    private String bairro;

    @Valid
    @NotNull
    private CidadeIdEntrada cidade;

    @NotBlank
    private String cep;
}
