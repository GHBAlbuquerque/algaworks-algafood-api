package com.algaworks.algafood.domain.model.mixin;

import com.algaworks.algafood.domain.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class RestauranteMixin {

    @JsonIgnoreProperties(value = {"nome"}, allowGetters = true)
    private Cozinha cozinha;

    @JsonIgnore
    private OffsetDateTime dataCadastro;

    @JsonIgnore
    private OffsetDateTime dataAtualizacao;

    @JsonIgnore
    private List<FormaPagamento> formasPagamento;

    @JsonIgnore
    private List<Produto> produtos = new ArrayList<>();

    @JsonIgnore
    private Endereco endereco;

    @JsonIgnore
    private List<Usuario> responsaveis;
}
