package org.finos.legend.depot.services.projects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
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
import org.finos.legend.depot.domain.project.ProjectSummary;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.services.api.metrics.query.VoidQueryMetricsRegistry;
import org.finos.legend.depot.services.api.notifications.queue.Queue;
import org.finos.legend.depot.services.api.notifications.queue.VoidQueue;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.store.api.projects.UpdateProjects;
import org.finos.legend.depot.store.api.projects.UpdateProjectsVersions;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.finos.legend.depot.store.mongo.projects.ProjectsMongo;
import org.finos.legend.depot.store.mongo.projects.ProjectsVersionsMongo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@Disabled("Failed pitest")
@ExtendWith(MockitoExtension.class)
class ManageProjectsServiceImplDiffblueTest {
  @InjectMocks
  private ManageProjectsServiceImpl manageProjectsServiceImpl;

  @Mock
  private ProjectsConfiguration projectsConfiguration;

  @Mock
  private QueryMetricsRegistry queryMetricsRegistry;

  @Mock
  private Queue queue;

  @Mock
  private UpdateProjects updateProjects;

  @Mock
  private UpdateProjectsVersions updateProjectsVersions;

  /**
   * Test {@link ManageProjectsServiceImpl#ManageProjectsServiceImpl(UpdateProjectsVersions, UpdateProjects, QueryMetricsRegistry, Queue, ProjectsConfiguration)}.
   * <p>
   * Method under test: {@link ManageProjectsServiceImpl#ManageProjectsServiceImpl(UpdateProjectsVersions, UpdateProjects, QueryMetricsRegistry, Queue, ProjectsConfiguration)}
   */
  @Test
  @DisplayName("Test new ManageProjectsServiceImpl(UpdateProjectsVersions, UpdateProjects, QueryMetricsRegistry, Queue, ProjectsConfiguration)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void ManageProjectsServiceImpl.<init>(UpdateProjectsVersions, UpdateProjects, QueryMetricsRegistry, Queue, ProjectsConfiguration)"})
  void testNewManageProjectsServiceImpl() {
    // Arrange and Act
    ManageProjectsServiceImpl actualManageProjectsServiceImpl = new ManageProjectsServiceImpl(updateProjectsVersions,
        updateProjects, queryMetricsRegistry, queue, projectsConfiguration);

    // Assert
    assertTrue(actualManageProjectsServiceImpl.getAll().isEmpty());
    assertTrue(actualManageProjectsServiceImpl.getProjectsSummary().isEmpty());
    assertTrue(actualManageProjectsServiceImpl.getAllProjectCoordinates().isEmpty());
  }

  /**
   * Test {@link ManageProjectsServiceImpl#getAll()}.
   * <ul>
   *   <li>Given {@link ProjectsVersionsMongo} {@link ProjectsVersionsMongo#getAll()} return {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManageProjectsServiceImpl#getAll()}
   */
  @Test
  @DisplayName("Test getAll(); given ProjectsVersionsMongo getAll() return ArrayList(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ManageProjectsServiceImpl.getAll()"})
  void testGetAll_givenProjectsVersionsMongoGetAllReturnArrayList_thenReturnEmpty() {
    // Arrange
    ProjectsVersionsMongo projectsVersions = mock(ProjectsVersionsMongo.class);
    when(projectsVersions.getAll()).thenReturn(new ArrayList<>());
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ProjectsMongo projects = new ProjectsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();

    // Act
    List<StoreProjectVersionData> actualAll = new ManageProjectsServiceImpl(projectsVersions, projects, metricsRegistry,
        queue, new ProjectsConfiguration("janedoe/featurebranch")).getAll();

    // Assert
    verify(projectsVersions).getAll();
    assertTrue(actualAll.isEmpty());
  }

  /**
   * Test {@link ManageProjectsServiceImpl#createOrUpdate(StoreProjectData)} with {@code StoreProjectData}.
   * <p>
   * Method under test: {@link ManageProjectsServiceImpl#createOrUpdate(StoreProjectData)}
   */
  @Test
  @DisplayName("Test createOrUpdate(StoreProjectData) with 'StoreProjectData'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"StoreProjectData ManageProjectsServiceImpl.createOrUpdate(StoreProjectData)"})
  void testCreateOrUpdateWithStoreProjectData() {
    // Arrange
    UpdateProjects projects = mock(UpdateProjects.class);
    StoreProjectData storeProjectData = new StoreProjectData("myproject", "42", "42");

    when(projects.createOrUpdate(Mockito.<StoreProjectData>any())).thenReturn(storeProjectData);
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ProjectsVersionsMongo projectsVersions = new ProjectsVersionsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();
    ManageProjectsServiceImpl manageProjectsServiceImpl = new ManageProjectsServiceImpl(projectsVersions, projects,
        metricsRegistry, queue, new ProjectsConfiguration("janedoe/featurebranch"));

    // Act
    StoreProjectData actualCreateOrUpdateResult = manageProjectsServiceImpl
        .createOrUpdate(new StoreProjectData("myproject", "42", "42"));

    // Assert
    verify(projects).createOrUpdate(isA(StoreProjectData.class));
    assertSame(storeProjectData, actualCreateOrUpdateResult);
  }

  /**
   * Test {@link ManageProjectsServiceImpl#createOrUpdate(StoreProjectVersionData)} with {@code StoreProjectVersionData}.
   * <ul>
   *   <li>Then return {@link StoreProjectVersionData#StoreProjectVersionData()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManageProjectsServiceImpl#createOrUpdate(StoreProjectVersionData)}
   */
  @Test
  @DisplayName("Test createOrUpdate(StoreProjectVersionData) with 'StoreProjectVersionData'; then return StoreProjectVersionData()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"StoreProjectVersionData ManageProjectsServiceImpl.createOrUpdate(StoreProjectVersionData)"})
  void testCreateOrUpdateWithStoreProjectVersionData_thenReturnStoreProjectVersionData() {
    // Arrange
    UpdateProjectsVersions projectsVersions = mock(UpdateProjectsVersions.class);
    StoreProjectVersionData storeProjectVersionData = new StoreProjectVersionData();
    when(projectsVersions.createOrUpdate(Mockito.<StoreProjectVersionData>any())).thenReturn(storeProjectVersionData);
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ProjectsMongo projects = new ProjectsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();
    ManageProjectsServiceImpl manageProjectsServiceImpl = new ManageProjectsServiceImpl(projectsVersions, projects,
        metricsRegistry, queue, new ProjectsConfiguration("janedoe/featurebranch"));

    // Act
    StoreProjectVersionData actualCreateOrUpdateResult = manageProjectsServiceImpl
        .createOrUpdate(new StoreProjectVersionData());

    // Assert
    verify(projectsVersions).createOrUpdate(isA(StoreProjectVersionData.class));
    assertSame(storeProjectVersionData, actualCreateOrUpdateResult);
  }

  /**
   * Test {@link ManageProjectsServiceImpl#delete(String, String, String)} with {@code groupId}, {@code artifactId}, {@code versionId}.
   * <ul>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManageProjectsServiceImpl#delete(String, String, String)}
   */
  @Test
  @DisplayName("Test delete(String, String, String) with 'groupId', 'artifactId', 'versionId'; then return one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"long ManageProjectsServiceImpl.delete(String, String, String)"})
  void testDeleteWithGroupIdArtifactIdVersionId_thenReturnOne() {
    // Arrange
    ProjectsVersionsMongo projectsVersions = mock(ProjectsVersionsMongo.class);
    when(projectsVersions.delete(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(1L);
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ProjectsMongo projects = new ProjectsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();

    // Act
    long actualDeleteResult = new ManageProjectsServiceImpl(projectsVersions, projects, metricsRegistry, queue,
        new ProjectsConfiguration("janedoe/featurebranch")).delete("42", "42", "42");

    // Assert
    verify(projectsVersions).delete(eq("42"), eq("42"), eq("42"));
    assertEquals(1L, actualDeleteResult);
  }

  /**
   * Test {@link ManageProjectsServiceImpl#delete(String, String)} with {@code groupId}, {@code artifactId}.
   * <ul>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManageProjectsServiceImpl#delete(String, String)}
   */
  @Test
  @DisplayName("Test delete(String, String) with 'groupId', 'artifactId'; then return one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"long ManageProjectsServiceImpl.delete(String, String)"})
  void testDeleteWithGroupIdArtifactId_thenReturnOne() {
    // Arrange
    UpdateProjectsVersions projectsVersions = mock(UpdateProjectsVersions.class);
    when(projectsVersions.delete(Mockito.<String>any(), Mockito.<String>any())).thenReturn(1L);
    ProjectsMongo projects = mock(ProjectsMongo.class);
    when(projects.delete(Mockito.<String>any(), Mockito.<String>any())).thenReturn(1L);
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();

    // Act
    long actualDeleteResult = new ManageProjectsServiceImpl(projectsVersions, projects, metricsRegistry, queue,
        new ProjectsConfiguration("janedoe/featurebranch")).delete("42", "42");

    // Assert
    verify(projectsVersions).delete(eq("42"), eq("42"));
    verify(projects).delete(eq("42"), eq("42"));
    assertEquals(1L, actualDeleteResult);
  }

  /**
   * Test {@link ManageProjectsServiceImpl#excludeProjectVersion(String, String, String, String)}.
   * <ul>
   *   <li>Then return {@link StoreProjectVersionData#StoreProjectVersionData()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManageProjectsServiceImpl#excludeProjectVersion(String, String, String, String)}
   */
  @Test
  @DisplayName("Test excludeProjectVersion(String, String, String, String); then return StoreProjectVersionData()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "StoreProjectVersionData ManageProjectsServiceImpl.excludeProjectVersion(String, String, String, String)"})
  void testExcludeProjectVersion_thenReturnStoreProjectVersionData() {
    // Arrange
    UpdateProjectsVersions projectsVersions = mock(UpdateProjectsVersions.class);
    StoreProjectVersionData storeProjectVersionData = new StoreProjectVersionData();
    when(projectsVersions.createOrUpdate(Mockito.<StoreProjectVersionData>any())).thenReturn(storeProjectVersionData);
    CodecRegistry codecRegistry = mock(CodecRegistry.class);
    ReadPreference readPreference = mock(ReadPreference.class);
    WriteConcern writeConcern = new WriteConcern(1);
    ProjectsMongo projects = new ProjectsMongo(
        new MongoDatabaseImpl("Name", codecRegistry, readPreference, writeConcern, true, true,
            new ReadConcern(ReadConcernLevel.LOCAL), UuidRepresentation.UNSPECIFIED, mock(OperationExecutor.class)));
    VoidQueryMetricsRegistry metricsRegistry = new VoidQueryMetricsRegistry();
    VoidQueue queue = new VoidQueue();

    // Act
    StoreProjectVersionData actualExcludeProjectVersionResult = new ManageProjectsServiceImpl(projectsVersions,
        projects, metricsRegistry, queue, new ProjectsConfiguration("janedoe/featurebranch"))
            .excludeProjectVersion("42", "42", "42", "Just cause");

    // Assert
    verify(projectsVersions).createOrUpdate(isA(StoreProjectVersionData.class));
    assertSame(storeProjectVersionData, actualExcludeProjectVersionResult);
  }

  /**
   * Test {@link ManageProjectsServiceImpl#getProjectsSummary()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManageProjectsServiceImpl#getProjectsSummary()}
   */
  @Test
  @DisplayName("Test getProjectsSummary(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ManageProjectsServiceImpl.getProjectsSummary()"})
  void testGetProjectsSummary_thenReturnEmpty() {
    // Arrange
    when(updateProjects.getAll()).thenReturn(new ArrayList<>());

    // Act
    List<ProjectSummary> actualProjectsSummary = manageProjectsServiceImpl.getProjectsSummary();

    // Assert
    verify(updateProjects).getAll();
    assertTrue(actualProjectsSummary.isEmpty());
  }

  /**
   * Test {@link ManageProjectsServiceImpl#getProjectsSummary()}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManageProjectsServiceImpl#getProjectsSummary()}
   */
  @Test
  @DisplayName("Test getProjectsSummary(); then return size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ManageProjectsServiceImpl.getProjectsSummary()"})
  void testGetProjectsSummary_thenReturnSizeIsOne() {
    // Arrange
    when(updateProjectsVersions.getVersionCount(Mockito.<String>any(), Mockito.<String>any())).thenReturn(3L);

    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData("myproject", "42", "42"));
    when(updateProjects.getAll()).thenReturn(storeProjectDataList);

    // Act
    List<ProjectSummary> actualProjectsSummary = manageProjectsServiceImpl.getProjectsSummary();

    // Assert
    verify(updateProjects).getAll();
    verify(updateProjectsVersions).getVersionCount(eq("42"), eq("42"));
    assertEquals(1, actualProjectsSummary.size());
    ProjectSummary getResult = actualProjectsSummary.get(0);
    assertEquals("42", getResult.artifactId);
    assertEquals("42", getResult.groupId);
    assertEquals("42-42", getResult.getMavenCoordinates());
    assertEquals("myproject", getResult.projectId);
    assertEquals(3L, getResult.versions);
  }

  /**
   * Test {@link ManageProjectsServiceImpl#getProjectsSummary()}.
   * <ul>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManageProjectsServiceImpl#getProjectsSummary()}
   */
  @Test
  @DisplayName("Test getProjectsSummary(); then return size is two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ManageProjectsServiceImpl.getProjectsSummary()"})
  void testGetProjectsSummary_thenReturnSizeIsTwo() {
    // Arrange
    when(updateProjectsVersions.getVersionCount(Mockito.<String>any(), Mockito.<String>any())).thenReturn(3L);

    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData("myproject", "42", "42"));
    storeProjectDataList.add(new StoreProjectData("myproject", "42", "42"));
    when(updateProjects.getAll()).thenReturn(storeProjectDataList);

    // Act
    List<ProjectSummary> actualProjectsSummary = manageProjectsServiceImpl.getProjectsSummary();

    // Assert
    verify(updateProjects).getAll();
    verify(updateProjectsVersions, atLeast(1)).getVersionCount(eq("42"), eq("42"));
    assertEquals(2, actualProjectsSummary.size());
    ProjectSummary getResult = actualProjectsSummary.get(0);
    assertEquals("42", getResult.artifactId);
    assertEquals("42", getResult.groupId);
    assertEquals("42-42", getResult.getMavenCoordinates());
    assertEquals("myproject", getResult.projectId);
    assertEquals(3L, getResult.versions);
    assertEquals(getResult, actualProjectsSummary.get(1));
  }
}
