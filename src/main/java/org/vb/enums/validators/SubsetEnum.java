package org.vb.enums.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.vb.enums.EstadoReserva;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SubsetEnumValidator.class)
public @interface SubsetEnum {
    EstadoReserva[] anyOf();
    String message() default "Estado no permitido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
