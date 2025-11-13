package org.finos.legend.depot.services.artifacts.reconciliation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.domain.project.ProjectVersionData;
import org.finos.legend.depot.domain.version.VersionMismatch;
import org.finos.legend.depot.services.artifacts.repository.maven.TestMavenArtifactsRepository;
import org.finos.legend.depot.services.projects.ManageProjectsServiceImpl;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class VersionsReconciliationServiceImplDiffblueTest {
  /**
   * Test {@link VersionsReconciliationServiceImpl#findVersionsMismatches()}.
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#findVersionsMismatches()}
   */
  @Test
  @DisplayName("Test findVersionsMismatches()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.findVersionsMismatches()"})
  void testFindVersionsMismatches() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new IllegalArgumentException());
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<VersionMismatch> actualFindVersionsMismatchesResult =
        versionsReconciliationServiceImpl.findVersionsMismatches();

    // Assert
    verify(projectsService).find(null, null);
    verify(projectsService).getAllProjectCoordinates();
    assertEquals(1, actualFindVersionsMismatchesResult.size());
    VersionMismatch getResult = actualFindVersionsMismatchesResult.get(0);
    List<String> stringList = getResult.errors;
    assertEquals(1, stringList.size());
    assertEquals("Could not get versions for null:null exception: null ", stringList.get(0));
    assertNull(getResult.artifactId);
    assertNull(getResult.groupId);
    assertNull(getResult.projectId);
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#findVersionsMismatches()}.
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#findVersionsMismatches()}
   */
  @Test
  @DisplayName("Test findVersionsMismatches()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.findVersionsMismatches()"})
  void testFindVersionsMismatches2() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(null, projectsService);

    // Act
    List<VersionMismatch> actualFindVersionsMismatchesResult =
        versionsReconciliationServiceImpl.findVersionsMismatches();

    // Assert
    verify(projectsService).find(null, null);
    verify(projectsService).getAllProjectCoordinates();
    assertEquals(1, actualFindVersionsMismatchesResult.size());
    VersionMismatch getResult = actualFindVersionsMismatchesResult.get(0);
    List<String> stringList = getResult.errors;
    assertEquals(1, stringList.size());
    assertEquals("Could not get versions for null:null exception: null ", stringList.get(0));
    assertNull(getResult.artifactId);
    assertNull(getResult.groupId);
    assertNull(getResult.projectId);
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#findVersionsMismatches()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link StoreProjectData#StoreProjectData()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#findVersionsMismatches()}
   */
  @Test
  @DisplayName(
      "Test findVersionsMismatches(); given ArrayList() add StoreProjectData(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.findVersionsMismatches()"})
  void testFindVersionsMismatches_givenArrayListAddStoreProjectData_thenReturnEmpty() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<VersionMismatch> actualFindVersionsMismatchesResult =
        versionsReconciliationServiceImpl.findVersionsMismatches();

    // Assert
    verify(projectsService).find(null, null);
    verify(projectsService).getAllProjectCoordinates();
    assertTrue(actualFindVersionsMismatchesResult.isEmpty());
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#findVersionsMismatches()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link StoreProjectData#StoreProjectData()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#findVersionsMismatches()}
   */
  @Test
  @DisplayName(
      "Test findVersionsMismatches(); given ArrayList() add StoreProjectData(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.findVersionsMismatches()"})
  void testFindVersionsMismatches_givenArrayListAddStoreProjectData_thenReturnEmpty2() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());
    storeProjectDataList.add(new StoreProjectData());

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<VersionMismatch> actualFindVersionsMismatchesResult =
        versionsReconciliationServiceImpl.findVersionsMismatches();

    // Assert
    verify(projectsService, atLeast(1)).find(null, null);
    verify(projectsService).getAllProjectCoordinates();
    assertTrue(actualFindVersionsMismatchesResult.isEmpty());
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#findVersionsMismatches()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link
   *       StoreProjectVersionData#StoreProjectVersionData()}.
   * </ul>
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#findVersionsMismatches()}
   */
  @Test
  @DisplayName("Test findVersionsMismatches(); given ArrayList() add StoreProjectVersionData()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.findVersionsMismatches()"})
  void testFindVersionsMismatches_givenArrayListAddStoreProjectVersionData() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());

    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    storeProjectVersionDataList.add(new StoreProjectVersionData());

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(storeProjectVersionDataList);
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<VersionMismatch> actualFindVersionsMismatchesResult =
        versionsReconciliationServiceImpl.findVersionsMismatches();

    // Assert
    verify(projectsService).find(null, null);
    verify(projectsService).getAllProjectCoordinates();
    assertEquals(1, actualFindVersionsMismatchesResult.size());
    VersionMismatch getResult = actualFindVersionsMismatchesResult.get(0);
    List<String> stringList = getResult.errors;
    assertEquals(1, stringList.size());
    assertEquals("Could not get versions for null:null exception: null ", stringList.get(0));
    assertNull(getResult.artifactId);
    assertNull(getResult.groupId);
    assertNull(getResult.projectId);
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#findVersionsMismatches()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#findVersionsMismatches()}
   */
  @Test
  @DisplayName("Test findVersionsMismatches(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.findVersionsMismatches()"})
  void testFindVersionsMismatches_thenReturnEmpty() {
    // Arrange
    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.getAllProjectCoordinates()).thenReturn(new ArrayList<>());
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<VersionMismatch> actualFindVersionsMismatchesResult =
        versionsReconciliationServiceImpl.findVersionsMismatches();

    // Assert
    verify(projectsService).getAllProjectCoordinates();
    assertTrue(actualFindVersionsMismatchesResult.isEmpty());
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#findVersionsMismatches()}.
   *
   * <ul>
   *   <li>Then return first {@link VersionMismatch#artifactId} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#findVersionsMismatches()}
   */
  @Test
  @DisplayName("Test findVersionsMismatches(); then return first artifactId is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.findVersionsMismatches()"})
  void testFindVersionsMismatches_thenReturnFirstArtifactIdIs42() {
    // Arrange
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.getArtifactId()).thenReturn("42");
    when(storeProjectData.getGroupId()).thenReturn("42");
    when(storeProjectData.getProjectId()).thenReturn("myproject");

    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(storeProjectData);

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new IllegalArgumentException());
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<VersionMismatch> actualFindVersionsMismatchesResult =
        versionsReconciliationServiceImpl.findVersionsMismatches();

    // Assert
    verify(storeProjectData, atLeast(1)).getArtifactId();
    verify(storeProjectData, atLeast(1)).getGroupId();
    verify(projectsService).find("42", "42");
    verify(projectsService).getAllProjectCoordinates();
    verify(storeProjectData).getProjectId();
    assertEquals(1, actualFindVersionsMismatchesResult.size());
    VersionMismatch getResult = actualFindVersionsMismatchesResult.get(0);
    assertEquals("42", getResult.artifactId);
    assertEquals("42", getResult.groupId);
    List<String> stringList = getResult.errors;
    assertEquals(1, stringList.size());
    assertEquals("Could not get versions for 42:42 exception: null ", stringList.get(0));
    assertEquals("myproject", getResult.projectId);
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}.
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}
   */
  @Test
  @DisplayName("Test syncLatestProjectVersions()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.syncLatestProjectVersions()"})
  void testSyncLatestProjectVersions() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new IllegalArgumentException());
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<StoreProjectData> actualSyncLatestProjectVersionsResult =
        versionsReconciliationServiceImpl.syncLatestProjectVersions();

    // Assert
    verify(projectsService).find(null, null);
    verify(projectsService).getAllProjectCoordinates();
    assertTrue(actualSyncLatestProjectVersionsResult.isEmpty());
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}.
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}
   */
  @Test
  @DisplayName("Test syncLatestProjectVersions()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.syncLatestProjectVersions()"})
  void testSyncLatestProjectVersions2() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());

    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    StoreProjectVersionData storeProjectVersionData = new StoreProjectVersionData("42", "42", "42");
    storeProjectVersionDataList.add(storeProjectVersionData);

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.createOrUpdate(Mockito.<StoreProjectData>any()))
        .thenThrow(new IllegalArgumentException());
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(storeProjectVersionDataList);
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<StoreProjectData> actualSyncLatestProjectVersionsResult =
        versionsReconciliationServiceImpl.syncLatestProjectVersions();

    // Assert
    verify(projectsService).createOrUpdate(isA(StoreProjectData.class));
    verify(projectsService).find(null, null);
    verify(projectsService).getAllProjectCoordinates();
    assertTrue(actualSyncLatestProjectVersionsResult.isEmpty());
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}.
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}
   */
  @Test
  @DisplayName("Test syncLatestProjectVersions()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.syncLatestProjectVersions()"})
  void testSyncLatestProjectVersions3() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());

    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    StoreProjectVersionData storeProjectVersionData =
        new StoreProjectVersionData("42", "42", "42", true, new ProjectVersionData());
    storeProjectVersionDataList.add(storeProjectVersionData);

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(storeProjectVersionDataList);
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<StoreProjectData> actualSyncLatestProjectVersionsResult =
        versionsReconciliationServiceImpl.syncLatestProjectVersions();

    // Assert
    verify(projectsService).find(null, null);
    verify(projectsService).getAllProjectCoordinates();
    assertTrue(actualSyncLatestProjectVersionsResult.isEmpty());
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}.
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}
   */
  @Test
  @DisplayName("Test syncLatestProjectVersions()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.syncLatestProjectVersions()"})
  void testSyncLatestProjectVersions4() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    StoreProjectData storeProjectData =
        new StoreProjectData("myproject", "42", "42", "janedoe/featurebranch", "1.0.2");
    storeProjectDataList.add(storeProjectData);

    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    StoreProjectVersionData storeProjectVersionData = new StoreProjectVersionData("42", "42", "42");
    storeProjectVersionDataList.add(storeProjectVersionData);

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(storeProjectVersionDataList);
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<StoreProjectData> actualSyncLatestProjectVersionsResult =
        versionsReconciliationServiceImpl.syncLatestProjectVersions();

    // Assert
    verify(projectsService).find("42", "42");
    verify(projectsService).getAllProjectCoordinates();
    assertTrue(actualSyncLatestProjectVersionsResult.isEmpty());
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}.
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}
   */
  @Test
  @DisplayName("Test syncLatestProjectVersions()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.syncLatestProjectVersions()"})
  void testSyncLatestProjectVersions5() {
    // Arrange
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.getProjectId()).thenThrow(new IllegalArgumentException());
    when(storeProjectData.evaluateLatestVersionAndUpdate(Mockito.<String>any())).thenReturn(true);
    when(storeProjectData.getArtifactId()).thenReturn("42");
    when(storeProjectData.getGroupId()).thenReturn("42");

    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(storeProjectData);

    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    StoreProjectVersionData storeProjectVersionData = new StoreProjectVersionData("42", "42", "42");
    storeProjectVersionDataList.add(storeProjectVersionData);

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(storeProjectVersionDataList);
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<StoreProjectData> actualSyncLatestProjectVersionsResult =
        versionsReconciliationServiceImpl.syncLatestProjectVersions();

    // Assert
    verify(storeProjectData, atLeast(1)).getArtifactId();
    verify(storeProjectData, atLeast(1)).getGroupId();
    verify(projectsService).find("42", "42");
    verify(projectsService).getAllProjectCoordinates();
    verify(storeProjectData).evaluateLatestVersionAndUpdate("42");
    verify(storeProjectData).getProjectId();
    assertTrue(actualSyncLatestProjectVersionsResult.isEmpty());
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}.
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}
   */
  @Test
  @DisplayName("Test syncLatestProjectVersions()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.syncLatestProjectVersions()"})
  void testSyncLatestProjectVersions6() {
    // Arrange
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.getArtifactId()).thenReturn("42");
    when(storeProjectData.getGroupId()).thenReturn("42");

    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(storeProjectData);

    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    StoreProjectVersionData storeProjectVersionData =
        new StoreProjectVersionData("42", "42", "-SNAPSHOT");
    storeProjectVersionDataList.add(storeProjectVersionData);

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(storeProjectVersionDataList);
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<StoreProjectData> actualSyncLatestProjectVersionsResult =
        versionsReconciliationServiceImpl.syncLatestProjectVersions();

    // Assert
    verify(storeProjectData).getArtifactId();
    verify(storeProjectData).getGroupId();
    verify(projectsService).find("42", "42");
    verify(projectsService).getAllProjectCoordinates();
    assertTrue(actualSyncLatestProjectVersionsResult.isEmpty());
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}.
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}
   */
  @Test
  @DisplayName("Test syncLatestProjectVersions()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.syncLatestProjectVersions()"})
  void testSyncLatestProjectVersions7() {
    // Arrange
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.evaluateLatestVersionAndUpdate(Mockito.<String>any())).thenReturn(false);
    when(storeProjectData.getArtifactId()).thenReturn("42");
    when(storeProjectData.getGroupId()).thenReturn("42");

    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(storeProjectData);

    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    StoreProjectVersionData storeProjectVersionData = new StoreProjectVersionData("42", "42", "42");
    storeProjectVersionDataList.add(storeProjectVersionData);

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(storeProjectVersionDataList);
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<StoreProjectData> actualSyncLatestProjectVersionsResult =
        versionsReconciliationServiceImpl.syncLatestProjectVersions();

    // Assert
    verify(storeProjectData).getArtifactId();
    verify(storeProjectData).getGroupId();
    verify(projectsService).find("42", "42");
    verify(projectsService).getAllProjectCoordinates();
    verify(storeProjectData).evaluateLatestVersionAndUpdate("42");
    assertTrue(actualSyncLatestProjectVersionsResult.isEmpty());
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}.
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}
   */
  @Test
  @DisplayName("Test syncLatestProjectVersions()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.syncLatestProjectVersions()"})
  void testSyncLatestProjectVersions8() {
    // Arrange
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.getArtifactId()).thenReturn("42");
    when(storeProjectData.getGroupId()).thenReturn("42");

    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(storeProjectData);

    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    ProjectVersionData versionData =
        new ProjectVersionData(dependencies, new ArrayList<>(), true, true);
    StoreProjectVersionData storeProjectVersionData =
        new StoreProjectVersionData("42", "42", "42", true, versionData);
    storeProjectVersionDataList.add(storeProjectVersionData);

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(storeProjectVersionDataList);
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<StoreProjectData> actualSyncLatestProjectVersionsResult =
        versionsReconciliationServiceImpl.syncLatestProjectVersions();

    // Assert
    verify(storeProjectData).getArtifactId();
    verify(storeProjectData).getGroupId();
    verify(projectsService).find("42", "42");
    verify(projectsService).getAllProjectCoordinates();
    assertTrue(actualSyncLatestProjectVersionsResult.isEmpty());
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}
   */
  @Test
  @DisplayName("Test syncLatestProjectVersions(); given ArrayList() add 'null'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.syncLatestProjectVersions()"})
  void testSyncLatestProjectVersions_givenArrayListAddNull_thenReturnEmpty() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());

    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    storeProjectVersionDataList.add(null);

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(storeProjectVersionDataList);
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<StoreProjectData> actualSyncLatestProjectVersionsResult =
        versionsReconciliationServiceImpl.syncLatestProjectVersions();

    // Assert
    verify(projectsService).find(null, null);
    verify(projectsService).getAllProjectCoordinates();
    assertTrue(actualSyncLatestProjectVersionsResult.isEmpty());
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link StoreProjectData#StoreProjectData()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}
   */
  @Test
  @DisplayName(
      "Test syncLatestProjectVersions(); given ArrayList() add StoreProjectData(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.syncLatestProjectVersions()"})
  void testSyncLatestProjectVersions_givenArrayListAddStoreProjectData_thenReturnEmpty() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<StoreProjectData> actualSyncLatestProjectVersionsResult =
        versionsReconciliationServiceImpl.syncLatestProjectVersions();

    // Assert
    verify(projectsService).find(null, null);
    verify(projectsService).getAllProjectCoordinates();
    assertTrue(actualSyncLatestProjectVersionsResult.isEmpty());
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link StoreProjectData#StoreProjectData()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}
   */
  @Test
  @DisplayName(
      "Test syncLatestProjectVersions(); given ArrayList() add StoreProjectData(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.syncLatestProjectVersions()"})
  void testSyncLatestProjectVersions_givenArrayListAddStoreProjectData_thenReturnEmpty2() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());
    storeProjectDataList.add(new StoreProjectData());

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<StoreProjectData> actualSyncLatestProjectVersionsResult =
        versionsReconciliationServiceImpl.syncLatestProjectVersions();

    // Assert
    verify(projectsService, atLeast(1)).find(null, null);
    verify(projectsService).getAllProjectCoordinates();
    assertTrue(actualSyncLatestProjectVersionsResult.isEmpty());
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link
   *       StoreProjectVersionData#StoreProjectVersionData()}.
   * </ul>
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}
   */
  @Test
  @DisplayName("Test syncLatestProjectVersions(); given ArrayList() add StoreProjectVersionData()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.syncLatestProjectVersions()"})
  void testSyncLatestProjectVersions_givenArrayListAddStoreProjectVersionData() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());

    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    storeProjectVersionDataList.add(new StoreProjectVersionData());

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(storeProjectVersionDataList);
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<StoreProjectData> actualSyncLatestProjectVersionsResult =
        versionsReconciliationServiceImpl.syncLatestProjectVersions();

    // Assert
    verify(projectsService).find(null, null);
    verify(projectsService).getAllProjectCoordinates();
    assertTrue(actualSyncLatestProjectVersionsResult.isEmpty());
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link
   *       StoreProjectVersionData#StoreProjectVersionData()}.
   * </ul>
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}
   */
  @Test
  @DisplayName("Test syncLatestProjectVersions(); given ArrayList() add StoreProjectVersionData()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.syncLatestProjectVersions()"})
  void testSyncLatestProjectVersions_givenArrayListAddStoreProjectVersionData2() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());

    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    storeProjectVersionDataList.add(new StoreProjectVersionData());
    storeProjectVersionDataList.add(new StoreProjectVersionData());

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(storeProjectVersionDataList);
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<StoreProjectData> actualSyncLatestProjectVersionsResult =
        versionsReconciliationServiceImpl.syncLatestProjectVersions();

    // Assert
    verify(projectsService).find(null, null);
    verify(projectsService).getAllProjectCoordinates();
    assertTrue(actualSyncLatestProjectVersionsResult.isEmpty());
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}.
   *
   * <ul>
   *   <li>Given {@link StoreProjectData} {@link StoreProjectData#getProjectId()} return {@code
   *       myproject}.
   * </ul>
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}
   */
  @Test
  @DisplayName(
      "Test syncLatestProjectVersions(); given StoreProjectData getProjectId() return 'myproject'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.syncLatestProjectVersions()"})
  void testSyncLatestProjectVersions_givenStoreProjectDataGetProjectIdReturnMyproject() {
    // Arrange
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.getProjectId()).thenReturn("myproject");
    when(storeProjectData.evaluateLatestVersionAndUpdate(Mockito.<String>any())).thenReturn(true);
    when(storeProjectData.getArtifactId()).thenReturn("42");
    when(storeProjectData.getGroupId()).thenReturn("42");

    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(storeProjectData);

    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    StoreProjectVersionData storeProjectVersionData = new StoreProjectVersionData("42", "42", "42");
    storeProjectVersionDataList.add(storeProjectVersionData);

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.createOrUpdate(Mockito.<StoreProjectData>any()))
        .thenReturn(new StoreProjectData());
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(storeProjectVersionDataList);
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<StoreProjectData> actualSyncLatestProjectVersionsResult =
        versionsReconciliationServiceImpl.syncLatestProjectVersions();

    // Assert
    verify(storeProjectData, atLeast(1)).getArtifactId();
    verify(storeProjectData, atLeast(1)).getGroupId();
    verify(projectsService).createOrUpdate(isA(StoreProjectData.class));
    verify(projectsService).find("42", "42");
    verify(projectsService).getAllProjectCoordinates();
    verify(storeProjectData).evaluateLatestVersionAndUpdate("42");
    verify(storeProjectData).getProjectId();
    assertEquals(1, actualSyncLatestProjectVersionsResult.size());
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}.
   *
   * <ul>
   *   <li>Given {@link StoreProjectData} {@link StoreProjectData#getProjectId()} return {@code
   *       myproject}.
   * </ul>
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}
   */
  @Test
  @DisplayName(
      "Test syncLatestProjectVersions(); given StoreProjectData getProjectId() return 'myproject'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.syncLatestProjectVersions()"})
  void testSyncLatestProjectVersions_givenStoreProjectDataGetProjectIdReturnMyproject2() {
    // Arrange
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.getProjectId()).thenReturn("myproject");
    when(storeProjectData.evaluateLatestVersionAndUpdate(Mockito.<String>any())).thenReturn(true);
    when(storeProjectData.getArtifactId()).thenReturn("42");
    when(storeProjectData.getGroupId()).thenReturn("42");

    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(storeProjectData);

    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    StoreProjectVersionData storeProjectVersionData = new StoreProjectVersionData("42", "42", "42");
    storeProjectVersionDataList.add(storeProjectVersionData);
    StoreProjectVersionData storeProjectVersionData2 =
        new StoreProjectVersionData("42", "42", "42");
    storeProjectVersionDataList.add(storeProjectVersionData2);

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.createOrUpdate(Mockito.<StoreProjectData>any()))
        .thenReturn(new StoreProjectData());
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(storeProjectVersionDataList);
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<StoreProjectData> actualSyncLatestProjectVersionsResult =
        versionsReconciliationServiceImpl.syncLatestProjectVersions();

    // Assert
    verify(storeProjectData, atLeast(1)).getArtifactId();
    verify(storeProjectData, atLeast(1)).getGroupId();
    verify(projectsService).createOrUpdate(isA(StoreProjectData.class));
    verify(projectsService).find("42", "42");
    verify(projectsService).getAllProjectCoordinates();
    verify(storeProjectData).evaluateLatestVersionAndUpdate("42");
    verify(storeProjectData).getProjectId();
    assertEquals(1, actualSyncLatestProjectVersionsResult.size());
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}
   */
  @Test
  @DisplayName("Test syncLatestProjectVersions(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.syncLatestProjectVersions()"})
  void testSyncLatestProjectVersions_thenReturnEmpty() {
    // Arrange
    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.getAllProjectCoordinates()).thenReturn(new ArrayList<>());
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<StoreProjectData> actualSyncLatestProjectVersionsResult =
        versionsReconciliationServiceImpl.syncLatestProjectVersions();

    // Assert
    verify(projectsService).getAllProjectCoordinates();
    assertTrue(actualSyncLatestProjectVersionsResult.isEmpty());
  }

  /**
   * Test {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}.
   *
   * <ul>
   *   <li>Then return first is {@link StoreProjectData#StoreProjectData()}.
   * </ul>
   *
   * <p>Method under test: {@link VersionsReconciliationServiceImpl#syncLatestProjectVersions()}
   */
  @Test
  @DisplayName("Test syncLatestProjectVersions(); then return first is StoreProjectData()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List VersionsReconciliationServiceImpl.syncLatestProjectVersions()"})
  void testSyncLatestProjectVersions_thenReturnFirstIsStoreProjectData() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    StoreProjectData storeProjectData = new StoreProjectData();
    storeProjectDataList.add(storeProjectData);

    ArrayList<StoreProjectVersionData> storeProjectVersionDataList = new ArrayList<>();
    StoreProjectVersionData storeProjectVersionData = new StoreProjectVersionData("42", "42", "42");
    storeProjectVersionDataList.add(storeProjectVersionData);

    ManageProjectsServiceImpl projectsService = mock(ManageProjectsServiceImpl.class);
    when(projectsService.createOrUpdate(Mockito.<StoreProjectData>any()))
        .thenReturn(new StoreProjectData());
    when(projectsService.find(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(storeProjectVersionDataList);
    when(projectsService.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    VersionsReconciliationServiceImpl versionsReconciliationServiceImpl =
        new VersionsReconciliationServiceImpl(new TestMavenArtifactsRepository(), projectsService);

    // Act
    List<StoreProjectData> actualSyncLatestProjectVersionsResult =
        versionsReconciliationServiceImpl.syncLatestProjectVersions();

    // Assert
    verify(projectsService).createOrUpdate(isA(StoreProjectData.class));
    verify(projectsService).find(null, null);
    verify(projectsService).getAllProjectCoordinates();
    assertEquals(1, actualSyncLatestProjectVersionsResult.size());
    assertSame(storeProjectData, actualSyncLatestProjectVersionsResult.get(0));
  }
}
