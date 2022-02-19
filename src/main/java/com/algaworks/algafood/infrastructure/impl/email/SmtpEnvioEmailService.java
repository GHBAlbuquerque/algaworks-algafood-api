package com.algaworks.algafood.infrastructure.impl.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.infrastructure.exception.EmailException;
import com.algaworks.algafood.infrastructure.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties properties;

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            var mimeMessage = mailSender.createMimeMessage();

            //encapsula o mimemssage e ajuda a atribuir os valores
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setSubject(mensagem.getAssunto());
            helper.setText(mensagem.getCorpo(), true);
            helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            helper.setFrom(properties.getRemetente());

            mailSender.send(mimeMessage);

        } catch (Exception ex) {
            throw new EmailException("Não foi possível enviar o e-mail.", ex.getCause());
        }
    }
}
