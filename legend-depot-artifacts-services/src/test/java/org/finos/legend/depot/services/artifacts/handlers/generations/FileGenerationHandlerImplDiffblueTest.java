package org.finos.legend.depot.services.artifacts.handlers.generations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
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
import org.finos.legend.depot.services.api.artifacts.repository.VoidArtifactRepositoryConfiguration;
import org.finos.legend.depot.services.api.artifacts.repository.VoidArtifactRepositoryProvider;
import org.finos.legend.depot.services.api.generations.ManageFileGenerationsService;
import org.finos.legend.depot.services.api.metrics.query.VoidQueryMetricsRegistry;
import org.finos.legend.depot.services.api.notifications.queue.VoidQueue;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.services.artifacts.repository.maven.TestMavenArtifactsRepository;
import org.finos.legend.depot.services.generations.impl.ManageFileGenerationsServiceImpl;
import org.finos.legend.depot.services.projects.ManageProjectsServiceImpl;
import org.finos.legend.depot.store.model.generations.StoredFileGeneration;
import org.finos.legend.depot.store.mongo.generations.FileGenerationsMongo;
import org.finos.legend.depot.store.mongo.projects.ProjectsMongo;
import org.finos.legend.depot.store.mongo.projects.ProjectsVersionsMongo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FileGenerationHandlerImplDiffblueTest {
  /**
   * Test {@link FileGenerationHandlerImpl#refreshProjectVersionArtifacts(String, String, String,
   * List)}.
   *
   * <p>Method under test: {@link FileGenerationHandlerImpl#refreshProjectVersionArtifacts(String,
   * String, String, List)}
   */
  @Test
  @DisplayName("Test refreshProjectVersionArtifacts(String, String, String, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse FileGenerationHandlerImpl.refreshProjectVersionArtifacts(String, String, String, List)"
  })
  void testRefreshProjectVersionArtifacts() {
    // Arrange
    VoidArtifactRepositoryProvider repository =
        new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration());
    FileGenerationsProvider provider = new FileGenerationsProvider();
    FileGenerationsMongo fileGenerations = new FileGenerationsMongo(null);
    ProjectsVersionsMongo projectsVersions = new ProjectsVersionsMongo(null);
    ProjectsMongo projects = new ProjectsMongo(null);
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();

    ManageProjectsServiceImpl projectsService =
        new ManageProjectsServiceImpl(
            projectsVersions,
            projects,
            metricsRegistry,
            queue,
            new ProjectsConfiguration("janedoe/featurebranch"));

    ManageFileGenerationsServiceImpl generations =
        new ManageFileGenerationsServiceImpl(fileGenerations, projectsService);

    FileGenerationHandlerImpl fileGenerationHandlerImpl =
        new FileGenerationHandlerImpl(repository, provider, generations);

    // Act
    MetadataNotificationResponse actualRefreshProjectVersionArtifactsResult =
        fileGenerationHandlerImpl.refreshProjectVersionArtifacts(
            "42", "42", "42", new ArrayList<>());

    // Assert
    List<String> messages = actualRefreshProjectVersionArtifactsResult.getMessages();
    assertEquals(1, messages.size());
    assertEquals("new [0] generations for [42-42-42] ", messages.get(0));
    assertEquals(
        MetadataNotificationStatus.SUCCESS, actualRefreshProjectVersionArtifactsResult.getStatus());
    assertFalse(actualRefreshProjectVersionArtifactsResult.hasErrors());
    assertTrue(actualRefreshProjectVersionArtifactsResult.getErrors().isEmpty());
  }

  /**
   * Test {@link FileGenerationHandlerImpl#refreshProjectVersionArtifacts(String, String, String,
   * List)}.
   *
   * <p>Method under test: {@link FileGenerationHandlerImpl#refreshProjectVersionArtifacts(String,
   * String, String, List)}
   */
  @Test
  @DisplayName("Test refreshProjectVersionArtifacts(String, String, String, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse FileGenerationHandlerImpl.refreshProjectVersionArtifacts(String, String, String, List)"
  })
  void testRefreshProjectVersionArtifacts2() {
    // Arrange
    VoidArtifactRepositoryProvider repository =
        new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration());
    FileGenerationsMongo fileGenerations = new FileGenerationsMongo(null);
    ProjectsVersionsMongo projectsVersions = new ProjectsVersionsMongo(null);
    ProjectsMongo projects = new ProjectsMongo(null);
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();

    ManageProjectsServiceImpl projectsService =
        new ManageProjectsServiceImpl(
            projectsVersions,
            projects,
            metricsRegistry,
            queue,
            new ProjectsConfiguration("janedoe/featurebranch"));

    ManageFileGenerationsServiceImpl generations =
        new ManageFileGenerationsServiceImpl(fileGenerations, projectsService);

    FileGenerationHandlerImpl fileGenerationHandlerImpl =
        new FileGenerationHandlerImpl(repository, null, generations);

    // Act
    MetadataNotificationResponse actualRefreshProjectVersionArtifactsResult =
        fileGenerationHandlerImpl.refreshProjectVersionArtifacts(
            "42", "42", "42", new ArrayList<>());

    // Assert
    List<String> errors = actualRefreshProjectVersionArtifactsResult.getErrors();
    assertEquals(1, errors.size());
    assertEquals("Error processing generations update for 42-42-42 , ERROR: [null]", errors.get(0));
    assertEquals(
        MetadataNotificationStatus.FAILED, actualRefreshProjectVersionArtifactsResult.getStatus());
    assertTrue(actualRefreshProjectVersionArtifactsResult.getMessages().isEmpty());
    assertTrue(actualRefreshProjectVersionArtifactsResult.hasErrors());
  }

  /**
   * Test {@link FileGenerationHandlerImpl#refreshProjectVersionArtifacts(String, String, String,
   * List)}.
   *
   * <p>Method under test: {@link FileGenerationHandlerImpl#refreshProjectVersionArtifacts(String,
   * String, String, List)}
   */
  @Test
  @DisplayName("Test refreshProjectVersionArtifacts(String, String, String, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse FileGenerationHandlerImpl.refreshProjectVersionArtifacts(String, String, String, List)"
  })
  void testRefreshProjectVersionArtifacts3() {
    // Arrange
    VoidArtifactRepositoryProvider repository =
        new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration());
    FileGenerationsProvider provider = new FileGenerationsProvider();
    ProjectsVersionsMongo projectsVersions = new ProjectsVersionsMongo(null);
    ProjectsMongo projects = new ProjectsMongo(null);
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();

    ManageProjectsServiceImpl projectsService =
        new ManageProjectsServiceImpl(
            projectsVersions,
            projects,
            metricsRegistry,
            queue,
            new ProjectsConfiguration("janedoe/featurebranch"));
    ManageFileGenerationsServiceImpl generations =
        new ManageFileGenerationsServiceImpl(null, projectsService);

    FileGenerationHandlerImpl fileGenerationHandlerImpl =
        new FileGenerationHandlerImpl(repository, provider, generations);

    // Act
    MetadataNotificationResponse actualRefreshProjectVersionArtifactsResult =
        fileGenerationHandlerImpl.refreshProjectVersionArtifacts(
            "42", "42", "42", new ArrayList<>());

    // Assert
    List<String> errors = actualRefreshProjectVersionArtifactsResult.getErrors();
    assertEquals(1, errors.size());
    assertEquals("Error processing generations update for 42-42-42 , ERROR: [null]", errors.get(0));
    assertEquals(
        MetadataNotificationStatus.FAILED, actualRefreshProjectVersionArtifactsResult.getStatus());
    assertTrue(actualRefreshProjectVersionArtifactsResult.getMessages().isEmpty());
    assertTrue(actualRefreshProjectVersionArtifactsResult.hasErrors());
  }

  /**
   * Test {@link FileGenerationHandlerImpl#refreshProjectVersionArtifacts(String, String, String,
   * List)}.
   *
   * <p>Method under test: {@link FileGenerationHandlerImpl#refreshProjectVersionArtifacts(String,
   * String, String, List)}
   */
  @Test
  @DisplayName("Test refreshProjectVersionArtifacts(String, String, String, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse FileGenerationHandlerImpl.refreshProjectVersionArtifacts(String, String, String, List)"
  })
  void testRefreshProjectVersionArtifacts4() {
    // Arrange
    VoidArtifactRepositoryProvider repository =
        new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration());
    FileGenerationsProvider provider = new FileGenerationsProvider();
    FileGenerationsMongo fileGenerations = new FileGenerationsMongo(null);
    ProjectsVersionsMongo projectsVersions = new ProjectsVersionsMongo(null);
    ProjectsMongo projects = new ProjectsMongo(null);
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();

    ManageProjectsServiceImpl projectsService =
        new ManageProjectsServiceImpl(
            projectsVersions,
            projects,
            metricsRegistry,
            queue,
            new ProjectsConfiguration("janedoe/featurebranch"));

    ManageFileGenerationsServiceImpl generations =
        new ManageFileGenerationsServiceImpl(fileGenerations, projectsService);

    FileGenerationHandlerImpl fileGenerationHandlerImpl =
        new FileGenerationHandlerImpl(repository, provider, generations);

    ArrayList<File> files = new ArrayList<>();
    files.add(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());

    // Act
    MetadataNotificationResponse actualRefreshProjectVersionArtifactsResult =
        fileGenerationHandlerImpl.refreshProjectVersionArtifacts("42", "42", "42", files);

    // Assert
    List<String> messages = actualRefreshProjectVersionArtifactsResult.getMessages();
    assertEquals(1, messages.size());
    assertEquals("new [0] generations for [42-42-42] ", messages.get(0));
    assertEquals(
        MetadataNotificationStatus.SUCCESS, actualRefreshProjectVersionArtifactsResult.getStatus());
    assertFalse(actualRefreshProjectVersionArtifactsResult.hasErrors());
    assertTrue(actualRefreshProjectVersionArtifactsResult.getErrors().isEmpty());
  }

  /**
   * Test {@link FileGenerationHandlerImpl#refreshProjectVersionArtifacts(String, String, String,
   * List)}.
   *
   * <p>Method under test: {@link FileGenerationHandlerImpl#refreshProjectVersionArtifacts(String,
   * String, String, List)}
   */
  @Test
  @DisplayName("Test refreshProjectVersionArtifacts(String, String, String, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse FileGenerationHandlerImpl.refreshProjectVersionArtifacts(String, String, String, List)"
  })
  void testRefreshProjectVersionArtifacts5() {
    // Arrange
    VoidArtifactRepositoryProvider repository =
        new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration());
    FileGenerationsProvider provider = new FileGenerationsProvider();
    FileGenerationsMongo fileGenerations = new FileGenerationsMongo(null);
    ProjectsVersionsMongo projectsVersions = new ProjectsVersionsMongo(null);
    ProjectsMongo projects = new ProjectsMongo(null);
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();

    ManageProjectsServiceImpl projectsService =
        new ManageProjectsServiceImpl(
            projectsVersions,
            projects,
            metricsRegistry,
            queue,
            new ProjectsConfiguration("janedoe/featurebranch"));

    ManageFileGenerationsServiceImpl generations =
        new ManageFileGenerationsServiceImpl(fileGenerations, projectsService);

    FileGenerationHandlerImpl fileGenerationHandlerImpl =
        new FileGenerationHandlerImpl(repository, provider, generations);

    ArrayList<File> files = new ArrayList<>();
    files.add(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());
    files.add(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());

    // Act
    MetadataNotificationResponse actualRefreshProjectVersionArtifactsResult =
        fileGenerationHandlerImpl.refreshProjectVersionArtifacts("42", "42", "42", files);

    // Assert
    List<String> messages = actualRefreshProjectVersionArtifactsResult.getMessages();
    assertEquals(1, messages.size());
    assertEquals("new [0] generations for [42-42-42] ", messages.get(0));
    assertEquals(
        MetadataNotificationStatus.SUCCESS, actualRefreshProjectVersionArtifactsResult.getStatus());
    assertFalse(actualRefreshProjectVersionArtifactsResult.hasErrors());
    assertTrue(actualRefreshProjectVersionArtifactsResult.getErrors().isEmpty());
  }

  /**
   * Test {@link FileGenerationHandlerImpl#refreshProjectVersionArtifacts(String, String, String,
   * List)}.
   *
   * <p>Method under test: {@link FileGenerationHandlerImpl#refreshProjectVersionArtifacts(String,
   * String, String, List)}
   */
  @Test
  @DisplayName("Test refreshProjectVersionArtifacts(String, String, String, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse FileGenerationHandlerImpl.refreshProjectVersionArtifacts(String, String, String, List)"
  })
  void testRefreshProjectVersionArtifacts6() {
    // Arrange
    VoidArtifactRepositoryProvider repository =
        new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration());
    FileGenerationsProvider provider = new FileGenerationsProvider();
    FileGenerationsMongo fileGenerations = new FileGenerationsMongo(null);
    ProjectsVersionsMongo projectsVersions = new ProjectsVersionsMongo(null);
    ProjectsMongo projects = new ProjectsMongo(null);
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();

    ManageProjectsServiceImpl projectsService =
        new ManageProjectsServiceImpl(
            projectsVersions,
            projects,
            metricsRegistry,
            queue,
            new ProjectsConfiguration("janedoe/featurebranch"));

    ManageFileGenerationsServiceImpl generations =
        new ManageFileGenerationsServiceImpl(fileGenerations, projectsService);

    FileGenerationHandlerImpl fileGenerationHandlerImpl =
        new FileGenerationHandlerImpl(repository, provider, generations);

    // Act
    MetadataNotificationResponse actualRefreshProjectVersionArtifactsResult =
        fileGenerationHandlerImpl.refreshProjectVersionArtifacts(
            "42", "42", "-SNAPSHOT", new ArrayList<>());

    // Assert
    List<String> errors = actualRefreshProjectVersionArtifactsResult.getErrors();
    assertEquals(1, errors.size());
    assertEquals(
        "Error processing generations update for 42-42--SNAPSHOT , ERROR: [null]", errors.get(0));
    List<String> messages = actualRefreshProjectVersionArtifactsResult.getMessages();
    assertEquals(1, messages.size());
    assertEquals(
        "removing prior FILE_GENERATIONS artifacts for [42-42--SNAPSHOT]", messages.get(0));
    assertEquals(
        MetadataNotificationStatus.FAILED, actualRefreshProjectVersionArtifactsResult.getStatus());
    assertTrue(actualRefreshProjectVersionArtifactsResult.hasErrors());
  }

  /**
   * Test {@link FileGenerationHandlerImpl#refreshProjectVersionArtifacts(String, String, String,
   * List)}.
   *
   * <ul>
   *   <li>Then return Errors first is a string.
   * </ul>
   *
   * <p>Method under test: {@link FileGenerationHandlerImpl#refreshProjectVersionArtifacts(String,
   * String, String, List)}
   */
  @Test
  @DisplayName(
      "Test refreshProjectVersionArtifacts(String, String, String, List); then return Errors first is a string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse FileGenerationHandlerImpl.refreshProjectVersionArtifacts(String, String, String, List)"
  })
  void testRefreshProjectVersionArtifacts_thenReturnErrorsFirstIsAString() {
    // Arrange
    TestMavenArtifactsRepository repository = new TestMavenArtifactsRepository();
    FileGenerationsProvider provider = new FileGenerationsProvider();
    FileGenerationsMongo fileGenerations = new FileGenerationsMongo(null);
    ProjectsVersionsMongo projectsVersions = new ProjectsVersionsMongo(null);
    ProjectsMongo projects = new ProjectsMongo(null);
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();

    ManageProjectsServiceImpl projectsService =
        new ManageProjectsServiceImpl(
            projectsVersions,
            projects,
            metricsRegistry,
            queue,
            new ProjectsConfiguration("janedoe/featurebranch"));

    ManageFileGenerationsServiceImpl generations =
        new ManageFileGenerationsServiceImpl(fileGenerations, projectsService);

    FileGenerationHandlerImpl fileGenerationHandlerImpl =
        new FileGenerationHandlerImpl(repository, provider, generations);

    // Act
    MetadataNotificationResponse actualRefreshProjectVersionArtifactsResult =
        fileGenerationHandlerImpl.refreshProjectVersionArtifacts(
            "42", "42", "42", new ArrayList<>());

    // Assert
    List<String> errors = actualRefreshProjectVersionArtifactsResult.getErrors();
    assertEquals(1, errors.size());
    assertEquals(
        "Error processing generations update for 42-42-42 , ERROR: [could not find repository/42/42/42"
            + "/42-42.pom]",
        errors.get(0));
    assertEquals(
        MetadataNotificationStatus.FAILED, actualRefreshProjectVersionArtifactsResult.getStatus());
    assertTrue(actualRefreshProjectVersionArtifactsResult.getMessages().isEmpty());
    assertTrue(actualRefreshProjectVersionArtifactsResult.hasErrors());
  }

  /**
   * Test {@link FileGenerationHandlerImpl#refreshProjectVersionArtifacts(String, String, String,
   * List)}.
   *
   * <ul>
   *   <li>Then return Messages size is two.
   * </ul>
   *
   * <p>Method under test: {@link FileGenerationHandlerImpl#refreshProjectVersionArtifacts(String,
   * String, String, List)}
   */
  @Test
  @DisplayName(
      "Test refreshProjectVersionArtifacts(String, String, String, List); then return Messages size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse FileGenerationHandlerImpl.refreshProjectVersionArtifacts(String, String, String, List)"
  })
  void testRefreshProjectVersionArtifacts_thenReturnMessagesSizeIsTwo() {
    // Arrange
    ManageFileGenerationsService generations = mock(ManageFileGenerationsService.class);
    when(generations.delete(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(1L);
    doNothing().when(generations).createOrUpdate(Mockito.<List<StoredFileGeneration>>any());
    VoidArtifactRepositoryProvider repository =
        new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration());

    FileGenerationHandlerImpl fileGenerationHandlerImpl =
        new FileGenerationHandlerImpl(repository, new FileGenerationsProvider(), generations);

    // Act
    MetadataNotificationResponse actualRefreshProjectVersionArtifactsResult =
        fileGenerationHandlerImpl.refreshProjectVersionArtifacts(
            "42", "42", "-SNAPSHOT", new ArrayList<>());

    // Assert
    verify(generations).createOrUpdate(isA(List.class));
    verify(generations).delete("42", "42", "-SNAPSHOT");
    List<String> messages = actualRefreshProjectVersionArtifactsResult.getMessages();
    assertEquals(2, messages.size());
    assertEquals("new [0] generations for [42-42--SNAPSHOT] ", messages.get(1));
    assertEquals(
        "removing prior FILE_GENERATIONS artifacts for [42-42--SNAPSHOT]", messages.get(0));
    assertEquals(
        MetadataNotificationStatus.SUCCESS, actualRefreshProjectVersionArtifactsResult.getStatus());
    assertFalse(actualRefreshProjectVersionArtifactsResult.hasErrors());
    assertTrue(actualRefreshProjectVersionArtifactsResult.getErrors().isEmpty());
  }

  /**
   * Test {@link FileGenerationHandlerImpl#getAllNonVersionedEntities(String, String, String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link FileGenerationHandlerImpl#getAllNonVersionedEntities(String,
   * String, String)}
   */
  @Test
  @DisplayName("Test getAllNonVersionedEntities(String, String, String); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List FileGenerationHandlerImpl.getAllNonVersionedEntities(String, String, String)"
  })
  void testGetAllNonVersionedEntities_thenReturnEmpty() {
    // Arrange
    VoidArtifactRepositoryProvider repository =
        new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration());
    FileGenerationsProvider provider = new FileGenerationsProvider();
    FileGenerationsMongo fileGenerations = new FileGenerationsMongo(null);
    ProjectsVersionsMongo projectsVersions = new ProjectsVersionsMongo(null);
    ProjectsMongo projects = new ProjectsMongo(null);
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();

    ManageProjectsServiceImpl projectsService =
        new ManageProjectsServiceImpl(
            projectsVersions,
            projects,
            metricsRegistry,
            queue,
            new ProjectsConfiguration("janedoe/featurebranch"));

    ManageFileGenerationsServiceImpl generations =
        new ManageFileGenerationsServiceImpl(fileGenerations, projectsService);

    FileGenerationHandlerImpl fileGenerationHandlerImpl =
        new FileGenerationHandlerImpl(repository, provider, generations);

    // Act and Assert
    assertTrue(fileGenerationHandlerImpl.getAllNonVersionedEntities("42", "42", "42").isEmpty());
  }

  /**
   * Test {@link FileGenerationHandlerImpl#delete(String, String, String)}.
   *
   * <ul>
   *   <li>Given {@link ManageFileGenerationsServiceImpl} {@link
   *       ManageFileGenerationsServiceImpl#delete(String, String, String)} return one.
   *   <li>Then calls {@link ManageFileGenerationsServiceImpl#delete(String, String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link FileGenerationHandlerImpl#delete(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test delete(String, String, String); given ManageFileGenerationsServiceImpl delete(String, String, String) return one; then calls delete(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FileGenerationHandlerImpl.delete(String, String, String)"})
  void testDelete_givenManageFileGenerationsServiceImplDeleteReturnOne_thenCallsDelete() {
    // Arrange
    ManageFileGenerationsServiceImpl generations = mock(ManageFileGenerationsServiceImpl.class);
    when(generations.delete(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(1L);
    TestMavenArtifactsRepository repository = new TestMavenArtifactsRepository();

    FileGenerationHandlerImpl fileGenerationHandlerImpl =
        new FileGenerationHandlerImpl(repository, new FileGenerationsProvider(), generations);

    // Act
    fileGenerationHandlerImpl.delete("42", "42", "42");

    // Assert
    verify(generations).delete("42", "42", "42");
  }
}
