package com.algaworks.algafood.api.v1.exceptionhandler;

import com.algaworks.algafood.domain.exception.*;
import com.algaworks.algafood.domain.exception.entitynotfound.EntidadeNaoEncontradaException;
import com.algaworks.algafood.infrastructure.exception.EmailException;
import com.algaworks.algafood.infrastructure.exception.StorageException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String MSG_ERRO_GENERICA = "Ocorreu um erro interno inesperado no sistema. Tente novamente e, se o problema persistir, entre em contato com o administrador.";
    @Autowired
    private MessageSource messageSource;

    // ------------ OVERRIDE DO EXCEPTION INTERNAL ---------------------

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = customProblemBuilder(status, ProblemTypeEnum.ERRO_DE_SISTEMA, ex.getClass().toString(), MSG_ERRO_GENERICA, OffsetDateTime.now(), null);
        } else if (body instanceof String) {
            body = customProblemBuilder(status, ProblemTypeEnum.ERRO_DE_SISTEMA, ex.getCause().toString(), (String) body, OffsetDateTime.now(), null);
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }


    // ------------ EXCEÇÕES DE NEGÓCIO ---------------------

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex,
                                                                  WebRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var problem = genericProblemBuilder(status, ProblemTypeEnum.RECURSO_NAO_ENCONTRADO, ex.getMessage()).build();

        return handleExceptionInternal(ex, problem, null, status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {
        var status = HttpStatus.CONFLICT;
        var problem = genericProblemBuilder(status, ProblemTypeEnum.ENTIDADE_EM_USO, ex.getMessage()).build();

        return handleExceptionInternal(ex, problem, null, status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var problem = genericProblemBuilder(status, ProblemTypeEnum.ERRO_DE_NEGOCICO, ex.getMessage()).build();

        return handleExceptionInternal(ex, problem, null, status, request);
    }

    @ExceptionHandler(EntidadeReferenciadaInexistenteException.class)
    public ResponseEntity<?> handleEntidadeReferenciadaInexistenteException(EntidadeReferenciadaInexistenteException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var problem = genericProblemBuilder(status, ProblemTypeEnum.RECURSO_NAO_ENCONTRADO, ex.getMessage()).build();

        return handleExceptionInternal(ex, problem, null, status, request);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<?> handleValidacaoException(ValidacaoException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        List<CustomProblem.Field> fields = fieldListBuilder(ex.getBindingResult());

        var problem = customProblemBuilder(status,
                ProblemTypeEnum.DADOS_INVALIDOS,
                "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",
                "Houve um erro ao validar os dados da requisição.",
                OffsetDateTime.now(), fields).build();

        return handleExceptionInternal(ex, problem, null, status, request);
    }

    @ExceptionHandler(ConversaoException.class)
    public ResponseEntity<?> handleConversaoException(ConversaoException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var problem = genericProblemBuilder(status, ProblemTypeEnum.ERRO_AO_CONVERTER, ex.getMessage()).build();

        return handleExceptionInternal(ex, problem, null, status, request);
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<?> handleStorageException(StorageException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var problem = genericProblemBuilder(status, ProblemTypeEnum.ERRO_AO_MANIPULAR_ARQUIVO, ex.getMessage()).build();

        return handleExceptionInternal(ex, problem, null, status, request);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<?> handleStorageException(EmailException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var problem = genericProblemBuilder(status, ProblemTypeEnum.ERRO_DE_EMAIL, ex.getMessage()).build();

        return handleExceptionInternal(ex, problem, null, status, request);
    }

    // ------------ OVERRIDE DE EXCEÇÕES DO SPRING PARA CUSTOMIZAÇÃO ---------------------

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleUncaughtException(ValidationException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var root = ExceptionUtils.getRootCause(ex);
        var problem = customProblemBuilder(status, ProblemTypeEnum.ERRO_AO_VALIDAR, root.getCause().toString(), "Houve um erro ao validar os dados da requisição.",
                OffsetDateTime.now(), null).build();


        return handleExceptionInternal(ex, problem, null, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaughtException(Exception ex, WebRequest request) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var problem = customProblemBuilder(status, ProblemTypeEnum.ERRO_DE_SISTEMA, ex.getMessage(),
                MSG_ERRO_GENERICA, OffsetDateTime.now(), null).build();

        ex.printStackTrace();

        return handleExceptionInternal(ex, problem, null, status, request);
    }


    // ------------ OVERRIDE DE TRATAMENTO DE EXCEÇÕES DO SPRING PARA CUSTOMIZAÇÃO ---------------------

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        var rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        }

        var problem = genericProblemBuilder(status, ProblemTypeEnum.MENSAGEM_INCOMPREENSIVEL,
                "O corpo da mensagem está inválido. Verifique erro de sintaxe.").build();

        return handleExceptionInternal(ex, problem, null, status, request);
    }


    //  **** EXCEPTION PARA LIDAR COM PROPRIEDADES INEXISTENTES OU IGNORADAS NO JSON ****
    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        var path = pathBuilder(ex);

        var message = ex instanceof IgnoredPropertyException
                ? "O valor não pode ser atribuído à propriedade '%s' pois ela está sendo ignorada. Verifique o corpo da requisição."
                : "O valor não pode ser atribuído à propriedade '%s' pois ela não existe. Verifique o corpo da requisição.";

        var problem = genericProblemBuilder(status, ProblemTypeEnum.MENSAGEM_INCOMPREENSIVEL,
                String.format(message, path)).build();

        return handleExceptionInternal(ex, problem, null, status, request);
    }


    // **** EXCEPTION PARA LIDAR COM TIPOS ERRADOS PASSADOS NO JSON ****
    // MÉTODO RECUPERANDO PROPRIEDADE, VALOR E TIPO PASSADOS ERRADOS NA SERIALIZAÇÃO!
    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
                                                                HttpStatus status, WebRequest request) {

        var path = pathBuilder(ex);

        var problem = genericProblemBuilder(status, ProblemTypeEnum.MENSAGEM_INCOMPREENSIVEL, String.format(
                "O nome da propriedade '%s' recebeu o valor '%s' que é de um tipo inválido. Informe um valor compatível com o tipo '%s'.",
                path, ex.getValue(), ex.getTargetType().getSimpleName())).build();

        return handleExceptionInternal(ex, problem, null, status, request);
    }


    //  **** EXCEPTION PARA LIDAR COM PARÂMETRO PASSADO ERRADO NA URL (PAI) ****
    @Override
    public ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                     HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) ex, headers, status,
                    request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }


    //  **** EXCEPTION PARA LIDAR COM PARÂMETRO PASSADO ERRADO NA URL  ****
    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
                                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {

        var problem = genericProblemBuilder(status, ProblemTypeEnum.PARAMETRO_INVALIDO, String.format(
                "O parâmetro '%s' passado na URL recebeu o valor '%s' que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s",
                ex.getName(), ex.getValue(), ex.getRequiredType())).build();

        return handleExceptionInternal(ex, problem, null, status, request);
    }

    //  **** EXCEPTION PARA LIDAR COM URLS INEXISTENTES  ****
    // necessario habilitar lancamento da exception no properties!
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers, HttpStatus status, WebRequest request) {

        var problemType = ProblemTypeEnum.RECURSO_NAO_ENCONTRADO;
        String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", ex.getRequestURL());

        var problem = genericProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    //  **** EXCEPTION PARA LIDAR COM VIOLAÇÕES DE BEAN VALIDATION (@Valid)  ****
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        // armazena todas as constraints de violacao do request
        var bindingResult = ex.getBindingResult();

        List<CustomProblem.Field> fields = fieldListBuilder(bindingResult);

        var problem = customProblemBuilder(status, ProblemTypeEnum.DADOS_INVALIDOS,
                "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",
                null, OffsetDateTime.now(), fields).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    //  **** EXCEPTION PARA LIDAR PARAMS ERRADOS

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        var bindingResult = ex.getBindingResult();

        List<CustomProblem.Field> fields = fieldListBuilder(bindingResult);

        var problem = customProblemBuilder(status, ProblemTypeEnum.DADOS_INVALIDOS,
                "Um ou mais parâmetros de pesquisa estão inválidos. Faça o preenchimento correto e tente novamente.",
                null, OffsetDateTime.now(), fields).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAcessDenied(AccessDeniedException ex, WebRequest request) {

        HttpStatus status = HttpStatus.FORBIDDEN;
        var problemType = ProblemTypeEnum.ACESSO_NEGADO;
        var detail = ex.getMessage();

        GenericProblem problem = genericProblemBuilder(status, problemType, detail)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    // ------------ BUILDERS ---------------------

    private GenericProblem.GenericProblemBuilder genericProblemBuilder(HttpStatus status,
                                                                       ProblemTypeEnum problemTypeEnum,
                                                                       String detail) {
        return GenericProblem.builder()
                .status(status.value())
                .type(problemTypeEnum.getUri())
                .title(problemTypeEnum.getTitle())
                .detail(detail);
    }

    private CustomProblem.CustomProblemBuilder customProblemBuilder(HttpStatus status,
                                                                    ProblemTypeEnum problemTypeEnum,
                                                                    String detail, String userMessage,
                                                                    OffsetDateTime dataHora,
                                                                    List<CustomProblem.Field> fields) {

        return CustomProblem.customProblemBuilder()
                .status(status.value())
                .type(problemTypeEnum.getUri())
                .title(problemTypeEnum.getTitle())
                .detail(detail)
                .userMessage(userMessage)
                .dataHora(dataHora)
                .fields(fields);

    }

    private String pathBuilder(JsonMappingException ex) {
        return ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
    }

    private List<CustomProblem.Field> fieldListBuilder(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    var message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                    String nome = objectError.getObjectName();

                    if (objectError instanceof FieldError) { //para o caso de serem validações de atributos
                        nome = ((FieldError) objectError).getField();
                    } //sem isso, o nome do campo será sempre o da classe.

                    return CustomProblem.Field.builder()
                            .nome(nome)
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());
    }


	/*		List<CustomProblem.Field> fields = bindingResult.getFieldErrors().stream()
						.map(fieldError -> CustomProblem.Field.builder()
							.nome(fieldError.getField())
							.userMessage(messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()))
							.build())
						.collect(Collectors.toList()); */

}
