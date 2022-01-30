package com.algaworks.algafood.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

    private int numeroMultiplo;

    @Override
    public void initialize(Multiplo constraintAnnotation) {
        this.numeroMultiplo = constraintAnnotation.numero();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        var valor = BigDecimal.valueOf(value.doubleValue());
        var numero = BigDecimal.valueOf(numeroMultiplo);

        return BigDecimal.ZERO.compareTo(valor.remainder(numero)) == 0;
    }
}
