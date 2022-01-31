package com.algaworks.algafood.validation.annotations;

import com.algaworks.algafood.validation.validators.HamburgueriaValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE }) //ela pode ser usada em classe, interface, enum... nao será usada em propriedades.
@Retention(RUNTIME)
@Constraint(validatedBy = {HamburgueriaValidator.class})
public @interface Hamburgueria {

    String message() default "{Hamburgueria}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String idField();
    String nomeField();
    String nomeObrigatorio();
}
