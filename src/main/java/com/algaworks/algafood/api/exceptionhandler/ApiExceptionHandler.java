package com.algaworks.algafood.api.exceptionhandler;

import java.net.http.HttpHeaders;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.ProblemTypeEnum;
import com.algaworks.algafood.domain.exception.entitynotfound.EntidadeNaoEncontradaException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
			org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request){
		var status = HttpStatus.NOT_FOUND;
		var problem = genericProblemBuilder(status, ProblemTypeEnum.ENTIDADE_NAO_ENCONTRADA, ex.getMessage()).build();
			
		return handleExceptionInternal(ex, problem, null, status, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request){
		var status = HttpStatus.CONFLICT;
		var problem = genericProblemBuilder(status, ProblemTypeEnum.ENTIDADE_EM_USO, ex.getMessage()).build();
			
		return handleExceptionInternal(ex, problem, null, status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request){
		var status = HttpStatus.BAD_REQUEST;
		var problem = genericProblemBuilder(status, ProblemTypeEnum.ERRO_DE_NEGOCICO, ex.getMessage()).build();
			
		return handleExceptionInternal(ex, problem, null, status, request);
	}
	
	private GenericProblem.GenericProblemBuilder genericProblemBuilder(HttpStatus status, ProblemTypeEnum problemTypeEnum, String detail) {
		return 	GenericProblem.builder()
				.status(status.value())
				.type(problemTypeEnum.getUri())
				.title(problemTypeEnum.getTitle())
				.detail(detail);
	}
}


















