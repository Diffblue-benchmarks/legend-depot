package org.finos.legend.depot.server.resources.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.junit.jupiter.api.Test;

class CoreDataResourcesModuleDiffblueTest {
  /**
   * Method under test:
   * {@link CoreDataResourcesModule#registerResourceMetrics(PrometheusMetricsHandler)}
   */
  @Test
  void testRegisterResourceMetrics() {
    // Arrange
    CoreDataResourcesModule coreDataResourcesModule = new CoreDataResourcesModule();

    // Act and Assert
    assertTrue(coreDataResourcesModule.registerResourceMetrics(new VoidPrometheusMetricsHandler()));
  }
}
