package org.finos.legend.depot.core.services.tracing;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.finos.legend.depot.core.services.api.tracing.configuration.OpenTracingConfiguration;
import org.finos.legend.depot.core.services.api.tracing.configuration.TracerProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class DefaultTracerProviderDiffblueTest {
  /**
   * Test {@link DefaultTracerProvider#create(OpenTracingConfiguration)}.
   * <ul>
   *   <li>Given {@code Configuration}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultTracerProvider#create(OpenTracingConfiguration)}
   */
  @Test
  @DisplayName("Test create(OpenTracingConfiguration); given 'Configuration'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"io.opentracing.Tracer DefaultTracerProvider.create(OpenTracingConfiguration)"})
  void testCreate_givenConfiguration() {
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
   * Test {@link DefaultTracerProvider#create(OpenTracingConfiguration)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link OpenTracingConfiguration} (default constructor) OpenTracingUri is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultTracerProvider#create(OpenTracingConfiguration)}
   */
  @Test
  @DisplayName("Test create(OpenTracingConfiguration); given 'null'; when OpenTracingConfiguration (default constructor) OpenTracingUri is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"io.opentracing.Tracer DefaultTracerProvider.create(OpenTracingConfiguration)"})
  void testCreate_givenNull_whenOpenTracingConfigurationOpenTracingUriIsNull() {
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
   * Test {@link DefaultTracerProvider#create(OpenTracingConfiguration)}.
   * <ul>
   *   <li>Given {@code Open Tracing Uri}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultTracerProvider#create(OpenTracingConfiguration)}
   */
  @Test
  @DisplayName("Test create(OpenTracingConfiguration); given 'Open Tracing Uri'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"io.opentracing.Tracer DefaultTracerProvider.create(OpenTracingConfiguration)"})
  void testCreate_givenOpenTracingUri() {
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
   * Test {@link DefaultTracerProvider#create(OpenTracingConfiguration)}.
   * <ul>
   *   <li>When {@link OpenTracingConfiguration} (default constructor) OpenTracingUri is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultTracerProvider#create(OpenTracingConfiguration)}
   */
  @Test
  @DisplayName("Test create(OpenTracingConfiguration); when OpenTracingConfiguration (default constructor) OpenTracingUri is empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"io.opentracing.Tracer DefaultTracerProvider.create(OpenTracingConfiguration)"})
  void testCreate_whenOpenTracingConfigurationOpenTracingUriIsEmptyString() {
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
   * Test {@link DefaultTracerProvider#create(OpenTracingConfiguration)}.
   * <ul>
   *   <li>When {@link OpenTracingConfiguration} (default constructor) ServiceName is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultTracerProvider#create(OpenTracingConfiguration)}
   */
  @Test
  @DisplayName("Test create(OpenTracingConfiguration); when OpenTracingConfiguration (default constructor) ServiceName is empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"io.opentracing.Tracer DefaultTracerProvider.create(OpenTracingConfiguration)"})
  void testCreate_whenOpenTracingConfigurationServiceNameIsEmptyString() {
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
