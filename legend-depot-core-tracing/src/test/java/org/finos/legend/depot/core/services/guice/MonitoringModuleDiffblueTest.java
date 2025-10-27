package org.finos.legend.depot.core.services.guice;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.opentracing.Tracer;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.configuration.PrometheusConfiguration;
import org.finos.legend.depot.core.services.api.tracing.configuration.OpenTracingConfiguration;
import org.finos.legend.depot.core.services.api.tracing.configuration.TracerProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MonitoringModuleDiffblueTest {
  /**
   * Method under test:
   * {@link MonitoringModule#initTracerFactory(OpenTracingConfiguration)}
   */
  @Test
  void testInitTracerFactory() {
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
   * Method under test:
   * {@link MonitoringModule#initTracerFactory(OpenTracingConfiguration)}
   */
  @Test
  void testInitTracerFactory2() {
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
   * Method under test:
   * {@link MonitoringModule#initialisePrometheusMetrics(PrometheusConfiguration)}
   */
  @Test
  void testInitialisePrometheusMetrics() {
    // Arrange
    MonitoringModule monitoringModule = new MonitoringModule();

    // Act and Assert
    assertTrue(monitoringModule
        .initialisePrometheusMetrics(new PrometheusConfiguration()) instanceof VoidPrometheusMetricsHandler);
  }

  /**
   * Method under test:
   * {@link MonitoringModule#initialisePrometheusMetrics(PrometheusConfiguration)}
   */
  @Test
  void testInitialisePrometheusMetrics2() {
    // Arrange, Act and Assert
    assertTrue((new MonitoringModule()).initialisePrometheusMetrics(null) instanceof VoidPrometheusMetricsHandler);
  }

  /**
   * Method under test:
   * {@link MonitoringModule#initialisePrometheusMetrics(PrometheusConfiguration)}
   */
  @Test
  void testInitialisePrometheusMetrics3() {
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
   * Method under test:
   * {@link MonitoringModule#initialisePrometheusMetrics(PrometheusConfiguration)}
   */
  @Test
  void testInitialisePrometheusMetrics4() {
    // Arrange
    MonitoringModule monitoringModule = new MonitoringModule();

    PrometheusConfiguration configuration = new PrometheusConfiguration();
    configuration.setEnabled(true);

    // Act and Assert
    assertTrue(monitoringModule.initialisePrometheusMetrics(configuration) instanceof VoidPrometheusMetricsHandler);
  }
}
