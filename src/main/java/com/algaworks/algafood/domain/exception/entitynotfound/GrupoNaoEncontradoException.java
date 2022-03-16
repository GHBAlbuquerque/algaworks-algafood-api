package com.algaworks.algafood.domain.exception.entitynotfound;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;
    private static final String MSG_GRUPO_NAO_ENCONTRADO = "Não existe grupo cadastrado para o id %s.";

    public GrupoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public GrupoNaoEncontradoException(Long id) {
        this(String.format(MSG_GRUPO_NAO_ENCONTRADO, id));
    }
}
