package org.finos.legend.depot.services.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.configuration.PrometheusConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificationsModuleDiffblueTest {
  @InjectMocks
  private NotificationsModule notificationsModule;

  /**
   * Method under test:
   * {@link NotificationsModule#registerMetrics(PrometheusConfiguration)}
   */
  @Test
  void testRegisterMetrics() {
    // Arrange, Act and Assert
    assertTrue(notificationsModule.registerMetrics(new PrometheusConfiguration()));
    assertTrue(
        notificationsModule.registerMetrics(new PrometheusConfiguration(true, new VoidPrometheusMetricsHandler())));
  }
}
