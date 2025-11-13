package org.finos.legend.depot.services.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.configuration.PrometheusConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class NotificationsModuleDiffblueTest {
  /**
   * Test {@link NotificationsModule#registerMetrics(PrometheusConfiguration)}.
   *
   * <p>Method under test: {@link NotificationsModule#registerMetrics(PrometheusConfiguration)}
   */
  @Test
  @DisplayName("Test registerMetrics(PrometheusConfiguration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean NotificationsModule.registerMetrics(PrometheusConfiguration)"})
  void testRegisterMetrics() {
    // Arrange
    NotificationsModule notificationsModule = new NotificationsModule();
    PrometheusConfiguration configuration =
        new PrometheusConfiguration(true, new VoidPrometheusMetricsHandler());

    // Act
    boolean actualRegisterMetricsResult = notificationsModule.registerMetrics(configuration);

    // Assert
    assertTrue(actualRegisterMetricsResult);
  }

  /**
   * Test {@link NotificationsModule#registerMetrics(PrometheusConfiguration)}.
   *
   * <ul>
   *   <li>When {@link PrometheusConfiguration#PrometheusConfiguration()}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link NotificationsModule#registerMetrics(PrometheusConfiguration)}
   */
  @Test
  @DisplayName(
      "Test registerMetrics(PrometheusConfiguration); when PrometheusConfiguration(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean NotificationsModule.registerMetrics(PrometheusConfiguration)"})
  void testRegisterMetrics_whenPrometheusConfiguration_thenReturnTrue() {
    // Arrange
    NotificationsModule notificationsModule = new NotificationsModule();

    // Act and Assert
    assertTrue(notificationsModule.registerMetrics(new PrometheusConfiguration()));
  }
}
