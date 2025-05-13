package es.miw.tfm.invierte.core.domain.model.validations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PositiveBigDecimalValidatorTest {

  private PositiveBigDecimalValidator validator;

  @BeforeEach
  void setUp() {
    validator = new PositiveBigDecimalValidator();
  }

  @Test
  void shouldReturnTrueForPositiveBigDecimal() {
    assertTrue(validator.isValid(BigDecimal.valueOf(10.5), null));
  }

  @Test
  void shouldReturnTrueForZero() {
    assertTrue(validator.isValid(BigDecimal.ZERO, null));
  }

  @Test
  void shouldReturnFalseForNegativeBigDecimal() {
    assertFalse(validator.isValid(BigDecimal.valueOf(-5.3), null));
  }

  @Test
  void shouldReturnFalseForNullValue() {
    assertFalse(validator.isValid(null, null));
  }
}
