package com.algaworks.algafood.di.notificador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;

@Profile("local")
@TipoDoNotificador(NivelUrgencia.NORMAL)
@Component
public class NotificadorEmailMock implements Notificador{
	
	@Autowired
	private NotificadorProperties notificadorProperties;

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("Notificando %s através do servidor %s. \nE-mail (mock) %s: %s\n", 
				 cliente.getNome(), notificadorProperties.getHostServidor(), cliente.getEmail(), mensagem);
	}

}
