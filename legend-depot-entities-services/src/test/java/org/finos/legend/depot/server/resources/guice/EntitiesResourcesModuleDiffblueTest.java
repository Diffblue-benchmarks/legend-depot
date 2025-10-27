package org.finos.legend.depot.server.resources.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.junit.jupiter.api.Test;

class EntitiesResourcesModuleDiffblueTest {
  /**
   * Method under test:
   * {@link EntitiesResourcesModule#registerResourceMetrics(PrometheusMetricsHandler)}
   */
  @Test
  void testRegisterResourceMetrics() {
    // Arrange
    EntitiesResourcesModule entitiesResourcesModule = new EntitiesResourcesModule();

    // Act and Assert
    assertTrue(entitiesResourcesModule.registerResourceMetrics(new VoidPrometheusMetricsHandler()));
  }
}
