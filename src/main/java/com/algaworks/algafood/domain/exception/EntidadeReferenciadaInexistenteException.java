package com.algaworks.algafood.domain.exception;

public class EntidadeReferenciadaInexistenteException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public EntidadeReferenciadaInexistenteException(String mensagem) {
		super(mensagem);
	}
}
