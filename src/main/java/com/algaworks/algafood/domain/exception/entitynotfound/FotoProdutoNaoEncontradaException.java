package com.algaworks.algafood.domain.exception.entitynotfound;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;
    private static final String MSG_FOTO_PRODUTO_NAO_ENCONTRADA = "Não existe foto cadastrada para o produto de id %s no restaurante de id %s.";

    public FotoProdutoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public FotoProdutoNaoEncontradaException(Long id, Long idRestaurante) {
        this(String.format(MSG_FOTO_PRODUTO_NAO_ENCONTRADA, id, idRestaurante));
    }
}
