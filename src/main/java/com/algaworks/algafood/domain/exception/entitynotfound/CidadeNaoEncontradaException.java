package com.algaworks.algafood.domain.exception.entitynotfound;

public class CidadeNaoEncontradaException  extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;
	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe cidade cadastrada para o id %s.";

    public CidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
    
    public CidadeNaoEncontradaException(Long cidadeId) {
        this(String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId));
    }   
}    
