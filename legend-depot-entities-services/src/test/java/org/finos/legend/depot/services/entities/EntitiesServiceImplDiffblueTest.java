package org.finos.legend.depot.services.entities;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.mongodb.ReadConcern;
import com.mongodb.ReadConcernLevel;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.client.internal.MongoDatabaseImpl;
import com.mongodb.client.internal.OperationExecutor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistry;
import org.finos.legend.depot.domain.entity.ProjectVersionEntities;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.services.api.projects.ProjectsService;
import org.finos.legend.depot.store.model.entities.StoredEntity;
import org.finos.legend.depot.store.mongo.versionedEntities.VersionedEntitiesMongo;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EntitiesServiceImplDiffblueTest {
  /**
   * Test {@link EntitiesServiceImpl#getEntityFromDependencies(String, String, String, List, boolean)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntitiesServiceImpl#getEntityFromDependencies(String, String, String, List, boolean)}
   */
  @Test
  @DisplayName("Test getEntityFromDependencies(String, String, String, List, boolean); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesServiceImpl.getEntityFromDependencies(String, String, String, List, boolean)"})
  void testGetEntityFromDependencies_thenReturnEmpty() {
    // Arrange
    ProjectsService projects = mock(ProjectsService.class);
    when(projects.resolveAliasesAndCheckVersionExists(Mockito.<String>any(), Mockito.<String>any(),
        Mockito.<String>any())).thenReturn("1.0.2");
    when(projects.getDependencies(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), anyBoolean()))
        .thenReturn(new HashSet<>());
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesServiceImpl<StoredEntity> entitiesServiceImpl = new EntitiesServiceImpl<>(new VersionedEntitiesMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class))),
        projects);

    // Act
    List<Entity> actualEntityFromDependencies = entitiesServiceImpl.getEntityFromDependencies("42", "42", "42",
        new ArrayList<>(), false);

    // Assert
    verify(projects).getDependencies(eq("42"), eq("42"), eq("1.0.2"), eq(true));
    verify(projects).resolveAliasesAndCheckVersionExists(eq("42"), eq("42"), eq("42"));
    assertTrue(actualEntityFromDependencies.isEmpty());
  }

  /**
   * Test {@link EntitiesServiceImpl#getDependenciesEntities(List, String, boolean, boolean)} with {@code projectDependencies}, {@code classifier}, {@code transitive}, {@code includeOrigin}.
   * <p>
   * Method under test: {@link EntitiesServiceImpl#getDependenciesEntities(List, String, boolean, boolean)}
   */
  @Test
  @DisplayName("Test getDependenciesEntities(List, String, boolean, boolean) with 'projectDependencies', 'classifier', 'transitive', 'includeOrigin'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesServiceImpl.getDependenciesEntities(List, String, boolean, boolean)"})
  void testGetDependenciesEntitiesWithProjectDependenciesClassifierTransitiveIncludeOrigin() {
    // Arrange
    ProjectsService projects = mock(ProjectsService.class);
    when(projects.getDependencies(Mockito.<List<ProjectVersion>>any(), anyBoolean())).thenReturn(new HashSet<>());
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesServiceImpl<StoredEntity> entitiesServiceImpl = new EntitiesServiceImpl<>(new VersionedEntitiesMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class))),
        projects);

    // Act
    List<ProjectVersionEntities> actualDependenciesEntities = entitiesServiceImpl
        .getDependenciesEntities(new ArrayList<>(), "Classifier", true, true);

    // Assert
    verify(projects).getDependencies(isA(List.class), eq(true));
    assertTrue(actualDependenciesEntities.isEmpty());
  }

  /**
   * Test {@link EntitiesServiceImpl#getDependenciesEntities(List, boolean, boolean)} with {@code projectDependencies}, {@code transitive}, {@code includeOrigin}.
   * <p>
   * Method under test: {@link EntitiesServiceImpl#getDependenciesEntities(List, boolean, boolean)}
   */
  @Test
  @DisplayName("Test getDependenciesEntities(List, boolean, boolean) with 'projectDependencies', 'transitive', 'includeOrigin'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesServiceImpl.getDependenciesEntities(List, boolean, boolean)"})
  void testGetDependenciesEntitiesWithProjectDependenciesTransitiveIncludeOrigin() {
    // Arrange
    ProjectsService projects = mock(ProjectsService.class);
    when(projects.getDependencies(Mockito.<List<ProjectVersion>>any(), anyBoolean())).thenReturn(new HashSet<>());
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesServiceImpl<StoredEntity> entitiesServiceImpl = new EntitiesServiceImpl<>(new VersionedEntitiesMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class))),
        projects);

    // Act
    List<ProjectVersionEntities> actualDependenciesEntities = entitiesServiceImpl
        .getDependenciesEntities(new ArrayList<>(), true, true);

    // Assert
    verify(projects).getDependencies(isA(List.class), eq(true));
    assertTrue(actualDependenciesEntities.isEmpty());
  }

  /**
   * Test {@link EntitiesServiceImpl#getDependenciesEntitiesByClassifier(List, String, boolean, boolean)} with {@code projectDependencies}, {@code classifier}, {@code transitive}, {@code includeOrigin}.
   * <p>
   * Method under test: {@link EntitiesServiceImpl#getDependenciesEntitiesByClassifier(List, String, boolean, boolean)}
   */
  @Test
  @DisplayName("Test getDependenciesEntitiesByClassifier(List, String, boolean, boolean) with 'projectDependencies', 'classifier', 'transitive', 'includeOrigin'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntitiesServiceImpl.getDependenciesEntitiesByClassifier(List, String, boolean, boolean)"})
  void testGetDependenciesEntitiesByClassifierWithProjectDependenciesClassifierTransitiveIncludeOrigin() {
    // Arrange
    ProjectsService projects = mock(ProjectsService.class);
    when(projects.getDependencies(Mockito.<List<ProjectVersion>>any(), anyBoolean())).thenReturn(new HashSet<>());
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    EntitiesServiceImpl<StoredEntity> entitiesServiceImpl = new EntitiesServiceImpl<>(new VersionedEntitiesMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class))),
        projects);

    // Act
    List<ProjectVersionEntities> actualDependenciesEntitiesByClassifier = entitiesServiceImpl
        .getDependenciesEntitiesByClassifier(new ArrayList<>(), "Classifier", true, true);

    // Assert
    verify(projects).getDependencies(isA(List.class), eq(true));
    assertTrue(actualDependenciesEntitiesByClassifier.isEmpty());
  }
}
