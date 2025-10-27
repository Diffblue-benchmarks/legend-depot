package org.finos.legend.depot.core.server.error;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class CatchAllExceptionMapperDiffblueTest {
  /**
   * Method under test: {@link CatchAllExceptionMapper#CatchAllExceptionMapper()}
   */
  @Test
  void testNewCatchAllExceptionMapper() {
    // Arrange, Act and Assert
    assertFalse((new CatchAllExceptionMapper()).includeStackTrace);
    assertTrue((new CatchAllExceptionMapper(true)).includeStackTrace);
  }
}
