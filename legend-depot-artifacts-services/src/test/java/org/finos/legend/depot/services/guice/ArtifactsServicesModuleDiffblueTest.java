package org.finos.legend.depot.services.guice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.ReadConcern;
import com.mongodb.ReadConcernLevel;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.internal.OperationExecutor;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistry;
import org.finos.legend.depot.core.services.api.metrics.PrometheusMetricsHandler;
import org.finos.legend.depot.core.services.api.metrics.VoidPrometheusMetricsHandler;
import org.finos.legend.depot.services.api.artifacts.configuration.ArtifactsRetentionPolicyConfiguration;
import org.finos.legend.depot.services.api.artifacts.handlers.entties.EntitiesArtifactsHandler;
import org.finos.legend.depot.services.api.artifacts.handlers.generations.FileGenerationsArtifactsHandler;
import org.finos.legend.depot.services.api.metrics.query.VoidQueryMetricsRegistry;
import org.finos.legend.depot.services.api.notifications.queue.VoidQueue;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.services.artifacts.handlers.entities.EntitiesHandlerImpl;
import org.finos.legend.depot.services.artifacts.handlers.entities.EntityProvider;
import org.finos.legend.depot.services.artifacts.handlers.generations.FileGenerationHandlerImpl;
import org.finos.legend.depot.services.artifacts.handlers.generations.FileGenerationsProvider;
import org.finos.legend.depot.services.artifacts.repository.maven.TestMavenArtifactsRepository;
import org.finos.legend.depot.services.generations.impl.ManageFileGenerationsServiceImpl;
import org.finos.legend.depot.services.projects.ManageProjectsServiceImpl;
import org.finos.legend.depot.services.versionedEntities.ManageVersionedEntitiesServiceImpl;
import org.finos.legend.depot.store.mongo.generations.FileGenerationsMongo;
import org.finos.legend.depot.store.mongo.projects.ProjectsMongo;
import org.finos.legend.depot.store.mongo.projects.ProjectsVersionsMongo;
import org.finos.legend.depot.store.mongo.versionedEntities.VersionedEntitiesMongo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ArtifactsServicesModuleDiffblueTest {
  /**
   * Test {@link ArtifactsServicesModule#registerEntityHandler(EntitiesArtifactsHandler)}.
   *
   * <ul>
   *   <li>When {@link ReadConcern#ReadConcern(ReadConcernLevel)} with level is {@code LOCAL}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ArtifactsServicesModule#registerEntityHandler(EntitiesArtifactsHandler)}
   */
  @Test
  @DisplayName(
      "Test registerEntityHandler(EntitiesArtifactsHandler); when ReadConcern(ReadConcernLevel) with level is 'LOCAL'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ArtifactsServicesModule.registerEntityHandler(EntitiesArtifactsHandler)"
  })
  void testRegisterEntityHandler_whenReadConcernWithLevelIsLocal_thenReturnTrue() {
    // Arrange
    ArtifactsServicesModule artifactsServicesModule = new ArtifactsServicesModule();
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern();

    MongoDatabaseImpl databaseProvider =
        new MongoDatabaseImpl(
            "Name",
            codecRegistry,
            readPreference,
            writeConcern,
            true,
            true,
            new ReadConcern(ReadConcernLevel.LOCAL),
            UuidRepresentation.UNSPECIFIED,
            mock(OperationExecutor.class));
    VersionedEntitiesMongo entities = new VersionedEntitiesMongo(databaseProvider);
    ProjectsVersionsMongo projectsVersions = new ProjectsVersionsMongo(mock(MongoDatabase.class));
    ProjectsMongo projects = new ProjectsMongo(mock(MongoDatabase.class));
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
    EntitiesHandlerImpl versionArtifactsHandler =
        new EntitiesHandlerImpl(entitiesService, new EntityProvider());

    // Act
    boolean actualRegisterEntityHandlerResult =
        artifactsServicesModule.registerEntityHandler(versionArtifactsHandler);

    // Assert
    assertTrue(actualRegisterEntityHandlerResult);
  }

  /**
   * Test {@link
   * ArtifactsServicesModule#registerFileGenerationHandler(FileGenerationsArtifactsHandler)}.
   *
   * <ul>
   *   <li>When {@link ReadConcern#ReadConcern(ReadConcernLevel)} with level is {@code LOCAL}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ArtifactsServicesModule#registerFileGenerationHandler(FileGenerationsArtifactsHandler)}
   */
  @Test
  @DisplayName(
      "Test registerFileGenerationHandler(FileGenerationsArtifactsHandler); when ReadConcern(ReadConcernLevel) with level is 'LOCAL'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ArtifactsServicesModule.registerFileGenerationHandler(FileGenerationsArtifactsHandler)"
  })
  void testRegisterFileGenerationHandler_whenReadConcernWithLevelIsLocal_thenReturnTrue() {
    // Arrange
    ArtifactsServicesModule artifactsServicesModule = new ArtifactsServicesModule();
    TestMavenArtifactsRepository repository = new TestMavenArtifactsRepository();
    FileGenerationsProvider provider = new FileGenerationsProvider();
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern();

    MongoDatabaseImpl databaseProvider =
        new MongoDatabaseImpl(
            "Name",
            codecRegistry,
            readPreference,
            writeConcern,
            true,
            true,
            new ReadConcern(ReadConcernLevel.LOCAL),
            UuidRepresentation.UNSPECIFIED,
            mock(OperationExecutor.class));
    FileGenerationsMongo fileGenerations = new FileGenerationsMongo(databaseProvider);
    ProjectsVersionsMongo projectsVersions = new ProjectsVersionsMongo(mock(MongoDatabase.class));
    ProjectsMongo projects = new ProjectsMongo(mock(MongoDatabase.class));
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

    FileGenerationHandlerImpl versionArtifactsHandler =
        new FileGenerationHandlerImpl(repository, provider, generations);

    // Act
    boolean actualRegisterFileGenerationHandlerResult =
        artifactsServicesModule.registerFileGenerationHandler(versionArtifactsHandler);

    // Assert
    assertTrue(actualRegisterFileGenerationHandlerResult);
  }

  /**
   * Test {@link ArtifactsServicesModule#registerMetrics(PrometheusMetricsHandler)}.
   *
   * <ul>
   *   <li>When {@link VoidPrometheusMetricsHandler} (default constructor).
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ArtifactsServicesModule#registerMetrics(PrometheusMetricsHandler)}
   */
  @Test
  @DisplayName(
      "Test registerMetrics(PrometheusMetricsHandler); when VoidPrometheusMetricsHandler (default constructor); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ArtifactsServicesModule.registerMetrics(PrometheusMetricsHandler)"})
  void testRegisterMetrics_whenVoidPrometheusMetricsHandler_thenReturnTrue() {
    // Arrange
    ArtifactsServicesModule artifactsServicesModule = new ArtifactsServicesModule();

    // Act and Assert
    assertTrue(artifactsServicesModule.registerMetrics(new VoidPrometheusMetricsHandler()));
  }

  /**
   * Test {@link
   * ArtifactsServicesModule#getNoOfSnapshotVersionsToRetain(ArtifactsRetentionPolicyConfiguration)}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link
   * ArtifactsServicesModule#getNoOfSnapshotVersionsToRetain(ArtifactsRetentionPolicyConfiguration)}
   */
  @Test
  @DisplayName(
      "Test getNoOfSnapshotVersionsToRetain(ArtifactsRetentionPolicyConfiguration); then return three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ArtifactsServicesModule.getNoOfSnapshotVersionsToRetain(ArtifactsRetentionPolicyConfiguration)"
  })
  void testGetNoOfSnapshotVersionsToRetain_thenReturnThree() {
    // Arrange
    ArtifactsServicesModule artifactsServicesModule = new ArtifactsServicesModule();

    // Act
    int actualNoOfSnapshotVersionsToRetain =
        artifactsServicesModule.getNoOfSnapshotVersionsToRetain(
            new ArtifactsRetentionPolicyConfiguration(3, 1, 1));

    // Assert
    assertEquals(3, actualNoOfSnapshotVersionsToRetain);
  }
}
