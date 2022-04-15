package com.algaworks.algafood.infrastructure.impl.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infrastructure.exception.EmailException;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;

@Component
public class ProcessadorEmailTemplate {

    @Autowired
    private Configuration freeMarkerConfig;

    public String processarTemplate(EnvioEmailService.Mensagem mensagem) {
        try {
            var template = freeMarkerConfig.getTemplate(mensagem.getCorpo());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
        } catch (IOException | TemplateException ex) {
            throw new EmailException("Não foi possível montar o template do e-mail.", ex.getCause());
        }
    }
}
