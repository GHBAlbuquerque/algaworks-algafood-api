package com.algaworks.algafood.di.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.service.AtivacaoClienteService;

@Controller
public class NotificacaoController {

	private AtivacaoClienteService ativacaoClienteService;
	
	public NotificacaoController(AtivacaoClienteService ativacaoClienteService) {
		this.ativacaoClienteService = ativacaoClienteService;
	}

	@GetMapping("/notificar")
	@ResponseBody
	public String notificar() {
	    
		 Cliente giovanna = new Cliente("Giovanna", "giovanna@xyz.com", "3499998888");
		    
		  ativacaoClienteService.ativar(giovanna);
		
		return "Notificado!";
	}
}
