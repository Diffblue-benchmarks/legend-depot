package org.finos.legend.depot.services.artifacts.handlers.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.finos.legend.depot.domain.notifications.MetadataNotificationResponse;
import org.finos.legend.depot.domain.notifications.MetadataNotificationStatus;
import org.finos.legend.depot.services.api.metrics.query.VoidQueryMetricsRegistry;
import org.finos.legend.depot.services.api.notifications.queue.VoidQueue;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.services.projects.ManageProjectsServiceImpl;
import org.finos.legend.depot.services.versionedEntities.ManageVersionedEntitiesServiceImpl;
import org.finos.legend.depot.store.mongo.projects.ProjectsMongo;
import org.finos.legend.depot.store.mongo.projects.ProjectsVersionsMongo;
import org.finos.legend.depot.store.mongo.versionedEntities.VersionedEntitiesMongo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EntitiesHandlerImplDiffblueTest {
  /**
   * Test {@link EntitiesHandlerImpl#refreshProjectVersionArtifacts(String, String, String, List)}.
   *
   * <p>Method under test: {@link EntitiesHandlerImpl#refreshProjectVersionArtifacts(String, String,
   * String, List)}
   */
  @Test
  @DisplayName("Test refreshProjectVersionArtifacts(String, String, String, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse EntitiesHandlerImpl.refreshProjectVersionArtifacts(String, String, String, List)"
  })
  void testRefreshProjectVersionArtifacts() {
    // Arrange
    VersionedEntitiesMongo entities = new VersionedEntitiesMongo(null);
    ProjectsVersionsMongo projectsVersions = new ProjectsVersionsMongo(null);
    ProjectsMongo projects = new ProjectsMongo(null);
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();

    ManageProjectsServiceImpl projects2 =
        new ManageProjectsServiceImpl(
            projectsVersions,
            projects,
            metricsRegistry,
            queue,
            new ProjectsConfiguration("janedoe/featurebranch"));

    ManageVersionedEntitiesServiceImpl entitiesService =
        new ManageVersionedEntitiesServiceImpl(entities, projects2);
    EntitiesHandlerImpl entitiesHandlerImpl =
        new EntitiesHandlerImpl(entitiesService, new EntityProvider());

    ArrayList<File> files = new ArrayList<>();
    files.add(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());

    // Act
    MetadataNotificationResponse actualRefreshProjectVersionArtifactsResult =
        entitiesHandlerImpl.refreshProjectVersionArtifacts("42", "42", "42", files);

    // Assert
    List<String> messages = actualRefreshProjectVersionArtifactsResult.getMessages();
    assertEquals(1, messages.size());
    assertEquals("found 0 ENTITIES for [42-42-42] ", messages.get(0));
    assertEquals(
        MetadataNotificationStatus.SUCCESS, actualRefreshProjectVersionArtifactsResult.getStatus());
    assertFalse(actualRefreshProjectVersionArtifactsResult.hasErrors());
    assertTrue(actualRefreshProjectVersionArtifactsResult.getErrors().isEmpty());
  }

  /**
   * Test {@link EntitiesHandlerImpl#refreshProjectVersionArtifacts(String, String, String, List)}.
   *
   * <p>Method under test: {@link EntitiesHandlerImpl#refreshProjectVersionArtifacts(String, String,
   * String, List)}
   */
  @Test
  @DisplayName("Test refreshProjectVersionArtifacts(String, String, String, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse EntitiesHandlerImpl.refreshProjectVersionArtifacts(String, String, String, List)"
  })
  void testRefreshProjectVersionArtifacts2() {
    // Arrange
    VersionedEntitiesMongo entities = new VersionedEntitiesMongo(null);
    ProjectsVersionsMongo projectsVersions = new ProjectsVersionsMongo(null);
    ProjectsMongo projects = new ProjectsMongo(null);
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();

    ManageProjectsServiceImpl projects2 =
        new ManageProjectsServiceImpl(
            projectsVersions,
            projects,
            metricsRegistry,
            queue,
            new ProjectsConfiguration("janedoe/featurebranch"));

    ManageVersionedEntitiesServiceImpl entitiesService =
        new ManageVersionedEntitiesServiceImpl(entities, projects2);
    EntitiesHandlerImpl entitiesHandlerImpl =
        new EntitiesHandlerImpl(entitiesService, new EntityProvider());

    ArrayList<File> files = new ArrayList<>();
    files.add(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());
    files.add(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());

    // Act
    MetadataNotificationResponse actualRefreshProjectVersionArtifactsResult =
        entitiesHandlerImpl.refreshProjectVersionArtifacts("42", "42", "42", files);

    // Assert
    List<String> messages = actualRefreshProjectVersionArtifactsResult.getMessages();
    assertEquals(1, messages.size());
    assertEquals("found 0 ENTITIES for [42-42-42] ", messages.get(0));
    assertEquals(
        MetadataNotificationStatus.SUCCESS, actualRefreshProjectVersionArtifactsResult.getStatus());
    assertFalse(actualRefreshProjectVersionArtifactsResult.hasErrors());
    assertTrue(actualRefreshProjectVersionArtifactsResult.getErrors().isEmpty());
  }

  /**
   * Test {@link EntitiesHandlerImpl#refreshProjectVersionArtifacts(String, String, String, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Messages size is one.
   * </ul>
   *
   * <p>Method under test: {@link EntitiesHandlerImpl#refreshProjectVersionArtifacts(String, String,
   * String, List)}
   */
  @Test
  @DisplayName(
      "Test refreshProjectVersionArtifacts(String, String, String, List); when ArrayList(); then return Messages size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse EntitiesHandlerImpl.refreshProjectVersionArtifacts(String, String, String, List)"
  })
  void testRefreshProjectVersionArtifacts_whenArrayList_thenReturnMessagesSizeIsOne() {
    // Arrange
    VersionedEntitiesMongo entities = new VersionedEntitiesMongo(null);
    ProjectsVersionsMongo projectsVersions = new ProjectsVersionsMongo(null);
    ProjectsMongo projects = new ProjectsMongo(null);
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();

    ManageProjectsServiceImpl projects2 =
        new ManageProjectsServiceImpl(
            projectsVersions,
            projects,
            metricsRegistry,
            queue,
            new ProjectsConfiguration("janedoe/featurebranch"));

    ManageVersionedEntitiesServiceImpl entitiesService =
        new ManageVersionedEntitiesServiceImpl(entities, projects2);
    EntitiesHandlerImpl entitiesHandlerImpl =
        new EntitiesHandlerImpl(entitiesService, new EntityProvider());

    // Act
    MetadataNotificationResponse actualRefreshProjectVersionArtifactsResult =
        entitiesHandlerImpl.refreshProjectVersionArtifacts("42", "42", "42", new ArrayList<>());

    // Assert
    List<String> messages = actualRefreshProjectVersionArtifactsResult.getMessages();
    assertEquals(1, messages.size());
    assertEquals("found 0 ENTITIES for [42-42-42] ", messages.get(0));
    assertEquals(
        MetadataNotificationStatus.SUCCESS, actualRefreshProjectVersionArtifactsResult.getStatus());
    assertFalse(actualRefreshProjectVersionArtifactsResult.hasErrors());
    assertTrue(actualRefreshProjectVersionArtifactsResult.getErrors().isEmpty());
  }

  /**
   * Test {@link EntitiesHandlerImpl#delete(String, String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link ManageVersionedEntitiesServiceImpl#delete(String, String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link EntitiesHandlerImpl#delete(String, String, String)}
   */
  @Test
  @DisplayName("Test delete(String, String, String); then calls delete(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EntitiesHandlerImpl.delete(String, String, String)"})
  void testDelete_thenCallsDelete() {
    // Arrange
    ManageVersionedEntitiesServiceImpl entitiesService =
        mock(ManageVersionedEntitiesServiceImpl.class);
    when(entitiesService.delete(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(1L);
    EntitiesHandlerImpl entitiesHandlerImpl =
        new EntitiesHandlerImpl(entitiesService, new EntityProvider());

    // Act
    entitiesHandlerImpl.delete("42", "42", "42");

    // Assert
    verify(entitiesService).delete("42", "42", "42");
  }
}
