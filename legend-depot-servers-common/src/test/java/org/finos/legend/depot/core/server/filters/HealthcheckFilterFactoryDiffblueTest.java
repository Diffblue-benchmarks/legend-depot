package org.finos.legend.depot.core.server.filters;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import ch.qos.logback.access.spi.IAccessEvent;
import ch.qos.logback.core.filter.Filter;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class HealthcheckFilterFactoryDiffblueTest {
  /**
   * Test {@link HealthcheckFilterFactory#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link HealthcheckFilterFactory#build()}
   *   <li>default or parameterless constructor of {@link HealthcheckFilterFactory}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void HealthcheckFilterFactory.<init>()", "Filter HealthcheckFilterFactory.build()"})
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
