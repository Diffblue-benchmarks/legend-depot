package org.finos.legend.depot.core.server.filters;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import ch.qos.logback.access.spi.IAccessEvent;
import ch.qos.logback.core.filter.Filter;
import org.junit.jupiter.api.Test;

class HealthcheckFilterFactoryDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link HealthcheckFilterFactory#build()}
   *   <li>default or parameterless constructor of {@link HealthcheckFilterFactory}
   * </ul>
   */
  @Test
  void testBuild() {
    // Arrange and Act
    Filter<IAccessEvent> actualBuildResult = (new HealthcheckFilterFactory()).build();

    // Assert
    assertNull(actualBuildResult.getContext());
    assertNull(actualBuildResult.getStatusManager());
    assertNull(actualBuildResult.getName());
    assertFalse(actualBuildResult.isStarted());
  }
}
