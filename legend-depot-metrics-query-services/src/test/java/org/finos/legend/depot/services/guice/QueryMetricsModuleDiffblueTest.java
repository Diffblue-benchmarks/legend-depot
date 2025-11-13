package org.finos.legend.depot.services.guice;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.services.metrics.query.InMemoryQueryMetricsRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class QueryMetricsModuleDiffblueTest {
  /**
   * Test {@link QueryMetricsModule#getQueryMetricsRegistry()}.
   *
   * <p>Method under test: {@link QueryMetricsModule#getQueryMetricsRegistry()}
   */
  @Test
  @DisplayName("Test getQueryMetricsRegistry()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"QueryMetricsRegistry QueryMetricsModule.getQueryMetricsRegistry()"})
  void testGetQueryMetricsRegistry() {
    // Arrange and Act
    QueryMetricsRegistry actualQueryMetricsRegistry =
        new QueryMetricsModule().getQueryMetricsRegistry();

    // Assert
    assertTrue(actualQueryMetricsRegistry instanceof InMemoryQueryMetricsRegistry);
    assertFalse(actualQueryMetricsRegistry.findFirst().isPresent());
  }

  /**
   * Test new {@link QueryMetricsModule} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link QueryMetricsModule}
   */
  @Test
  @DisplayName("Test new QueryMetricsModule (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void QueryMetricsModule.<init>()"})
  void testNewQueryMetricsModule() {
    // Arrange, Act and Assert
    assertTrue(
        new QueryMetricsModule().getQueryMetricsRegistry() instanceof InMemoryQueryMetricsRegistry);
  }
}
