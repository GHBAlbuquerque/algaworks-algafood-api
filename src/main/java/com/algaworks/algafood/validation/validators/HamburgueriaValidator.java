package com.algaworks.algafood.validation.validators;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.validation.annotations.Hamburgueria;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//no caso, a validação é da classe Retsaurante (poderia colocar o objeto Restaurante ou Object)
public class HamburgueriaValidator implements ConstraintValidator<Hamburgueria, Restaurante> {

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
    public boolean isValid(Restaurante value, ConstraintValidatorContext context) {
        var id = value.getCozinha().getId();

        if(id.equals(8L)) {
            return value.getNome().contains(this.nomeObrigatorio);
        }

        return true;
    }


}
