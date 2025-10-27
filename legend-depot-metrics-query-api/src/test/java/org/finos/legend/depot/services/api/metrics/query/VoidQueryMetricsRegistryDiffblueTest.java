package org.finos.legend.depot.services.api.metrics.query;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

class VoidQueryMetricsRegistryDiffblueTest {
  /**
   * Method under test: {@link VoidQueryMetricsRegistry#findFirst()}
   */
  @Test
  void testFindFirst() {
    // Arrange, Act and Assert
    assertFalse((new VoidQueryMetricsRegistry()).findFirst().isPresent());
  }
}
