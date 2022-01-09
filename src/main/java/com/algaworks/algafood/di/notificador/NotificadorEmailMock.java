package com.algaworks.algafood.di.notificador;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("local")
@TipoDoNotificador(NivelUrgencia.NORMAL)
@Component
public class NotificadorEmailMock implements Notificador{

	@Override
	public void notificar(String mensagem) {
		System.out.printf("Notificando através do e-mail (mock) %s: %s\n", "email@email.com", mensagem);
	
	}

}
