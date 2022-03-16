package com.algaworks.algafood.domain.exception.entitynotfound;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;
    private static final String MSG_PERMISSAO_NAO_ENCONTRADA = "Não existe permissao cadastrado para o id %s.";

    public PermissaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public PermissaoNaoEncontradaException(Long id) {
        this(String.format(MSG_PERMISSAO_NAO_ENCONTRADA, id));
    }
}
