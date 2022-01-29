package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;


@Getter
public class CustomProblem extends GenericProblem{
	
	private String userMessage;
	private LocalDateTime dataHora;
	
	@Builder(builderMethodName = "customProblemBuilder")
	public CustomProblem(Integer status, String type, String title, String detail, String userMessage,
			LocalDateTime dataHora) {
		super(status, type, title, detail);
		this.userMessage = userMessage;
		this.dataHora = dataHora;
	}
	

}
