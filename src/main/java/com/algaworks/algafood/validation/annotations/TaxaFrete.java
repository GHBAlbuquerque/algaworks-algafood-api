package com.algaworks.algafood.validation.annotations;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@PositiveOrZero
@NotNull
public @interface TaxaFrete {

    @OverridesAttribute(constraint = PositiveOrZero.class, name="message")
    String message() default "{0} está fora do intervalo válido de frete.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
