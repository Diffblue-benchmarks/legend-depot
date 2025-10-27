package org.finos.legend.depot.core.server.error;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class DepotServerExceptionMapperDiffblueTest {
  /**
   * Method under test:
   * {@link DepotServerExceptionMapper#DepotServerExceptionMapper()}
   */
  @Test
  void testNewDepotServerExceptionMapper() {
    // Arrange, Act and Assert
    assertFalse((new DepotServerExceptionMapper()).includeStackTrace);
    assertTrue((new DepotServerExceptionMapper(true)).includeStackTrace);
  }
}
