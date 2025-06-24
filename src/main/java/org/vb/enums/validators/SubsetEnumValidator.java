package org.vb.enums.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.vb.enums.EstadoReserva;

import java.util.Arrays;
import java.util.List;

public class SubsetEnumValidator implements ConstraintValidator<SubsetEnum, EstadoReserva> {

    private List<EstadoReserva> subset;

    @Override
    public void initialize(SubsetEnum constraint) {
        subset = Arrays.asList(constraint.anyOf());
    }

    @Override
    public boolean isValid(EstadoReserva value, ConstraintValidatorContext context) {
        return value == null || subset.contains(value);
    }
}
