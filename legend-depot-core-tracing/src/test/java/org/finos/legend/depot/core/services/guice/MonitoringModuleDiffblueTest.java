package org.finos.legend.depot.core.services.guice;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import io.opentracing.Tracer;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.configuration.PrometheusConfiguration;
import org.finos.legend.depot.core.services.api.tracing.configuration.OpenTracingConfiguration;
import org.finos.legend.depot.core.services.api.tracing.configuration.TracerProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MonitoringModuleDiffblueTest {
  /**
   * Test {@link MonitoringModule#initTracerFactory(OpenTracingConfiguration)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link OpenTracingConfiguration} (default constructor) OpenTracingUri is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoringModule#initTracerFactory(OpenTracingConfiguration)}
   */
  @Test
  @DisplayName("Test initTracerFactory(OpenTracingConfiguration); given '42'; when OpenTracingConfiguration (default constructor) OpenTracingUri is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.finos.legend.depot.core.services.tracing.TracerFactory MonitoringModule.initTracerFactory(OpenTracingConfiguration)"})
  void testInitTracerFactory_given42_whenOpenTracingConfigurationOpenTracingUriIs42() {
    // Arrange
    MonitoringModule monitoringModule = new MonitoringModule();
    TracerProvider tracerProvider = mock(TracerProvider.class);
    when(tracerProvider.create(Mockito.<OpenTracingConfiguration>any())).thenReturn(mock(Tracer.class));

    OpenTracingConfiguration openTracingConfiguration = new OpenTracingConfiguration();
    openTracingConfiguration.setEnabled(true);
    openTracingConfiguration.setOpenTracingUri("42");
    openTracingConfiguration.setServiceName("Service Name");
    openTracingConfiguration.setTracerProvider(tracerProvider);

    // Act
    monitoringModule.initTracerFactory(openTracingConfiguration);

    // Assert
    verify(tracerProvider).create(isA(OpenTracingConfiguration.class));
  }

  /**
   * Test {@link MonitoringModule#initTracerFactory(OpenTracingConfiguration)}.
   * <ul>
   *   <li>Given {@link TracerProvider} {@link TracerProvider#create(OpenTracingConfiguration)} return {@link Tracer}.</li>
   *   <li>Then calls {@link TracerProvider#create(OpenTracingConfiguration)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoringModule#initTracerFactory(OpenTracingConfiguration)}
   */
  @Test
  @DisplayName("Test initTracerFactory(OpenTracingConfiguration); given TracerProvider create(OpenTracingConfiguration) return Tracer; then calls create(OpenTracingConfiguration)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.finos.legend.depot.core.services.tracing.TracerFactory MonitoringModule.initTracerFactory(OpenTracingConfiguration)"})
  void testInitTracerFactory_givenTracerProviderCreateReturnTracer_thenCallsCreate() {
    // Arrange
    MonitoringModule monitoringModule = new MonitoringModule();
    TracerProvider tracerProvider = mock(TracerProvider.class);
    when(tracerProvider.create(Mockito.<OpenTracingConfiguration>any())).thenReturn(mock(Tracer.class));

    OpenTracingConfiguration openTracingConfiguration = new OpenTracingConfiguration();
    openTracingConfiguration.setEnabled(true);
    openTracingConfiguration.setOpenTracingUri("Open Tracing Uri");
    openTracingConfiguration.setServiceName("Service Name");
    openTracingConfiguration.setTracerProvider(tracerProvider);

    // Act
    monitoringModule.initTracerFactory(openTracingConfiguration);

    // Assert
    verify(tracerProvider).create(isA(OpenTracingConfiguration.class));
  }

  /**
   * Test {@link MonitoringModule#initialisePrometheusMetrics(PrometheusConfiguration)}.
   * <ul>
   *   <li>Given {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoringModule#initialisePrometheusMetrics(PrometheusConfiguration)}
   */
  @Test
  @DisplayName("Test initialisePrometheusMetrics(PrometheusConfiguration); given 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"PrometheusMetricsHandler MonitoringModule.initialisePrometheusMetrics(PrometheusConfiguration)"})
  void testInitialisePrometheusMetrics_givenTrue() {
    // Arrange
    MonitoringModule monitoringModule = new MonitoringModule();

    PrometheusConfiguration configuration = new PrometheusConfiguration();
    configuration.setEnabled(true);

    // Act and Assert
    assertTrue(monitoringModule.initialisePrometheusMetrics(configuration) instanceof VoidPrometheusMetricsHandler);
  }

  /**
   * Test {@link MonitoringModule#initialisePrometheusMetrics(PrometheusConfiguration)}.
   * <ul>
   *   <li>Then return {@link VoidPrometheusMetricsHandler} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoringModule#initialisePrometheusMetrics(PrometheusConfiguration)}
   */
  @Test
  @DisplayName("Test initialisePrometheusMetrics(PrometheusConfiguration); then return VoidPrometheusMetricsHandler (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"PrometheusMetricsHandler MonitoringModule.initialisePrometheusMetrics(PrometheusConfiguration)"})
  void testInitialisePrometheusMetrics_thenReturnVoidPrometheusMetricsHandler() {
    // Arrange
    MonitoringModule monitoringModule = new MonitoringModule();
    VoidPrometheusMetricsHandler metricsHandler = new VoidPrometheusMetricsHandler();

    // Act
    PrometheusMetricsHandler actualInitialisePrometheusMetricsResult = monitoringModule
        .initialisePrometheusMetrics(new PrometheusConfiguration(true, metricsHandler));

    // Assert
    assertTrue(actualInitialisePrometheusMetricsResult instanceof VoidPrometheusMetricsHandler);
    assertSame(metricsHandler, actualInitialisePrometheusMetricsResult);
  }

  /**
   * Test {@link MonitoringModule#initialisePrometheusMetrics(PrometheusConfiguration)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoringModule#initialisePrometheusMetrics(PrometheusConfiguration)}
   */
  @Test
  @DisplayName("Test initialisePrometheusMetrics(PrometheusConfiguration); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"PrometheusMetricsHandler MonitoringModule.initialisePrometheusMetrics(PrometheusConfiguration)"})
  void testInitialisePrometheusMetrics_whenNull() {
    // Arrange, Act and Assert
    assertTrue(new MonitoringModule().initialisePrometheusMetrics(null) instanceof VoidPrometheusMetricsHandler);
  }

  /**
   * Test {@link MonitoringModule#initialisePrometheusMetrics(PrometheusConfiguration)}.
   * <ul>
   *   <li>When {@link PrometheusConfiguration#PrometheusConfiguration()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoringModule#initialisePrometheusMetrics(PrometheusConfiguration)}
   */
  @Test
  @DisplayName("Test initialisePrometheusMetrics(PrometheusConfiguration); when PrometheusConfiguration()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"PrometheusMetricsHandler MonitoringModule.initialisePrometheusMetrics(PrometheusConfiguration)"})
  void testInitialisePrometheusMetrics_whenPrometheusConfiguration() {
    // Arrange
    MonitoringModule monitoringModule = new MonitoringModule();

    // Act and Assert
    assertTrue(monitoringModule
        .initialisePrometheusMetrics(new PrometheusConfiguration()) instanceof VoidPrometheusMetricsHandler);
  }
}
