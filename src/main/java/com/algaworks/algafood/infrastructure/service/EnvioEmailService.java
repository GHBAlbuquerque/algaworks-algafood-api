package com.algaworks.algafood.infrastructure.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.Set;

public interface EnvioEmailService {

    void enviar(Mensagem mensagem);

    @Getter
    @Builder
    class Mensagem {
        @Singular
        private Set<String> destinatarios;
        private String assunto;
        private String corpo;
    }
}
