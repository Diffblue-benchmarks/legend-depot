package org.finos.legend.depot.services.projects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
import java.util.Optional;
import java.util.Set;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistry;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.list.MutableList;
import org.finos.legend.depot.domain.VersionedData;
import org.finos.legend.depot.domain.notifications.MetadataNotification;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.domain.project.ProjectVersionData;
import org.finos.legend.depot.domain.project.dependencies.ProjectDependencyGraph;
import org.finos.legend.depot.domain.project.dependencies.ProjectDependencyReport;
import org.finos.legend.depot.domain.project.dependencies.ProjectDependencyReport.SerializedGraph;
import org.finos.legend.depot.domain.project.dependencies.ProjectDependencyVersionNode;
import org.finos.legend.depot.domain.project.dependencies.ProjectDependencyWithPlatformVersions;
import org.finos.legend.depot.domain.project.dependencies.VersionDependencyReport;
import org.finos.legend.depot.services.api.dependencies.DependencyOverride;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.services.api.metrics.query.VoidQueryMetricsRegistry;
import org.finos.legend.depot.services.api.notifications.queue.Queue;
import org.finos.legend.depot.services.api.notifications.queue.VoidQueue;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.services.dependencies.ProjectDependencyGraphWalkerContext;
import org.finos.legend.depot.store.api.projects.Projects;
import org.finos.legend.depot.store.api.projects.ProjectsVersions;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.finos.legend.depot.store.mongo.projects.ProjectsMongo;
import org.finos.legend.depot.store.mongo.projects.ProjectsVersionsMongo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectsServiceImplDiffblueTest {
  @Mock
  private DependencyOverride dependencyOverride;

  @Mock
  private Projects projects;

  @Mock
  private ProjectsConfiguration projectsConfiguration;

  @InjectMocks
  private ProjectsServiceImpl projectsServiceImpl;

  @Mock
  private ProjectsVersions projectsVersions;

  @Mock
  private QueryMetricsRegistry queryMetricsRegistry;

  @Mock
  private Queue queue;

  /**
   * Test {@link ProjectsServiceImpl#getAllProjectCoordinates()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getAllProjectCoordinates()}
   */
  @Test
  @DisplayName("Test getAllProjectCoordinates(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getAllProjectCoordinates()"})
  void testGetAllProjectCoordinates_thenReturnEmpty() {
    // Arrange
    ProjectsMongo projects = mock(ProjectsMongo.class);
    when(projects.getAll()).thenReturn(new ArrayList<>());
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ProjectsVersionsMongo projectsVersions = new ProjectsVersionsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();

    // Act
    List<StoreProjectData> actualAllProjectCoordinates = new ProjectsServiceImpl(projectsVersions, projects,
        metricsRegistry, queue, new ProjectsConfiguration("janedoe/featurebranch")).getAllProjectCoordinates();

    // Assert
    verify(projects).getAll();
    assertTrue(actualAllProjectCoordinates.isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#getVersions(String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code includeSnapshots}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getVersions(String, String, boolean)}
   */
  @Test
  @DisplayName("Test getVersions(String, String, boolean) with 'groupId', 'artifactId', 'includeSnapshots'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getVersions(String, String, boolean)"})
  void testGetVersionsWithGroupIdArtifactIdIncludeSnapshots() {
    // Arrange
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new IllegalArgumentException("foo"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.getVersions("42", "42", true));
    verify(projectsVersions).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getVersions(String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code includeSnapshots}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getVersions(String, String, boolean)}
   */
  @Test
  @DisplayName("Test getVersions(String, String, boolean) with 'groupId', 'artifactId', 'includeSnapshots'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getVersions(String, String, boolean)"})
  void testGetVersionsWithGroupIdArtifactIdIncludeSnapshots2() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    when(storeProjectVersionData.getVersionData())
        .thenReturn(new ProjectVersionData(dependencies, new ArrayList<>(), true, true));

    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    storeProjectVersionDataList.add(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(storeProjectVersionDataList);

    // Act
    List<String> actualVersions = projectsServiceImpl.getVersions("42", "42", true);

    // Assert
    verify(projectsVersions).find(eq("42"), eq("42"));
    verify(storeProjectVersionData).getVersionData();
    assertTrue(actualVersions.isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#getVersions(String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code includeSnapshots}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getVersions(String, String, boolean)}
   */
  @Test
  @DisplayName("Test getVersions(String, String, boolean) with 'groupId', 'artifactId', 'includeSnapshots'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getVersions(String, String, boolean)"})
  void testGetVersionsWithGroupIdArtifactIdIncludeSnapshots3() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionId()).thenThrow(new IllegalArgumentException("foo"));

    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    storeProjectVersionDataList.add(new StoreProjectVersionData("42", "42", "42"));
    storeProjectVersionDataList.add(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(storeProjectVersionDataList);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.getVersions("42", "42", false));
    verify(storeProjectVersionData).getVersionId();
    verify(projectsVersions).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getVersions(String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code includeSnapshots}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getVersions(String, String, boolean)}
   */
  @Test
  @DisplayName("Test getVersions(String, String, boolean) with 'groupId', 'artifactId', 'includeSnapshots'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getVersions(String, String, boolean)"})
  void testGetVersionsWithGroupIdArtifactIdIncludeSnapshots4() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionId()).thenThrow(new IllegalArgumentException("foo"));

    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    storeProjectVersionDataList.add(new StoreProjectVersionData("42", "42", "-SNAPSHOT"));
    storeProjectVersionDataList.add(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(storeProjectVersionDataList);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.getVersions("42", "42", false));
    verify(storeProjectVersionData).getVersionId();
    verify(projectsVersions).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getVersions(String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code includeSnapshots}.
   * <ul>
   *   <li>Then calls {@link StoreProjectVersionData#getVersionData()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getVersions(String, String, boolean)}
   */
  @Test
  @DisplayName("Test getVersions(String, String, boolean) with 'groupId', 'artifactId', 'includeSnapshots'; then calls getVersionData()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getVersions(String, String, boolean)"})
  void testGetVersionsWithGroupIdArtifactIdIncludeSnapshots_thenCallsGetVersionData() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionId()).thenThrow(new IllegalArgumentException("foo"));
    when(storeProjectVersionData.getVersionData()).thenReturn(new ProjectVersionData());

    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    storeProjectVersionDataList.add(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(storeProjectVersionDataList);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.getVersions("42", "42", true));
    verify(storeProjectVersionData).getVersionId();
    verify(projectsVersions).find(eq("42"), eq("42"));
    verify(storeProjectVersionData).getVersionData();
  }

  /**
   * Test {@link ProjectsServiceImpl#getVersions(String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code includeSnapshots}.
   * <ul>
   *   <li>Then calls {@link VersionedData#getVersionId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getVersions(String, String, boolean)}
   */
  @Test
  @DisplayName("Test getVersions(String, String, boolean) with 'groupId', 'artifactId', 'includeSnapshots'; then calls getVersionId()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getVersions(String, String, boolean)"})
  void testGetVersionsWithGroupIdArtifactIdIncludeSnapshots_thenCallsGetVersionId() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionId()).thenThrow(new IllegalArgumentException("foo"));

    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    storeProjectVersionDataList.add(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(storeProjectVersionDataList);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.getVersions("42", "42", false));
    verify(storeProjectVersionData).getVersionId();
    verify(projectsVersions).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getVersions(String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code includeSnapshots}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getVersions(String, String, boolean)}
   */
  @Test
  @DisplayName("Test getVersions(String, String, boolean) with 'groupId', 'artifactId', 'includeSnapshots'; then return size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getVersions(String, String, boolean)"})
  void testGetVersionsWithGroupIdArtifactIdIncludeSnapshots_thenReturnSizeIsOne() {
    // Arrange
    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    storeProjectVersionDataList.add(new StoreProjectVersionData());
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(storeProjectVersionDataList);

    // Act
    List<String> actualVersions = projectsServiceImpl.getVersions("42", "42", true);

    // Assert
    verify(projectsVersions).find(eq("42"), eq("42"));
    assertEquals(1, actualVersions.size());
    assertNull(actualVersions.get(0));
  }

  /**
   * Test {@link ProjectsServiceImpl#getVersions(String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code includeSnapshots}.
   * <ul>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getVersions(String, String, boolean)}
   */
  @Test
  @DisplayName("Test getVersions(String, String, boolean) with 'groupId', 'artifactId', 'includeSnapshots'; then return size is two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getVersions(String, String, boolean)"})
  void testGetVersionsWithGroupIdArtifactIdIncludeSnapshots_thenReturnSizeIsTwo() {
    // Arrange
    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    storeProjectVersionDataList.add(new StoreProjectVersionData());
    storeProjectVersionDataList.add(new StoreProjectVersionData());
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(storeProjectVersionDataList);

    // Act
    List<String> actualVersions = projectsServiceImpl.getVersions("42", "42", true);

    // Assert
    verify(projectsVersions).find(eq("42"), eq("42"));
    assertEquals(2, actualVersions.size());
    assertNull(actualVersions.get(0));
    assertNull(actualVersions.get(1));
  }

  /**
   * Test {@link ProjectsServiceImpl#getVersions(String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code includeSnapshots}.
   * <ul>
   *   <li>When {@code true}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getVersions(String, String, boolean)}
   */
  @Test
  @DisplayName("Test getVersions(String, String, boolean) with 'groupId', 'artifactId', 'includeSnapshots'; when 'true'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getVersions(String, String, boolean)"})
  void testGetVersionsWithGroupIdArtifactIdIncludeSnapshots_whenTrue_thenReturnEmpty() {
    // Arrange
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<String> actualVersions = projectsServiceImpl.getVersions("42", "42", true);

    // Assert
    verify(projectsVersions).find(eq("42"), eq("42"));
    assertTrue(actualVersions.isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#findByProjectId(String)}.
   * <ul>
   *   <li>Given {@link Projects} {@link Projects#findByProjectId(String)} return {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#findByProjectId(String)}
   */
  @Test
  @DisplayName("Test findByProjectId(String); given Projects findByProjectId(String) return ArrayList(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.findByProjectId(String)"})
  void testFindByProjectId_givenProjectsFindByProjectIdReturnArrayList_thenReturnEmpty() {
    // Arrange
    when(projects.findByProjectId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<StoreProjectData> actualFindByProjectIdResult = projectsServiceImpl.findByProjectId("myproject");

    // Assert
    verify(projects).findByProjectId(eq("myproject"));
    assertTrue(actualFindByProjectIdResult.isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#findByProjectId(String)}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#findByProjectId(String)}
   */
  @Test
  @DisplayName("Test findByProjectId(String); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.findByProjectId(String)"})
  void testFindByProjectId_thenThrowIllegalArgumentException() {
    // Arrange
    when(projects.findByProjectId(Mockito.<String>any())).thenThrow(new IllegalArgumentException("foo"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.findByProjectId("myproject"));
    verify(projects).findByProjectId(eq("myproject"));
  }

  /**
   * Test {@link ProjectsServiceImpl#findByUpdatedDate(long, long)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#findByUpdatedDate(long, long)}
   */
  @Test
  @DisplayName("Test findByUpdatedDate(long, long); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.findByUpdatedDate(long, long)"})
  void testFindByUpdatedDate_thenReturnEmpty() {
    // Arrange
    ProjectsVersionsMongo projectsVersions = mock(ProjectsVersionsMongo.class);
    when(projectsVersions.findByUpdatedDate(anyLong(), anyLong())).thenReturn(new ArrayList<>());
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ProjectsMongo projects = new ProjectsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();

    // Act
    List<StoreProjectVersionData> actualFindByUpdatedDateResult = new ProjectsServiceImpl(projectsVersions, projects,
        metricsRegistry, queue, new ProjectsConfiguration("janedoe/featurebranch")).findByUpdatedDate(1L, 1L);

    // Assert
    verify(projectsVersions).findByUpdatedDate(eq(1L), eq(1L));
    assertTrue(actualFindByUpdatedDateResult.isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#find(String, String, String)} with {@code groupId}, {@code artifactId}, {@code versionId}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#find(String, String, String)}
   */
  @Test
  @DisplayName("Test find(String, String, String) with 'groupId', 'artifactId', 'versionId'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ProjectsServiceImpl.find(String, String, String)"})
  void testFindWithGroupIdArtifactIdVersionId() {
    // Arrange
    Optional<StoreProjectData> ofResult = Optional.of(new StoreProjectData("myproject", "42", "42"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);

    // Act
    Optional<StoreProjectVersionData> actualFindResult = projectsServiceImpl.find("42", "42", "latest");

    // Assert
    verify(projects).find(eq("42"), eq("42"));
    assertFalse(actualFindResult.isPresent());
  }

  /**
   * Test {@link ProjectsServiceImpl#find(String, String, String)} with {@code groupId}, {@code artifactId}, {@code versionId}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#find(String, String, String)}
   */
  @Test
  @DisplayName("Test find(String, String, String) with 'groupId', 'artifactId', 'versionId'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ProjectsServiceImpl.find(String, String, String)"})
  void testFindWithGroupIdArtifactIdVersionId2() {
    // Arrange
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional
        .of(new StoreProjectData("myproject", "42", "42", "janedoe/featurebranch", "1.0.2"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);

    // Act
    Optional<StoreProjectVersionData> actualFindResult = projectsServiceImpl.find("42", "42", "latest");

    // Assert
    verify(projects).find(eq("42"), eq("42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("1.0.2"));
    assertSame(ofResult, actualFindResult);
  }

  /**
   * Test {@link ProjectsServiceImpl#find(String, String, String)} with {@code groupId}, {@code artifactId}, {@code versionId}.
   * <ul>
   *   <li>Given {@link Projects} {@link Projects#find(String, String)} return empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#find(String, String, String)}
   */
  @Test
  @DisplayName("Test find(String, String, String) with 'groupId', 'artifactId', 'versionId'; given Projects find(String, String) return empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ProjectsServiceImpl.find(String, String, String)"})
  void testFindWithGroupIdArtifactIdVersionId_givenProjectsFindReturnEmpty() {
    // Arrange
    Optional<StoreProjectData> emptyResult = Optional.empty();
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(emptyResult);

    // Act
    Optional<StoreProjectVersionData> actualFindResult = projectsServiceImpl.find("42", "42", "latest");

    // Assert
    verify(projects).find(eq("42"), eq("42"));
    assertFalse(actualFindResult.isPresent());
  }

  /**
   * Test {@link ProjectsServiceImpl#find(String, String, String)} with {@code groupId}, {@code artifactId}, {@code versionId}.
   * <ul>
   *   <li>Then return of {@link StoreProjectVersionData#StoreProjectVersionData()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#find(String, String, String)}
   */
  @Test
  @DisplayName("Test find(String, String, String) with 'groupId', 'artifactId', 'versionId'; then return of StoreProjectVersionData()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ProjectsServiceImpl.find(String, String, String)"})
  void testFindWithGroupIdArtifactIdVersionId_thenReturnOfStoreProjectVersionData() {
    // Arrange
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);

    // Act
    Optional<StoreProjectVersionData> actualFindResult = projectsServiceImpl.find("42", "42", "42");

    // Assert
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
    assertSame(ofResult, actualFindResult);
  }

  /**
   * Test {@link ProjectsServiceImpl#find(String, String, String)} with {@code groupId}, {@code artifactId}, {@code versionId}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#find(String, String, String)}
   */
  @Test
  @DisplayName("Test find(String, String, String) with 'groupId', 'artifactId', 'versionId'; then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ProjectsServiceImpl.find(String, String, String)"})
  void testFindWithGroupIdArtifactIdVersionId_thenThrowIllegalArgumentException() {
    // Arrange
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.getLatestVersion()).thenThrow(new IllegalArgumentException("foo"));
    Optional<StoreProjectData> ofResult = Optional.of(storeProjectData);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.find("42", "42", "latest"));
    verify(projects).find(eq("42"), eq("42"));
    verify(storeProjectData).getLatestVersion();
  }

  /**
   * Test {@link ProjectsServiceImpl#find(String, String)} with {@code groupId}, {@code artifactId}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#find(String, String)}
   */
  @Test
  @DisplayName("Test find(String, String) with 'groupId', 'artifactId'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.find(String, String)"})
  void testFindWithGroupIdArtifactId_thenReturnEmpty() {
    // Arrange
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<StoreProjectVersionData> actualFindResult = projectsServiceImpl.find("42", "42");

    // Assert
    verify(projectsVersions).find(eq("42"), eq("42"));
    assertTrue(actualFindResult.isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#find(String, String)} with {@code groupId}, {@code artifactId}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#find(String, String)}
   */
  @Test
  @DisplayName("Test find(String, String) with 'groupId', 'artifactId'; then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.find(String, String)"})
  void testFindWithGroupIdArtifactId_thenThrowIllegalArgumentException() {
    // Arrange
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new IllegalArgumentException("foo"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.find("42", "42"));
    verify(projectsVersions).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}
   */
  @Test
  @DisplayName("Test resolveAliasesAndCheckVersionExists(String, String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProjectsServiceImpl.resolveAliasesAndCheckVersionExists(String, String, String)"})
  void testResolveAliasesAndCheckVersionExists() {
    // Arrange
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    doThrow(new IllegalArgumentException("latest")).when(queryMetricsRegistry)
        .record(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.resolveAliasesAndCheckVersionExists("42", "42", "42"));
    verify(queryMetricsRegistry).record(eq("42"), eq("42"), isNull());
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}
   */
  @Test
  @DisplayName("Test resolveAliasesAndCheckVersionExists(String, String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProjectsServiceImpl.resolveAliasesAndCheckVersionExists(String, String, String)"})
  void testResolveAliasesAndCheckVersionExists2() {
    // Arrange
    Optional<StoreProjectVersionData> ofResult = Optional
        .of(new StoreProjectVersionData("42", "42", "42", true, new ProjectVersionData()));
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional.of(new StoreProjectData("myproject", "42", "42"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);
    when(queue.push(Mockito.<MetadataNotification>any())).thenThrow(new IllegalArgumentException("latest"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.resolveAliasesAndCheckVersionExists("42", "42", "42"));
    verify(queue).push(isA(MetadataNotification.class));
    verify(projects).find(eq("42"), eq("42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}
   */
  @Test
  @DisplayName("Test resolveAliasesAndCheckVersionExists(String, String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProjectsServiceImpl.resolveAliasesAndCheckVersionExists(String, String, String)"})
  void testResolveAliasesAndCheckVersionExists3() {
    // Arrange
    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData("42", "42", "42", true,
        new ProjectVersionData(dependencies, new ArrayList<>(), true, true)));
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.resolveAliasesAndCheckVersionExists("42", "42", "42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}
   */
  @Test
  @DisplayName("Test resolveAliasesAndCheckVersionExists(String, String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProjectsServiceImpl.resolveAliasesAndCheckVersionExists(String, String, String)"})
  void testResolveAliasesAndCheckVersionExists4() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionId()).thenThrow(new IllegalStateException("foo"));
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional
        .of(new StoreProjectData("myproject", "42", "42", "janedoe/featurebranch", "1.0.2"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> projectsServiceImpl.resolveAliasesAndCheckVersionExists("42", "42", "head"));
    verify(storeProjectVersionData).getVersionId();
    verify(projects).find(eq("42"), eq("42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("janedoe/featurebranch-SNAPSHOT"));
  }

  /**
   * Test {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}
   */
  @Test
  @DisplayName("Test resolveAliasesAndCheckVersionExists(String, String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProjectsServiceImpl.resolveAliasesAndCheckVersionExists(String, String, String)"})
  void testResolveAliasesAndCheckVersionExists5() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionId()).thenThrow(new IllegalStateException("foo"));
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.getLatestVersion()).thenReturn("1.0.2");
    Optional<StoreProjectData> ofResult2 = Optional.of(storeProjectData);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> projectsServiceImpl.resolveAliasesAndCheckVersionExists("42", "42", "latest"));
    verify(storeProjectVersionData).getVersionId();
    verify(projects).find(eq("42"), eq("42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("1.0.2"));
    verify(storeProjectData, atLeast(1)).getLatestVersion();
  }

  /**
   * Test {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}
   */
  @Test
  @DisplayName("Test resolveAliasesAndCheckVersionExists(String, String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProjectsServiceImpl.resolveAliasesAndCheckVersionExists(String, String, String)"})
  void testResolveAliasesAndCheckVersionExists6() {
    // Arrange
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.getLatestVersion()).thenThrow(new IllegalArgumentException("foo"));
    Optional<StoreProjectData> ofResult = Optional.of(storeProjectData);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.resolveAliasesAndCheckVersionExists("42", "42", "latest"));
    verify(projects).find(eq("42"), eq("42"));
    verify(storeProjectData).getLatestVersion();
  }

  /**
   * Test {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}.
   * <ul>
   *   <li>Given {@link Projects} {@link Projects#find(String, String)} return empty.</li>
   *   <li>When {@code head}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}
   */
  @Test
  @DisplayName("Test resolveAliasesAndCheckVersionExists(String, String, String); given Projects find(String, String) return empty; when 'head'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProjectsServiceImpl.resolveAliasesAndCheckVersionExists(String, String, String)"})
  void testResolveAliasesAndCheckVersionExists_givenProjectsFindReturnEmpty_whenHead() {
    // Arrange
    Optional<StoreProjectData> emptyResult = Optional.empty();
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(emptyResult);

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.resolveAliasesAndCheckVersionExists("42", "42", "head"));
    verify(projects).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}.
   * <ul>
   *   <li>Given {@link Projects} {@link Projects#find(String, String)} return empty.</li>
   *   <li>When {@code latest}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}
   */
  @Test
  @DisplayName("Test resolveAliasesAndCheckVersionExists(String, String, String); given Projects find(String, String) return empty; when 'latest'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProjectsServiceImpl.resolveAliasesAndCheckVersionExists(String, String, String)"})
  void testResolveAliasesAndCheckVersionExists_givenProjectsFindReturnEmpty_whenLatest() {
    // Arrange
    Optional<StoreProjectData> emptyResult = Optional.empty();
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(emptyResult);

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.resolveAliasesAndCheckVersionExists("42", "42", "latest"));
    verify(projects).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}.
   * <ul>
   *   <li>Given {@link ProjectsVersions} {@link ProjectsVersions#find(String, String, String)} return empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}
   */
  @Test
  @DisplayName("Test resolveAliasesAndCheckVersionExists(String, String, String); given ProjectsVersions find(String, String, String) return empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProjectsServiceImpl.resolveAliasesAndCheckVersionExists(String, String, String)"})
  void testResolveAliasesAndCheckVersionExists_givenProjectsVersionsFindReturnEmpty() {
    // Arrange
    Optional<StoreProjectVersionData> emptyResult = Optional.empty();
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(emptyResult);

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.resolveAliasesAndCheckVersionExists("42", "42", "42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}.
   * <ul>
   *   <li>Then calls {@link ProjectsConfiguration#getDefaultBranch()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}
   */
  @Test
  @DisplayName("Test resolveAliasesAndCheckVersionExists(String, String, String); then calls getDefaultBranch()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProjectsServiceImpl.resolveAliasesAndCheckVersionExists(String, String, String)"})
  void testResolveAliasesAndCheckVersionExists_thenCallsGetDefaultBranch() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionId()).thenThrow(new IllegalStateException("foo"));
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional.of(new StoreProjectData("myproject", "42", "42"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);
    when(projectsConfiguration.getDefaultBranch()).thenReturn("janedoe/featurebranch");

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> projectsServiceImpl.resolveAliasesAndCheckVersionExists("42", "42", "head"));
    verify(storeProjectVersionData).getVersionId();
    verify(projectsConfiguration).getDefaultBranch();
    verify(projects).find(eq("42"), eq("42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("janedoe/featurebranch-SNAPSHOT"));
  }

  /**
   * Test {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}.
   * <ul>
   *   <li>Then calls {@link StoreProjectData#getDefaultBranch()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}
   */
  @Test
  @DisplayName("Test resolveAliasesAndCheckVersionExists(String, String, String); then calls getDefaultBranch()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProjectsServiceImpl.resolveAliasesAndCheckVersionExists(String, String, String)"})
  void testResolveAliasesAndCheckVersionExists_thenCallsGetDefaultBranch2() {
    // Arrange
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.getDefaultBranch()).thenThrow(new IllegalArgumentException("foo"));
    Optional<StoreProjectData> ofResult = Optional.of(storeProjectData);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.resolveAliasesAndCheckVersionExists("42", "42", "head"));
    verify(projects).find(eq("42"), eq("42"));
    verify(storeProjectData).getDefaultBranch();
  }

  /**
   * Test {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}.
   * <ul>
   *   <li>Then calls {@link VersionedData#getVersionId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}
   */
  @Test
  @DisplayName("Test resolveAliasesAndCheckVersionExists(String, String, String); then calls getVersionId()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProjectsServiceImpl.resolveAliasesAndCheckVersionExists(String, String, String)"})
  void testResolveAliasesAndCheckVersionExists_thenCallsGetVersionId() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionId()).thenThrow(new IllegalStateException("foo"));
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> projectsServiceImpl.resolveAliasesAndCheckVersionExists("42", "42", "42"));
    verify(storeProjectVersionData).getVersionId();
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}.
   * <ul>
   *   <li>Then calls {@link Queue#push(MetadataNotification)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}
   */
  @Test
  @DisplayName("Test resolveAliasesAndCheckVersionExists(String, String, String); then calls push(MetadataNotification)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProjectsServiceImpl.resolveAliasesAndCheckVersionExists(String, String, String)"})
  void testResolveAliasesAndCheckVersionExists_thenCallsPush() {
    // Arrange
    Optional<StoreProjectVersionData> ofResult = Optional
        .of(new StoreProjectVersionData("42", "42", "42", true, new ProjectVersionData()));
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional.of(new StoreProjectData("myproject", "42", "42"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);
    when(queue.push(Mockito.<MetadataNotification>any())).thenReturn("Push");

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> projectsServiceImpl.resolveAliasesAndCheckVersionExists("42", "42", "42"));
    verify(queue).push(isA(MetadataNotification.class));
    verify(projects).find(eq("42"), eq("42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}
   */
  @Test
  @DisplayName("Test resolveAliasesAndCheckVersionExists(String, String, String); then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProjectsServiceImpl.resolveAliasesAndCheckVersionExists(String, String, String)"})
  void testResolveAliasesAndCheckVersionExists_thenReturnNull() {
    // Arrange
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    doNothing().when(queryMetricsRegistry).record(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    String actualResolveAliasesAndCheckVersionExistsResult = projectsServiceImpl
        .resolveAliasesAndCheckVersionExists("42", "42", "42");

    // Assert
    verify(queryMetricsRegistry).record(eq("42"), eq("42"), isNull());
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
    assertNull(actualResolveAliasesAndCheckVersionExistsResult);
  }

  /**
   * Test {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}.
   * <ul>
   *   <li>When {@code latest}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#resolveAliasesAndCheckVersionExists(String, String, String)}
   */
  @Test
  @DisplayName("Test resolveAliasesAndCheckVersionExists(String, String, String); when 'latest'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProjectsServiceImpl.resolveAliasesAndCheckVersionExists(String, String, String)"})
  void testResolveAliasesAndCheckVersionExists_whenLatest() {
    // Arrange
    Optional<StoreProjectData> ofResult = Optional.of(new StoreProjectData("myproject", "42", "42"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.resolveAliasesAndCheckVersionExists("42", "42", "latest"));
    verify(projects).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#findVersion(Boolean)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#findVersion(Boolean)}
   */
  @Test
  @DisplayName("Test findVersion(Boolean); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.findVersion(Boolean)"})
  void testFindVersion_thenReturnEmpty() {
    // Arrange
    ProjectsVersionsMongo projectsVersions = mock(ProjectsVersionsMongo.class);
    when(projectsVersions.findVersion(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ProjectsMongo projects = new ProjectsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();

    // Act
    List<StoreProjectVersionData> actualFindVersionResult = new ProjectsServiceImpl(projectsVersions, projects,
        metricsRegistry, queue, new ProjectsConfiguration("janedoe/featurebranch")).findVersion(true);

    // Assert
    verify(projectsVersions).findVersion(eq(true));
    assertTrue(actualFindVersionResult.isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#findSnapshotVersions(String, String)}.
   * <ul>
   *   <li>Then return {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#findSnapshotVersions(String, String)}
   */
  @Test
  @DisplayName("Test findSnapshotVersions(String, String); then return ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.findSnapshotVersions(String, String)"})
  void testFindSnapshotVersions_thenReturnArrayList() {
    // Arrange
    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    storeProjectVersionDataList.add(new StoreProjectVersionData("42", "42", "-SNAPSHOT"));
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(storeProjectVersionDataList);

    // Act
    List<StoreProjectVersionData> actualFindSnapshotVersionsResult = projectsServiceImpl.findSnapshotVersions("42",
        "42");

    // Assert
    verify(projectsVersions).find(eq("42"), eq("42"));
    assertEquals(storeProjectVersionDataList, actualFindSnapshotVersionsResult);
  }

  /**
   * Test {@link ProjectsServiceImpl#findSnapshotVersions(String, String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#findSnapshotVersions(String, String)}
   */
  @Test
  @DisplayName("Test findSnapshotVersions(String, String); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.findSnapshotVersions(String, String)"})
  void testFindSnapshotVersions_thenReturnEmpty() {
    // Arrange
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<StoreProjectVersionData> actualFindSnapshotVersionsResult = projectsServiceImpl.findSnapshotVersions("42",
        "42");

    // Assert
    verify(projectsVersions).find(eq("42"), eq("42"));
    assertTrue(actualFindSnapshotVersionsResult.isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#findSnapshotVersions(String, String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#findSnapshotVersions(String, String)}
   */
  @Test
  @DisplayName("Test findSnapshotVersions(String, String); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.findSnapshotVersions(String, String)"})
  void testFindSnapshotVersions_thenReturnEmpty2() {
    // Arrange
    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    storeProjectVersionDataList.add(new StoreProjectVersionData("42", "42", "42"));
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(storeProjectVersionDataList);

    // Act
    List<StoreProjectVersionData> actualFindSnapshotVersionsResult = projectsServiceImpl.findSnapshotVersions("42",
        "42");

    // Assert
    verify(projectsVersions).find(eq("42"), eq("42"));
    assertTrue(actualFindSnapshotVersionsResult.isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#findSnapshotVersions(String, String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#findSnapshotVersions(String, String)}
   */
  @Test
  @DisplayName("Test findSnapshotVersions(String, String); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.findSnapshotVersions(String, String)"})
  void testFindSnapshotVersions_thenReturnEmpty3() {
    // Arrange
    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    storeProjectVersionDataList.add(new StoreProjectVersionData("42", "42", "42"));
    storeProjectVersionDataList.add(new StoreProjectVersionData("42", "42", "42"));
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(storeProjectVersionDataList);

    // Act
    List<StoreProjectVersionData> actualFindSnapshotVersionsResult = projectsServiceImpl.findSnapshotVersions("42",
        "42");

    // Assert
    verify(projectsVersions).find(eq("42"), eq("42"));
    assertTrue(actualFindSnapshotVersionsResult.isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#findSnapshotVersions(String, String)}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#findSnapshotVersions(String, String)}
   */
  @Test
  @DisplayName("Test findSnapshotVersions(String, String); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.findSnapshotVersions(String, String)"})
  void testFindSnapshotVersions_thenThrowIllegalArgumentException() {
    // Arrange
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new IllegalArgumentException("foo"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.findSnapshotVersions("42", "42"));
    verify(projectsVersions).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#findCoordinates(String, String)}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#findCoordinates(String, String)}
   */
  @Test
  @DisplayName("Test findCoordinates(String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ProjectsServiceImpl.findCoordinates(String, String)"})
  void testFindCoordinates() {
    // Arrange
    Optional<StoreProjectData> ofResult = Optional.of(new StoreProjectData("myproject", "42", "42"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);

    // Act
    Optional<StoreProjectData> actualFindCoordinatesResult = projectsServiceImpl.findCoordinates("42", "42");

    // Assert
    verify(projects).find(eq("42"), eq("42"));
    assertSame(ofResult, actualFindCoordinatesResult);
  }

  /**
   * Test {@link ProjectsServiceImpl#findCoordinates(String, String)}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#findCoordinates(String, String)}
   */
  @Test
  @DisplayName("Test findCoordinates(String, String); then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ProjectsServiceImpl.findCoordinates(String, String)"})
  void testFindCoordinates_thenThrowIllegalArgumentException() {
    // Arrange
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenThrow(new IllegalArgumentException("foo"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.findCoordinates("42", "42"));
    verify(projects).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#checkExists(String, String)}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#checkExists(String, String)}
   */
  @Test
  @DisplayName("Test checkExists(String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectsServiceImpl.checkExists(String, String)"})
  void testCheckExists() throws IllegalArgumentException {
    // Arrange
    Optional<StoreProjectData> ofResult = Optional.of(new StoreProjectData("myproject", "42", "42"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);

    // Act
    projectsServiceImpl.checkExists("42", "42");

    // Assert
    verify(projects).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#checkExists(String, String)}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#checkExists(String, String)}
   */
  @Test
  @DisplayName("Test checkExists(String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectsServiceImpl.checkExists(String, String)"})
  void testCheckExists2() throws IllegalArgumentException {
    // Arrange
    when(projects.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new IllegalArgumentException("No project found for %s-%s"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.checkExists("42", "42"));
    verify(projects).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#checkExists(String, String)}.
   * <ul>
   *   <li>Given {@link Projects} {@link Projects#find(String, String)} return empty.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#checkExists(String, String)}
   */
  @Test
  @DisplayName("Test checkExists(String, String); given Projects find(String, String) return empty; then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectsServiceImpl.checkExists(String, String)"})
  void testCheckExists_givenProjectsFindReturnEmpty_thenThrowIllegalArgumentException()
      throws IllegalArgumentException {
    // Arrange
    Optional<StoreProjectData> emptyResult = Optional.empty();
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(emptyResult);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.checkExists("42", "42"));
    verify(projects).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependencies(List, boolean)} with {@code projectVersions}, {@code transitive}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependencies(List, boolean)}
   */
  @Test
  @DisplayName("Test getDependencies(List, boolean) with 'projectVersions', 'transitive'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set ProjectsServiceImpl.getDependencies(List, boolean)"})
  void testGetDependenciesWithProjectVersionsTransitive() {
    // Arrange
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    doNothing().when(queryMetricsRegistry).record(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    when(dependencyOverride.overrideWith(Mockito.<List<ProjectVersion>>any(), Mockito.<List<ProjectVersion>>any(),
        Mockito.<Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>>>any()))
            .thenThrow(new IllegalArgumentException("latest"));

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.getDependencies(projectVersions, true));
    verify(dependencyOverride).overrideWith(isA(List.class), isA(List.class), isA(Function2.class));
    verify(queryMetricsRegistry).record(eq("42"), eq("42"), isNull());
    verify(projectsVersions, atLeast(1)).find(eq("42"), eq("42"), Mockito.<String>any());
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependencies(List, boolean)} with {@code projectVersions}, {@code transitive}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependencies(List, boolean)}
   */
  @Test
  @DisplayName("Test getDependencies(List, boolean) with 'projectVersions', 'transitive'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set ProjectsServiceImpl.getDependencies(List, boolean)"})
  void testGetDependenciesWithProjectVersionsTransitive2() {
    // Arrange
    Optional<StoreProjectVersionData> ofResult = Optional
        .of(new StoreProjectVersionData("42", "42", "42", true, new ProjectVersionData()));
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional.of(new StoreProjectData("myproject", "42", "42"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);
    when(queue.push(Mockito.<MetadataNotification>any())).thenThrow(new IllegalArgumentException("latest"));

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.getDependencies(projectVersions, true));
    verify(queue).push(isA(MetadataNotification.class));
    verify(projects).find(eq("42"), eq("42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependencies(List, boolean)} with {@code projectVersions}, {@code transitive}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependencies(List, boolean)}
   */
  @Test
  @DisplayName("Test getDependencies(List, boolean) with 'projectVersions', 'transitive'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set ProjectsServiceImpl.getDependencies(List, boolean)"})
  void testGetDependenciesWithProjectVersionsTransitive3() {
    // Arrange
    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData("42", "42", "42", true,
        new ProjectVersionData(dependencies, new ArrayList<>(), true, true)));
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.getDependencies(projectVersions, true));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependencies(List, boolean)} with {@code projectVersions}, {@code transitive}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependencies(List, boolean)}
   */
  @Test
  @DisplayName("Test getDependencies(List, boolean) with 'projectVersions', 'transitive'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set ProjectsServiceImpl.getDependencies(List, boolean)"})
  void testGetDependenciesWithProjectVersionsTransitive4() {
    // Arrange
    Optional<StoreProjectVersionData> emptyResult = Optional.empty();
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(emptyResult);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.getDependencies(projectVersions, true));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependencies(List, boolean)} with {@code projectVersions}, {@code transitive}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependencies(List, boolean)}
   */
  @Test
  @DisplayName("Test getDependencies(List, boolean) with 'projectVersions', 'transitive'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set ProjectsServiceImpl.getDependencies(List, boolean)"})
  void testGetDependenciesWithProjectVersionsTransitive5() {
    // Arrange
    Optional<StoreProjectData> ofResult = Optional.of(new StoreProjectData("myproject", "42", "42"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "latest"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.getDependencies(projectVersions, true));
    verify(projects).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependencies(List, boolean)} with {@code projectVersions}, {@code transitive}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependencies(List, boolean)}
   */
  @Test
  @DisplayName("Test getDependencies(List, boolean) with 'projectVersions', 'transitive'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set ProjectsServiceImpl.getDependencies(List, boolean)"})
  void testGetDependenciesWithProjectVersionsTransitive6() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionId()).thenThrow(new IllegalStateException("foo"));
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional
        .of(new StoreProjectData("myproject", "42", "42", "janedoe/featurebranch", "1.0.2"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "head"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> projectsServiceImpl.getDependencies(projectVersions, true));
    verify(storeProjectVersionData).getVersionId();
    verify(projects).find(eq("42"), eq("42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("janedoe/featurebranch-SNAPSHOT"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependencies(List, boolean)} with {@code projectVersions}, {@code transitive}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependencies(List, boolean)}
   */
  @Test
  @DisplayName("Test getDependencies(List, boolean) with 'projectVersions', 'transitive'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set ProjectsServiceImpl.getDependencies(List, boolean)"})
  void testGetDependenciesWithProjectVersionsTransitive7() {
    // Arrange
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.getLatestVersion()).thenThrow(new IllegalArgumentException("foo"));
    Optional<StoreProjectData> ofResult = Optional.of(storeProjectData);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "latest"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.getDependencies(projectVersions, true));
    verify(projects).find(eq("42"), eq("42"));
    verify(storeProjectData).getLatestVersion();
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependencies(List, boolean)} with {@code projectVersions}, {@code transitive}.
   * <ul>
   *   <li>Given {@link Projects} {@link Projects#find(String, String)} return empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependencies(List, boolean)}
   */
  @Test
  @DisplayName("Test getDependencies(List, boolean) with 'projectVersions', 'transitive'; given Projects find(String, String) return empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set ProjectsServiceImpl.getDependencies(List, boolean)"})
  void testGetDependenciesWithProjectVersionsTransitive_givenProjectsFindReturnEmpty() {
    // Arrange
    Optional<StoreProjectData> emptyResult = Optional.empty();
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(emptyResult);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "head"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.getDependencies(projectVersions, true));
    verify(projects).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependencies(List, boolean)} with {@code projectVersions}, {@code transitive}.
   * <ul>
   *   <li>Given {@link Projects} {@link Projects#find(String, String)} return empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependencies(List, boolean)}
   */
  @Test
  @DisplayName("Test getDependencies(List, boolean) with 'projectVersions', 'transitive'; given Projects find(String, String) return empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set ProjectsServiceImpl.getDependencies(List, boolean)"})
  void testGetDependenciesWithProjectVersionsTransitive_givenProjectsFindReturnEmpty2() {
    // Arrange
    Optional<StoreProjectData> emptyResult = Optional.empty();
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(emptyResult);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "latest"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.getDependencies(projectVersions, true));
    verify(projects).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependencies(List, boolean)} with {@code projectVersions}, {@code transitive}.
   * <ul>
   *   <li>Then calls {@link ProjectsConfiguration#getDefaultBranch()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependencies(List, boolean)}
   */
  @Test
  @DisplayName("Test getDependencies(List, boolean) with 'projectVersions', 'transitive'; then calls getDefaultBranch()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set ProjectsServiceImpl.getDependencies(List, boolean)"})
  void testGetDependenciesWithProjectVersionsTransitive_thenCallsGetDefaultBranch() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionId()).thenThrow(new IllegalStateException("foo"));
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional.of(new StoreProjectData("myproject", "42", "42"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);
    when(projectsConfiguration.getDefaultBranch()).thenReturn("janedoe/featurebranch");

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "head"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> projectsServiceImpl.getDependencies(projectVersions, true));
    verify(storeProjectVersionData).getVersionId();
    verify(projectsConfiguration).getDefaultBranch();
    verify(projects).find(eq("42"), eq("42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("janedoe/featurebranch-SNAPSHOT"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependencies(List, boolean)} with {@code projectVersions}, {@code transitive}.
   * <ul>
   *   <li>Then calls {@link StoreProjectData#getDefaultBranch()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependencies(List, boolean)}
   */
  @Test
  @DisplayName("Test getDependencies(List, boolean) with 'projectVersions', 'transitive'; then calls getDefaultBranch()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set ProjectsServiceImpl.getDependencies(List, boolean)"})
  void testGetDependenciesWithProjectVersionsTransitive_thenCallsGetDefaultBranch2() {
    // Arrange
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.getDefaultBranch()).thenThrow(new IllegalArgumentException("foo"));
    Optional<StoreProjectData> ofResult = Optional.of(storeProjectData);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "head"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.getDependencies(projectVersions, true));
    verify(projects).find(eq("42"), eq("42"));
    verify(storeProjectData).getDefaultBranch();
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependencies(List, boolean)} with {@code projectVersions}, {@code transitive}.
   * <ul>
   *   <li>Then calls {@link StoreProjectData#getLatestVersion()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependencies(List, boolean)}
   */
  @Test
  @DisplayName("Test getDependencies(List, boolean) with 'projectVersions', 'transitive'; then calls getLatestVersion()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set ProjectsServiceImpl.getDependencies(List, boolean)"})
  void testGetDependenciesWithProjectVersionsTransitive_thenCallsGetLatestVersion() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionId()).thenThrow(new IllegalStateException("foo"));
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.getLatestVersion()).thenReturn("1.0.2");
    Optional<StoreProjectData> ofResult2 = Optional.of(storeProjectData);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "latest"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> projectsServiceImpl.getDependencies(projectVersions, true));
    verify(storeProjectVersionData).getVersionId();
    verify(projects).find(eq("42"), eq("42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("1.0.2"));
    verify(storeProjectData, atLeast(1)).getLatestVersion();
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependencies(List, boolean)} with {@code projectVersions}, {@code transitive}.
   * <ul>
   *   <li>Then calls {@link VersionedData#getVersionId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependencies(List, boolean)}
   */
  @Test
  @DisplayName("Test getDependencies(List, boolean) with 'projectVersions', 'transitive'; then calls getVersionId()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set ProjectsServiceImpl.getDependencies(List, boolean)"})
  void testGetDependenciesWithProjectVersionsTransitive_thenCallsGetVersionId() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionId()).thenThrow(new IllegalStateException("foo"));
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> projectsServiceImpl.getDependencies(projectVersions, true));
    verify(storeProjectVersionData).getVersionId();
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependencies(List, boolean)} with {@code projectVersions}, {@code transitive}.
   * <ul>
   *   <li>Then calls {@link VersionedData#getVersionId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependencies(List, boolean)}
   */
  @Test
  @DisplayName("Test getDependencies(List, boolean) with 'projectVersions', 'transitive'; then calls getVersionId()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set ProjectsServiceImpl.getDependencies(List, boolean)"})
  void testGetDependenciesWithProjectVersionsTransitive_thenCallsGetVersionId2() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionId()).thenThrow(new IllegalStateException("foo"));
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> projectsServiceImpl.getDependencies(projectVersions, true));
    verify(storeProjectVersionData).getVersionId();
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependencies(List, boolean)} with {@code projectVersions}, {@code transitive}.
   * <ul>
   *   <li>Then calls {@link Queue#push(MetadataNotification)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependencies(List, boolean)}
   */
  @Test
  @DisplayName("Test getDependencies(List, boolean) with 'projectVersions', 'transitive'; then calls push(MetadataNotification)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set ProjectsServiceImpl.getDependencies(List, boolean)"})
  void testGetDependenciesWithProjectVersionsTransitive_thenCallsPush() {
    // Arrange
    Optional<StoreProjectVersionData> ofResult = Optional
        .of(new StoreProjectVersionData("42", "42", "42", true, new ProjectVersionData()));
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional.of(new StoreProjectData("myproject", "42", "42"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);
    when(queue.push(Mockito.<MetadataNotification>any())).thenReturn("Push");

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> projectsServiceImpl.getDependencies(projectVersions, true));
    verify(queue).push(isA(MetadataNotification.class));
    verify(projects).find(eq("42"), eq("42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependencies(List, boolean)} with {@code projectVersions}, {@code transitive}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependencies(List, boolean)}
   */
  @Test
  @DisplayName("Test getDependencies(List, boolean) with 'projectVersions', 'transitive'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set ProjectsServiceImpl.getDependencies(List, boolean)"})
  void testGetDependenciesWithProjectVersionsTransitive_thenReturnEmpty() {
    // Arrange
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    doNothing().when(queryMetricsRegistry).record(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    when(dependencyOverride.overrideWith(Mockito.<List<ProjectVersion>>any(), Mockito.<List<ProjectVersion>>any(),
        Mockito.<Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>>>any())).thenReturn(new ArrayList<>());

    ArrayList<ProjectVersion> projectVersions = new ArrayList<>();
    projectVersions.add(new ProjectVersion("42", "42", "42"));

    // Act
    Set<ProjectVersion> actualDependencies = projectsServiceImpl.getDependencies(projectVersions, true);

    // Assert
    verify(dependencyOverride).overrideWith(isA(List.class), isA(List.class), isA(Function2.class));
    verify(queryMetricsRegistry).record(eq("42"), eq("42"), isNull());
    verify(projectsVersions, atLeast(1)).find(eq("42"), eq("42"), Mockito.<String>any());
    assertTrue(actualDependencies.isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependencies(List, boolean)} with {@code projectVersions}, {@code transitive}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependencies(List, boolean)}
   */
  @Test
  @DisplayName("Test getDependencies(List, boolean) with 'projectVersions', 'transitive'; when ArrayList(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set ProjectsServiceImpl.getDependencies(List, boolean)"})
  void testGetDependenciesWithProjectVersionsTransitive_whenArrayList_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(projectsServiceImpl.getDependencies(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)}
   */
  @Test
  @DisplayName("Test buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void ProjectsServiceImpl.buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)"})
  void testBuildDependencyGraph() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    when(storeProjectVersionData.getVersionData())
        .thenReturn(new ProjectVersionData(dependencies, new ArrayList<>(), true, true));
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    ProjectDependencyGraph graph = new ProjectDependencyGraph();
    ProjectVersion parent = new ProjectVersion("42", "42", "42");

    ArrayList<ProjectVersion> children = new ArrayList<>();
    children.add(new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.buildDependencyGraph(graph, parent, children,
        new ProjectDependencyGraphWalkerContext()));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
    verify(storeProjectVersionData).getVersionData();
  }

  /**
   * Test {@link ProjectsServiceImpl#buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)}.
   * <ul>
   *   <li>Given {@code false}.</li>
   *   <li>Then calls {@link ProjectDependencyGraph#addNode(ProjectVersion, ProjectVersion)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)}
   */
  @Test
  @DisplayName("Test buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext); given 'false'; then calls addNode(ProjectVersion, ProjectVersion)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void ProjectsServiceImpl.buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)"})
  void testBuildDependencyGraph_givenFalse_thenCallsAddNode() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionData()).thenReturn(new ProjectVersionData());
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    ProjectDependencyGraph graph = mock(ProjectDependencyGraph.class);
    when(graph.hasNode(Mockito.<ProjectVersion>any())).thenReturn(false);
    doNothing().when(graph).addNode(Mockito.<ProjectVersion>any(), Mockito.<ProjectVersion>any());
    ProjectVersion parent = new ProjectVersion("42", "42", "42");

    ArrayList<ProjectVersion> children = new ArrayList<>();
    children.add(new ProjectVersion("42", "42", "42"));
    ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();

    // Act
    projectsServiceImpl.buildDependencyGraph(graph, parent, children, context);

    // Assert
    verify(graph).addNode(isA(ProjectVersion.class), isA(ProjectVersion.class));
    verify(graph).hasNode(isA(ProjectVersion.class));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
    verify(storeProjectVersionData, atLeast(1)).getVersionData();
    MutableList<List<ProjectVersion>> toListResult = context.getProjectVersionToDependencyMap().toList();
    assertEquals(1, toListResult.size());
    assertTrue(toListResult.get(0).isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)}.
   * <ul>
   *   <li>Given {@link ProjectsVersions} {@link ProjectsVersions#find(String, String, String)} return empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)}
   */
  @Test
  @DisplayName("Test buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext); given ProjectsVersions find(String, String, String) return empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void ProjectsServiceImpl.buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)"})
  void testBuildDependencyGraph_givenProjectsVersionsFindReturnEmpty() {
    // Arrange
    Optional<StoreProjectVersionData> emptyResult = Optional.empty();
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(emptyResult);
    ProjectDependencyGraph graph = new ProjectDependencyGraph();
    ProjectVersion parent = new ProjectVersion("42", "42", "42");

    ArrayList<ProjectVersion> children = new ArrayList<>();
    children.add(new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> projectsServiceImpl.buildDependencyGraph(graph, parent, children,
        new ProjectDependencyGraphWalkerContext()));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>When {@link ProjectDependencyGraph} {@link ProjectDependencyGraph#hasNode(ProjectVersion)} return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)}
   */
  @Test
  @DisplayName("Test buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext); given 'true'; when ProjectDependencyGraph hasNode(ProjectVersion) return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void ProjectsServiceImpl.buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)"})
  void testBuildDependencyGraph_givenTrue_whenProjectDependencyGraphHasNodeReturnTrue() {
    // Arrange
    ProjectDependencyGraph graph = mock(ProjectDependencyGraph.class);
    when(graph.hasNode(Mockito.<ProjectVersion>any())).thenReturn(true);
    ProjectVersion parent = new ProjectVersion("42", "42", "42");

    ArrayList<ProjectVersion> children = new ArrayList<>();
    children.add(new ProjectVersion("42", "42", "42"));
    ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();

    // Act
    projectsServiceImpl.buildDependencyGraph(graph, parent, children, context);

    // Assert that nothing has changed
    verify(graph).hasNode(isA(ProjectVersion.class));
    assertTrue(context.getProjectVersionToDependencyMap().toList().isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>When {@link ProjectDependencyGraph} {@link ProjectDependencyGraph#hasNode(ProjectVersion)} return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)}
   */
  @Test
  @DisplayName("Test buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext); given 'true'; when ProjectDependencyGraph hasNode(ProjectVersion) return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void ProjectsServiceImpl.buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)"})
  void testBuildDependencyGraph_givenTrue_whenProjectDependencyGraphHasNodeReturnTrue2() {
    // Arrange
    ProjectDependencyGraph graph = mock(ProjectDependencyGraph.class);
    when(graph.hasNode(Mockito.<ProjectVersion>any())).thenReturn(true);
    ProjectVersion parent = new ProjectVersion("42", "42", "42");

    ArrayList<ProjectVersion> children = new ArrayList<>();
    children.add(new ProjectVersion("42", "42", "42"));
    children.add(new ProjectVersion("42", "42", "42"));
    ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();

    // Act
    projectsServiceImpl.buildDependencyGraph(graph, parent, children, context);

    // Assert that nothing has changed
    verify(graph, atLeast(1)).hasNode(isA(ProjectVersion.class));
    assertTrue(context.getProjectVersionToDependencyMap().toList().isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)}.
   * <ul>
   *   <li>Then {@link ProjectDependencyGraph} (default constructor) BackEdges toList size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)}
   */
  @Test
  @DisplayName("Test buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext); then ProjectDependencyGraph (default constructor) BackEdges toList size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void ProjectsServiceImpl.buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)"})
  void testBuildDependencyGraph_thenProjectDependencyGraphBackEdgesToListSizeIsOne() {
    // Arrange
    ProjectVersionData projectVersionData = new ProjectVersionData();
    projectVersionData.addDependency(new ProjectVersion("42", "42", "42"));
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionData()).thenReturn(projectVersionData);
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    ProjectDependencyGraph graph = new ProjectDependencyGraph();
    ProjectVersion parent = new ProjectVersion("42", "42", "42");

    ArrayList<ProjectVersion> children = new ArrayList<>();
    children.add(new ProjectVersion("42", "42", "42"));
    ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();

    // Act
    projectsServiceImpl.buildDependencyGraph(graph, parent, children, context);

    // Assert
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
    verify(storeProjectVersionData, atLeast(1)).getVersionData();
    assertEquals(1, graph.getBackEdges().toList().size());
    MutableList<List<ProjectVersion>> toListResult = context.getProjectVersionToDependencyMap().toList();
    assertEquals(1, toListResult.size());
    assertEquals(children, toListResult.get(0));
  }

  /**
   * Test {@link ProjectsServiceImpl#buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)}.
   * <ul>
   *   <li>Then {@link ProjectDependencyGraph} (default constructor) Nodes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)}
   */
  @Test
  @DisplayName("Test buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext); then ProjectDependencyGraph (default constructor) Nodes size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void ProjectsServiceImpl.buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)"})
  void testBuildDependencyGraph_thenProjectDependencyGraphNodesSizeIsOne() {
    // Arrange
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    ProjectDependencyGraph graph = new ProjectDependencyGraph();
    ProjectVersion parent = new ProjectVersion("42", "42", "42");

    ArrayList<ProjectVersion> children = new ArrayList<>();
    children.add(new ProjectVersion("42", "42", "42"));
    ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();

    // Act
    projectsServiceImpl.buildDependencyGraph(graph, parent, children, context);

    // Assert
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
    MutableList<List<ProjectVersion>> toListResult = context.getProjectVersionToDependencyMap().toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, graph.getNodes().size());
    assertTrue(toListResult.get(0).isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link ProjectDependencyGraph} (default constructor) Nodes Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)}
   */
  @Test
  @DisplayName("Test buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext); when ArrayList(); then ProjectDependencyGraph (default constructor) Nodes Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void ProjectsServiceImpl.buildDependencyGraph(ProjectDependencyGraph, ProjectVersion, List, ProjectDependencyGraphWalkerContext)"})
  void testBuildDependencyGraph_whenArrayList_thenProjectDependencyGraphNodesEmpty() {
    // Arrange
    ProjectDependencyGraph graph = new ProjectDependencyGraph();
    ProjectVersion parent = new ProjectVersion("42", "42", "42");

    ArrayList<ProjectVersion> children = new ArrayList<>();
    ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();

    // Act
    projectsServiceImpl.buildDependencyGraph(graph, parent, children, context);

    // Assert that nothing has changed
    assertTrue(context.getProjectVersionToDependencyMap().toList().isEmpty());
    assertTrue(graph.getNodes().isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#getProjectDependencyReport(List)} with {@code projectDependencyVersions}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getProjectDependencyReport(List)}
   */
  @Test
  @DisplayName("Test getProjectDependencyReport(List) with 'projectDependencyVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProjectDependencyReport ProjectsServiceImpl.getProjectDependencyReport(List)"})
  void testGetProjectDependencyReportWithProjectDependencyVersions() {
    // Arrange and Act
    ProjectDependencyReport actualProjectDependencyReport = projectsServiceImpl
        .getProjectDependencyReport(new ArrayList<>());

    // Assert
    SerializedGraph graph = actualProjectDependencyReport.getGraph();
    assertTrue(graph.getNodes().toList().isEmpty());
    assertTrue(actualProjectDependencyReport.getConflicts().isEmpty());
    assertTrue(graph.getRootNodes().isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#getProjectDependencyReport(List)} with {@code projectDependencyVersions}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getProjectDependencyReport(List)}
   */
  @Test
  @DisplayName("Test getProjectDependencyReport(List) with 'projectDependencyVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProjectDependencyReport ProjectsServiceImpl.getProjectDependencyReport(List)"})
  void testGetProjectDependencyReportWithProjectDependencyVersions2() {
    // Arrange
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional.of(new StoreProjectData("myproject", "42", "42"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);
    when(dependencyOverride.overrideWith(Mockito.<List<ProjectVersion>>any(), Mockito.<List<ProjectVersion>>any(),
        Mockito.<Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>>>any())).thenReturn(new ArrayList<>());

    ArrayList<ProjectVersion> projectDependencyVersions = new ArrayList<>();
    projectDependencyVersions.add(new ProjectVersion("42", "42", "42"));

    // Act
    ProjectDependencyReport actualProjectDependencyReport = projectsServiceImpl
        .getProjectDependencyReport(projectDependencyVersions);

    // Assert
    verify(dependencyOverride).overrideWith(isA(List.class), isA(List.class), isA(Function2.class));
    verify(projects).find(isNull(), isNull());
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
    SerializedGraph graph = actualProjectDependencyReport.getGraph();
    MutableList<ProjectDependencyVersionNode> toListResult = graph.getNodes().toList();
    assertEquals(1, toListResult.size());
    ProjectDependencyVersionNode getResult = toListResult.get(0);
    assertEquals("42", getResult.getArtifactId());
    assertEquals("42", getResult.getGroupId());
    assertEquals("42", getResult.getVersionId());
    assertEquals("42:42", getResult.getCoordinates());
    assertEquals("42:42:42", getResult.getGav());
    assertEquals("42:42:42", getResult.getId());
    assertEquals("myproject", getResult.getProjectId());
    Set<String> rootNodes = graph.getRootNodes();
    assertEquals(1, rootNodes.size());
    assertTrue(rootNodes.contains("42:42:42"));
    assertTrue(getResult.getBackEdges().isEmpty());
    assertTrue(getResult.getForwardEdges().isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#getProjectDependencyReport(List)} with {@code projectDependencyVersions}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getProjectDependencyReport(List)}
   */
  @Test
  @DisplayName("Test getProjectDependencyReport(List) with 'projectDependencyVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProjectDependencyReport ProjectsServiceImpl.getProjectDependencyReport(List)"})
  void testGetProjectDependencyReportWithProjectDependencyVersions3() {
    // Arrange
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    when(dependencyOverride.overrideWith(Mockito.<List<ProjectVersion>>any(), Mockito.<List<ProjectVersion>>any(),
        Mockito.<Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>>>any()))
            .thenThrow(new IllegalArgumentException("latest"));

    ArrayList<ProjectVersion> projectDependencyVersions = new ArrayList<>();
    projectDependencyVersions.add(new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getProjectDependencyReport(projectDependencyVersions));
    verify(dependencyOverride).overrideWith(isA(List.class), isA(List.class), isA(Function2.class));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getProjectDependencyReport(List)} with {@code projectDependencyVersions}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getProjectDependencyReport(List)}
   */
  @Test
  @DisplayName("Test getProjectDependencyReport(List) with 'projectDependencyVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProjectDependencyReport ProjectsServiceImpl.getProjectDependencyReport(List)"})
  void testGetProjectDependencyReportWithProjectDependencyVersions4() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getGroupId()).thenThrow(new IllegalStateException("foo"));
    when(storeProjectVersionData.getTransitiveDependenciesReport()).thenReturn(new VersionDependencyReport());
    when(storeProjectVersionData.getVersionData()).thenReturn(new ProjectVersionData());
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    when(dependencyOverride.overrideWith(Mockito.<List<ProjectVersion>>any(), Mockito.<List<ProjectVersion>>any(),
        Mockito.<Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>>>any())).thenReturn(new ArrayList<>());

    ArrayList<ProjectVersion> projectDependencyVersions = new ArrayList<>();
    projectDependencyVersions.add(new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> projectsServiceImpl.getProjectDependencyReport(projectDependencyVersions));
    verify(storeProjectVersionData).getGroupId();
    verify(dependencyOverride).overrideWith(isA(List.class), isA(List.class), isA(Function2.class));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
    verify(storeProjectVersionData, atLeast(1)).getTransitiveDependenciesReport();
    verify(storeProjectVersionData, atLeast(1)).getVersionData();
  }

  /**
   * Test {@link ProjectsServiceImpl#getProjectDependencyReport(List)} with {@code projectDependencyVersions}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getProjectDependencyReport(List)}
   */
  @Test
  @DisplayName("Test getProjectDependencyReport(List) with 'projectDependencyVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProjectDependencyReport ProjectsServiceImpl.getProjectDependencyReport(List)"})
  void testGetProjectDependencyReportWithProjectDependencyVersions5() {
    // Arrange
    VersionDependencyReport versionDependencyReport = mock(VersionDependencyReport.class);
    when(versionDependencyReport.getTransitiveDependencies()).thenThrow(new IllegalArgumentException("foo"));
    when(versionDependencyReport.isValid()).thenReturn(true);
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getTransitiveDependenciesReport()).thenReturn(versionDependencyReport);
    when(storeProjectVersionData.getVersionData()).thenReturn(new ProjectVersionData());
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);

    ArrayList<ProjectVersion> projectDependencyVersions = new ArrayList<>();
    projectDependencyVersions.add(new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getProjectDependencyReport(projectDependencyVersions));
    verify(versionDependencyReport).getTransitiveDependencies();
    verify(versionDependencyReport).isValid();
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
    verify(storeProjectVersionData, atLeast(1)).getTransitiveDependenciesReport();
    verify(storeProjectVersionData, atLeast(1)).getVersionData();
  }

  /**
   * Test {@link ProjectsServiceImpl#getProjectDependencyReport(List)} with {@code projectDependencyVersions}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getProjectDependencyReport(List)}
   */
  @Test
  @DisplayName("Test getProjectDependencyReport(List) with 'projectDependencyVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProjectDependencyReport ProjectsServiceImpl.getProjectDependencyReport(List)"})
  void testGetProjectDependencyReportWithProjectDependencyVersions6() {
    // Arrange
    VersionDependencyReport versionDependencyReport = mock(VersionDependencyReport.class);
    when(versionDependencyReport.isValid()).thenReturn(false);
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getGroupId()).thenThrow(new IllegalStateException("foo"));
    when(storeProjectVersionData.getTransitiveDependenciesReport()).thenReturn(versionDependencyReport);
    when(storeProjectVersionData.getVersionData()).thenReturn(new ProjectVersionData());
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);

    ArrayList<ProjectVersion> projectDependencyVersions = new ArrayList<>();
    projectDependencyVersions.add(new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> projectsServiceImpl.getProjectDependencyReport(projectDependencyVersions));
    verify(storeProjectVersionData).getGroupId();
    verify(versionDependencyReport).isValid();
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
    verify(storeProjectVersionData).getTransitiveDependenciesReport();
    verify(storeProjectVersionData, atLeast(1)).getVersionData();
  }

  /**
   * Test {@link ProjectsServiceImpl#getProjectDependencyReport(List)} with {@code projectDependencyVersions}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getProjectDependencyReport(List)}
   */
  @Test
  @DisplayName("Test getProjectDependencyReport(List) with 'projectDependencyVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProjectDependencyReport ProjectsServiceImpl.getProjectDependencyReport(List)"})
  void testGetProjectDependencyReportWithProjectDependencyVersions7() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    when(storeProjectVersionData.getVersionData())
        .thenReturn(new ProjectVersionData(dependencies, new ArrayList<>(), true, true));
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);

    ArrayList<ProjectVersion> projectDependencyVersions = new ArrayList<>();
    projectDependencyVersions.add(new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getProjectDependencyReport(projectDependencyVersions));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
    verify(storeProjectVersionData).getVersionData();
  }

  /**
   * Test {@link ProjectsServiceImpl#getProjectDependencyReport(List)} with {@code projectDependencyVersions}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getProjectDependencyReport(List)}
   */
  @Test
  @DisplayName("Test getProjectDependencyReport(List) with 'projectDependencyVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProjectDependencyReport ProjectsServiceImpl.getProjectDependencyReport(List)"})
  void testGetProjectDependencyReportWithProjectDependencyVersions8() {
    // Arrange
    ProjectVersionData projectVersionData = new ProjectVersionData();
    projectVersionData.addDependency(new ProjectVersion("42", "42", "42"));
    VersionDependencyReport versionDependencyReport = mock(VersionDependencyReport.class);
    when(versionDependencyReport.getTransitiveDependencies()).thenThrow(new IllegalArgumentException("foo"));
    when(versionDependencyReport.isValid()).thenReturn(true);
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getTransitiveDependenciesReport()).thenReturn(versionDependencyReport);
    when(storeProjectVersionData.getVersionData()).thenReturn(projectVersionData);
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);

    ArrayList<ProjectVersion> projectDependencyVersions = new ArrayList<>();
    projectDependencyVersions.add(new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getProjectDependencyReport(projectDependencyVersions));
    verify(versionDependencyReport).getTransitiveDependencies();
    verify(versionDependencyReport).isValid();
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
    verify(storeProjectVersionData, atLeast(1)).getTransitiveDependenciesReport();
    verify(storeProjectVersionData, atLeast(1)).getVersionData();
  }

  /**
   * Test {@link ProjectsServiceImpl#getProjectDependencyReport(List)} with {@code projectDependencyVersions}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getProjectDependencyReport(List)}
   */
  @Test
  @DisplayName("Test getProjectDependencyReport(List) with 'projectDependencyVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProjectDependencyReport ProjectsServiceImpl.getProjectDependencyReport(List)"})
  void testGetProjectDependencyReportWithProjectDependencyVersions9() {
    // Arrange
    Optional<StoreProjectVersionData> emptyResult = Optional.empty();
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(emptyResult);

    ArrayList<ProjectVersion> projectDependencyVersions = new ArrayList<>();
    projectDependencyVersions.add(new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getProjectDependencyReport(projectDependencyVersions));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getProjectDependencyReport(List)} with {@code projectDependencyVersions}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getProjectDependencyReport(List)}
   */
  @Test
  @DisplayName("Test getProjectDependencyReport(List) with 'projectDependencyVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProjectDependencyReport ProjectsServiceImpl.getProjectDependencyReport(List)"})
  void testGetProjectDependencyReportWithProjectDependencyVersions10() {
    // Arrange
    VersionDependencyReport versionDependencyReport = mock(VersionDependencyReport.class);
    when(versionDependencyReport.getTransitiveDependencies()).thenThrow(new IllegalArgumentException("foo"));
    when(versionDependencyReport.isValid()).thenReturn(true);
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getTransitiveDependenciesReport()).thenReturn(versionDependencyReport);
    when(storeProjectVersionData.getVersionData()).thenReturn(new ProjectVersionData());
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);

    ArrayList<ProjectVersion> projectDependencyVersions = new ArrayList<>();
    projectDependencyVersions.add(new ProjectVersion("42", "42", "42"));
    projectDependencyVersions.add(new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getProjectDependencyReport(projectDependencyVersions));
    verify(versionDependencyReport).getTransitiveDependencies();
    verify(versionDependencyReport).isValid();
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
    verify(storeProjectVersionData, atLeast(1)).getTransitiveDependenciesReport();
    verify(storeProjectVersionData, atLeast(1)).getVersionData();
  }

  /**
   * Test {@link ProjectsServiceImpl#getProjectDependencyReport(List)} with {@code projectDependencyVersions}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getProjectDependencyReport(List)}
   */
  @Test
  @DisplayName("Test getProjectDependencyReport(List) with 'projectDependencyVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProjectDependencyReport ProjectsServiceImpl.getProjectDependencyReport(List)"})
  void testGetProjectDependencyReportWithProjectDependencyVersions11() {
    // Arrange
    Optional<StoreProjectData> ofResult = Optional.of(new StoreProjectData("myproject", "42", "42"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);

    ArrayList<ProjectVersion> projectDependencyVersions = new ArrayList<>();
    projectDependencyVersions.add(new ProjectVersion("42", "42", "latest"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getProjectDependencyReport(projectDependencyVersions));
    verify(projects).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getProjectDependencyReport(List)} with {@code projectDependencyVersions}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getProjectDependencyReport(List)}
   */
  @Test
  @DisplayName("Test getProjectDependencyReport(List) with 'projectDependencyVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProjectDependencyReport ProjectsServiceImpl.getProjectDependencyReport(List)"})
  void testGetProjectDependencyReportWithProjectDependencyVersions12() {
    // Arrange
    VersionDependencyReport versionDependencyReport = mock(VersionDependencyReport.class);
    when(versionDependencyReport.getTransitiveDependencies()).thenThrow(new IllegalArgumentException("foo"));
    when(versionDependencyReport.isValid()).thenReturn(true);
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getTransitiveDependenciesReport()).thenReturn(versionDependencyReport);
    when(storeProjectVersionData.getVersionData()).thenReturn(new ProjectVersionData());
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional.of(new StoreProjectData("myproject", "42", "42"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);
    when(projectsConfiguration.getDefaultBranch()).thenReturn("janedoe/featurebranch");

    ArrayList<ProjectVersion> projectDependencyVersions = new ArrayList<>();
    projectDependencyVersions.add(new ProjectVersion("42", "42", "head"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getProjectDependencyReport(projectDependencyVersions));
    verify(versionDependencyReport).getTransitiveDependencies();
    verify(versionDependencyReport).isValid();
    verify(projectsConfiguration).getDefaultBranch();
    verify(projects).find(eq("42"), eq("42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("janedoe/featurebranch-SNAPSHOT"));
    verify(storeProjectVersionData, atLeast(1)).getTransitiveDependenciesReport();
    verify(storeProjectVersionData, atLeast(1)).getVersionData();
  }

  /**
   * Test {@link ProjectsServiceImpl#getProjectDependencyReport(List)} with {@code projectDependencyVersions}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getProjectDependencyReport(List)}
   */
  @Test
  @DisplayName("Test getProjectDependencyReport(List) with 'projectDependencyVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProjectDependencyReport ProjectsServiceImpl.getProjectDependencyReport(List)"})
  void testGetProjectDependencyReportWithProjectDependencyVersions13() {
    // Arrange
    ProjectVersionData projectVersionData = new ProjectVersionData();
    projectVersionData.addDependency(new ProjectVersion("42", "42", "42"));
    VersionDependencyReport versionDependencyReport = mock(VersionDependencyReport.class);
    when(versionDependencyReport.getTransitiveDependencies()).thenThrow(new IllegalArgumentException("foo"));
    when(versionDependencyReport.isValid()).thenReturn(true);
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getTransitiveDependenciesReport()).thenReturn(versionDependencyReport);
    when(storeProjectVersionData.getVersionData()).thenReturn(projectVersionData);
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional.of(new StoreProjectData("myproject", "42", "42"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);
    when(projectsConfiguration.getDefaultBranch()).thenReturn("janedoe/featurebranch");

    ArrayList<ProjectVersion> projectDependencyVersions = new ArrayList<>();
    projectDependencyVersions.add(new ProjectVersion("42", "42", "head"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getProjectDependencyReport(projectDependencyVersions));
    verify(versionDependencyReport).getTransitiveDependencies();
    verify(versionDependencyReport).isValid();
    verify(projectsConfiguration).getDefaultBranch();
    verify(projects).find(eq("42"), eq("42"));
    verify(projectsVersions, atLeast(1)).find(eq("42"), eq("42"), Mockito.<String>any());
    verify(storeProjectVersionData, atLeast(1)).getTransitiveDependenciesReport();
    verify(storeProjectVersionData, atLeast(1)).getVersionData();
  }

  /**
   * Test {@link ProjectsServiceImpl#getProjectDependencyReport(List)} with {@code projectDependencyVersions}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getProjectDependencyReport(List)}
   */
  @Test
  @DisplayName("Test getProjectDependencyReport(List) with 'projectDependencyVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProjectDependencyReport ProjectsServiceImpl.getProjectDependencyReport(List)"})
  void testGetProjectDependencyReportWithProjectDependencyVersions14() {
    // Arrange
    VersionDependencyReport versionDependencyReport = mock(VersionDependencyReport.class);
    when(versionDependencyReport.getTransitiveDependencies()).thenThrow(new IllegalArgumentException("foo"));
    when(versionDependencyReport.isValid()).thenReturn(true);
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getTransitiveDependenciesReport()).thenReturn(versionDependencyReport);
    when(storeProjectVersionData.getVersionData()).thenReturn(new ProjectVersionData());
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional
        .of(new StoreProjectData("myproject", "42", "42", "janedoe/featurebranch", "1.0.2"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);

    ArrayList<ProjectVersion> projectDependencyVersions = new ArrayList<>();
    projectDependencyVersions.add(new ProjectVersion("42", "42", "head"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getProjectDependencyReport(projectDependencyVersions));
    verify(versionDependencyReport).getTransitiveDependencies();
    verify(versionDependencyReport).isValid();
    verify(projects).find(eq("42"), eq("42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("janedoe/featurebranch-SNAPSHOT"));
    verify(storeProjectVersionData, atLeast(1)).getTransitiveDependenciesReport();
    verify(storeProjectVersionData, atLeast(1)).getVersionData();
  }

  /**
   * Test {@link ProjectsServiceImpl#getProjectDependencyReport(List)} with {@code projectDependencyVersions}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getProjectDependencyReport(List)}
   */
  @Test
  @DisplayName("Test getProjectDependencyReport(List) with 'projectDependencyVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProjectDependencyReport ProjectsServiceImpl.getProjectDependencyReport(List)"})
  void testGetProjectDependencyReportWithProjectDependencyVersions15() {
    // Arrange
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.getDefaultBranch()).thenThrow(new IllegalArgumentException("foo"));
    Optional<StoreProjectData> ofResult = Optional.of(storeProjectData);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);

    ArrayList<ProjectVersion> projectDependencyVersions = new ArrayList<>();
    projectDependencyVersions.add(new ProjectVersion("42", "42", "head"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getProjectDependencyReport(projectDependencyVersions));
    verify(projects).find(eq("42"), eq("42"));
    verify(storeProjectData).getDefaultBranch();
  }

  /**
   * Test {@link ProjectsServiceImpl#getProjectDependencyReport(List)} with {@code projectDependencyVersions}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getProjectDependencyReport(List)}
   */
  @Test
  @DisplayName("Test getProjectDependencyReport(List) with 'projectDependencyVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProjectDependencyReport ProjectsServiceImpl.getProjectDependencyReport(List)"})
  void testGetProjectDependencyReportWithProjectDependencyVersions16() {
    // Arrange
    Optional<StoreProjectData> emptyResult = Optional.empty();
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(emptyResult);

    ArrayList<ProjectVersion> projectDependencyVersions = new ArrayList<>();
    projectDependencyVersions.add(new ProjectVersion("42", "42", "head"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getProjectDependencyReport(projectDependencyVersions));
    verify(projects).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getProjectDependencyReport(List)} with {@code projectDependencyVersions}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getProjectDependencyReport(List)}
   */
  @Test
  @DisplayName("Test getProjectDependencyReport(List) with 'projectDependencyVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProjectDependencyReport ProjectsServiceImpl.getProjectDependencyReport(List)"})
  void testGetProjectDependencyReportWithProjectDependencyVersions17() {
    // Arrange
    VersionDependencyReport versionDependencyReport = mock(VersionDependencyReport.class);
    when(versionDependencyReport.getTransitiveDependencies()).thenThrow(new IllegalArgumentException("foo"));
    when(versionDependencyReport.isValid()).thenReturn(true);
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getTransitiveDependenciesReport()).thenReturn(versionDependencyReport);
    when(storeProjectVersionData.getVersionData()).thenReturn(new ProjectVersionData());
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.getLatestVersion()).thenReturn("1.0.2");
    Optional<StoreProjectData> ofResult2 = Optional.of(storeProjectData);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);

    ArrayList<ProjectVersion> projectDependencyVersions = new ArrayList<>();
    projectDependencyVersions.add(new ProjectVersion("42", "42", "latest"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getProjectDependencyReport(projectDependencyVersions));
    verify(versionDependencyReport).getTransitiveDependencies();
    verify(versionDependencyReport).isValid();
    verify(projects).find(eq("42"), eq("42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("1.0.2"));
    verify(storeProjectData, atLeast(1)).getLatestVersion();
    verify(storeProjectVersionData, atLeast(1)).getTransitiveDependenciesReport();
    verify(storeProjectVersionData, atLeast(1)).getVersionData();
  }

  /**
   * Test {@link ProjectsServiceImpl#getProjectDependencyReport(List)} with {@code projectDependencyVersions}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getProjectDependencyReport(List)}
   */
  @Test
  @DisplayName("Test getProjectDependencyReport(List) with 'projectDependencyVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProjectDependencyReport ProjectsServiceImpl.getProjectDependencyReport(List)"})
  void testGetProjectDependencyReportWithProjectDependencyVersions18() {
    // Arrange
    Optional<StoreProjectData> emptyResult = Optional.empty();
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(emptyResult);

    ArrayList<ProjectVersion> projectDependencyVersions = new ArrayList<>();
    projectDependencyVersions.add(new ProjectVersion("42", "42", "latest"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getProjectDependencyReport(projectDependencyVersions));
    verify(projects).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getProjectDependencyReport(List)} with {@code projectDependencyVersions}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getProjectDependencyReport(List)}
   */
  @Test
  @DisplayName("Test getProjectDependencyReport(List) with 'projectDependencyVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProjectDependencyReport ProjectsServiceImpl.getProjectDependencyReport(List)"})
  void testGetProjectDependencyReportWithProjectDependencyVersions19() {
    // Arrange
    ProjectVersion projectVersion = mock(ProjectVersion.class);
    when(projectVersion.getGroupId()).thenThrow(new IllegalStateException("foo"));

    ArrayList<ProjectVersion> projectDependencyVersions = new ArrayList<>();
    projectDependencyVersions.add(projectVersion);

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> projectsServiceImpl.getProjectDependencyReport(projectDependencyVersions));
    verify(projectVersion).getGroupId();
  }

  /**
   * Test {@link ProjectsServiceImpl#getProjectDependencyReport(List)} with {@code projectDependencyVersions}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getProjectDependencyReport(List)}
   */
  @Test
  @DisplayName("Test getProjectDependencyReport(List) with 'projectDependencyVersions'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ProjectDependencyReport ProjectsServiceImpl.getProjectDependencyReport(List)"})
  void testGetProjectDependencyReportWithProjectDependencyVersions20() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionData()).thenReturn(new ProjectVersionData());
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    ProjectVersion projectVersion = mock(ProjectVersion.class);
    when(projectVersion.getGroupId()).thenThrow(new IllegalStateException("foo"));

    ArrayList<ProjectVersion> projectDependencyVersions = new ArrayList<>();
    projectDependencyVersions.add(new ProjectVersion("42", "42", "42"));
    projectDependencyVersions.add(projectVersion);

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> projectsServiceImpl.getProjectDependencyReport(projectDependencyVersions));
    verify(projectVersion).getGroupId();
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
    verify(storeProjectVersionData, atLeast(1)).getVersionData();
  }

  /**
   * Test {@link ProjectsServiceImpl#buildReportFromGraph(ProjectDependencyGraph, ProjectDependencyGraphWalkerContext)}.
   * <ul>
   *   <li>Then return Graph Nodes toList Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#buildReportFromGraph(ProjectDependencyGraph, ProjectDependencyGraphWalkerContext)}
   */
  @Test
  @DisplayName("Test buildReportFromGraph(ProjectDependencyGraph, ProjectDependencyGraphWalkerContext); then return Graph Nodes toList Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ProjectDependencyReport ProjectsServiceImpl.buildReportFromGraph(ProjectDependencyGraph, ProjectDependencyGraphWalkerContext)"})
  void testBuildReportFromGraph_thenReturnGraphNodesToListEmpty() {
    // Arrange
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ProjectsVersionsMongo projectsVersions = new ProjectsVersionsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));
    CodecRegistry codecRegistry2 = mock(CodecRegistry.class);
    ReadPreference readPreference2 = mock(ReadPreference.class);
    WriteConcern writeConcern2 = new WriteConcern(1);
    ProjectsMongo projects = new ProjectsMongo(
        new MongoDatabaseImpl("Name", codecRegistry2, readPreference2, writeConcern2, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();
    ProjectsServiceImpl projectsServiceImpl = new ProjectsServiceImpl(projectsVersions, projects, metricsRegistry,
        queue, new ProjectsConfiguration("janedoe/featurebranch"));
    ProjectDependencyGraph dependencyGraph = new ProjectDependencyGraph();

    // Act
    ProjectDependencyReport actualBuildReportFromGraphResult = projectsServiceImpl.buildReportFromGraph(dependencyGraph,
        new ProjectDependencyGraphWalkerContext());

    // Assert
    SerializedGraph graph = actualBuildReportFromGraphResult.getGraph();
    assertTrue(graph.getNodes().toList().isEmpty());
    assertTrue(actualBuildReportFromGraphResult.getConflicts().isEmpty());
    assertTrue(graph.getRootNodes().isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code latestOnly}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test getDependantProjects(String, String, String, boolean) with 'groupId', 'artifactId', 'versionId', 'latestOnly'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getDependantProjects(String, String, String, boolean)"})
  void testGetDependantProjectsWithGroupIdArtifactIdVersionIdLatestOnly() {
    // Arrange
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    doThrow(new IllegalArgumentException("ALL")).when(queryMetricsRegistry)
        .record(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getDependantProjects("42", "42", "42", true));
    verify(queryMetricsRegistry).record(eq("42"), eq("42"), isNull());
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code latestOnly}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test getDependantProjects(String, String, String, boolean) with 'groupId', 'artifactId', 'versionId', 'latestOnly'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getDependantProjects(String, String, String, boolean)"})
  void testGetDependantProjectsWithGroupIdArtifactIdVersionIdLatestOnly2() {
    // Arrange
    Optional<StoreProjectVersionData> ofResult = Optional
        .of(new StoreProjectVersionData("42", "42", "42", true, new ProjectVersionData()));
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional.of(new StoreProjectData("myproject", "42", "42"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);
    when(queue.push(Mockito.<MetadataNotification>any())).thenThrow(new IllegalArgumentException("ALL"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getDependantProjects("42", "42", "42", true));
    verify(queue).push(isA(MetadataNotification.class));
    verify(projects).find(eq("42"), eq("42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code latestOnly}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test getDependantProjects(String, String, String, boolean) with 'groupId', 'artifactId', 'versionId', 'latestOnly'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getDependantProjects(String, String, String, boolean)"})
  void testGetDependantProjectsWithGroupIdArtifactIdVersionIdLatestOnly3() {
    // Arrange
    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData("42", "42", "42", true,
        new ProjectVersionData(dependencies, new ArrayList<>(), true, true)));
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getDependantProjects("42", "42", "42", true));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code latestOnly}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test getDependantProjects(String, String, String, boolean) with 'groupId', 'artifactId', 'versionId', 'latestOnly'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getDependantProjects(String, String, String, boolean)"})
  void testGetDependantProjectsWithGroupIdArtifactIdVersionIdLatestOnly4() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionId()).thenThrow(new IllegalStateException("foo"));
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> projectsServiceImpl.getDependantProjects("42", "42", "42", true));
    verify(storeProjectVersionData).getVersionId();
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code latestOnly}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test getDependantProjects(String, String, String, boolean) with 'groupId', 'artifactId', 'versionId', 'latestOnly'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getDependantProjects(String, String, String, boolean)"})
  void testGetDependantProjectsWithGroupIdArtifactIdVersionIdLatestOnly5() {
    // Arrange
    Optional<StoreProjectVersionData> emptyResult = Optional.empty();
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(emptyResult);

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getDependantProjects("42", "42", "42", true));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code latestOnly}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test getDependantProjects(String, String, String, boolean) with 'groupId', 'artifactId', 'versionId', 'latestOnly'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getDependantProjects(String, String, String, boolean)"})
  void testGetDependantProjectsWithGroupIdArtifactIdVersionIdLatestOnly6() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionId()).thenThrow(new IllegalStateException("foo"));
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional.of(new StoreProjectData("myproject", "42", "42"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);
    when(projectsConfiguration.getDefaultBranch()).thenReturn("janedoe/featurebranch");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> projectsServiceImpl.getDependantProjects("42", "42", "head", true));
    verify(storeProjectVersionData).getVersionId();
    verify(projectsConfiguration).getDefaultBranch();
    verify(projects).find(eq("42"), eq("42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("janedoe/featurebranch-SNAPSHOT"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code latestOnly}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test getDependantProjects(String, String, String, boolean) with 'groupId', 'artifactId', 'versionId', 'latestOnly'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getDependantProjects(String, String, String, boolean)"})
  void testGetDependantProjectsWithGroupIdArtifactIdVersionIdLatestOnly7() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionId()).thenThrow(new IllegalStateException("foo"));
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional
        .of(new StoreProjectData("myproject", "42", "42", "janedoe/featurebranch", "1.0.2"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> projectsServiceImpl.getDependantProjects("42", "42", "head", true));
    verify(storeProjectVersionData).getVersionId();
    verify(projects).find(eq("42"), eq("42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("janedoe/featurebranch-SNAPSHOT"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code latestOnly}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test getDependantProjects(String, String, String, boolean) with 'groupId', 'artifactId', 'versionId', 'latestOnly'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getDependantProjects(String, String, String, boolean)"})
  void testGetDependantProjectsWithGroupIdArtifactIdVersionIdLatestOnly8() {
    // Arrange
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.getDefaultBranch()).thenThrow(new IllegalArgumentException("foo"));
    Optional<StoreProjectData> ofResult = Optional.of(storeProjectData);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getDependantProjects("42", "42", "head", true));
    verify(projects).find(eq("42"), eq("42"));
    verify(storeProjectData).getDefaultBranch();
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code latestOnly}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test getDependantProjects(String, String, String, boolean) with 'groupId', 'artifactId', 'versionId', 'latestOnly'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getDependantProjects(String, String, String, boolean)"})
  void testGetDependantProjectsWithGroupIdArtifactIdVersionIdLatestOnly9() {
    // Arrange
    Optional<StoreProjectData> emptyResult = Optional.empty();
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(emptyResult);

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getDependantProjects("42", "42", "head", true));
    verify(projects).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code latestOnly}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test getDependantProjects(String, String, String, boolean) with 'groupId', 'artifactId', 'versionId', 'latestOnly'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getDependantProjects(String, String, String, boolean)"})
  void testGetDependantProjectsWithGroupIdArtifactIdVersionIdLatestOnly10() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionId()).thenThrow(new IllegalStateException("foo"));
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.getLatestVersion()).thenReturn("1.0.2");
    Optional<StoreProjectData> ofResult2 = Optional.of(storeProjectData);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> projectsServiceImpl.getDependantProjects("42", "42", "latest", true));
    verify(storeProjectVersionData).getVersionId();
    verify(projects).find(eq("42"), eq("42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("1.0.2"));
    verify(storeProjectData, atLeast(1)).getLatestVersion();
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code latestOnly}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test getDependantProjects(String, String, String, boolean) with 'groupId', 'artifactId', 'versionId', 'latestOnly'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getDependantProjects(String, String, String, boolean)"})
  void testGetDependantProjectsWithGroupIdArtifactIdVersionIdLatestOnly11() {
    // Arrange
    Optional<StoreProjectData> emptyResult = Optional.empty();
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(emptyResult);

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getDependantProjects("42", "42", "latest", true));
    verify(projects).find(eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code latestOnly}.
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test getDependantProjects(String, String, String, boolean) with 'groupId', 'artifactId', 'versionId', 'latestOnly'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getDependantProjects(String, String, String, boolean)"})
  void testGetDependantProjectsWithGroupIdArtifactIdVersionIdLatestOnly12() {
    // Arrange
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.getLatestVersion()).thenThrow(new IllegalArgumentException("foo"));
    Optional<StoreProjectData> ofResult = Optional.of(storeProjectData);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getDependantProjects("42", "42", "latest", true));
    verify(projects).find(eq("42"), eq("42"));
    verify(storeProjectData).getLatestVersion();
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code latestOnly}.
   * <ul>
   *   <li>Then calls {@link Queue#push(MetadataNotification)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test getDependantProjects(String, String, String, boolean) with 'groupId', 'artifactId', 'versionId', 'latestOnly'; then calls push(MetadataNotification)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getDependantProjects(String, String, String, boolean)"})
  void testGetDependantProjectsWithGroupIdArtifactIdVersionIdLatestOnly_thenCallsPush() {
    // Arrange
    Optional<StoreProjectVersionData> ofResult = Optional
        .of(new StoreProjectVersionData("42", "42", "42", true, new ProjectVersionData()));
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional.of(new StoreProjectData("myproject", "42", "42"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);
    when(queue.push(Mockito.<MetadataNotification>any())).thenReturn("Push");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> projectsServiceImpl.getDependantProjects("42", "42", "42", true));
    verify(queue).push(isA(MetadataNotification.class));
    verify(projects).find(eq("42"), eq("42"));
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code latestOnly}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test getDependantProjects(String, String, String, boolean) with 'groupId', 'artifactId', 'versionId', 'latestOnly'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getDependantProjects(String, String, String, boolean)"})
  void testGetDependantProjectsWithGroupIdArtifactIdVersionIdLatestOnly_thenReturnEmpty() {
    // Arrange
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projectsVersions.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    when(projects.getAll()).thenReturn(new ArrayList<>());
    doNothing().when(queryMetricsRegistry).record(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    List<ProjectDependencyWithPlatformVersions> actualDependantProjects = projectsServiceImpl.getDependantProjects("42",
        "42", "42", true);

    // Assert
    verify(queryMetricsRegistry).record(eq("42"), eq("42"), isNull());
    verify(projects).getAll();
    verify(projectsVersions).find(eq("42"), eq("42"), eq("42"));
    assertTrue(actualDependantProjects.isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code latestOnly}.
   * <ul>
   *   <li>When {@code ALL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test getDependantProjects(String, String, String, boolean) with 'groupId', 'artifactId', 'versionId', 'latestOnly'; when 'ALL'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getDependantProjects(String, String, String, boolean)"})
  void testGetDependantProjectsWithGroupIdArtifactIdVersionIdLatestOnly_whenAll() {
    // Arrange
    when(projects.getAll()).thenReturn(new ArrayList<>());

    // Act
    List<ProjectDependencyWithPlatformVersions> actualDependantProjects = projectsServiceImpl.getDependantProjects("42",
        "42", "ALL", true);

    // Assert
    verify(projects).getAll();
    assertTrue(actualDependantProjects.isEmpty());
  }

  /**
   * Test {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code latestOnly}.
   * <ul>
   *   <li>When {@code latest}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectsServiceImpl#getDependantProjects(String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test getDependantProjects(String, String, String, boolean) with 'groupId', 'artifactId', 'versionId', 'latestOnly'; when 'latest'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ProjectsServiceImpl.getDependantProjects(String, String, String, boolean)"})
  void testGetDependantProjectsWithGroupIdArtifactIdVersionIdLatestOnly_whenLatest() {
    // Arrange
    Optional<StoreProjectData> ofResult = Optional.of(new StoreProjectData("myproject", "42", "42"));
    when(projects.find(Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> projectsServiceImpl.getDependantProjects("42", "42", "latest", true));
    verify(projects).find(eq("42"), eq("42"));
  }
}
