package com.algaworks.algafood.domain.exception.entitynotfound;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;
    private static final String MSG_FORMA_PAGAMENTO_NAO_ENCONTRADO = "Não existe forma de pagamento cadastrada para o id %s.";

    public FormaPagamentoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public FormaPagamentoNaoEncontradaException(Long id) {
        this(String.format(MSG_FORMA_PAGAMENTO_NAO_ENCONTRADO, id));
    }
}
