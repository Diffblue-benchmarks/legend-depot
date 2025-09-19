package org.finos.legend.depot.core.server.error.configuration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ExceptionMapperConfigurationDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link ExceptionMapperConfiguration}
   *   <li>{@link ExceptionMapperConfiguration#setIncludeStackTrace(boolean)}
   *   <li>{@link ExceptionMapperConfiguration#includeStackTrace()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExceptionMapperConfiguration.<init>()",
    "boolean ExceptionMapperConfiguration.includeStackTrace()",
    "void ExceptionMapperConfiguration.setIncludeStackTrace(boolean)"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    ExceptionMapperConfiguration actualExceptionMapperConfiguration =
        new ExceptionMapperConfiguration();
    actualExceptionMapperConfiguration.setIncludeStackTrace(true);

    // Assert
    assertTrue(actualExceptionMapperConfiguration.includeStackTrace());
  }
}
