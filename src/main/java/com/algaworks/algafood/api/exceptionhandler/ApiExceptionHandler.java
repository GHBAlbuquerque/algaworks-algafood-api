package com.algaworks.algafood.api.exceptionhandler;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.ProblemTypeEnum;
import com.algaworks.algafood.domain.exception.entitynotfound.EntidadeNaoEncontradaException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

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
	
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var rootCause = ExceptionUtils.getRootCause(ex);
		
		if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
		} 
		
		var problem = genericProblemBuilder(status, ProblemTypeEnum.MENSAGEM_INCOMPREENSIVEL, 
				"O corpo da mensagem está inválido. Verifique erro de sintaxe.").build();
		
		return handleExceptionInternal(ex, problem, null, status, request);
	}

	
	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
		
		var message = ex instanceof IgnoredPropertyException ? 
					"O valor não pode ser atribuído à propriedade '%s' pois ela está sendo ignorada. Verifique o corpo da requisição." :
					"O valor não pode ser atribuído à propriedade '%s' pois ela não existe. Verifique o corpo da requisição.";
				
		var problem = genericProblemBuilder(status, ProblemTypeEnum.MENSAGEM_INCOMPREENSIVEL, String.format(message, path)).build(); 
		
		return handleExceptionInternal(ex, problem, null, status, request);
	
	}


	//MÉTODO RECUPERANDO PROPRIEDADE, VALOR E TIPO PASSADOS ERRADOS NA SERIALIZAÇÃO!
	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
		
		var problem = genericProblemBuilder(status, ProblemTypeEnum.MENSAGEM_INCOMPREENSIVEL, 
				String.format("O nome da propriedade '%s' recebeu o valor '%s' que é de um tipo inválido. Informe um valor compatível com o tipo '%s'.", path, ex.getValue(), ex.getTargetType().getSimpleName()))
				.build();
		
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


















