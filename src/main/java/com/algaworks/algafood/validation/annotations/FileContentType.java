package com.algaworks.algafood.validation.annotations;

import com.algaworks.algafood.validation.validators.FileContentTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {FileContentTypeValidator.class})
public @interface FileContentType {

    String message() default "Extensão inválida. A foto deve ser do tipo JPEG ou PNG.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] allowed();
}
