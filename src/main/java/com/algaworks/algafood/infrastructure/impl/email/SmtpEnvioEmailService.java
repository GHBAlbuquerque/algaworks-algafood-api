package com.algaworks.algafood.infrastructure.impl.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.infrastructure.exception.EmailException;
import com.algaworks.algafood.infrastructure.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties properties;

    @Autowired
    private Configuration freeMarkerConfig;

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            var mimeMessage = criarMimeMessage(mensagem);

            mailSender.send(mimeMessage);

        } catch (Exception ex) {
            throw new EmailException("Não foi possível enviar o e-mail.", ex.getCause());
        }
    }

    protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
        String corpo = processarTemplate(mensagem);
        var mimeMessage = mailSender.createMimeMessage();

        //encapsula o mimemssage e ajuda a atribuir os valores
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        helper.setSubject(mensagem.getAssunto());
        helper.setText(corpo, true);
        helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
        helper.setFrom(properties.getRemetente());

        return mimeMessage;
    }

    private String processarTemplate(Mensagem mensagem) {
        try {
            var template = freeMarkerConfig.getTemplate(mensagem.getCorpo());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
        } catch (IOException | TemplateException ex) {
            throw new EmailException("Não foi possível montar o template do e-mail.", ex.getCause());
        }
    }
}
