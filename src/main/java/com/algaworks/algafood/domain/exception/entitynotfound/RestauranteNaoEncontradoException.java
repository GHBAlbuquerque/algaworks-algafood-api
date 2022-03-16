package com.algaworks.algafood.domain.exception.entitynotfound;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;
    private static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não existe restaurante cadastrado para o id %s.";

    public RestauranteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public RestauranteNaoEncontradoException(Long restauranteId) {
        this(String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId));
    }
}        