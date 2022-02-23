package com.algaworks.algafood.infrastructure.impl.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.infrastructure.exception.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class SandBoxEnvioEmailService extends SmtpEnvioEmailService {

    @Autowired
    private EmailProperties emailProperties;

    @Override
    protected MimeMessage criarMimeMessage(Mensagem mensagem) {
        try {
            MimeMessage mimeMessage = super.criarMimeMessage(mensagem);

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setTo(emailProperties.getSandbox().getDestinatario());
            return mimeMessage;
        } catch (MessagingException e) {
            throw new EmailException("Erro ao enviar e-mail de sandbox");
        }
    }
}

