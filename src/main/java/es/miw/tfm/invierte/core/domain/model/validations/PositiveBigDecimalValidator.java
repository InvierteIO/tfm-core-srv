package es.miw.tfm.invierte.core.domain.model.validations;

import java.math.BigDecimal;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PositiveBigDecimalValidator implements ConstraintValidator<PositiveBigDecimal, BigDecimal> {

    @Override
    public void initialize(PositiveBigDecimal constraint) {
    }

    @Override
    public boolean isValid(BigDecimal bigDecimal, ConstraintValidatorContext context) {
        return bigDecimal != null && bigDecimal.signum() != -1;
    }

}

