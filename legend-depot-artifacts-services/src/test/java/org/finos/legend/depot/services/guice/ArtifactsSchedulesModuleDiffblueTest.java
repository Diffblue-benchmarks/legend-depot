package org.finos.legend.depot.services.guice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.function.Supplier;
import org.finos.legend.depot.services.api.artifacts.configuration.ArtifactsRefreshPolicyConfiguration;
import org.finos.legend.depot.services.api.artifacts.configuration.ArtifactsRetentionPolicyConfiguration;
import org.finos.legend.depot.services.api.artifacts.configuration.IncludeProjectPropertiesConfiguration;
import org.finos.legend.depot.services.api.artifacts.purge.ArtifactsPurgeService;
import org.finos.legend.depot.services.api.artifacts.refresh.ArtifactsRefreshService;
import org.finos.legend.depot.services.api.notifications.queue.VoidQueue;
import org.finos.legend.depot.services.api.schedules.SchedulesFactory;
import org.finos.legend.depot.services.artifacts.refresh.ArtifactsRefreshServiceImpl;
import org.finos.legend.depot.services.artifacts.repository.maven.TestMavenArtifactsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ArtifactsSchedulesModuleDiffblueTest {
  /**
   * Test {@link ArtifactsSchedulesModule#initVersions(SchedulesFactory, ArtifactsRefreshService,
   * ArtifactsRefreshPolicyConfiguration)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ArtifactsSchedulesModule#initVersions(SchedulesFactory,
   * ArtifactsRefreshService, ArtifactsRefreshPolicyConfiguration)}
   */
  @Test
  @DisplayName(
      "Test initVersions(SchedulesFactory, ArtifactsRefreshService, ArtifactsRefreshPolicyConfiguration); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ArtifactsSchedulesModule.initVersions(SchedulesFactory, ArtifactsRefreshService, ArtifactsRefreshPolicyConfiguration)"
  })
  void testInitVersions_thenReturnTrue() {
    // Arrange
    ArtifactsSchedulesModule artifactsSchedulesModule = new ArtifactsSchedulesModule();

    SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
    doNothing()
        .when(schedulesFactory)
        .registerExternalTriggerSchedule(
            Mockito.<String>any(), anyLong(), Mockito.<Supplier<Object>>any());
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();
    ArtifactsRefreshServiceImpl artifactsRefreshService =
        new ArtifactsRefreshServiceImpl(null, repositoryServices, new VoidQueue());
    ArrayList<String> properties = new ArrayList<>();
    IncludeProjectPropertiesConfiguration includeProjectPropertiesConfiguration =
        new IncludeProjectPropertiesConfiguration(properties, new ArrayList<>());
    ArtifactsRefreshPolicyConfiguration configuration =
        new ArtifactsRefreshPolicyConfiguration(42L, includeProjectPropertiesConfiguration);

    // Act
    boolean actualInitVersionsResult =
        artifactsSchedulesModule.initVersions(
            schedulesFactory, artifactsRefreshService, configuration);

    // Assert
    verify(schedulesFactory)
        .registerExternalTriggerSchedule(
            eq("REFRESH_ALL_VERSION_ARTIFACTS_SCHEDULE"), eq(42L), isA(Supplier.class));
    assertTrue(actualInitVersionsResult);
  }

  /**
   * Test {@link ArtifactsSchedulesModule#scheduleEvictionOfProjectVersions(SchedulesFactory,
   * ArtifactsPurgeService, ArtifactsRetentionPolicyConfiguration)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ArtifactsSchedulesModule#scheduleEvictionOfProjectVersions(SchedulesFactory,
   * ArtifactsPurgeService, ArtifactsRetentionPolicyConfiguration)}
   */
  @Test
  @DisplayName(
      "Test scheduleEvictionOfProjectVersions(SchedulesFactory, ArtifactsPurgeService, ArtifactsRetentionPolicyConfiguration); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ArtifactsSchedulesModule.scheduleEvictionOfProjectVersions(SchedulesFactory, ArtifactsPurgeService, ArtifactsRetentionPolicyConfiguration)"
  })
  void testScheduleEvictionOfProjectVersions_thenReturnTrue() {
    // Arrange
    ArtifactsSchedulesModule artifactsSchedulesModule = new ArtifactsSchedulesModule();

    SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
    doNothing()
        .when(schedulesFactory)
        .registerSingleInstance(
            Mockito.<String>any(), anyLong(), anyLong(), Mockito.<Supplier<Object>>any());

    // Act
    boolean actualScheduleEvictionOfProjectVersionsResult =
        artifactsSchedulesModule.scheduleEvictionOfProjectVersions(
            schedulesFactory, null, new ArtifactsRetentionPolicyConfiguration(3, 1, 1));

    // Assert
    verify(schedulesFactory)
        .registerSingleInstance(
            eq("evict-LRU-project-versions"), eq(6000L), eq(86400000L), isA(Supplier.class));
    assertTrue(actualScheduleEvictionOfProjectVersionsResult);
  }

  /**
   * Test {@link ArtifactsSchedulesModule#scheduleDeprecationOfProjectVersions(SchedulesFactory,
   * ArtifactsPurgeService)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ArtifactsSchedulesModule#scheduleDeprecationOfProjectVersions(SchedulesFactory,
   * ArtifactsPurgeService)}
   */
  @Test
  @DisplayName(
      "Test scheduleDeprecationOfProjectVersions(SchedulesFactory, ArtifactsPurgeService); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ArtifactsSchedulesModule.scheduleDeprecationOfProjectVersions(SchedulesFactory, ArtifactsPurgeService)"
  })
  void testScheduleDeprecationOfProjectVersions_thenReturnTrue() {
    // Arrange
    ArtifactsSchedulesModule artifactsSchedulesModule = new ArtifactsSchedulesModule();

    SchedulesFactory schedulesFactory = mock(SchedulesFactory.class);
    doNothing()
        .when(schedulesFactory)
        .registerSingleInstance(
            Mockito.<String>any(), anyLong(), anyLong(), Mockito.<Supplier<Object>>any());

    // Act
    boolean actualScheduleDeprecationOfProjectVersionsResult =
        artifactsSchedulesModule.scheduleDeprecationOfProjectVersions(schedulesFactory, null);

    // Assert
    verify(schedulesFactory)
        .registerSingleInstance(
            eq("deprecate-versions-notInRepository"),
            eq(6000L),
            eq(172800000L),
            isA(Supplier.class));
    assertTrue(actualScheduleDeprecationOfProjectVersionsResult);
  }
}
