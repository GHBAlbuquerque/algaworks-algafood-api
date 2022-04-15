package com.algaworks.algafood.infrastructure.impl.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infrastructure.exception.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties properties;

    @Autowired
    private ProcessadorEmailTemplate processadorEmailTemplate;

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
        String corpo = processadorEmailTemplate.processarTemplate(mensagem);
        var mimeMessage = mailSender.createMimeMessage();

        //encapsula o mimemssage e ajuda a atribuir os valores
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        helper.setSubject(mensagem.getAssunto());
        helper.setText(corpo, true);
        helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
        helper.setFrom(properties.getRemetente());

        return mimeMessage;
    }


}
