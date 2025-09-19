package org.finos.legend.depot.core.services.api.metrics.configuration;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class PrometheusConfigurationDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link PrometheusConfiguration#PrometheusConfiguration()}
   *   <li>{@link PrometheusConfiguration#setEnabled(boolean)}
   *   <li>{@link PrometheusConfiguration#setMetricsHandler(PrometheusMetricsHandler)}
   *   <li>{@link PrometheusConfiguration#getMetricsHandler()}
   *   <li>{@link PrometheusConfiguration#isEnabled()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void PrometheusConfiguration.<init>()",
    "void PrometheusConfiguration.<init>(boolean, PrometheusMetricsHandler)",
    "PrometheusMetricsHandler PrometheusConfiguration.getMetricsHandler()",
    "boolean PrometheusConfiguration.isEnabled()",
    "void PrometheusConfiguration.setEnabled(boolean)",
    "void PrometheusConfiguration.setMetricsHandler(PrometheusMetricsHandler)"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    PrometheusConfiguration actualPrometheusConfiguration = new PrometheusConfiguration();
    actualPrometheusConfiguration.setEnabled(true);
    VoidPrometheusMetricsHandler metricsHandler = new VoidPrometheusMetricsHandler();
    actualPrometheusConfiguration.setMetricsHandler(metricsHandler);
    PrometheusMetricsHandler actualMetricsHandler =
        actualPrometheusConfiguration.getMetricsHandler();

    // Assert
    assertTrue(actualMetricsHandler instanceof VoidPrometheusMetricsHandler);
    assertTrue(actualPrometheusConfiguration.isEnabled());
    assertSame(metricsHandler, actualMetricsHandler);
  }

  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@code true}.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link PrometheusConfiguration#PrometheusConfiguration(boolean,
   *       PrometheusMetricsHandler)}
   *   <li>{@link PrometheusConfiguration#setEnabled(boolean)}
   *   <li>{@link PrometheusConfiguration#setMetricsHandler(PrometheusMetricsHandler)}
   *   <li>{@link PrometheusConfiguration#getMetricsHandler()}
   *   <li>{@link PrometheusConfiguration#isEnabled()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void PrometheusConfiguration.<init>()",
    "void PrometheusConfiguration.<init>(boolean, PrometheusMetricsHandler)",
    "PrometheusMetricsHandler PrometheusConfiguration.getMetricsHandler()",
    "boolean PrometheusConfiguration.isEnabled()",
    "void PrometheusConfiguration.setEnabled(boolean)",
    "void PrometheusConfiguration.setMetricsHandler(PrometheusMetricsHandler)"
  })
  void testGettersAndSetters_whenTrue() {
    // Arrange and Act
    PrometheusConfiguration actualPrometheusConfiguration =
        new PrometheusConfiguration(true, new VoidPrometheusMetricsHandler());
    actualPrometheusConfiguration.setEnabled(true);
    VoidPrometheusMetricsHandler metricsHandler = new VoidPrometheusMetricsHandler();
    actualPrometheusConfiguration.setMetricsHandler(metricsHandler);
    PrometheusMetricsHandler actualMetricsHandler =
        actualPrometheusConfiguration.getMetricsHandler();

    // Assert
    assertTrue(actualMetricsHandler instanceof VoidPrometheusMetricsHandler);
    assertTrue(actualPrometheusConfiguration.isEnabled());
    assertSame(metricsHandler, actualMetricsHandler);
  }
}
