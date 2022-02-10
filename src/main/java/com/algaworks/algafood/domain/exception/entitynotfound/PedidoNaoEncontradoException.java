package com.algaworks.algafood.domain.exception.entitynotfound;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	private static final String MSG_PEDIDO_NAO_ENCONTRADO = "Não existe pedido cadastrado para o id %s.";

	public PedidoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public PedidoNaoEncontradoException(Long id) {
		super(String.format(MSG_PEDIDO_NAO_ENCONTRADO, id));
	} 

}
