package org.finos.legend.depot.core.services.api.tracing.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

class OpenTracingConfigurationDiffblueTest {
  /**
   * Methods under test:
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

    // Assert that nothing has changed
    assertEquals("Open Tracing Uri", actualOpenTracingUri);
    assertEquals("Service Name", actualServiceName);
    assertTrue(actualOpenTracingConfiguration.isEnabled());
    assertSame(tracerProvider, actualTracerProvider);
  }
}
