package com.algaworks.algafood.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

    @NotNull
    private String remetente;

    private TipoImplementacao impl;

    //TODO: remover esse new para testar a criação de erros que vem sem cause
    private Sandbox sandbox;

    public enum TipoImplementacao {
        FAKE, SMTP, SANDBOX;
    }

    @Getter
    @Setter
    public class Sandbox {

        private String destinatario;

    }
}
