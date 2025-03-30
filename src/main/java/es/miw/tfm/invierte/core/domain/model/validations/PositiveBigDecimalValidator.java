package es.miw.tfm.invierte.core.domain.model.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class PositiveBigDecimalValidator implements ConstraintValidator<PositiveBigDecimal, BigDecimal> {

    @Override
    public void initialize(PositiveBigDecimal constraint) {
    }

    @Override
    public boolean isValid(BigDecimal bigDecimal, ConstraintValidatorContext context) {
        return bigDecimal != null && bigDecimal.signum() != -1;
    }

}

