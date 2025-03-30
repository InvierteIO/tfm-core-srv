package es.miw.tfm.invierte.core.domain.model.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PositiveBigDecimalValidator.class)
public @interface PositiveBigDecimal {
    String message() default "Expected positive";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

