package org.finos.legend.depot.services.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.function.Supplier;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.configuration.PrometheusConfiguration;
import org.finos.legend.depot.services.api.artifacts.reconciliation.VersionsReconciliationService;
import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.finos.legend.depot.services.artifacts.reconciliation.VersionsReconciliationServiceImpl;
import org.finos.legend.depot.services.artifacts.repository.maven.TestMavenArtifactsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class VersionReconciliationSchedulesModuleDiffblueTest {
  /**
   * Test {@link VersionReconciliationSchedulesModule#registerMetrics(PrometheusConfiguration,
   * SchedulesFactory, VersionsReconciliationService)}.
   *
   * <ul>
   *   <li>Then calls {@link SchedulesFactory#register(String, long, long, Supplier)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * VersionReconciliationSchedulesModule#registerMetrics(PrometheusConfiguration, SchedulesFactory,
   * VersionsReconciliationService)}
   */
  @Test
  @DisplayName(
      "Test registerMetrics(PrometheusConfiguration, SchedulesFactory, VersionsReconciliationService); then calls register(String, long, long, Supplier)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean VersionReconciliationSchedulesModule.registerMetrics(PrometheusConfiguration, SchedulesFactory, VersionsReconciliationService)"
  })
  void testRegisterMetrics_thenCallsRegister() {
    // Arrange
    VersionReconciliationSchedulesModule versionReconciliationSchedulesModule =
        new VersionReconciliationSchedulesModule();
    PrometheusConfiguration prometheusConfiguration =
        new PrometheusConfiguration(true, new VoidPrometheusMetricsHandler());

    SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
    doNothing()
        .when(schedulesFactory)
        .register(Mockito.<String>any(), anyLong(), anyLong(), Mockito.<Supplier<Object>>any());
    VersionsReconciliationServiceImpl versionsReconciliationService =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), null);

    // Act
    boolean actualRegisterMetricsResult =
        versionReconciliationSchedulesModule.registerMetrics(
            prometheusConfiguration, schedulesFactory, versionsReconciliationService);

    // Assert
    verify(schedulesFactory, atLeast(1))
        .register(Mockito.<String>any(), eq(30000L), eq(30000L), Mockito.<Supplier<Object>>any());
    assertTrue(actualRegisterMetricsResult);
  }

  /**
   * Test {@link VersionReconciliationSchedulesModule#registerMetrics(PrometheusConfiguration,
   * SchedulesFactory, VersionsReconciliationService)}.
   *
   * <ul>
   *   <li>When {@link PrometheusConfiguration#PrometheusConfiguration()}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * VersionReconciliationSchedulesModule#registerMetrics(PrometheusConfiguration, SchedulesFactory,
   * VersionsReconciliationService)}
   */
  @Test
  @DisplayName(
      "Test registerMetrics(PrometheusConfiguration, SchedulesFactory, VersionsReconciliationService); when PrometheusConfiguration(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean VersionReconciliationSchedulesModule.registerMetrics(PrometheusConfiguration, SchedulesFactory, VersionsReconciliationService)"
  })
  void testRegisterMetrics_whenPrometheusConfiguration_thenReturnTrue() {
    // Arrange
    VersionReconciliationSchedulesModule versionReconciliationSchedulesModule =
        new VersionReconciliationSchedulesModule();
    PrometheusConfiguration prometheusConfiguration = new PrometheusConfiguration();
    SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
    VersionsReconciliationServiceImpl versionsReconciliationService =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), null);

    // Act and Assert
    assertTrue(
        versionReconciliationSchedulesModule.registerMetrics(
            prometheusConfiguration, schedulesFactory, versionsReconciliationService));
  }
}
