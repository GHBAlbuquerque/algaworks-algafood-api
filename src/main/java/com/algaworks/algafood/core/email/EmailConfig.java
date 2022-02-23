package com.algaworks.algafood.core.email;

import com.algaworks.algafood.infrastructure.impl.email.FakeEnvioEmailService;
import com.algaworks.algafood.infrastructure.impl.email.SandBoxEnvioEmailService;
import com.algaworks.algafood.infrastructure.impl.email.SmtpEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService(){
        switch(emailProperties.getImpl()) {
            case SMTP:
                return new SmtpEnvioEmailService();
            case SANDBOX:
                return new SandBoxEnvioEmailService();
            default:
                return new FakeEnvioEmailService();
        }

    }
}
