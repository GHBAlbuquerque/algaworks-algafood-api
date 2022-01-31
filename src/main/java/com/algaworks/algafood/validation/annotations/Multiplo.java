package com.algaworks.algafood.validation.annotations;

import com.algaworks.algafood.validation.validators.MultiploValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = {MultiploValidator.class})
public @interface Multiplo {

    String message() default "{0} não é um múltiplo do número {1}.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    int numero();
}
