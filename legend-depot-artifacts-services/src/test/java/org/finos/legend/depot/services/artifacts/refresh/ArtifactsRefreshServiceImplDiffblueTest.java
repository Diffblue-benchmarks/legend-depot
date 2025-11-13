package org.finos.legend.depot.services.artifacts.refresh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.finos.legend.depot.domain.notifications.MetadataNotificationResponse;
import org.finos.legend.depot.domain.notifications.MetadataNotificationStatus;
import org.finos.legend.depot.domain.project.ProjectVersionData;
import org.finos.legend.depot.services.api.notifications.queue.Queue;
import org.finos.legend.depot.services.api.notifications.queue.VoidQueue;
import org.finos.legend.depot.services.artifacts.repository.maven.TestMavenArtifactsRepository;
import org.finos.legend.depot.services.projects.ManageProjectsServiceImpl;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.finos.legend.sdlc.domain.model.version.VersionId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ArtifactsRefreshServiceImplDiffblueTest {
  /**
   * Test {@link ArtifactsRefreshServiceImpl#refreshAllVersionsForAllProjects(boolean, boolean,
   * boolean, String)}.
   *
   * <p>Method under test: {@link
   * ArtifactsRefreshServiceImpl#refreshAllVersionsForAllProjects(boolean, boolean, boolean,
   * String)}
   */
  @Test
  @DisplayName("Test refreshAllVersionsForAllProjects(boolean, boolean, boolean, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse ArtifactsRefreshServiceImpl.refreshAllVersionsForAllProjects(boolean, boolean, boolean, String)"
  })
  void testRefreshAllVersionsForAllProjects() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional.of(new StoreProjectData());
    when(projects.findCoordinates(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult2);
    when(projects.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();

    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(projects, repositoryServices, new VoidQueue());

    // Act
    MetadataNotificationResponse actualRefreshAllVersionsForAllProjectsResult =
        artifactsRefreshServiceImpl.refreshAllVersionsForAllProjects(true, true, true, "42");

    // Assert
    verify(projects).find(null, null, "head");
    verify(projects).findCoordinates(null, null);
    verify(projects).getAllProjectCoordinates();
    List<String> errors = actualRefreshAllVersionsForAllProjectsResult.getErrors();
    assertEquals(1, errors.size());
    assertEquals("invalid coordinates : [null-null] ", errors.get(0));
    List<String> messages = actualRefreshAllVersionsForAllProjectsResult.getMessages();
    assertEquals(3, messages.size());
    assertEquals(
        "queued: [null-null-null], parentEventId :[42], full/transitive :[true/true],event id :[null] ",
        messages.get(2));
    assertEquals(
        MetadataNotificationStatus.FAILED,
        actualRefreshAllVersionsForAllProjectsResult.getStatus());
    assertTrue(actualRefreshAllVersionsForAllProjectsResult.hasErrors());
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#refreshAllVersionsForAllProjects(boolean, boolean,
   * boolean, String)}.
   *
   * <p>Method under test: {@link
   * ArtifactsRefreshServiceImpl#refreshAllVersionsForAllProjects(boolean, boolean, boolean,
   * String)}
   */
  @Test
  @DisplayName("Test refreshAllVersionsForAllProjects(boolean, boolean, boolean, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse ArtifactsRefreshServiceImpl.refreshAllVersionsForAllProjects(boolean, boolean, boolean, String)"
  })
  void testRefreshAllVersionsForAllProjects2() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    StoreProjectVersionData storeProjectVersionData =
        new StoreProjectVersionData("42", "42", "42", true, new ProjectVersionData());
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional.of(new StoreProjectData());
    when(projects.findCoordinates(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult2);
    when(projects.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();

    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(projects, repositoryServices, new VoidQueue());

    // Act
    MetadataNotificationResponse actualRefreshAllVersionsForAllProjectsResult =
        artifactsRefreshServiceImpl.refreshAllVersionsForAllProjects(true, true, true, "42");

    // Assert
    verify(projects).find(null, null, "head");
    verify(projects).findCoordinates(null, null);
    verify(projects).getAllProjectCoordinates();
    List<String> errors = actualRefreshAllVersionsForAllProjectsResult.getErrors();
    assertEquals(1, errors.size());
    assertEquals("invalid coordinates : [null-null] ", errors.get(0));
    assertEquals(2, actualRefreshAllVersionsForAllProjectsResult.getMessages().size());
    assertEquals(
        MetadataNotificationStatus.FAILED,
        actualRefreshAllVersionsForAllProjectsResult.getStatus());
    assertTrue(actualRefreshAllVersionsForAllProjectsResult.hasErrors());
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#refreshAllVersionsForAllProjects(boolean, boolean,
   * boolean, String)}.
   *
   * <p>Method under test: {@link
   * ArtifactsRefreshServiceImpl#refreshAllVersionsForAllProjects(boolean, boolean, boolean,
   * String)}
   */
  @Test
  @DisplayName("Test refreshAllVersionsForAllProjects(boolean, boolean, boolean, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse ArtifactsRefreshServiceImpl.refreshAllVersionsForAllProjects(boolean, boolean, boolean, String)"
  })
  void testRefreshAllVersionsForAllProjects3() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    StoreProjectData storeProjectData = new StoreProjectData("myproject", "42", "42");
    Optional<StoreProjectData> ofResult2 = Optional.of(storeProjectData);
    when(projects.findCoordinates(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult2);
    when(projects.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();

    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(projects, repositoryServices, new VoidQueue());

    // Act
    MetadataNotificationResponse actualRefreshAllVersionsForAllProjectsResult =
        artifactsRefreshServiceImpl.refreshAllVersionsForAllProjects(true, true, true, "42");

    // Assert
    verify(projects).find("42", "42", "head");
    verify(projects).findCoordinates(null, null);
    verify(projects).getAllProjectCoordinates();
    List<String> messages = actualRefreshAllVersionsForAllProjectsResult.getMessages();
    assertEquals(3, messages.size());
    assertEquals(
        "queued: [42-42-null], parentEventId :[42], full/transitive :[true/true],event id :[null] ",
        messages.get(2));
    assertEquals(
        MetadataNotificationStatus.SUCCESS,
        actualRefreshAllVersionsForAllProjectsResult.getStatus());
    assertFalse(actualRefreshAllVersionsForAllProjectsResult.hasErrors());
    assertTrue(actualRefreshAllVersionsForAllProjectsResult.getErrors().isEmpty());
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#refreshAllVersionsForAllProjects(boolean, boolean,
   * boolean, String)}.
   *
   * <p>Method under test: {@link
   * ArtifactsRefreshServiceImpl#refreshAllVersionsForAllProjects(boolean, boolean, boolean,
   * String)}
   */
  @Test
  @DisplayName("Test refreshAllVersionsForAllProjects(boolean, boolean, boolean, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse ArtifactsRefreshServiceImpl.refreshAllVersionsForAllProjects(boolean, boolean, boolean, String)"
  })
  void testRefreshAllVersionsForAllProjects4() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    Optional<StoreProjectVersionData> emptyResult = Optional.empty();
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(emptyResult);
    Optional<StoreProjectData> ofResult = Optional.of(new StoreProjectData());
    when(projects.findCoordinates(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    when(projects.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(
            projects, new TestMavenArtifactsRepository(), mock(Queue.class));

    // Act
    MetadataNotificationResponse actualRefreshAllVersionsForAllProjectsResult =
        artifactsRefreshServiceImpl.refreshAllVersionsForAllProjects(true, true, true, "42");

    // Assert
    verify(projects).find(null, null, "head");
    verify(projects).findCoordinates(null, null);
    verify(projects).getAllProjectCoordinates();
    List<String> errors = actualRefreshAllVersionsForAllProjectsResult.getErrors();
    assertEquals(1, errors.size());
    assertEquals("invalid coordinates : [null-null] ", errors.get(0));
    assertEquals(2, actualRefreshAllVersionsForAllProjectsResult.getMessages().size());
    assertEquals(
        MetadataNotificationStatus.FAILED,
        actualRefreshAllVersionsForAllProjectsResult.getStatus());
    assertTrue(actualRefreshAllVersionsForAllProjectsResult.hasErrors());
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#refreshAllVersionsForAllProjects(boolean, boolean,
   * boolean, String)}.
   *
   * <ul>
   *   <li>Then return Messages size is five.
   * </ul>
   *
   * <p>Method under test: {@link
   * ArtifactsRefreshServiceImpl#refreshAllVersionsForAllProjects(boolean, boolean, boolean,
   * String)}
   */
  @Test
  @DisplayName(
      "Test refreshAllVersionsForAllProjects(boolean, boolean, boolean, String); then return Messages size is five")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse ArtifactsRefreshServiceImpl.refreshAllVersionsForAllProjects(boolean, boolean, boolean, String)"
  })
  void testRefreshAllVersionsForAllProjects_thenReturnMessagesSizeIsFive() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());
    storeProjectDataList.add(new StoreProjectData());

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional.of(new StoreProjectData());
    when(projects.findCoordinates(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult2);
    when(projects.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();

    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(projects, repositoryServices, new VoidQueue());

    // Act
    MetadataNotificationResponse actualRefreshAllVersionsForAllProjectsResult =
        artifactsRefreshServiceImpl.refreshAllVersionsForAllProjects(true, true, true, "42");

    // Assert
    verify(projects, atLeast(1)).find(null, null, "head");
    verify(projects, atLeast(1)).findCoordinates(null, null);
    verify(projects).getAllProjectCoordinates();
    List<String> messages = actualRefreshAllVersionsForAllProjectsResult.getMessages();
    assertEquals(5, messages.size());
    assertEquals(
        "Executing: [null-null-all], parentEventId :[42], full/allVersions/transitive :[true/true/true]",
        messages.get(3));
    List<String> errors = actualRefreshAllVersionsForAllProjectsResult.getErrors();
    assertEquals(2, errors.size());
    assertEquals("invalid coordinates : [null-null] ", errors.get(1));
    assertEquals(
        "queued: [null-null-null], parentEventId :[42], full/transitive :[true/true],event id :[null] ",
        messages.get(4));
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#refreshAllVersionsForAllProjects(boolean, boolean,
   * boolean, String)}.
   *
   * <ul>
   *   <li>Then return Messages size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ArtifactsRefreshServiceImpl#refreshAllVersionsForAllProjects(boolean, boolean, boolean,
   * String)}
   */
  @Test
  @DisplayName(
      "Test refreshAllVersionsForAllProjects(boolean, boolean, boolean, String); then return Messages size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse ArtifactsRefreshServiceImpl.refreshAllVersionsForAllProjects(boolean, boolean, boolean, String)"
  })
  void testRefreshAllVersionsForAllProjects_thenReturnMessagesSizeIsOne() {
    // Arrange
    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    when(projects.getAllProjectCoordinates()).thenReturn(new ArrayList<>());
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();

    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(projects, repositoryServices, new VoidQueue());

    // Act
    MetadataNotificationResponse actualRefreshAllVersionsForAllProjectsResult =
        artifactsRefreshServiceImpl.refreshAllVersionsForAllProjects(true, true, true, "42");

    // Assert
    verify(projects).getAllProjectCoordinates();
    assertEquals(1, actualRefreshAllVersionsForAllProjectsResult.getMessages().size());
    assertEquals(
        MetadataNotificationStatus.SUCCESS,
        actualRefreshAllVersionsForAllProjectsResult.getStatus());
    assertFalse(actualRefreshAllVersionsForAllProjectsResult.hasErrors());
    assertTrue(actualRefreshAllVersionsForAllProjectsResult.getErrors().isEmpty());
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#refreshDefaultSnapshotsForAllProjects(boolean, boolean,
   * String)}.
   *
   * <p>Method under test: {@link
   * ArtifactsRefreshServiceImpl#refreshDefaultSnapshotsForAllProjects(boolean, boolean, String)}
   */
  @Test
  @DisplayName("Test refreshDefaultSnapshotsForAllProjects(boolean, boolean, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse ArtifactsRefreshServiceImpl.refreshDefaultSnapshotsForAllProjects(boolean, boolean, String)"
  })
  void testRefreshDefaultSnapshotsForAllProjects() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    StoreProjectVersionData storeProjectVersionData =
        new StoreProjectVersionData("42", "42", "42", true, new ProjectVersionData());
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    when(projects.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();

    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(projects, repositoryServices, new VoidQueue());

    // Act
    MetadataNotificationResponse actualRefreshDefaultSnapshotsForAllProjectsResult =
        artifactsRefreshServiceImpl.refreshDefaultSnapshotsForAllProjects(true, true, "42");

    // Assert
    verify(projects).find(null, null, "head");
    verify(projects).getAllProjectCoordinates();
    List<String> messages = actualRefreshDefaultSnapshotsForAllProjectsResult.getMessages();
    assertEquals(1, messages.size());
    assertEquals(
        "Executing: [all-all-all-SNAPSHOT], parentEventId :[42], full/transitive :[true/true]",
        messages.get(0));
    assertEquals(
        MetadataNotificationStatus.SUCCESS,
        actualRefreshDefaultSnapshotsForAllProjectsResult.getStatus());
    assertFalse(actualRefreshDefaultSnapshotsForAllProjectsResult.hasErrors());
    assertTrue(actualRefreshDefaultSnapshotsForAllProjectsResult.getErrors().isEmpty());
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#refreshDefaultSnapshotsForAllProjects(boolean, boolean,
   * String)}.
   *
   * <p>Method under test: {@link
   * ArtifactsRefreshServiceImpl#refreshDefaultSnapshotsForAllProjects(boolean, boolean, String)}
   */
  @Test
  @DisplayName("Test refreshDefaultSnapshotsForAllProjects(boolean, boolean, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse ArtifactsRefreshServiceImpl.refreshDefaultSnapshotsForAllProjects(boolean, boolean, String)"
  })
  void testRefreshDefaultSnapshotsForAllProjects2() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    Optional<StoreProjectVersionData> emptyResult = Optional.empty();
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(emptyResult);
    when(projects.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();

    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(projects, repositoryServices, new VoidQueue());

    // Act
    MetadataNotificationResponse actualRefreshDefaultSnapshotsForAllProjectsResult =
        artifactsRefreshServiceImpl.refreshDefaultSnapshotsForAllProjects(true, true, "42");

    // Assert
    verify(projects).find(null, null, "head");
    verify(projects).getAllProjectCoordinates();
    List<String> messages = actualRefreshDefaultSnapshotsForAllProjectsResult.getMessages();
    assertEquals(1, messages.size());
    assertEquals(
        "Executing: [all-all-all-SNAPSHOT], parentEventId :[42], full/transitive :[true/true]",
        messages.get(0));
    assertEquals(
        MetadataNotificationStatus.SUCCESS,
        actualRefreshDefaultSnapshotsForAllProjectsResult.getStatus());
    assertFalse(actualRefreshDefaultSnapshotsForAllProjectsResult.hasErrors());
    assertTrue(actualRefreshDefaultSnapshotsForAllProjectsResult.getErrors().isEmpty());
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#refreshDefaultSnapshotsForAllProjects(boolean, boolean,
   * String)}.
   *
   * <p>Method under test: {@link
   * ArtifactsRefreshServiceImpl#refreshDefaultSnapshotsForAllProjects(boolean, boolean, String)}
   */
  @Test
  @DisplayName("Test refreshDefaultSnapshotsForAllProjects(boolean, boolean, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse ArtifactsRefreshServiceImpl.refreshDefaultSnapshotsForAllProjects(boolean, boolean, String)"
  })
  void testRefreshDefaultSnapshotsForAllProjects3() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());
    storeProjectDataList.add(new StoreProjectData());

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    Optional<StoreProjectVersionData> emptyResult = Optional.empty();
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(emptyResult);
    when(projects.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();

    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(projects, repositoryServices, new VoidQueue());

    // Act
    MetadataNotificationResponse actualRefreshDefaultSnapshotsForAllProjectsResult =
        artifactsRefreshServiceImpl.refreshDefaultSnapshotsForAllProjects(true, true, "42");

    // Assert
    verify(projects, atLeast(1)).find(null, null, "head");
    verify(projects).getAllProjectCoordinates();
    List<String> messages = actualRefreshDefaultSnapshotsForAllProjectsResult.getMessages();
    assertEquals(1, messages.size());
    assertEquals(
        "Executing: [all-all-all-SNAPSHOT], parentEventId :[42], full/transitive :[true/true]",
        messages.get(0));
    assertEquals(
        MetadataNotificationStatus.SUCCESS,
        actualRefreshDefaultSnapshotsForAllProjectsResult.getStatus());
    assertFalse(actualRefreshDefaultSnapshotsForAllProjectsResult.hasErrors());
    assertTrue(actualRefreshDefaultSnapshotsForAllProjectsResult.getErrors().isEmpty());
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#refreshDefaultSnapshotsForAllProjects(boolean, boolean,
   * String)}.
   *
   * <ul>
   *   <li>Then return Messages size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ArtifactsRefreshServiceImpl#refreshDefaultSnapshotsForAllProjects(boolean, boolean, String)}
   */
  @Test
  @DisplayName(
      "Test refreshDefaultSnapshotsForAllProjects(boolean, boolean, String); then return Messages size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse ArtifactsRefreshServiceImpl.refreshDefaultSnapshotsForAllProjects(boolean, boolean, String)"
  })
  void testRefreshDefaultSnapshotsForAllProjects_thenReturnMessagesSizeIsOne() {
    // Arrange
    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    when(projects.getAllProjectCoordinates()).thenReturn(new ArrayList<>());
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();

    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(projects, repositoryServices, new VoidQueue());

    // Act
    MetadataNotificationResponse actualRefreshDefaultSnapshotsForAllProjectsResult =
        artifactsRefreshServiceImpl.refreshDefaultSnapshotsForAllProjects(true, true, "42");

    // Assert
    verify(projects).getAllProjectCoordinates();
    List<String> messages = actualRefreshDefaultSnapshotsForAllProjectsResult.getMessages();
    assertEquals(1, messages.size());
    assertEquals(
        "Executing: [all-all-all-SNAPSHOT], parentEventId :[42], full/transitive :[true/true]",
        messages.get(0));
    assertEquals(
        MetadataNotificationStatus.SUCCESS,
        actualRefreshDefaultSnapshotsForAllProjectsResult.getStatus());
    assertFalse(actualRefreshDefaultSnapshotsForAllProjectsResult.hasErrors());
    assertTrue(actualRefreshDefaultSnapshotsForAllProjectsResult.getErrors().isEmpty());
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#refreshDefaultSnapshotsForAllProjects(boolean, boolean,
   * String)}.
   *
   * <ul>
   *   <li>Then return Messages size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * ArtifactsRefreshServiceImpl#refreshDefaultSnapshotsForAllProjects(boolean, boolean, String)}
   */
  @Test
  @DisplayName(
      "Test refreshDefaultSnapshotsForAllProjects(boolean, boolean, String); then return Messages size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse ArtifactsRefreshServiceImpl.refreshDefaultSnapshotsForAllProjects(boolean, boolean, String)"
  })
  void testRefreshDefaultSnapshotsForAllProjects_thenReturnMessagesSizeIsTwo() {
    // Arrange
    ArrayList<StoreProjectData> storeProjectDataList = new ArrayList<>();
    storeProjectDataList.add(new StoreProjectData());

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    when(projects.getAllProjectCoordinates()).thenReturn(storeProjectDataList);
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();

    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(projects, repositoryServices, new VoidQueue());

    // Act
    MetadataNotificationResponse actualRefreshDefaultSnapshotsForAllProjectsResult =
        artifactsRefreshServiceImpl.refreshDefaultSnapshotsForAllProjects(true, true, "42");

    // Assert
    verify(projects).find(null, null, "head");
    verify(projects).getAllProjectCoordinates();
    List<String> messages = actualRefreshDefaultSnapshotsForAllProjectsResult.getMessages();
    assertEquals(2, messages.size());
    assertEquals(
        "Executing: [all-all-all-SNAPSHOT], parentEventId :[42], full/transitive :[true/true]",
        messages.get(0));
    assertEquals(
        "queued: [null-null-null], parentEventId :[42], full/transitive :[true/true],event id :[null] ",
        messages.get(1));
    assertEquals(
        MetadataNotificationStatus.SUCCESS,
        actualRefreshDefaultSnapshotsForAllProjectsResult.getStatus());
    assertFalse(actualRefreshDefaultSnapshotsForAllProjectsResult.hasErrors());
    assertTrue(actualRefreshDefaultSnapshotsForAllProjectsResult.getErrors().isEmpty());
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#refreshAllVersionsForProject(String, String, boolean,
   * boolean, boolean, String)} with {@code groupId}, {@code artifactId}, {@code fullUpdate}, {@code
   * allVersions}, {@code transitive}, {@code parentEventId}.
   *
   * <p>Method under test: {@link ArtifactsRefreshServiceImpl#refreshAllVersionsForProject(String,
   * String, boolean, boolean, boolean, String)}
   */
  @Test
  @DisplayName(
      "Test refreshAllVersionsForProject(String, String, boolean, boolean, boolean, String) with 'groupId', 'artifactId', 'fullUpdate', 'allVersions', 'transitive', 'parentEventId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse ArtifactsRefreshServiceImpl.refreshAllVersionsForProject(String, String, boolean, boolean, boolean, String)"
  })
  void
      testRefreshAllVersionsForProjectWithGroupIdArtifactIdFullUpdateAllVersionsTransitiveParentEventId() {
    // Arrange
    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional.of(new StoreProjectData());
    when(projects.findCoordinates(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult2);
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();

    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(projects, repositoryServices, new VoidQueue());

    // Act
    MetadataNotificationResponse actualRefreshAllVersionsForProjectResult =
        artifactsRefreshServiceImpl.refreshAllVersionsForProject(
            "42", "42", true, true, true, "42");

    // Assert
    verify(projects).find(null, null, "head");
    verify(projects).findCoordinates("42", "42");
    List<String> messages = actualRefreshAllVersionsForProjectResult.getMessages();
    assertEquals(2, messages.size());
    assertEquals(
        "Executing: [42-42-all], parentEventId :[42], full/allVersions/transitive :[true/true/true]",
        messages.get(0));
    List<String> errors = actualRefreshAllVersionsForProjectResult.getErrors();
    assertEquals(1, errors.size());
    assertEquals("invalid coordinates : [null-null] ", errors.get(0));
    assertEquals(
        "queued: [null-null-null], parentEventId :[42], full/transitive :[true/true],event id :[null] ",
        messages.get(1));
    assertEquals(
        MetadataNotificationStatus.FAILED, actualRefreshAllVersionsForProjectResult.getStatus());
    assertTrue(actualRefreshAllVersionsForProjectResult.hasErrors());
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#refreshAllVersionsForProject(String, String, boolean,
   * boolean, boolean, String)} with {@code groupId}, {@code artifactId}, {@code fullUpdate}, {@code
   * allVersions}, {@code transitive}, {@code parentEventId}.
   *
   * <p>Method under test: {@link ArtifactsRefreshServiceImpl#refreshAllVersionsForProject(String,
   * String, boolean, boolean, boolean, String)}
   */
  @Test
  @DisplayName(
      "Test refreshAllVersionsForProject(String, String, boolean, boolean, boolean, String) with 'groupId', 'artifactId', 'fullUpdate', 'allVersions', 'transitive', 'parentEventId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse ArtifactsRefreshServiceImpl.refreshAllVersionsForProject(String, String, boolean, boolean, boolean, String)"
  })
  void
      testRefreshAllVersionsForProjectWithGroupIdArtifactIdFullUpdateAllVersionsTransitiveParentEventId2() {
    // Arrange
    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    StoreProjectVersionData storeProjectVersionData =
        new StoreProjectVersionData("42", "42", "42", true, new ProjectVersionData());
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<StoreProjectData> ofResult2 = Optional.of(new StoreProjectData());
    when(projects.findCoordinates(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult2);
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();

    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(projects, repositoryServices, new VoidQueue());

    // Act
    MetadataNotificationResponse actualRefreshAllVersionsForProjectResult =
        artifactsRefreshServiceImpl.refreshAllVersionsForProject(
            "42", "42", true, true, true, "42");

    // Assert
    verify(projects).find(null, null, "head");
    verify(projects).findCoordinates("42", "42");
    List<String> messages = actualRefreshAllVersionsForProjectResult.getMessages();
    assertEquals(1, messages.size());
    assertEquals(
        "Executing: [42-42-all], parentEventId :[42], full/allVersions/transitive :[true/true/true]",
        messages.get(0));
    List<String> errors = actualRefreshAllVersionsForProjectResult.getErrors();
    assertEquals(1, errors.size());
    assertEquals("invalid coordinates : [null-null] ", errors.get(0));
    assertEquals(
        MetadataNotificationStatus.FAILED, actualRefreshAllVersionsForProjectResult.getStatus());
    assertTrue(actualRefreshAllVersionsForProjectResult.hasErrors());
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#refreshAllVersionsForProject(String, String, boolean,
   * boolean, boolean, String)} with {@code groupId}, {@code artifactId}, {@code fullUpdate}, {@code
   * allVersions}, {@code transitive}, {@code parentEventId}.
   *
   * <p>Method under test: {@link ArtifactsRefreshServiceImpl#refreshAllVersionsForProject(String,
   * String, boolean, boolean, boolean, String)}
   */
  @Test
  @DisplayName(
      "Test refreshAllVersionsForProject(String, String, boolean, boolean, boolean, String) with 'groupId', 'artifactId', 'fullUpdate', 'allVersions', 'transitive', 'parentEventId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse ArtifactsRefreshServiceImpl.refreshAllVersionsForProject(String, String, boolean, boolean, boolean, String)"
  })
  void
      testRefreshAllVersionsForProjectWithGroupIdArtifactIdFullUpdateAllVersionsTransitiveParentEventId3() {
    // Arrange
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.getProjectId()).thenThrow(new IllegalArgumentException());
    Optional<StoreProjectData> ofResult = Optional.of(storeProjectData);

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    when(projects.findCoordinates(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();

    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(projects, repositoryServices, new VoidQueue());

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            artifactsRefreshServiceImpl.refreshAllVersionsForProject(
                "42", "42", true, true, true, "42"));
    verify(projects).findCoordinates("42", "42");
    verify(storeProjectData).getProjectId();
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#refreshAllVersionsForProject(String, String, boolean,
   * boolean, boolean, String)} with {@code groupId}, {@code artifactId}, {@code fullUpdate}, {@code
   * allVersions}, {@code transitive}, {@code parentEventId}.
   *
   * <p>Method under test: {@link ArtifactsRefreshServiceImpl#refreshAllVersionsForProject(String,
   * String, boolean, boolean, boolean, String)}
   */
  @Test
  @DisplayName(
      "Test refreshAllVersionsForProject(String, String, boolean, boolean, boolean, String) with 'groupId', 'artifactId', 'fullUpdate', 'allVersions', 'transitive', 'parentEventId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse ArtifactsRefreshServiceImpl.refreshAllVersionsForProject(String, String, boolean, boolean, boolean, String)"
  })
  void
      testRefreshAllVersionsForProjectWithGroupIdArtifactIdFullUpdateAllVersionsTransitiveParentEventId4() {
    // Arrange
    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    Optional<StoreProjectData> emptyResult = Optional.empty();
    when(projects.findCoordinates(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(emptyResult);
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();

    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(projects, repositoryServices, new VoidQueue());

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            artifactsRefreshServiceImpl.refreshAllVersionsForProject(
                "42", "42", true, true, true, "42"));
    verify(projects).findCoordinates("42", "42");
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#refreshVersionForProject(String, String, String,
   * boolean, boolean, String)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * fullUpdate}, {@code transitive}, {@code parentEventId}.
   *
   * <p>Method under test: {@link ArtifactsRefreshServiceImpl#refreshVersionForProject(String,
   * String, String, boolean, boolean, String)}
   */
  @Test
  @DisplayName(
      "Test refreshVersionForProject(String, String, String, boolean, boolean, String) with 'groupId', 'artifactId', 'versionId', 'fullUpdate', 'transitive', 'parentEventId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse ArtifactsRefreshServiceImpl.refreshVersionForProject(String, String, String, boolean, boolean, String)"
  })
  void
      testRefreshVersionForProjectWithGroupIdArtifactIdVersionIdFullUpdateTransitiveParentEventId() {
    // Arrange
    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    Optional<StoreProjectData> ofResult = Optional.of(new StoreProjectData());
    when(projects.findCoordinates(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();

    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(projects, repositoryServices, new VoidQueue());

    // Act
    MetadataNotificationResponse actualRefreshVersionForProjectResult =
        artifactsRefreshServiceImpl.refreshVersionForProject("42", "42", "42", true, true, "42");

    // Assert
    verify(projects).findCoordinates("42", "42");
    List<String> messages = actualRefreshVersionForProjectResult.getMessages();
    assertEquals(2, messages.size());
    assertEquals(
        "Executing: [42-42-42], parentEventId :[42], full/transitive :[true/true]",
        messages.get(0));
    assertEquals(
        "queued: [null-null-42], parentEventId :[42], full/transitive :[true/true],event id :[null] ",
        messages.get(1));
    assertEquals(
        MetadataNotificationStatus.SUCCESS, actualRefreshVersionForProjectResult.getStatus());
    assertFalse(actualRefreshVersionForProjectResult.hasErrors());
    assertTrue(actualRefreshVersionForProjectResult.getErrors().isEmpty());
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#refreshVersionForProject(String, String, String,
   * boolean, boolean, String)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * fullUpdate}, {@code transitive}, {@code parentEventId}.
   *
   * <p>Method under test: {@link ArtifactsRefreshServiceImpl#refreshVersionForProject(String,
   * String, String, boolean, boolean, String)}
   */
  @Test
  @DisplayName(
      "Test refreshVersionForProject(String, String, String, boolean, boolean, String) with 'groupId', 'artifactId', 'versionId', 'fullUpdate', 'transitive', 'parentEventId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse ArtifactsRefreshServiceImpl.refreshVersionForProject(String, String, String, boolean, boolean, String)"
  })
  void
      testRefreshVersionForProjectWithGroupIdArtifactIdVersionIdFullUpdateTransitiveParentEventId2() {
    // Arrange
    StoreProjectData storeProjectData = mock(StoreProjectData.class);
    when(storeProjectData.getProjectId()).thenThrow(new IllegalArgumentException());
    Optional<StoreProjectData> ofResult = Optional.of(storeProjectData);

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    when(projects.findCoordinates(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();

    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(projects, repositoryServices, new VoidQueue());

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            artifactsRefreshServiceImpl.refreshVersionForProject(
                "42", "42", "42", true, true, "42"));
    verify(projects).findCoordinates("42", "42");
    verify(storeProjectData).getProjectId();
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#refreshVersionForProject(String, String, String,
   * boolean, boolean, String)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * fullUpdate}, {@code transitive}, {@code parentEventId}.
   *
   * <p>Method under test: {@link ArtifactsRefreshServiceImpl#refreshVersionForProject(String,
   * String, String, boolean, boolean, String)}
   */
  @Test
  @DisplayName(
      "Test refreshVersionForProject(String, String, String, boolean, boolean, String) with 'groupId', 'artifactId', 'versionId', 'fullUpdate', 'transitive', 'parentEventId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse ArtifactsRefreshServiceImpl.refreshVersionForProject(String, String, String, boolean, boolean, String)"
  })
  void
      testRefreshVersionForProjectWithGroupIdArtifactIdVersionIdFullUpdateTransitiveParentEventId3() {
    // Arrange
    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    Optional<StoreProjectData> emptyResult = Optional.empty();
    when(projects.findCoordinates(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(emptyResult);
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();

    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(projects, repositoryServices, new VoidQueue());

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            artifactsRefreshServiceImpl.refreshVersionForProject(
                "42", "42", "42", true, true, "42"));
    verify(projects).findCoordinates("42", "42");
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#refreshVersionForProject(String, String, String,
   * boolean, boolean, String)} with {@code groupId}, {@code artifactId}, {@code versionId}, {@code
   * fullUpdate}, {@code transitive}, {@code parentEventId}.
   *
   * <p>Method under test: {@link ArtifactsRefreshServiceImpl#refreshVersionForProject(String,
   * String, String, boolean, boolean, String)}
   */
  @Test
  @DisplayName(
      "Test refreshVersionForProject(String, String, String, boolean, boolean, String) with 'groupId', 'artifactId', 'versionId', 'fullUpdate', 'transitive', 'parentEventId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MetadataNotificationResponse ArtifactsRefreshServiceImpl.refreshVersionForProject(String, String, String, boolean, boolean, String)"
  })
  void
      testRefreshVersionForProjectWithGroupIdArtifactIdVersionIdFullUpdateTransitiveParentEventId4() {
    // Arrange
    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    Optional<StoreProjectData> ofResult = Optional.of(new StoreProjectData());
    when(projects.findCoordinates(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();

    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(projects, repositoryServices, new VoidQueue());

    // Act
    MetadataNotificationResponse actualRefreshVersionForProjectResult =
        artifactsRefreshServiceImpl.refreshVersionForProject("42", "42", "42", true, true, null);

    // Assert
    verify(projects).findCoordinates("42", "42");
    List<String> messages = actualRefreshVersionForProjectResult.getMessages();
    assertEquals(2, messages.size());
    assertEquals(
        "Executing: [42-42-42], parentEventId :[42_42_42], full/transitive :[true/true]",
        messages.get(0));
    assertEquals(
        "queued: [null-null-42], parentEventId :[42_42_42], full/transitive :[true/true],event id :[null] ",
        messages.get(1));
    assertEquals(
        MetadataNotificationStatus.SUCCESS, actualRefreshVersionForProjectResult.getStatus());
    assertFalse(actualRefreshVersionForProjectResult.hasErrors());
    assertTrue(actualRefreshVersionForProjectResult.getErrors().isEmpty());
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#calculateCandidateVersions(List, List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then return {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ArtifactsRefreshServiceImpl#calculateCandidateVersions(List,
   * List)}
   */
  @Test
  @DisplayName(
      "Test calculateCandidateVersions(List, List); given '42'; when ArrayList() add '42'; then return ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ArtifactsRefreshServiceImpl.calculateCandidateVersions(List, List)"})
  void testCalculateCandidateVersions_given42_whenArrayListAdd42_thenReturnArrayList() {
    // Arrange
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();
    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(null, repositoryServices, new VoidQueue());

    VersionId versionId = mock(VersionId.class);
    when(versionId.toVersionIdString()).thenReturn("1.0.2");

    VersionId versionId2 = mock(VersionId.class);
    when(versionId2.toVersionIdString()).thenReturn("1.0.2");

    ArrayList<VersionId> repoVersions = new ArrayList<>();
    repoVersions.add(versionId2);
    repoVersions.add(versionId);

    ArrayList<String> versions = new ArrayList<>();
    versions.add("42");
    versions.add("foo");

    // Act
    List<VersionId> actualCalculateCandidateVersionsResult =
        artifactsRefreshServiceImpl.calculateCandidateVersions(repoVersions, versions);

    // Assert
    verify(versionId2).toVersionIdString();
    verify(versionId).toVersionIdString();
    assertEquals(repoVersions, actualCalculateCandidateVersionsResult);
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#calculateCandidateVersions(List, List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   *   <li>Then return {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ArtifactsRefreshServiceImpl#calculateCandidateVersions(List,
   * List)}
   */
  @Test
  @DisplayName(
      "Test calculateCandidateVersions(List, List); given 'foo'; when ArrayList() add 'foo'; then return ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ArtifactsRefreshServiceImpl.calculateCandidateVersions(List, List)"})
  void testCalculateCandidateVersions_givenFoo_whenArrayListAddFoo_thenReturnArrayList() {
    // Arrange
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();
    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(null, repositoryServices, new VoidQueue());

    VersionId versionId = mock(VersionId.class);
    when(versionId.toVersionIdString()).thenReturn("1.0.2");

    VersionId versionId2 = mock(VersionId.class);
    when(versionId2.toVersionIdString()).thenReturn("1.0.2");

    ArrayList<VersionId> repoVersions = new ArrayList<>();
    repoVersions.add(versionId2);
    repoVersions.add(versionId);

    ArrayList<String> versions = new ArrayList<>();
    versions.add("foo");

    // Act
    List<VersionId> actualCalculateCandidateVersionsResult =
        artifactsRefreshServiceImpl.calculateCandidateVersions(repoVersions, versions);

    // Assert
    verify(versionId2).toVersionIdString();
    verify(versionId).toVersionIdString();
    assertEquals(repoVersions, actualCalculateCandidateVersionsResult);
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#calculateCandidateVersions(List, List)}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link ArtifactsRefreshServiceImpl#calculateCandidateVersions(List,
   * List)}
   */
  @Test
  @DisplayName("Test calculateCandidateVersions(List, List); then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ArtifactsRefreshServiceImpl.calculateCandidateVersions(List, List)"})
  void testCalculateCandidateVersions_thenReturnSizeIsOne() {
    // Arrange
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();
    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(null, repositoryServices, new VoidQueue());

    VersionId versionId = mock(VersionId.class);
    when(versionId.toVersionIdString()).thenReturn("1.0.2");

    VersionId versionId2 = mock(VersionId.class);
    when(versionId2.toVersionIdString()).thenReturn("foo");

    ArrayList<VersionId> repoVersions = new ArrayList<>();
    repoVersions.add(versionId2);
    repoVersions.add(versionId);

    ArrayList<String> versions = new ArrayList<>();
    versions.add("foo");

    // Act
    List<VersionId> actualCalculateCandidateVersionsResult =
        artifactsRefreshServiceImpl.calculateCandidateVersions(repoVersions, versions);

    // Assert
    verify(versionId2).toVersionIdString();
    verify(versionId).toVersionIdString();
    assertEquals(1, actualCalculateCandidateVersionsResult.size());
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#calculateCandidateVersions(List, List)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ArtifactsRefreshServiceImpl#calculateCandidateVersions(List,
   * List)}
   */
  @Test
  @DisplayName("Test calculateCandidateVersions(List, List); then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ArtifactsRefreshServiceImpl.calculateCandidateVersions(List, List)"})
  void testCalculateCandidateVersions_thenThrowIllegalArgumentException() {
    // Arrange
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();
    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(null, repositoryServices, new VoidQueue());

    VersionId versionId = mock(VersionId.class);
    when(versionId.toVersionIdString()).thenThrow(new IllegalArgumentException());

    ArrayList<VersionId> repoVersions = new ArrayList<>();
    repoVersions.add(versionId);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            artifactsRefreshServiceImpl.calculateCandidateVersions(
                repoVersions, new ArrayList<>()));
    verify(versionId).toVersionIdString();
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#calculateCandidateVersions(List, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ArtifactsRefreshServiceImpl#calculateCandidateVersions(List,
   * List)}
   */
  @Test
  @DisplayName(
      "Test calculateCandidateVersions(List, List); when ArrayList(); then return ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ArtifactsRefreshServiceImpl.calculateCandidateVersions(List, List)"})
  void testCalculateCandidateVersions_whenArrayList_thenReturnArrayList() {
    // Arrange
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();
    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(null, repositoryServices, new VoidQueue());

    VersionId versionId = mock(VersionId.class);
    when(versionId.toVersionIdString()).thenReturn("1.0.2");

    ArrayList<VersionId> repoVersions = new ArrayList<>();
    repoVersions.add(versionId);

    // Act
    List<VersionId> actualCalculateCandidateVersionsResult =
        artifactsRefreshServiceImpl.calculateCandidateVersions(repoVersions, new ArrayList<>());

    // Assert
    verify(versionId).toVersionIdString();
    assertEquals(repoVersions, actualCalculateCandidateVersionsResult);
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#calculateCandidateVersions(List, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ArtifactsRefreshServiceImpl#calculateCandidateVersions(List,
   * List)}
   */
  @Test
  @DisplayName(
      "Test calculateCandidateVersions(List, List); when ArrayList(); then return ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ArtifactsRefreshServiceImpl.calculateCandidateVersions(List, List)"})
  void testCalculateCandidateVersions_whenArrayList_thenReturnArrayList2() {
    // Arrange
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();
    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(null, repositoryServices, new VoidQueue());

    VersionId versionId = mock(VersionId.class);
    when(versionId.toVersionIdString()).thenReturn("1.0.2");

    VersionId versionId2 = mock(VersionId.class);
    when(versionId2.toVersionIdString()).thenReturn("1.0.2");

    ArrayList<VersionId> repoVersions = new ArrayList<>();
    repoVersions.add(versionId2);
    repoVersions.add(versionId);

    // Act
    List<VersionId> actualCalculateCandidateVersionsResult =
        artifactsRefreshServiceImpl.calculateCandidateVersions(repoVersions, new ArrayList<>());

    // Assert
    verify(versionId2).toVersionIdString();
    verify(versionId).toVersionIdString();
    assertEquals(repoVersions, actualCalculateCandidateVersionsResult);
  }

  /**
   * Test {@link ArtifactsRefreshServiceImpl#calculateCandidateVersions(List, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ArtifactsRefreshServiceImpl#calculateCandidateVersions(List,
   * List)}
   */
  @Test
  @DisplayName("Test calculateCandidateVersions(List, List); when ArrayList(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ArtifactsRefreshServiceImpl.calculateCandidateVersions(List, List)"})
  void testCalculateCandidateVersions_whenArrayList_thenReturnEmpty() {
    // Arrange
    TestMavenArtifactsRepository repositoryServices = new TestMavenArtifactsRepository();
    ArtifactsRefreshServiceImpl artifactsRefreshServiceImpl =
        new ArtifactsRefreshServiceImpl(null, repositoryServices, new VoidQueue());
    ArrayList<VersionId> repoVersions = new ArrayList<>();

    // Act and Assert
    assertTrue(
        artifactsRefreshServiceImpl
            .calculateCandidateVersions(repoVersions, new ArrayList<>())
            .isEmpty());
  }
}
