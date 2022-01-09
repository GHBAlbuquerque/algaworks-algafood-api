package com.algaworks.algafood.di.notificacao;

import org.springframework.stereotype.Component;

@TipoDoNotificador(NivelUrgencia.NORMAL)
@Component
public class NotificadorEmail implements Notificador{

	@Override
	public void notificar(String mensagem) {
		System.out.printf("Notificando através do e-mail %s: %s\n", "email@email.com", mensagem);
	
	}

}
