package com.algaworks.algafood.validation.validators;

import com.algaworks.algafood.api.model.entrada.RestauranteEntradaDTO;
import com.algaworks.algafood.validation.annotations.Hamburgueria;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//no caso, a validação é da classe RestauranteEntradaDTO (poderia colocar o objeto Restaurante ou Object)
public class HamburgueriaValidator implements ConstraintValidator<Hamburgueria, RestauranteEntradaDTO> {

    private String idField;
    private String nomeField;
    private String nomeObrigatorio;

    @Override
    public void initialize(Hamburgueria constraintAnnotation) {
        this.idField = constraintAnnotation.idField();
        this.nomeField = constraintAnnotation.nomeField();
        this.nomeObrigatorio = constraintAnnotation.nomeObrigatorio();
    }

    @Override
    public boolean isValid(RestauranteEntradaDTO value, ConstraintValidatorContext context) {
            Long id = value.getCozinhaId();
            Long idHamburgueria = 8l;
            if (idHamburgueria.equals(id)) return value.getNome().contains(this.nomeObrigatorio);

            return true;
    }
}
