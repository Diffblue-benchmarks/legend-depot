package org.finos.legend.depot.core.services.api.tracing.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class OpenTracingConfigurationDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link OpenTracingConfiguration}
   *   <li>{@link OpenTracingConfiguration#setEnabled(boolean)}
   *   <li>{@link OpenTracingConfiguration#setOpenTracingUri(String)}
   *   <li>{@link OpenTracingConfiguration#setServiceName(String)}
   *   <li>{@link OpenTracingConfiguration#setTracerProvider(TracerProvider)}
   *   <li>{@link OpenTracingConfiguration#getOpenTracingUri()}
   *   <li>{@link OpenTracingConfiguration#getServiceName()}
   *   <li>{@link OpenTracingConfiguration#getTracerProvider()}
   *   <li>{@link OpenTracingConfiguration#isEnabled()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void OpenTracingConfiguration.<init>()",
    "String OpenTracingConfiguration.getOpenTracingUri()",
    "String OpenTracingConfiguration.getServiceName()",
    "TracerProvider OpenTracingConfiguration.getTracerProvider()",
    "boolean OpenTracingConfiguration.isEnabled()",
    "void OpenTracingConfiguration.setEnabled(boolean)",
    "void OpenTracingConfiguration.setOpenTracingUri(String)",
    "void OpenTracingConfiguration.setServiceName(String)",
    "void OpenTracingConfiguration.setTracerProvider(TracerProvider)"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    OpenTracingConfiguration actualOpenTracingConfiguration = new OpenTracingConfiguration();
    actualOpenTracingConfiguration.setEnabled(true);
    actualOpenTracingConfiguration.setOpenTracingUri("Open Tracing Uri");
    actualOpenTracingConfiguration.setServiceName("Service Name");
    TracerProvider tracerProvider = mock(TracerProvider.class);
    actualOpenTracingConfiguration.setTracerProvider(tracerProvider);
    String actualOpenTracingUri = actualOpenTracingConfiguration.getOpenTracingUri();
    String actualServiceName = actualOpenTracingConfiguration.getServiceName();
    TracerProvider actualTracerProvider = actualOpenTracingConfiguration.getTracerProvider();

    // Assert
    assertEquals("Open Tracing Uri", actualOpenTracingUri);
    assertEquals("Service Name", actualServiceName);
    assertTrue(actualOpenTracingConfiguration.isEnabled());
    assertSame(tracerProvider, actualTracerProvider);
  }
}
