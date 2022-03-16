package com.algaworks.algafood.domain.exception;

public class EntidadeReferenciadaInexistenteException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final String MSG_ENTIDADE_REFERENCIADA_NAO_ENCONTRADA = "Não existe recurso do tipo %s para o id %s.";


    public EntidadeReferenciadaInexistenteException(String mensagem) {
        super(mensagem);
    }

    public EntidadeReferenciadaInexistenteException(Class classe, Long id) {
        this(String.format(MSG_ENTIDADE_REFERENCIADA_NAO_ENCONTRADA, classe.getSimpleName(), id));
    }
}
