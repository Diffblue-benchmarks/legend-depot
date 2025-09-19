package org.finos.legend.depot.core.services.guice;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.configuration.PrometheusConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MonitoringModuleDiffblueTest {
  /**
   * Test {@link MonitoringModule#initialisePrometheusMetrics(PrometheusConfiguration)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MonitoringModule#initialisePrometheusMetrics(PrometheusConfiguration)}
   */
  @Test
  @DisplayName("Test initialisePrometheusMetrics(PrometheusConfiguration); given 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PrometheusMetricsHandler MonitoringModule.initialisePrometheusMetrics(PrometheusConfiguration)"
  })
  void testInitialisePrometheusMetrics_givenTrue() {
    // Arrange
    MonitoringModule monitoringModule = new MonitoringModule();

    PrometheusConfiguration configuration = new PrometheusConfiguration();
    configuration.setEnabled(true);

    // Act and Assert
    assertTrue(
        monitoringModule.initialisePrometheusMetrics(configuration)
            instanceof VoidPrometheusMetricsHandler);
  }

  /**
   * Test {@link MonitoringModule#initialisePrometheusMetrics(PrometheusConfiguration)}.
   *
   * <ul>
   *   <li>Then return {@link VoidPrometheusMetricsHandler} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * MonitoringModule#initialisePrometheusMetrics(PrometheusConfiguration)}
   */
  @Test
  @DisplayName(
      "Test initialisePrometheusMetrics(PrometheusConfiguration); then return VoidPrometheusMetricsHandler (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PrometheusMetricsHandler MonitoringModule.initialisePrometheusMetrics(PrometheusConfiguration)"
  })
  void testInitialisePrometheusMetrics_thenReturnVoidPrometheusMetricsHandler() {
    // Arrange
    MonitoringModule monitoringModule = new MonitoringModule();
    VoidPrometheusMetricsHandler metricsHandler = new VoidPrometheusMetricsHandler();
    PrometheusConfiguration configuration = new PrometheusConfiguration(true, metricsHandler);

    // Act
    PrometheusMetricsHandler actualInitialisePrometheusMetricsResult =
        monitoringModule.initialisePrometheusMetrics(configuration);

    // Assert
    assertTrue(actualInitialisePrometheusMetricsResult instanceof VoidPrometheusMetricsHandler);
    assertSame(metricsHandler, actualInitialisePrometheusMetricsResult);
  }

  /**
   * Test {@link MonitoringModule#initialisePrometheusMetrics(PrometheusConfiguration)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MonitoringModule#initialisePrometheusMetrics(PrometheusConfiguration)}
   */
  @Test
  @DisplayName("Test initialisePrometheusMetrics(PrometheusConfiguration); when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PrometheusMetricsHandler MonitoringModule.initialisePrometheusMetrics(PrometheusConfiguration)"
  })
  void testInitialisePrometheusMetrics_whenNull() {
    // Arrange, Act and Assert
    assertTrue(
        new MonitoringModule().initialisePrometheusMetrics(null)
            instanceof VoidPrometheusMetricsHandler);
  }

  /**
   * Test {@link MonitoringModule#initialisePrometheusMetrics(PrometheusConfiguration)}.
   *
   * <ul>
   *   <li>When {@link PrometheusConfiguration#PrometheusConfiguration()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * MonitoringModule#initialisePrometheusMetrics(PrometheusConfiguration)}
   */
  @Test
  @DisplayName(
      "Test initialisePrometheusMetrics(PrometheusConfiguration); when PrometheusConfiguration()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PrometheusMetricsHandler MonitoringModule.initialisePrometheusMetrics(PrometheusConfiguration)"
  })
  void testInitialisePrometheusMetrics_whenPrometheusConfiguration() {
    // Arrange
    MonitoringModule monitoringModule = new MonitoringModule();

    // Act and Assert
    assertTrue(
        monitoringModule.initialisePrometheusMetrics(new PrometheusConfiguration())
            instanceof VoidPrometheusMetricsHandler);
  }
}
