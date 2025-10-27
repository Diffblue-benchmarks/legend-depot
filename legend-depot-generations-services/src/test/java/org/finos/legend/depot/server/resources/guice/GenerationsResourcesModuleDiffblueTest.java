package org.finos.legend.depot.server.resources.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.junit.jupiter.api.Test;

class GenerationsResourcesModuleDiffblueTest {
  /**
   * Method under test:
   * {@link GenerationsResourcesModule#registerResourceMetrics(PrometheusMetricsHandler)}
   */
  @Test
  void testRegisterResourceMetrics() {
    // Arrange
    GenerationsResourcesModule generationsResourcesModule = new GenerationsResourcesModule();

    // Act and Assert
    assertTrue(generationsResourcesModule.registerResourceMetrics(new VoidPrometheusMetricsHandler()));
  }
}
