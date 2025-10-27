package org.finos.legend.depot.server.resources.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CoreDataResourcesModuleDiffblueTest {
  /**
   * Test {@link CoreDataResourcesModule#registerResourceMetrics(PrometheusMetricsHandler)}.
   * <ul>
   *   <li>When {@link VoidPrometheusMetricsHandler} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CoreDataResourcesModule#registerResourceMetrics(PrometheusMetricsHandler)}
   */
  @Test
  @DisplayName("Test registerResourceMetrics(PrometheusMetricsHandler); when VoidPrometheusMetricsHandler (default constructor); then return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CoreDataResourcesModule.registerResourceMetrics(PrometheusMetricsHandler)"})
  void testRegisterResourceMetrics_whenVoidPrometheusMetricsHandler_thenReturnTrue() {
    // Arrange
    CoreDataResourcesModule coreDataResourcesModule = new CoreDataResourcesModule();

    // Act and Assert
    assertTrue(coreDataResourcesModule.registerResourceMetrics(new VoidPrometheusMetricsHandler()));
  }
}
