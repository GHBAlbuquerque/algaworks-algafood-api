package com.algaworks.algafood.validation.annotations;

import com.algaworks.algafood.validation.validators.FileSizeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {FileSizeValidator.class})
public @interface FileSize {

    String message() default "Tamanho do arquivo inválido. Não deve exceder {1} .";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String maxSize();
}
