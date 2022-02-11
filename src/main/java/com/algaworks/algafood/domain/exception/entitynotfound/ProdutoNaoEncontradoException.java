package com.algaworks.algafood.domain.exception.entitynotfound;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;
    private static final String MSG_PRODUTO_NAO_ENCONTRADO = "Não existe produto de id %s cadastrado no restaurante de id %s.";

    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoNaoEncontradoException(Long id, Long idRestaurante) {
        this(String.format(MSG_PRODUTO_NAO_ENCONTRADO, id, idRestaurante));
    }
}
