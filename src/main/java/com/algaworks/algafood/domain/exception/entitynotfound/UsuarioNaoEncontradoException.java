package com.algaworks.algafood.domain.exception.entitynotfound;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;
    private static final String MSG_USUARIO_NAO_ENCONTRADO = "Não existe usuário cadastrado para o id %s.";

    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public UsuarioNaoEncontradoException(Long restauranteId) {
        this(String.format(MSG_USUARIO_NAO_ENCONTRADO, restauranteId));
    }
}