package com.algaworks.algafood.di.notificacao;

import org.springframework.stereotype.Component;

@TipoDoNotificador(value = NivelUrgencia.URGENTE)
@Component
public class NotificadorSMS implements Notificador{

	@Override
	public void notificar(String mensagem) {
		System.out.printf("Notificando por SMS através do telefone %s: %s\n", "+55 11 xxxxx-xxxx", mensagem);
	}

}
