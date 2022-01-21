package com.algaworks.algafood.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	private static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe estado cadastrado para o id %s.";

	public EstadoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public EstadoNaoEncontradoException(Long id) {
		super(String.format(MSG_ESTADO_NAO_ENCONTRADO, id));
	} 


}
