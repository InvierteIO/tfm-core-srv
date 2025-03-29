package es.miw.tfm.invierte.core.domain.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BadGatewayExceptionTest {

  private BadGatewayException badGatewayException;

  @BeforeEach
  void setUp() {
    this.badGatewayException = new BadGatewayException("detail");
  }

  @Test
  void testGetMessage() {
    assertEquals("Bad Gateway Exception. detail", this.badGatewayException.getMessage());
  }

}
