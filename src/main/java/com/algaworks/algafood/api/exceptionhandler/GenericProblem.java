package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GenericProblem {

	private LocalDateTime dataHora;
	private String mensagem;
}
