package org.finos.legend.depot.core.services.api.metrics.configuration;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.junit.jupiter.api.Test;

class PrometheusConfigurationDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link PrometheusConfiguration#PrometheusConfiguration()}
   *   <li>{@link PrometheusConfiguration#setEnabled(boolean)}
   *   <li>
   * {@link PrometheusConfiguration#setMetricsHandler(PrometheusMetricsHandler)}
   *   <li>{@link PrometheusConfiguration#getMetricsHandler()}
   *   <li>{@link PrometheusConfiguration#isEnabled()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    PrometheusConfiguration actualPrometheusConfiguration = new PrometheusConfiguration();
    actualPrometheusConfiguration.setEnabled(true);
    VoidPrometheusMetricsHandler metricsHandler = new VoidPrometheusMetricsHandler();
    actualPrometheusConfiguration.setMetricsHandler(metricsHandler);
    PrometheusMetricsHandler actualMetricsHandler = actualPrometheusConfiguration.getMetricsHandler();

    // Assert that nothing has changed
    assertTrue(actualMetricsHandler instanceof VoidPrometheusMetricsHandler);
    assertTrue(actualPrometheusConfiguration.isEnabled());
    assertSame(metricsHandler, actualMetricsHandler);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link PrometheusConfiguration#PrometheusConfiguration(boolean, PrometheusMetricsHandler)}
   *   <li>{@link PrometheusConfiguration#setEnabled(boolean)}
   *   <li>
   * {@link PrometheusConfiguration#setMetricsHandler(PrometheusMetricsHandler)}
   *   <li>{@link PrometheusConfiguration#getMetricsHandler()}
   *   <li>{@link PrometheusConfiguration#isEnabled()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange and Act
    PrometheusConfiguration actualPrometheusConfiguration = new PrometheusConfiguration(true,
        new VoidPrometheusMetricsHandler());
    actualPrometheusConfiguration.setEnabled(true);
    VoidPrometheusMetricsHandler metricsHandler = new VoidPrometheusMetricsHandler();
    actualPrometheusConfiguration.setMetricsHandler(metricsHandler);
    PrometheusMetricsHandler actualMetricsHandler = actualPrometheusConfiguration.getMetricsHandler();

    // Assert that nothing has changed
    assertTrue(actualMetricsHandler instanceof VoidPrometheusMetricsHandler);
    assertTrue(actualPrometheusConfiguration.isEnabled());
    assertSame(metricsHandler, actualMetricsHandler);
  }
}
