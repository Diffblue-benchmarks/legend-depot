package org.finos.legend.depot.services.entities;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
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
import java.util.List;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistry;
import org.finos.legend.depot.domain.entity.DepotEntity;
import org.finos.legend.depot.domain.entity.DepotEntityOverview;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.domain.version.Scope;
import org.finos.legend.depot.services.api.projects.ProjectsService;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.mongo.entities.EntitiesMongo;
import org.finos.legend.depot.store.mongo.versionedEntities.VersionedEntitiesMongo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EntityClassifierServiceImplDiffblueTest {
  /**
   * Test {@link EntityClassifierServiceImpl#getEntitiesByClassifierPath(String, String, Integer, Scope, boolean)}.
   * <p>
   * Method under test: {@link EntityClassifierServiceImpl#getEntitiesByClassifierPath(String, String, Integer, Scope, boolean)}
   */
  @Test
  @DisplayName("Test getEntitiesByClassifierPath(String, String, Integer, Scope, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List EntityClassifierServiceImpl.getEntitiesByClassifierPath(String, String, Integer, Scope, boolean)"})
  void testGetEntitiesByClassifierPath() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData("myproject", "42", "42"));
    ProjectsService projects = mock(ProjectsService.class);
    when(projects.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

    // Act
    List<DepotEntity> actualEntitiesByClassifierPath = new EntityClassifierServiceImpl(projects,
        new VersionedEntitiesMongo(new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true,
            true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
            mock(OperationExecutor.class)))).getEntitiesByClassifierPath("Classifier Path", "Search", 1, Scope.RELEASES,
                true);

    // Assert
    verify(projects).getAllProjectCoordinates();
    assertTrue(actualEntitiesByClassifierPath.isEmpty());
  }

  /**
   * Test {@link EntityClassifierServiceImpl#getEntitiesByClassifierPath(String, String, Integer, Scope, boolean)}.
   * <p>
   * Method under test: {@link EntityClassifierServiceImpl#getEntitiesByClassifierPath(String, String, Integer, Scope, boolean)}
   */
  @Test
  @DisplayName("Test getEntitiesByClassifierPath(String, String, Integer, Scope, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List EntityClassifierServiceImpl.getEntitiesByClassifierPath(String, String, Integer, Scope, boolean)"})
  void testGetEntitiesByClassifierPath2() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData("myproject", "42", "42"));
    storeProjectDataList.add(new StoreProjectData("myproject", "42", "42"));
    ProjectsService projects = mock(ProjectsService.class);
    when(projects.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

    // Act
    List<DepotEntity> actualEntitiesByClassifierPath = new EntityClassifierServiceImpl(projects,
        new VersionedEntitiesMongo(new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true,
            true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
            mock(OperationExecutor.class)))).getEntitiesByClassifierPath("Classifier Path", "Search", 1, Scope.RELEASES,
                true);

    // Assert
    verify(projects).getAllProjectCoordinates();
    assertTrue(actualEntitiesByClassifierPath.isEmpty());
  }

  /**
   * Test {@link EntityClassifierServiceImpl#getEntitiesByClassifierPath(String, String, Integer, Scope, boolean)}.
   * <ul>
   *   <li>Then calls {@link EntitiesMongo#findLatestClassifierEntities(String, String, Integer)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntityClassifierServiceImpl#getEntitiesByClassifierPath(String, String, Integer, Scope, boolean)}
   */
  @Test
  @DisplayName("Test getEntitiesByClassifierPath(String, String, Integer, Scope, boolean); then calls findLatestClassifierEntities(String, String, Integer)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List EntityClassifierServiceImpl.getEntitiesByClassifierPath(String, String, Integer, Scope, boolean)"})
  void testGetEntitiesByClassifierPath_thenCallsFindLatestClassifierEntities() {
    // Arrange
    VersionedEntitiesMongo versions = mock(VersionedEntitiesMongo.class);
    when(versions.findLatestClassifierEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Integer>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<DepotEntity> actualEntitiesByClassifierPath = new EntityClassifierServiceImpl(mock(ProjectsService.class),
        versions).getEntitiesByClassifierPath("Classifier Path", "Search", 1, Scope.SNAPSHOT, true);

    // Assert
    verify(versions).findLatestClassifierEntities(eq("Classifier Path"), eq("Search"), eq(1));
    assertTrue(actualEntitiesByClassifierPath.isEmpty());
  }

  /**
   * Test {@link EntityClassifierServiceImpl#getEntitiesByClassifierPath(String, String, Integer, Scope, boolean)}.
   * <ul>
   *   <li>Then calls {@link ProjectsService#getAllProjectCoordinates()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntityClassifierServiceImpl#getEntitiesByClassifierPath(String, String, Integer, Scope, boolean)}
   */
  @Test
  @DisplayName("Test getEntitiesByClassifierPath(String, String, Integer, Scope, boolean); then calls getAllProjectCoordinates()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List EntityClassifierServiceImpl.getEntitiesByClassifierPath(String, String, Integer, Scope, boolean)"})
  void testGetEntitiesByClassifierPath_thenCallsGetAllProjectCoordinates() {
    // Arrange
    ProjectsService projects = mock(ProjectsService.class);
    when(projects.getAllProjectCoordinates()).thenReturn(new ArrayList<>());
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

    // Act
    List<DepotEntity> actualEntitiesByClassifierPath = new EntityClassifierServiceImpl(projects,
        new VersionedEntitiesMongo(new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true,
            true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
            mock(OperationExecutor.class)))).getEntitiesByClassifierPath("Classifier Path", "Search", 1, Scope.RELEASES,
                true);

    // Assert
    verify(projects).getAllProjectCoordinates();
    assertTrue(actualEntitiesByClassifierPath.isEmpty());
  }

  /**
   * Test {@link EntityClassifierServiceImpl#getEntitiesByClassifierPath(String, String, Integer, Scope, boolean)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then calls {@link ProjectsService#getAllProjectCoordinates()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntityClassifierServiceImpl#getEntitiesByClassifierPath(String, String, Integer, Scope, boolean)}
   */
  @Test
  @DisplayName("Test getEntitiesByClassifierPath(String, String, Integer, Scope, boolean); when 'null'; then calls getAllProjectCoordinates()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List EntityClassifierServiceImpl.getEntitiesByClassifierPath(String, String, Integer, Scope, boolean)"})
  void testGetEntitiesByClassifierPath_whenNull_thenCallsGetAllProjectCoordinates() {
    // Arrange
    ProjectsService projects = mock(ProjectsService.class);
    when(projects.getAllProjectCoordinates()).thenReturn(new ArrayList<>());
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);

    // Act
    List<DepotEntity> actualEntitiesByClassifierPath = new EntityClassifierServiceImpl(projects,
        new VersionedEntitiesMongo(new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true,
            true, new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED,
            mock(OperationExecutor.class)))).getEntitiesByClassifierPath("Classifier Path", "Search", null,
                Scope.RELEASES, true);

    // Assert
    verify(projects).getAllProjectCoordinates();
    assertTrue(actualEntitiesByClassifierPath.isEmpty());
  }

  /**
   * Test {@link EntityClassifierServiceImpl#findClassifierEntities(String, Scope)} with {@code classifier}, {@code scope}.
   * <p>
   * Method under test: {@link EntityClassifierServiceImpl#findClassifierEntities(String, Scope)}
   */
  @Test
  @DisplayName("Test findClassifierEntities(String, Scope) with 'classifier', 'scope'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntityClassifierServiceImpl.findClassifierEntities(String, Scope)"})
  void testFindClassifierEntitiesWithClassifierScope() {
    // Arrange
    VersionedEntitiesMongo versions = mock(VersionedEntitiesMongo.class);
    when(versions.findReleasedClassifierEntities(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<DepotEntity> actualFindClassifierEntitiesResult = new EntityClassifierServiceImpl(mock(ProjectsService.class),
        versions).findClassifierEntities("Classifier", Scope.RELEASES);

    // Assert
    verify(versions).findReleasedClassifierEntities(eq("Classifier"));
    assertTrue(actualFindClassifierEntitiesResult.isEmpty());
  }

  /**
   * Test {@link EntityClassifierServiceImpl#findClassifierEntities(String, Scope)} with {@code classifier}, {@code scope}.
   * <p>
   * Method under test: {@link EntityClassifierServiceImpl#findClassifierEntities(String, Scope)}
   */
  @Test
  @DisplayName("Test findClassifierEntities(String, Scope) with 'classifier', 'scope'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntityClassifierServiceImpl.findClassifierEntities(String, Scope)"})
  void testFindClassifierEntitiesWithClassifierScope2() {
    // Arrange
    VersionedEntitiesMongo versions = mock(VersionedEntitiesMongo.class);
    when(versions.findLatestClassifierEntities(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<DepotEntity> actualFindClassifierEntitiesResult = new EntityClassifierServiceImpl(mock(ProjectsService.class),
        versions).findClassifierEntities("Classifier", Scope.SNAPSHOT);

    // Assert
    verify(versions).findLatestClassifierEntities(eq("Classifier"));
    assertTrue(actualFindClassifierEntitiesResult.isEmpty());
  }

  /**
   * Test {@link EntityClassifierServiceImpl#findClassifierEntities(String, Scope, String, Integer)} with {@code classifier}, {@code scope}, {@code search}, {@code limit}.
   * <p>
   * Method under test: {@link EntityClassifierServiceImpl#findClassifierEntities(String, Scope, String, Integer)}
   */
  @Test
  @DisplayName("Test findClassifierEntities(String, Scope, String, Integer) with 'classifier', 'scope', 'search', 'limit'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntityClassifierServiceImpl.findClassifierEntities(String, Scope, String, Integer)"})
  void testFindClassifierEntitiesWithClassifierScopeSearchLimit() {
    // Arrange
    VersionedEntitiesMongo versions = mock(VersionedEntitiesMongo.class);
    when(versions.findReleasedClassifierEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Integer>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<DepotEntity> actualFindClassifierEntitiesResult = new EntityClassifierServiceImpl(mock(ProjectsService.class),
        versions).findClassifierEntities("Classifier", Scope.RELEASES, "Search", 1);

    // Assert
    verify(versions).findReleasedClassifierEntities(eq("Classifier"), eq("Search"), eq(1));
    assertTrue(actualFindClassifierEntitiesResult.isEmpty());
  }

  /**
   * Test {@link EntityClassifierServiceImpl#findClassifierEntities(String, Scope, String, Integer)} with {@code classifier}, {@code scope}, {@code search}, {@code limit}.
   * <p>
   * Method under test: {@link EntityClassifierServiceImpl#findClassifierEntities(String, Scope, String, Integer)}
   */
  @Test
  @DisplayName("Test findClassifierEntities(String, Scope, String, Integer) with 'classifier', 'scope', 'search', 'limit'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntityClassifierServiceImpl.findClassifierEntities(String, Scope, String, Integer)"})
  void testFindClassifierEntitiesWithClassifierScopeSearchLimit2() {
    // Arrange
    VersionedEntitiesMongo versions = mock(VersionedEntitiesMongo.class);
    when(versions.findLatestClassifierEntities(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Integer>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<DepotEntity> actualFindClassifierEntitiesResult = new EntityClassifierServiceImpl(mock(ProjectsService.class),
        versions).findClassifierEntities("Classifier", Scope.SNAPSHOT, "Search", 1);

    // Assert
    verify(versions).findLatestClassifierEntities(eq("Classifier"), eq("Search"), eq(1));
    assertTrue(actualFindClassifierEntitiesResult.isEmpty());
  }

  /**
   * Test {@link EntityClassifierServiceImpl#findClassifierEntitiesByVersions(String, List)} with {@code classifier}, {@code projectVersions}.
   * <p>
   * Method under test: {@link EntityClassifierServiceImpl#findClassifierEntitiesByVersions(String, List)}
   */
  @Test
  @DisplayName("Test findClassifierEntitiesByVersions(String, List) with 'classifier', 'projectVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntityClassifierServiceImpl.findClassifierEntitiesByVersions(String, List)"})
  void testFindClassifierEntitiesByVersionsWithClassifierProjectVersions() {
    // Arrange
    VersionedEntitiesMongo versions = mock(VersionedEntitiesMongo.class);
    when(versions.findClassifierEntitiesByVersions(Mockito.<String>any(), Mockito.<List<ProjectVersion>>any()))
        .thenReturn(new ArrayList<>());
    EntityClassifierServiceImpl entityClassifierServiceImpl = new EntityClassifierServiceImpl(
        mock(ProjectsService.class), versions);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act
    List<DepotEntity> actualFindClassifierEntitiesByVersionsResult = entityClassifierServiceImpl
        .findClassifierEntitiesByVersions("Classifier", projectVersions);

    // Assert
    verify(versions).findClassifierEntitiesByVersions(eq("Classifier"), isA(List.class));
    assertTrue(actualFindClassifierEntitiesByVersionsResult.isEmpty());
  }

  /**
   * Test {@link EntityClassifierServiceImpl#findClassifierEntitiesByVersions(String, List)} with {@code classifier}, {@code projectVersions}.
   * <p>
   * Method under test: {@link EntityClassifierServiceImpl#findClassifierEntitiesByVersions(String, List)}
   */
  @Test
  @DisplayName("Test findClassifierEntitiesByVersions(String, List) with 'classifier', 'projectVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntityClassifierServiceImpl.findClassifierEntitiesByVersions(String, List)"})
  void testFindClassifierEntitiesByVersionsWithClassifierProjectVersions2() {
    // Arrange
    VersionedEntitiesMongo versions = mock(VersionedEntitiesMongo.class);
    when(versions.findClassifierEntitiesByVersions(Mockito.<String>any(), Mockito.<List<ProjectVersion>>any()))
        .thenReturn(new ArrayList<>());
    EntityClassifierServiceImpl entityClassifierServiceImpl = new EntityClassifierServiceImpl(
        mock(ProjectsService.class), versions);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act
    List<DepotEntity> actualFindClassifierEntitiesByVersionsResult = entityClassifierServiceImpl
        .findClassifierEntitiesByVersions("Classifier", projectVersions);

    // Assert
    verify(versions).findClassifierEntitiesByVersions(eq("Classifier"), isA(List.class));
    assertTrue(actualFindClassifierEntitiesByVersionsResult.isEmpty());
  }

  /**
   * Test {@link EntityClassifierServiceImpl#findClassifierEntitiesByVersions(String, List, String, Integer)} with {@code classifier}, {@code projectVersions}, {@code search}, {@code limit}.
   * <p>
   * Method under test: {@link EntityClassifierServiceImpl#findClassifierEntitiesByVersions(String, List, String, Integer)}
   */
  @Test
  @DisplayName("Test findClassifierEntitiesByVersions(String, List, String, Integer) with 'classifier', 'projectVersions', 'search', 'limit'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List EntityClassifierServiceImpl.findClassifierEntitiesByVersions(String, List, String, Integer)"})
  void testFindClassifierEntitiesByVersionsWithClassifierProjectVersionsSearchLimit() {
    // Arrange
    VersionedEntitiesMongo versions = mock(VersionedEntitiesMongo.class);
    when(versions.findClassifierEntitiesByVersions(Mockito.<String>any(), Mockito.<List<ProjectVersion>>any(),
        Mockito.<String>any(), Mockito.<Integer>any())).thenReturn(new ArrayList<>());
    EntityClassifierServiceImpl entityClassifierServiceImpl = new EntityClassifierServiceImpl(
        mock(ProjectsService.class), versions);

    // Act
    List<DepotEntity> actualFindClassifierEntitiesByVersionsResult = entityClassifierServiceImpl
        .findClassifierEntitiesByVersions("Classifier", new ArrayList<>(), "Search", 1);

    // Assert
    verify(versions).findClassifierEntitiesByVersions(eq("Classifier"), isA(List.class), eq("Search"), eq(1));
    assertTrue(actualFindClassifierEntitiesByVersionsResult.isEmpty());
  }

  /**
   * Test {@link EntityClassifierServiceImpl#findClassifierEntitiesByVersions(String, List, String, Integer)} with {@code classifier}, {@code projectVersions}, {@code search}, {@code limit}.
   * <p>
   * Method under test: {@link EntityClassifierServiceImpl#findClassifierEntitiesByVersions(String, List, String, Integer)}
   */
  @Test
  @DisplayName("Test findClassifierEntitiesByVersions(String, List, String, Integer) with 'classifier', 'projectVersions', 'search', 'limit'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List EntityClassifierServiceImpl.findClassifierEntitiesByVersions(String, List, String, Integer)"})
  void testFindClassifierEntitiesByVersionsWithClassifierProjectVersionsSearchLimit2() {
    // Arrange
    VersionedEntitiesMongo versions = mock(VersionedEntitiesMongo.class);
    when(versions.findClassifierEntitiesByVersions(Mockito.<String>any(), Mockito.<List<ProjectVersion>>any(),
        Mockito.<String>any(), Mockito.<Integer>any())).thenReturn(new ArrayList<>());
    EntityClassifierServiceImpl entityClassifierServiceImpl = new EntityClassifierServiceImpl(
        mock(ProjectsService.class), versions);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act
    List<DepotEntity> actualFindClassifierEntitiesByVersionsResult = entityClassifierServiceImpl
        .findClassifierEntitiesByVersions("Classifier", projectVersions, "Search", 1);

    // Assert
    verify(versions).findClassifierEntitiesByVersions(eq("Classifier"), isA(List.class), eq("Search"), eq(1));
    assertTrue(actualFindClassifierEntitiesByVersionsResult.isEmpty());
  }

  /**
   * Test {@link EntityClassifierServiceImpl#findClassifierEntitiesByVersions(String, List, String, Integer)} with {@code classifier}, {@code projectVersions}, {@code search}, {@code limit}.
   * <p>
   * Method under test: {@link EntityClassifierServiceImpl#findClassifierEntitiesByVersions(String, List, String, Integer)}
   */
  @Test
  @DisplayName("Test findClassifierEntitiesByVersions(String, List, String, Integer) with 'classifier', 'projectVersions', 'search', 'limit'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List EntityClassifierServiceImpl.findClassifierEntitiesByVersions(String, List, String, Integer)"})
  void testFindClassifierEntitiesByVersionsWithClassifierProjectVersionsSearchLimit3() {
    // Arrange
    VersionedEntitiesMongo versions = mock(VersionedEntitiesMongo.class);
    when(versions.findClassifierEntitiesByVersions(Mockito.<String>any(), Mockito.<List<ProjectVersion>>any(),
        Mockito.<String>any(), Mockito.<Integer>any())).thenReturn(new ArrayList<>());
    EntityClassifierServiceImpl entityClassifierServiceImpl = new EntityClassifierServiceImpl(
        mock(ProjectsService.class), versions);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act
    List<DepotEntity> actualFindClassifierEntitiesByVersionsResult = entityClassifierServiceImpl
        .findClassifierEntitiesByVersions("Classifier", projectVersions, "Search", 1);

    // Assert
    verify(versions).findClassifierEntitiesByVersions(eq("Classifier"), isA(List.class), eq("Search"), eq(1));
    assertTrue(actualFindClassifierEntitiesByVersionsResult.isEmpty());
  }

  /**
   * Test {@link EntityClassifierServiceImpl#findClassifierEntitiesByVersions(String, List)} with {@code classifier}, {@code projectVersions}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntityClassifierServiceImpl#findClassifierEntitiesByVersions(String, List)}
   */
  @Test
  @DisplayName("Test findClassifierEntitiesByVersions(String, List) with 'classifier', 'projectVersions'; when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntityClassifierServiceImpl.findClassifierEntitiesByVersions(String, List)"})
  void testFindClassifierEntitiesByVersionsWithClassifierProjectVersions_whenArrayList() {
    // Arrange
    VersionedEntitiesMongo versions = mock(VersionedEntitiesMongo.class);
    when(versions.findClassifierEntitiesByVersions(Mockito.<String>any(), Mockito.<List<ProjectVersion>>any()))
        .thenReturn(new ArrayList<>());
    EntityClassifierServiceImpl entityClassifierServiceImpl = new EntityClassifierServiceImpl(
        mock(ProjectsService.class), versions);

    // Act
    List<DepotEntity> actualFindClassifierEntitiesByVersionsResult = entityClassifierServiceImpl
        .findClassifierEntitiesByVersions("Classifier", new ArrayList<>());

    // Assert
    verify(versions).findClassifierEntitiesByVersions(eq("Classifier"), isA(List.class));
    assertTrue(actualFindClassifierEntitiesByVersionsResult.isEmpty());
  }

  /**
   * Test {@link EntityClassifierServiceImpl#findClassifierSummaries(String, Scope)}.
   * <ul>
   *   <li>Then calls {@link EntitiesMongo#findLatestClassifierSummaries(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntityClassifierServiceImpl#findClassifierSummaries(String, Scope)}
   */
  @Test
  @DisplayName("Test findClassifierSummaries(String, Scope); then calls findLatestClassifierSummaries(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntityClassifierServiceImpl.findClassifierSummaries(String, Scope)"})
  void testFindClassifierSummaries_thenCallsFindLatestClassifierSummaries() {
    // Arrange
    VersionedEntitiesMongo versions = mock(VersionedEntitiesMongo.class);
    when(versions.findLatestClassifierSummaries(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<DepotEntityOverview> actualFindClassifierSummariesResult = new EntityClassifierServiceImpl(
        mock(ProjectsService.class), versions).findClassifierSummaries("Classifier", Scope.SNAPSHOT);

    // Assert
    verify(versions).findLatestClassifierSummaries(eq("Classifier"));
    assertTrue(actualFindClassifierSummariesResult.isEmpty());
  }

  /**
   * Test {@link EntityClassifierServiceImpl#findClassifierSummaries(String, Scope)}.
   * <ul>
   *   <li>Then calls {@link EntitiesMongo#findReleasedClassifierSummaries(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntityClassifierServiceImpl#findClassifierSummaries(String, Scope)}
   */
  @Test
  @DisplayName("Test findClassifierSummaries(String, Scope); then calls findReleasedClassifierSummaries(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntityClassifierServiceImpl.findClassifierSummaries(String, Scope)"})
  void testFindClassifierSummaries_thenCallsFindReleasedClassifierSummaries() {
    // Arrange
    VersionedEntitiesMongo versions = mock(VersionedEntitiesMongo.class);
    when(versions.findReleasedClassifierSummaries(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<DepotEntityOverview> actualFindClassifierSummariesResult = new EntityClassifierServiceImpl(
        mock(ProjectsService.class), versions).findClassifierSummaries("Classifier", Scope.RELEASES);

    // Assert
    verify(versions).findReleasedClassifierSummaries(eq("Classifier"));
    assertTrue(actualFindClassifierSummariesResult.isEmpty());
  }

  /**
   * Test {@link EntityClassifierServiceImpl#findClassifierSummariesByVersions(String, List)}.
   * <p>
   * Method under test: {@link EntityClassifierServiceImpl#findClassifierSummariesByVersions(String, List)}
   */
  @Test
  @DisplayName("Test findClassifierSummariesByVersions(String, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntityClassifierServiceImpl.findClassifierSummariesByVersions(String, List)"})
  void testFindClassifierSummariesByVersions() {
    // Arrange
    VersionedEntitiesMongo versions = mock(VersionedEntitiesMongo.class);
    when(versions.findClassifierSummariesByVersions(Mockito.<String>any(), Mockito.<List<ProjectVersion>>any()))
        .thenReturn(new ArrayList<>());
    EntityClassifierServiceImpl entityClassifierServiceImpl = new EntityClassifierServiceImpl(
        mock(ProjectsService.class), versions);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act
    List<DepotEntityOverview> actualFindClassifierSummariesByVersionsResult = entityClassifierServiceImpl
        .findClassifierSummariesByVersions("Classifier", projectVersions);

    // Assert
    verify(versions).findClassifierSummariesByVersions(eq("Classifier"), isA(List.class));
    assertTrue(actualFindClassifierSummariesByVersionsResult.isEmpty());
  }

  /**
   * Test {@link EntityClassifierServiceImpl#findClassifierSummariesByVersions(String, List)}.
   * <p>
   * Method under test: {@link EntityClassifierServiceImpl#findClassifierSummariesByVersions(String, List)}
   */
  @Test
  @DisplayName("Test findClassifierSummariesByVersions(String, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntityClassifierServiceImpl.findClassifierSummariesByVersions(String, List)"})
  void testFindClassifierSummariesByVersions2() {
    // Arrange
    VersionedEntitiesMongo versions = mock(VersionedEntitiesMongo.class);
    when(versions.findClassifierSummariesByVersions(Mockito.<String>any(), Mockito.<List<ProjectVersion>>any()))
        .thenReturn(new ArrayList<>());
    EntityClassifierServiceImpl entityClassifierServiceImpl = new EntityClassifierServiceImpl(
        mock(ProjectsService.class), versions);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act
    List<DepotEntityOverview> actualFindClassifierSummariesByVersionsResult = entityClassifierServiceImpl
        .findClassifierSummariesByVersions("Classifier", projectVersions);

    // Assert
    verify(versions).findClassifierSummariesByVersions(eq("Classifier"), isA(List.class));
    assertTrue(actualFindClassifierSummariesByVersionsResult.isEmpty());
  }

  /**
   * Test {@link EntityClassifierServiceImpl#findClassifierSummariesByVersions(String, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntityClassifierServiceImpl#findClassifierSummariesByVersions(String, List)}
   */
  @Test
  @DisplayName("Test findClassifierSummariesByVersions(String, List); when ArrayList(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List EntityClassifierServiceImpl.findClassifierSummariesByVersions(String, List)"})
  void testFindClassifierSummariesByVersions_whenArrayList_thenReturnEmpty() {
    // Arrange
    VersionedEntitiesMongo versions = mock(VersionedEntitiesMongo.class);
    when(versions.findClassifierSummariesByVersions(Mockito.<String>any(), Mockito.<List<ProjectVersion>>any()))
        .thenReturn(new ArrayList<>());
    EntityClassifierServiceImpl entityClassifierServiceImpl = new EntityClassifierServiceImpl(
        mock(ProjectsService.class), versions);

    // Act
    List<DepotEntityOverview> actualFindClassifierSummariesByVersionsResult = entityClassifierServiceImpl
        .findClassifierSummariesByVersions("Classifier", new ArrayList<>());

    // Assert
    verify(versions).findClassifierSummariesByVersions(eq("Classifier"), isA(List.class));
    assertTrue(actualFindClassifierSummariesByVersionsResult.isEmpty());
  }
}
