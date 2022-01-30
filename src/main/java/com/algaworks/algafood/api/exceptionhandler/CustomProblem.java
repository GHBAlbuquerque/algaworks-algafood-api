package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomProblem extends GenericProblem{
	
	private String userMessage;
	private LocalDateTime dataHora;
	private List<Field> fields;
	
	@Builder(builderMethodName = "customProblemBuilder")
	public CustomProblem(Integer status, String type, String title, String detail, String userMessage,
			LocalDateTime dataHora, List<Field> fields) {
		super(status, type, title, detail);
		this.userMessage = userMessage;
		this.dataHora = dataHora;
		this.fields = fields;
	}

	@Getter
	@Builder
	public static class Field {
		private String nome;
		private String userMessage;
	}

}
