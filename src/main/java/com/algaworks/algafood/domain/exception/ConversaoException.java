package com.algaworks.algafood.domain.exception;

public class ConversaoException extends NegocioException {

    public ConversaoException(String mensagem) {
        super(mensagem);
    }

    public ConversaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
