package com.algaworks.algafood.domain.exception.entitynotfound;

import com.algaworks.algafood.domain.exception.NegocioException;

public abstract class EntidadeNaoEncontradaException extends NegocioException {

	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
}
