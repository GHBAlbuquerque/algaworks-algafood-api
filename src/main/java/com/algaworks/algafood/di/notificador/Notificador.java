package com.algaworks.algafood.di.notificador;

import com.algaworks.algafood.di.modelo.Cliente;

public interface Notificador {
	
	public void notificar(Cliente cliente, String mensagem);

}
