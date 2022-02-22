package com.algaworks.algafood.infrastructure.impl.email;

import com.algaworks.algafood.infrastructure.service.EnvioEmailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService implements EnvioEmailService {

    @Override
    public void enviar(Mensagem mensagem) {
        log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), mensagem.getAssunto());
    }

}
