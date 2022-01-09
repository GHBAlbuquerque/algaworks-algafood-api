package com.algaworks.algafood.di.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	    
	    ativacaoClienteService.ativar();
		
		return "Notificado!";
	}
}
