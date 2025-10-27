package org.finos.legend.depot.server.resources.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.junit.jupiter.api.Test;

class PureModelContextResourcesModuleDiffblueTest {
  /**
   * Method under test:
   * {@link PureModelContextResourcesModule#registerResourceMetrics(PrometheusMetricsHandler)}
   */
  @Test
  void testRegisterResourceMetrics() {
    // Arrange
    PureModelContextResourcesModule pureModelContextResourcesModule = new PureModelContextResourcesModule();

    // Act and Assert
    assertTrue(pureModelContextResourcesModule.registerResourceMetrics(new VoidPrometheusMetricsHandler()));
  }
}
