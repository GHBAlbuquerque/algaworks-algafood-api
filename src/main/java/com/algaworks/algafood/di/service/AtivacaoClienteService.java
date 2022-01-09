package com.algaworks.algafood.di.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.notificador.NivelUrgencia;
import com.algaworks.algafood.di.notificador.Notificador;
import com.algaworks.algafood.di.notificador.TipoDoNotificador;

@Component
public class AtivacaoClienteService {
	
	@Autowired
	@TipoDoNotificador(NivelUrgencia.NORMAL)
	private Notificador notificador;
	
	public void ativar() {
		notificador.notificar("Seu cadastro no sistema está ativo!");
	}

}
