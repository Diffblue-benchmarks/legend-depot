package org.finos.legend.depot.core.services.tracing;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import org.finos.legend.depot.core.services.api.tracing.configuration.OpenTracingConfiguration;
import org.finos.legend.depot.core.services.api.tracing.configuration.TracerProvider;
import org.junit.jupiter.api.Test;

class DefaultTracerProviderDiffblueTest {
  /**
   * Method under test:
   * {@link DefaultTracerProvider#create(OpenTracingConfiguration)}
   */
  @Test
  void testCreate() {
    // Arrange
    DefaultTracerProvider defaultTracerProvider = new DefaultTracerProvider();

    OpenTracingConfiguration configuration = new OpenTracingConfiguration();
    configuration.setEnabled(true);
    configuration.setOpenTracingUri("Open Tracing Uri");
    configuration.setServiceName("Service Name");
    configuration.setTracerProvider(mock(TracerProvider.class));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> defaultTracerProvider.create(configuration));
  }

  /**
   * Method under test:
   * {@link DefaultTracerProvider#create(OpenTracingConfiguration)}
   */
  @Test
  void testCreate2() {
    // Arrange
    DefaultTracerProvider defaultTracerProvider = new DefaultTracerProvider();

    OpenTracingConfiguration configuration = new OpenTracingConfiguration();
    configuration.setEnabled(true);
    configuration.setTracerProvider(mock(TracerProvider.class));
    configuration.setOpenTracingUri(null);
    configuration.setServiceName(null);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> defaultTracerProvider.create(configuration));
  }

  /**
   * Method under test:
   * {@link DefaultTracerProvider#create(OpenTracingConfiguration)}
   */
  @Test
  void testCreate3() {
    // Arrange
    DefaultTracerProvider defaultTracerProvider = new DefaultTracerProvider();

    OpenTracingConfiguration configuration = new OpenTracingConfiguration();
    configuration.setEnabled(true);
    configuration.setTracerProvider(mock(TracerProvider.class));
    configuration.setOpenTracingUri("");
    configuration.setServiceName(null);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> defaultTracerProvider.create(configuration));
  }

  /**
   * Method under test:
   * {@link DefaultTracerProvider#create(OpenTracingConfiguration)}
   */
  @Test
  void testCreate4() {
    // Arrange
    DefaultTracerProvider defaultTracerProvider = new DefaultTracerProvider();

    OpenTracingConfiguration configuration = new OpenTracingConfiguration();
    configuration.setEnabled(true);
    configuration.setTracerProvider(mock(TracerProvider.class));
    configuration.setOpenTracingUri("Configuration");
    configuration.setServiceName(null);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> defaultTracerProvider.create(configuration));
  }

  /**
   * Method under test:
   * {@link DefaultTracerProvider#create(OpenTracingConfiguration)}
   */
  @Test
  void testCreate5() {
    // Arrange
    DefaultTracerProvider defaultTracerProvider = new DefaultTracerProvider();

    OpenTracingConfiguration configuration = new OpenTracingConfiguration();
    configuration.setEnabled(true);
    configuration.setTracerProvider(mock(TracerProvider.class));
    configuration.setOpenTracingUri("Configuration");
    configuration.setServiceName("");

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> defaultTracerProvider.create(configuration));
  }
}
