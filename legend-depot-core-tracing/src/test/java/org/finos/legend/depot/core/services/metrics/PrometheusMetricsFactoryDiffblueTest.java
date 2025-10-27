package org.finos.legend.depot.core.services.metrics;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.configuration.PrometheusConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PrometheusMetricsFactoryDiffblueTest {
  @InjectMocks
  private PrometheusMetricsFactory prometheusMetricsFactory;

  /**
   * Method under test: {@link PrometheusMetricsFactory#getInstance()}
   */
  @Test
  void testGetInstance() {
    // Arrange, Act and Assert
    assertTrue(PrometheusMetricsFactory.getInstance() instanceof VoidPrometheusMetricsHandler);
  }

  /**
   * Method under test:
   * {@link PrometheusMetricsFactory#configure(PrometheusConfiguration)}
   */
  @Test
  void testConfigure() {
    // Arrange, Act and Assert
    assertTrue(
        PrometheusMetricsFactory.configure(new PrometheusConfiguration()) instanceof VoidPrometheusMetricsHandler);
    assertTrue(PrometheusMetricsFactory.configure(null) instanceof VoidPrometheusMetricsHandler);
    assertTrue(PrometheusMetricsFactory.configure(
        new PrometheusConfiguration(true, new VoidPrometheusMetricsHandler())) instanceof VoidPrometheusMetricsHandler);
  }

  /**
   * Method under test:
   * {@link PrometheusMetricsFactory#configure(PrometheusConfiguration)}
   */
  @Test
  void testConfigure2() {
    // Arrange
    PrometheusConfiguration configuration = mock(PrometheusConfiguration.class);
    when(configuration.getMetricsHandler()).thenReturn(new VoidPrometheusMetricsHandler());
    when(configuration.isEnabled()).thenReturn(true);

    // Act
    PrometheusMetricsHandler actualConfigureResult = PrometheusMetricsFactory.configure(configuration);

    // Assert
    verify(configuration, atLeast(1)).getMetricsHandler();
    verify(configuration).isEnabled();
    assertTrue(actualConfigureResult instanceof VoidPrometheusMetricsHandler);
  }

  /**
   * Method under test:
   * {@link PrometheusMetricsFactory#configure(PrometheusConfiguration)}
   */
  @Test
  void testConfigure3() {
    // Arrange
    PrometheusConfiguration configuration = mock(PrometheusConfiguration.class);
    when(configuration.getMetricsHandler()).thenReturn(null);
    when(configuration.isEnabled()).thenReturn(true);

    // Act
    PrometheusMetricsHandler actualConfigureResult = PrometheusMetricsFactory.configure(configuration);

    // Assert
    verify(configuration).getMetricsHandler();
    verify(configuration).isEnabled();
    assertTrue(actualConfigureResult instanceof VoidPrometheusMetricsHandler);
  }
}
