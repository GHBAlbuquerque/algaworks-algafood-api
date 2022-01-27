package com.algaworks.algafood.domain.exception.entitynotfound;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	private static final String MSG_COZINHA_NAO_ENCONTRADA = "Não existe cozinha cadastrada para o id %s.";

	public CozinhaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public CozinhaNaoEncontradaException(Long id) {
		super(String.format(MSG_COZINHA_NAO_ENCONTRADA, id));
	} 

}
