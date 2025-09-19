package org.finos.legend.depot.server.resources.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GenerationsResourcesModuleDiffblueTest {
  /**
   * Test {@link GenerationsResourcesModule#registerResourceMetrics(PrometheusMetricsHandler)}.
   *
   * <ul>
   *   <li>When {@link VoidPrometheusMetricsHandler} (default constructor).
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * GenerationsResourcesModule#registerResourceMetrics(PrometheusMetricsHandler)}
   */
  @Test
  @DisplayName(
      "Test registerResourceMetrics(PrometheusMetricsHandler); when VoidPrometheusMetricsHandler (default constructor); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean GenerationsResourcesModule.registerResourceMetrics(PrometheusMetricsHandler)"
  })
  void testRegisterResourceMetrics_whenVoidPrometheusMetricsHandler_thenReturnTrue() {
    // Arrange
    GenerationsResourcesModule generationsResourcesModule = new GenerationsResourcesModule();

    // Act and Assert
    assertTrue(
        generationsResourcesModule.registerResourceMetrics(new VoidPrometheusMetricsHandler()));
  }
}
