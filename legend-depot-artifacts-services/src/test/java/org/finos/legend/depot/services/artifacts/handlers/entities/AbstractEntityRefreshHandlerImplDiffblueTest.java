package org.finos.legend.depot.services.artifacts.handlers.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ch.qos.logback.classic.Logger;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.ReadConcern;
import com.mongodb.ReadConcernLevel;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.internal.OperationExecutor;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistry;
import org.finos.legend.depot.domain.notifications.MetadataNotificationResponse;
import org.finos.legend.depot.domain.notifications.MetadataNotificationStatus;
import org.finos.legend.depot.services.api.entities.ManageEntitiesService;
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

class AbstractEntityRefreshHandlerImplDiffblueTest {
  /**
   * Test {@link AbstractEntityRefreshHandlerImpl#getLOGGER()}.
   *
   * <ul>
   *   <li>Given {@link ReadConcern#ReadConcern(ReadConcernLevel)} with level is {@code LOCAL}.
   *   <li>Then return {@link Logger}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEntityRefreshHandlerImpl#getLOGGER()}
   */
  @Test
  @DisplayName(
      "Test getLOGGER(); given ReadConcern(ReadConcernLevel) with level is 'LOCAL'; then return Logger")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.slf4j.Logger AbstractEntityRefreshHandlerImpl.getLOGGER()"})
  void testGetLOGGER_givenReadConcernWithLevelIsLocal_thenReturnLogger() {
    // Arrange
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
    EntitiesHandlerImpl entitiesHandlerImpl =
        new EntitiesHandlerImpl(entitiesService, new EntityProvider());

    // Act
    org.slf4j.Logger actualLOGGER = entitiesHandlerImpl.getLOGGER();

    // Assert
    assertTrue(actualLOGGER instanceof Logger);
    assertEquals(
        "org.finos.legend.depot.services.artifacts.handlers.entities.AbstractEntityRefreshHandlerImpl",
        actualLOGGER.getName());
    assertNull(((Logger) actualLOGGER).getLevel());
    assertFalse(actualLOGGER.isDebugEnabled());
    assertFalse(actualLOGGER.isErrorEnabled());
    assertFalse(actualLOGGER.isInfoEnabled());
    assertFalse(actualLOGGER.isTraceEnabled());
    assertFalse(actualLOGGER.isWarnEnabled());
    assertTrue(((Logger) actualLOGGER).isAdditive());
  }

  /**
   * Test {@link AbstractEntityRefreshHandlerImpl#getEntitiesApi()}.
   *
   * <ul>
   *   <li>Then return {@link ManageVersionedEntitiesServiceImpl}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEntityRefreshHandlerImpl#getEntitiesApi()}
   */
  @Test
  @DisplayName("Test getEntitiesApi(); then return ManageVersionedEntitiesServiceImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ManageEntitiesService AbstractEntityRefreshHandlerImpl.getEntitiesApi()"})
  void testGetEntitiesApi_thenReturnManageVersionedEntitiesServiceImpl() {
    // Arrange
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
    EntitiesHandlerImpl entitiesHandlerImpl =
        new EntitiesHandlerImpl(entitiesService, new EntityProvider());

    // Act
    ManageEntitiesService actualEntitiesApi = entitiesHandlerImpl.getEntitiesApi();

    // Assert
    assertTrue(actualEntitiesApi instanceof ManageVersionedEntitiesServiceImpl);
    assertSame(entitiesService, actualEntitiesApi);
  }

  /**
   * Test {@link AbstractEntityRefreshHandlerImpl#deleteByVersion(String, String, String)}.
   *
   * <ul>
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEntityRefreshHandlerImpl#deleteByVersion(String, String,
   * String)}
   */
  @Test
  @DisplayName("Test deleteByVersion(String, String, String); then return one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "long AbstractEntityRefreshHandlerImpl.deleteByVersion(String, String, String)"
  })
  void testDeleteByVersion_thenReturnOne() {
    // Arrange
    ManageVersionedEntitiesServiceImpl entitiesService =
        mock(ManageVersionedEntitiesServiceImpl.class);
    when(entitiesService.delete(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(1L);
    EntitiesHandlerImpl entitiesHandlerImpl =
        new EntitiesHandlerImpl(entitiesService, new EntityProvider());

    // Act
    long actualDeleteByVersionResult = entitiesHandlerImpl.deleteByVersion("42", "42", "42");

    // Assert
    verify(entitiesService).delete("42", "42", "42");
    assertEquals(1L, actualDeleteByVersionResult);
  }

  /**
   * Test {@link AbstractEntityRefreshHandlerImpl#refreshVersionArtifacts(String, String, String,
   * List)}.
   *
   * <p>Method under test: {@link AbstractEntityRefreshHandlerImpl#refreshVersionArtifacts(String,
   * String, String, List)}
   */
  @Test
  @DisplayName("Test refreshVersionArtifacts(String, String, String, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse AbstractEntityRefreshHandlerImpl.refreshVersionArtifacts(String, String, String, List)"
  })
  void testRefreshVersionArtifacts() {
    // Arrange
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
    EntitiesHandlerImpl entitiesHandlerImpl =
        new EntitiesHandlerImpl(entitiesService, new EntityProvider());

    ArrayList<File> files = new ArrayList<>();
    files.add(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());

    // Act
    MetadataNotificationResponse actualRefreshVersionArtifactsResult =
        entitiesHandlerImpl.refreshVersionArtifacts("42", "42", "42", files);

    // Assert
    List<String> messages = actualRefreshVersionArtifactsResult.getMessages();
    assertEquals(1, messages.size());
    assertEquals("found 0 ENTITIES for [42-42-42] ", messages.get(0));
    assertEquals(
        MetadataNotificationStatus.SUCCESS, actualRefreshVersionArtifactsResult.getStatus());
    assertFalse(actualRefreshVersionArtifactsResult.hasErrors());
    assertTrue(actualRefreshVersionArtifactsResult.getErrors().isEmpty());
  }

  /**
   * Test {@link AbstractEntityRefreshHandlerImpl#refreshVersionArtifacts(String, String, String,
   * List)}.
   *
   * <p>Method under test: {@link AbstractEntityRefreshHandlerImpl#refreshVersionArtifacts(String,
   * String, String, List)}
   */
  @Test
  @DisplayName("Test refreshVersionArtifacts(String, String, String, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse AbstractEntityRefreshHandlerImpl.refreshVersionArtifacts(String, String, String, List)"
  })
  void testRefreshVersionArtifacts2() {
    // Arrange
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
    EntitiesHandlerImpl entitiesHandlerImpl =
        new EntitiesHandlerImpl(entitiesService, new EntityProvider());

    ArrayList<File> files = new ArrayList<>();
    files.add(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());
    files.add(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());

    // Act
    MetadataNotificationResponse actualRefreshVersionArtifactsResult =
        entitiesHandlerImpl.refreshVersionArtifacts("42", "42", "42", files);

    // Assert
    List<String> messages = actualRefreshVersionArtifactsResult.getMessages();
    assertEquals(1, messages.size());
    assertEquals("found 0 ENTITIES for [42-42-42] ", messages.get(0));
    assertEquals(
        MetadataNotificationStatus.SUCCESS, actualRefreshVersionArtifactsResult.getStatus());
    assertFalse(actualRefreshVersionArtifactsResult.hasErrors());
    assertTrue(actualRefreshVersionArtifactsResult.getErrors().isEmpty());
  }

  /**
   * Test {@link AbstractEntityRefreshHandlerImpl#refreshVersionArtifacts(String, String, String,
   * List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Messages size is one.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEntityRefreshHandlerImpl#refreshVersionArtifacts(String,
   * String, String, List)}
   */
  @Test
  @DisplayName(
      "Test refreshVersionArtifacts(String, String, String, List); when ArrayList(); then return Messages size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse AbstractEntityRefreshHandlerImpl.refreshVersionArtifacts(String, String, String, List)"
  })
  void testRefreshVersionArtifacts_whenArrayList_thenReturnMessagesSizeIsOne() {
    // Arrange
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
    EntitiesHandlerImpl entitiesHandlerImpl =
        new EntitiesHandlerImpl(entitiesService, new EntityProvider());

    // Act
    MetadataNotificationResponse actualRefreshVersionArtifactsResult =
        entitiesHandlerImpl.refreshVersionArtifacts("42", "42", "42", new ArrayList<>());

    // Assert
    List<String> messages = actualRefreshVersionArtifactsResult.getMessages();
    assertEquals(1, messages.size());
    assertEquals("found 0 ENTITIES for [42-42-42] ", messages.get(0));
    assertEquals(
        MetadataNotificationStatus.SUCCESS, actualRefreshVersionArtifactsResult.getStatus());
    assertFalse(actualRefreshVersionArtifactsResult.hasErrors());
    assertTrue(actualRefreshVersionArtifactsResult.getErrors().isEmpty());
  }
}
