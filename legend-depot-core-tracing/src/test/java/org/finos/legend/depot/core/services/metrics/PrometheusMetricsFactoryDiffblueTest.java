package org.finos.legend.depot.core.services.metrics;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.configuration.PrometheusConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class PrometheusMetricsFactoryDiffblueTest {
  /**
   * Test {@link PrometheusMetricsFactory#getInstance()}.
   *
   * <p>Method under test: {@link PrometheusMetricsFactory#getInstance()}
   */
  @Test
  @DisplayName("Test getInstance()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"PrometheusMetricsHandler PrometheusMetricsFactory.getInstance()"})
  void testGetInstance() {
    // Arrange, Act and Assert
    assertTrue(PrometheusMetricsFactory.getInstance() instanceof VoidPrometheusMetricsHandler);
  }

  /**
   * Test {@link PrometheusMetricsFactory#configure(PrometheusConfiguration)}.
   *
   * <p>Method under test: {@link PrometheusMetricsFactory#configure(PrometheusConfiguration)}
   */
  @Test
  @DisplayName("Test configure(PrometheusConfiguration)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PrometheusMetricsHandler PrometheusMetricsFactory.configure(PrometheusConfiguration)"
  })
  void testConfigure() {
    // Arrange
    PrometheusConfiguration configuration =
        new PrometheusConfiguration(true, new VoidPrometheusMetricsHandler());

    // Act and Assert
    assertTrue(
        PrometheusMetricsFactory.configure(configuration) instanceof VoidPrometheusMetricsHandler);
  }

  /**
   * Test {@link PrometheusMetricsFactory#configure(PrometheusConfiguration)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then calls {@link PrometheusConfiguration#getMetricsHandler()}.
   * </ul>
   *
   * <p>Method under test: {@link PrometheusMetricsFactory#configure(PrometheusConfiguration)}
   */
  @Test
  @DisplayName(
      "Test configure(PrometheusConfiguration); given 'null'; then calls getMetricsHandler()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PrometheusMetricsHandler PrometheusMetricsFactory.configure(PrometheusConfiguration)"
  })
  void testConfigure_givenNull_thenCallsGetMetricsHandler() {
    // Arrange
    PrometheusConfiguration configuration = mock(PrometheusConfiguration.class);
    when(configuration.getMetricsHandler()).thenReturn(null);
    when(configuration.isEnabled()).thenReturn(true);

    // Act
    PrometheusMetricsHandler actualConfigureResult =
        PrometheusMetricsFactory.configure(configuration);

    // Assert
    verify(configuration).getMetricsHandler();
    verify(configuration).isEnabled();
    assertTrue(actualConfigureResult instanceof VoidPrometheusMetricsHandler);
  }

  /**
   * Test {@link PrometheusMetricsFactory#configure(PrometheusConfiguration)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link PrometheusMetricsFactory#configure(PrometheusConfiguration)}
   */
  @Test
  @DisplayName("Test configure(PrometheusConfiguration); when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PrometheusMetricsHandler PrometheusMetricsFactory.configure(PrometheusConfiguration)"
  })
  void testConfigure_whenNull() {
    // Arrange, Act and Assert
    assertTrue(PrometheusMetricsFactory.configure(null) instanceof VoidPrometheusMetricsHandler);
  }

  /**
   * Test {@link PrometheusMetricsFactory#configure(PrometheusConfiguration)}.
   *
   * <ul>
   *   <li>When {@link PrometheusConfiguration#PrometheusConfiguration()}.
   * </ul>
   *
   * <p>Method under test: {@link PrometheusMetricsFactory#configure(PrometheusConfiguration)}
   */
  @Test
  @DisplayName("Test configure(PrometheusConfiguration); when PrometheusConfiguration()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "PrometheusMetricsHandler PrometheusMetricsFactory.configure(PrometheusConfiguration)"
  })
  void testConfigure_whenPrometheusConfiguration() {
    // Arrange, Act and Assert
    assertTrue(
        PrometheusMetricsFactory.configure(new PrometheusConfiguration())
            instanceof VoidPrometheusMetricsHandler);
  }
}
