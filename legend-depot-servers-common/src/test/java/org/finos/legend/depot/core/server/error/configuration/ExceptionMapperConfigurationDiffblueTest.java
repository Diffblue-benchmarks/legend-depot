package org.finos.legend.depot.core.server.error.configuration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ExceptionMapperConfigurationDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link ExceptionMapperConfiguration}
   *   <li>{@link ExceptionMapperConfiguration#setIncludeStackTrace(boolean)}
   *   <li>{@link ExceptionMapperConfiguration#includeStackTrace()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    ExceptionMapperConfiguration actualExceptionMapperConfiguration = new ExceptionMapperConfiguration();
    actualExceptionMapperConfiguration.setIncludeStackTrace(true);

    // Assert that nothing has changed
    assertTrue(actualExceptionMapperConfiguration.includeStackTrace());
  }
}
