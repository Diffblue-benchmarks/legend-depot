package org.finos.legend.depot.services.artifacts.refresh;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.eclipse.collections.api.block.function.Function2;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.domain.project.ProjectVersionData;
import org.finos.legend.depot.domain.project.dependencies.VersionDependencyReport;
import org.finos.legend.depot.services.api.artifacts.repository.VoidArtifactRepositoryConfiguration;
import org.finos.legend.depot.services.api.artifacts.repository.VoidArtifactRepositoryProvider;
import org.finos.legend.depot.services.api.dependencies.DependencyOverride;
import org.finos.legend.depot.services.artifacts.repository.maven.TestMavenArtifactsRepository;
import org.finos.legend.depot.services.projects.ManageProjectsServiceImpl;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RefreshDependenciesServiceImplDiffblueTest {
  /**
   * Test {@link RefreshDependenciesServiceImpl#retrieveDependenciesFromRepository(String, String,
   * String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#retrieveDependenciesFromRepository(String, String, String)}
   */
  @Test
  @DisplayName("Test retrieveDependenciesFromRepository(String, String, String); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List RefreshDependenciesServiceImpl.retrieveDependenciesFromRepository(String, String, String)"
  })
  void testRetrieveDependenciesFromRepository_thenReturnEmpty() {
    // Arrange
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            null,
            new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration()),
            mock(DependencyOverride.class));

    // Act and Assert
    assertTrue(
        refreshDependenciesServiceImpl
            .retrieveDependenciesFromRepository("42", "42", "42")
            .isEmpty());
  }

  /**
   * Test {@link RefreshDependenciesServiceImpl#validateDependencies(List, String)}.
   *
   * <p>Method under test: {@link RefreshDependenciesServiceImpl#validateDependencies(List, String)}
   */
  @Test
  @DisplayName("Test validateDependencies(List, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List RefreshDependenciesServiceImpl.validateDependencies(List, String)"})
  void testValidateDependencies() {
    // Arrange
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            null, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    ProjectVersion projectVersion = new ProjectVersion("42", "42", "42");
    dependencies.add(projectVersion);

    // Act and Assert
    assertTrue(
        refreshDependenciesServiceImpl.validateDependencies(dependencies, "1.0.2").isEmpty());
  }

  /**
   * Test {@link RefreshDependenciesServiceImpl#validateDependencies(List, String)}.
   *
   * <ul>
   *   <li>Given {@link ProjectVersion#ProjectVersion()}.
   *   <li>When {@code 42}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RefreshDependenciesServiceImpl#validateDependencies(List, String)}
   */
  @Test
  @DisplayName(
      "Test validateDependencies(List, String); given ProjectVersion(); when '42'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List RefreshDependenciesServiceImpl.validateDependencies(List, String)"})
  void testValidateDependencies_givenProjectVersion_when42_thenReturnEmpty() {
    // Arrange
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            null, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    dependencies.add(new ProjectVersion());

    // Act and Assert
    assertTrue(refreshDependenciesServiceImpl.validateDependencies(dependencies, "42").isEmpty());
  }

  /**
   * Test {@link RefreshDependenciesServiceImpl#validateDependencies(List, String)}.
   *
   * <ul>
   *   <li>Given {@link ProjectVersion#ProjectVersion()}.
   *   <li>When {@code 42}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RefreshDependenciesServiceImpl#validateDependencies(List, String)}
   */
  @Test
  @DisplayName(
      "Test validateDependencies(List, String); given ProjectVersion(); when '42'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List RefreshDependenciesServiceImpl.validateDependencies(List, String)"})
  void testValidateDependencies_givenProjectVersion_when42_thenReturnEmpty2() {
    // Arrange
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            null, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    dependencies.add(new ProjectVersion());
    dependencies.add(new ProjectVersion());

    // Act and Assert
    assertTrue(refreshDependenciesServiceImpl.validateDependencies(dependencies, "42").isEmpty());
  }

  /**
   * Test {@link RefreshDependenciesServiceImpl#validateDependencies(List, String)}.
   *
   * <ul>
   *   <li>Given {@link ProjectVersion#ProjectVersion()}.
   *   <li>When {@code List}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RefreshDependenciesServiceImpl#validateDependencies(List, String)}
   */
  @Test
  @DisplayName(
      "Test validateDependencies(List, String); given ProjectVersion(); when 'java.util.List'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List RefreshDependenciesServiceImpl.validateDependencies(List, String)"})
  void testValidateDependencies_givenProjectVersion_whenJavaUtilList_thenReturnEmpty() {
    // Arrange
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            null, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    dependencies.add(new ProjectVersion());

    // Act and Assert
    assertTrue(
        refreshDependenciesServiceImpl
            .validateDependencies(dependencies, "java.util.List")
            .isEmpty());
  }

  /**
   * Test {@link RefreshDependenciesServiceImpl#validateDependencies(List, String)}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link RefreshDependenciesServiceImpl#validateDependencies(List, String)}
   */
  @Test
  @DisplayName("Test validateDependencies(List, String); then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List RefreshDependenciesServiceImpl.validateDependencies(List, String)"})
  void testValidateDependencies_thenReturnSizeIsOne() {
    // Arrange
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            null, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    ProjectVersion projectVersion = new ProjectVersion("42", "42", "-SNAPSHOT");
    dependencies.add(projectVersion);

    // Act
    List<String> actualValidateDependenciesResult =
        refreshDependenciesServiceImpl.validateDependencies(dependencies, "1.0.2");

    // Assert
    assertEquals(1, actualValidateDependenciesResult.size());
    assertEquals(
        "Snapshot dependency 42-42--SNAPSHOT not allowed in versions",
        actualValidateDependenciesResult.get(0));
  }

  /**
   * Test {@link RefreshDependenciesServiceImpl#validateDependencies(List, String)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link RefreshDependenciesServiceImpl#validateDependencies(List, String)}
   */
  @Test
  @DisplayName("Test validateDependencies(List, String); when ArrayList(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List RefreshDependenciesServiceImpl.validateDependencies(List, String)"})
  void testValidateDependencies_whenArrayList_thenReturnEmpty() {
    // Arrange
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            null, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    // Act and Assert
    assertTrue(
        refreshDependenciesServiceImpl.validateDependencies(new ArrayList<>(), "42").isEmpty());
  }

  /**
   * Test {@link RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String,
   * String)}.
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String, String)}
   */
  @Test
  @DisplayName("Test updateTransitiveDependencies(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StoreProjectVersionData RefreshDependenciesServiceImpl.updateTransitiveDependencies(String, String, String)"
  })
  void testUpdateTransitiveDependencies() {
    // Arrange
    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    when(projects.createOrUpdate(Mockito.<StoreProjectVersionData>any()))
        .thenThrow(new IllegalStateException());
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            projects, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> refreshDependenciesServiceImpl.updateTransitiveDependencies("42", "42", "42"));
    verify(projects).createOrUpdate(isA(StoreProjectVersionData.class));
    verify(projects).find("42", "42", "42");
  }

  /**
   * Test {@link RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String,
   * String)}.
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String, String)}
   */
  @Test
  @DisplayName("Test updateTransitiveDependencies(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StoreProjectVersionData RefreshDependenciesServiceImpl.updateTransitiveDependencies(String, String, String)"
  })
  void testUpdateTransitiveDependencies2() {
    // Arrange
    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    StoreProjectVersionData storeProjectVersionData = new StoreProjectVersionData("42", "42", "42");
    when(projects.createOrUpdate(Mockito.<StoreProjectVersionData>any()))
        .thenReturn(storeProjectVersionData);
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            projects, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    // Act
    StoreProjectVersionData actualUpdateTransitiveDependenciesResult =
        refreshDependenciesServiceImpl.updateTransitiveDependencies("42", "42", "42");

    // Assert
    verify(projects).createOrUpdate(isA(StoreProjectVersionData.class));
    verify(projects).find("42", "42", "42");
    assertSame(storeProjectVersionData, actualUpdateTransitiveDependenciesResult);
  }

  /**
   * Test {@link RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String,
   * String)}.
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String, String)}
   */
  @Test
  @DisplayName("Test updateTransitiveDependencies(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StoreProjectVersionData RefreshDependenciesServiceImpl.updateTransitiveDependencies(String, String, String)"
  })
  void testUpdateTransitiveDependencies3() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionData()).thenThrow(new IllegalStateException());
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            projects, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> refreshDependenciesServiceImpl.updateTransitiveDependencies("42", "42", "42"));
    verify(projects).find("42", "42", "42");
    verify(storeProjectVersionData).getVersionData();
  }

  /**
   * Test {@link RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String,
   * String)}.
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String, String)}
   */
  @Test
  @DisplayName("Test updateTransitiveDependencies(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StoreProjectVersionData RefreshDependenciesServiceImpl.updateTransitiveDependencies(String, String, String)"
  })
  void testUpdateTransitiveDependencies4() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    ProjectVersionData projectVersionData =
        new ProjectVersionData(dependencies, new ArrayList<>(), true, true);
    when(storeProjectVersionData.getVersionData()).thenReturn(projectVersionData);
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            projects, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> refreshDependenciesServiceImpl.updateTransitiveDependencies("42", "42", "42"));
    verify(projects).find("42", "42", "42");
    verify(storeProjectVersionData).getVersionData();
  }

  /**
   * Test {@link RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String,
   * String)}.
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String, String)}
   */
  @Test
  @DisplayName("Test updateTransitiveDependencies(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StoreProjectVersionData RefreshDependenciesServiceImpl.updateTransitiveDependencies(String, String, String)"
  })
  void testUpdateTransitiveDependencies5() {
    // Arrange
    ProjectVersionData projectVersionData = new ProjectVersionData();
    projectVersionData.addDependency(new ProjectVersion());

    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getTransitiveDependenciesReport())
        .thenThrow(new IllegalStateException());
    doThrow(new IllegalStateException())
        .when(storeProjectVersionData)
        .setTransitiveDependenciesReport(Mockito.<VersionDependencyReport>any());
    when(storeProjectVersionData.getVersionData()).thenReturn(projectVersionData);
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            projects, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> refreshDependenciesServiceImpl.updateTransitiveDependencies("42", "42", "42"));
    verify(projects, atLeast(1))
        .find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(storeProjectVersionData).getTransitiveDependenciesReport();
    verify(storeProjectVersionData, atLeast(1)).getVersionData();
    verify(storeProjectVersionData)
        .setTransitiveDependenciesReport(isA(VersionDependencyReport.class));
  }

  /**
   * Test {@link RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String,
   * String)}.
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String, String)}
   */
  @Test
  @DisplayName("Test updateTransitiveDependencies(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StoreProjectVersionData RefreshDependenciesServiceImpl.updateTransitiveDependencies(String, String, String)"
  })
  void testUpdateTransitiveDependencies6() {
    // Arrange
    ProjectVersionData projectVersionData = new ProjectVersionData();
    projectVersionData.addDependency(new ProjectVersion());

    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getTransitiveDependenciesReport())
        .thenThrow(new IllegalArgumentException());
    when(storeProjectVersionData.getVersionData()).thenReturn(projectVersionData);
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            projects, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> refreshDependenciesServiceImpl.updateTransitiveDependencies("42", "42", "42"));
    verify(projects, atLeast(1))
        .find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(storeProjectVersionData).getTransitiveDependenciesReport();
    verify(storeProjectVersionData, atLeast(1)).getVersionData();
  }

  /**
   * Test {@link RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String,
   * String)}.
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String, String)}
   */
  @Test
  @DisplayName("Test updateTransitiveDependencies(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StoreProjectVersionData RefreshDependenciesServiceImpl.updateTransitiveDependencies(String, String, String)"
  })
  void testUpdateTransitiveDependencies7() {
    // Arrange
    ProjectVersionData projectVersionData = new ProjectVersionData();
    projectVersionData.addDependency(new ProjectVersion());

    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getTransitiveDependenciesReport())
        .thenReturn(new VersionDependencyReport());
    doThrow(new IllegalStateException())
        .when(storeProjectVersionData)
        .setTransitiveDependenciesReport(Mockito.<VersionDependencyReport>any());
    when(storeProjectVersionData.getVersionData()).thenReturn(projectVersionData);
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);

    DependencyOverride dependencyOverride = mock(DependencyOverride.class);
    when(dependencyOverride.overrideWith(
            Mockito.<List<ProjectVersion>>any(),
            Mockito.<List<ProjectVersion>>any(),
            Mockito.<Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>>>any()))
        .thenThrow(new IllegalStateException());

    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            projects, new TestMavenArtifactsRepository(), dependencyOverride);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> refreshDependenciesServiceImpl.updateTransitiveDependencies("42", "42", "42"));
    verify(dependencyOverride).overrideWith(isA(List.class), isA(List.class), isA(Function2.class));
    verify(projects, atLeast(1))
        .find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(storeProjectVersionData, atLeast(1)).getTransitiveDependenciesReport();
    verify(storeProjectVersionData, atLeast(1)).getVersionData();
    verify(storeProjectVersionData)
        .setTransitiveDependenciesReport(isA(VersionDependencyReport.class));
  }

  /**
   * Test {@link RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String,
   * String)}.
   *
   * <ul>
   *   <li>Given {@link ManageProjectsServiceImpl} {@link ManageProjectsServiceImpl#find(String,
   *       String, String)} return empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test updateTransitiveDependencies(String, String, String); given ManageProjectsServiceImpl find(String, String, String) return empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StoreProjectVersionData RefreshDependenciesServiceImpl.updateTransitiveDependencies(String, String, String)"
  })
  void testUpdateTransitiveDependencies_givenManageProjectsServiceImplFindReturnEmpty() {
    // Arrange
    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    Optional<StoreProjectVersionData> emptyResult = Optional.empty();
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(emptyResult);
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            projects, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> refreshDependenciesServiceImpl.updateTransitiveDependencies("42", "42", "42"));
    verify(projects).find("42", "42", "42");
  }

  /**
   * Test {@link RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String,
   * String)}.
   *
   * <ul>
   *   <li>Then calls {@link VersionDependencyReport#isValid()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String, String)}
   */
  @Test
  @DisplayName("Test updateTransitiveDependencies(String, String, String); then calls isValid()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StoreProjectVersionData RefreshDependenciesServiceImpl.updateTransitiveDependencies(String, String, String)"
  })
  void testUpdateTransitiveDependencies_thenCallsIsValid() {
    // Arrange
    ProjectVersionData projectVersionData = new ProjectVersionData();
    projectVersionData.addDependency(new ProjectVersion());

    VersionDependencyReport versionDependencyReport = mock(VersionDependencyReport.class);
    when(versionDependencyReport.isValid()).thenReturn(false);

    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getTransitiveDependenciesReport())
        .thenReturn(versionDependencyReport);
    doThrow(new IllegalStateException())
        .when(storeProjectVersionData)
        .setTransitiveDependenciesReport(Mockito.<VersionDependencyReport>any());
    when(storeProjectVersionData.getVersionData()).thenReturn(projectVersionData);
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            projects, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> refreshDependenciesServiceImpl.updateTransitiveDependencies("42", "42", "42"));
    verify(versionDependencyReport).isValid();
    verify(projects, atLeast(1))
        .find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(storeProjectVersionData).getTransitiveDependenciesReport();
    verify(storeProjectVersionData, atLeast(1)).getVersionData();
    verify(storeProjectVersionData)
        .setTransitiveDependenciesReport(isA(VersionDependencyReport.class));
  }

  /**
   * Test {@link RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String,
   * String)}.
   *
   * <ul>
   *   <li>Then calls {@link DependencyOverride#overrideWith(List, List, Function2)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test updateTransitiveDependencies(String, String, String); then calls overrideWith(List, List, Function2)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StoreProjectVersionData RefreshDependenciesServiceImpl.updateTransitiveDependencies(String, String, String)"
  })
  void testUpdateTransitiveDependencies_thenCallsOverrideWith() {
    // Arrange
    ProjectVersionData projectVersionData = new ProjectVersionData();
    projectVersionData.addDependency(new ProjectVersion());

    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getTransitiveDependenciesReport())
        .thenReturn(new VersionDependencyReport());
    doThrow(new IllegalStateException())
        .when(storeProjectVersionData)
        .setTransitiveDependenciesReport(Mockito.<VersionDependencyReport>any());
    when(storeProjectVersionData.getVersionData()).thenReturn(projectVersionData);
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);

    DependencyOverride dependencyOverride = mock(DependencyOverride.class);
    when(dependencyOverride.overrideWith(
            Mockito.<List<ProjectVersion>>any(),
            Mockito.<List<ProjectVersion>>any(),
            Mockito.<Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>>>any()))
        .thenReturn(new ArrayList<>());

    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            projects, new TestMavenArtifactsRepository(), dependencyOverride);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> refreshDependenciesServiceImpl.updateTransitiveDependencies("42", "42", "42"));
    verify(dependencyOverride).overrideWith(isA(List.class), isA(List.class), isA(Function2.class));
    verify(projects, atLeast(1))
        .find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(storeProjectVersionData, atLeast(1)).getTransitiveDependenciesReport();
    verify(storeProjectVersionData, atLeast(1)).getVersionData();
    verify(storeProjectVersionData)
        .setTransitiveDependenciesReport(isA(VersionDependencyReport.class));
  }

  /**
   * Test {@link RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String,
   * String)}.
   *
   * <ul>
   *   <li>Then calls {@link
   *       StoreProjectVersionData#setTransitiveDependenciesReport(VersionDependencyReport)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#updateTransitiveDependencies(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test updateTransitiveDependencies(String, String, String); then calls setTransitiveDependenciesReport(VersionDependencyReport)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StoreProjectVersionData RefreshDependenciesServiceImpl.updateTransitiveDependencies(String, String, String)"
  })
  void testUpdateTransitiveDependencies_thenCallsSetTransitiveDependenciesReport() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    doThrow(new IllegalStateException())
        .when(storeProjectVersionData)
        .setTransitiveDependenciesReport(Mockito.<VersionDependencyReport>any());
    when(storeProjectVersionData.getVersionData()).thenReturn(new ProjectVersionData());
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            projects, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> refreshDependenciesServiceImpl.updateTransitiveDependencies("42", "42", "42"));
    verify(projects).find("42", "42", "42");
    verify(storeProjectVersionData, atLeast(1)).getVersionData();
    verify(storeProjectVersionData)
        .setTransitiveDependenciesReport(isA(VersionDependencyReport.class));
  }

  /**
   * Test {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}.
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}
   */
  @Test
  @DisplayName("Test setProjectDataTransitiveDependencies(StoreProjectVersionData)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RefreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(StoreProjectVersionData)"
  })
  void testSetProjectDataTransitiveDependencies() {
    // Arrange
    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);

    DependencyOverride dependencyOverride = mock(DependencyOverride.class);
    when(dependencyOverride.overrideWith(
            Mockito.<List<ProjectVersion>>any(),
            Mockito.<List<ProjectVersion>>any(),
            Mockito.<Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>>>any()))
        .thenReturn(new ArrayList<>());

    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            projects, new TestMavenArtifactsRepository(), dependencyOverride);

    ProjectVersion dependency = mock(ProjectVersion.class);
    when(dependency.getGroupId()).thenThrow(new IllegalStateException());

    ProjectVersionData projectVersionData = new ProjectVersionData();
    projectVersionData.addDependency(new ProjectVersion());
    projectVersionData.addDependency(dependency);

    StoreProjectVersionData projectData = mock(StoreProjectVersionData.class);
    doThrow(new IllegalStateException())
        .when(projectData)
        .setTransitiveDependenciesReport(Mockito.<VersionDependencyReport>any());
    when(projectData.getVersionData()).thenReturn(projectVersionData);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> refreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(projectData));
    verify(dependency).getGroupId();
    verify(dependencyOverride).overrideWith(isA(List.class), isA(List.class), isA(Function2.class));
    verify(projects).find(null, null, null);
    verify(projectData).getVersionData();
    verify(projectData).setTransitiveDependenciesReport(isA(VersionDependencyReport.class));
  }

  /**
   * Test {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}.
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}
   */
  @Test
  @DisplayName("Test setProjectDataTransitiveDependencies(StoreProjectVersionData)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RefreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(StoreProjectVersionData)"
  })
  void testSetProjectDataTransitiveDependencies2() {
    // Arrange
    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    Optional<StoreProjectVersionData> ofResult = Optional.of(new StoreProjectVersionData());
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);

    DependencyOverride dependencyOverride = mock(DependencyOverride.class);
    when(dependencyOverride.overrideWith(
            Mockito.<List<ProjectVersion>>any(),
            Mockito.<List<ProjectVersion>>any(),
            Mockito.<Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>>>any()))
        .thenThrow(new IllegalStateException());

    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            projects, new TestMavenArtifactsRepository(), dependencyOverride);

    ProjectVersionData projectVersionData = new ProjectVersionData();
    projectVersionData.addDependency(new ProjectVersion());
    projectVersionData.addDependency(mock(ProjectVersion.class));

    StoreProjectVersionData projectData = mock(StoreProjectVersionData.class);
    doThrow(new IllegalStateException())
        .when(projectData)
        .setTransitiveDependenciesReport(Mockito.<VersionDependencyReport>any());
    when(projectData.getVersionData()).thenReturn(projectVersionData);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> refreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(projectData));
    verify(dependencyOverride).overrideWith(isA(List.class), isA(List.class), isA(Function2.class));
    verify(projects).find(null, null, null);
    verify(projectData).getVersionData();
    verify(projectData).setTransitiveDependenciesReport(isA(VersionDependencyReport.class));
  }

  /**
   * Test {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}.
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}
   */
  @Test
  @DisplayName("Test setProjectDataTransitiveDependencies(StoreProjectVersionData)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RefreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(StoreProjectVersionData)"
  })
  void testSetProjectDataTransitiveDependencies3() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getVersionData()).thenThrow(new IllegalStateException());
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            projects, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    ProjectVersionData projectVersionData = new ProjectVersionData();
    projectVersionData.addDependency(new ProjectVersion());
    projectVersionData.addDependency(mock(ProjectVersion.class));

    StoreProjectVersionData projectData = mock(StoreProjectVersionData.class);
    doThrow(new IllegalStateException())
        .when(projectData)
        .setTransitiveDependenciesReport(Mockito.<VersionDependencyReport>any());
    when(projectData.getVersionData()).thenReturn(projectVersionData);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> refreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(projectData));
    verify(projects).find(null, null, null);
    verify(storeProjectVersionData).getVersionData();
    verify(projectData).getVersionData();
    verify(projectData).setTransitiveDependenciesReport(isA(VersionDependencyReport.class));
  }

  /**
   * Test {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}.
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}
   */
  @Test
  @DisplayName("Test setProjectDataTransitiveDependencies(StoreProjectVersionData)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RefreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(StoreProjectVersionData)"
  })
  void testSetProjectDataTransitiveDependencies4() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getTransitiveDependenciesReport())
        .thenThrow(new IllegalStateException());
    when(storeProjectVersionData.getVersionData()).thenReturn(new ProjectVersionData());
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            projects, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    ProjectVersionData projectVersionData = new ProjectVersionData();
    projectVersionData.addDependency(new ProjectVersion());
    projectVersionData.addDependency(mock(ProjectVersion.class));

    StoreProjectVersionData projectData = mock(StoreProjectVersionData.class);
    doThrow(new IllegalStateException())
        .when(projectData)
        .setTransitiveDependenciesReport(Mockito.<VersionDependencyReport>any());
    when(projectData.getVersionData()).thenReturn(projectVersionData);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> refreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(projectData));
    verify(projects).find(null, null, null);
    verify(storeProjectVersionData).getTransitiveDependenciesReport();
    verify(storeProjectVersionData).getVersionData();
    verify(projectData).getVersionData();
    verify(projectData).setTransitiveDependenciesReport(isA(VersionDependencyReport.class));
  }

  /**
   * Test {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}.
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}
   */
  @Test
  @DisplayName("Test setProjectDataTransitiveDependencies(StoreProjectVersionData)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RefreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(StoreProjectVersionData)"
  })
  void testSetProjectDataTransitiveDependencies5() {
    // Arrange
    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    ProjectVersionData projectVersionData =
        new ProjectVersionData(dependencies, new ArrayList<>(), true, true);
    when(storeProjectVersionData.getVersionData()).thenReturn(projectVersionData);
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            projects, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    ProjectVersionData projectVersionData2 = new ProjectVersionData();
    projectVersionData2.addDependency(new ProjectVersion());
    projectVersionData2.addDependency(mock(ProjectVersion.class));

    StoreProjectVersionData projectData = mock(StoreProjectVersionData.class);
    doThrow(new IllegalStateException())
        .when(projectData)
        .setTransitiveDependenciesReport(Mockito.<VersionDependencyReport>any());
    when(projectData.getVersionData()).thenReturn(projectVersionData2);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> refreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(projectData));
    verify(projects).find(null, null, null);
    verify(storeProjectVersionData).getVersionData();
    verify(projectData).getVersionData();
    verify(projectData).setTransitiveDependenciesReport(isA(VersionDependencyReport.class));
  }

  /**
   * Test {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}.
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}
   */
  @Test
  @DisplayName("Test setProjectDataTransitiveDependencies(StoreProjectVersionData)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RefreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(StoreProjectVersionData)"
  })
  void testSetProjectDataTransitiveDependencies6() {
    // Arrange
    VersionDependencyReport versionDependencyReport = mock(VersionDependencyReport.class);
    when(versionDependencyReport.isValid()).thenReturn(false);

    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getTransitiveDependenciesReport())
        .thenReturn(versionDependencyReport);
    when(storeProjectVersionData.getVersionData()).thenReturn(new ProjectVersionData());
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            projects, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    ProjectVersionData projectVersionData = new ProjectVersionData();
    projectVersionData.addDependency(new ProjectVersion());
    projectVersionData.addDependency(mock(ProjectVersion.class));

    StoreProjectVersionData projectData = mock(StoreProjectVersionData.class);
    doThrow(new IllegalStateException())
        .when(projectData)
        .setTransitiveDependenciesReport(Mockito.<VersionDependencyReport>any());
    when(projectData.getVersionData()).thenReturn(projectVersionData);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> refreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(projectData));
    verify(versionDependencyReport).isValid();
    verify(projects).find(null, null, null);
    verify(storeProjectVersionData).getTransitiveDependenciesReport();
    verify(storeProjectVersionData).getVersionData();
    verify(projectData).getVersionData();
    verify(projectData).setTransitiveDependenciesReport(isA(VersionDependencyReport.class));
  }

  /**
   * Test {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}.
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}
   */
  @Test
  @DisplayName("Test setProjectDataTransitiveDependencies(StoreProjectVersionData)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RefreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(StoreProjectVersionData)"
  })
  void testSetProjectDataTransitiveDependencies7() {
    // Arrange
    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    Optional<StoreProjectVersionData> emptyResult = Optional.empty();
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(emptyResult);

    DependencyOverride dependencyOverride = mock(DependencyOverride.class);
    when(dependencyOverride.overrideWith(
            Mockito.<List<ProjectVersion>>any(),
            Mockito.<List<ProjectVersion>>any(),
            Mockito.<Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>>>any()))
        .thenReturn(new ArrayList<>());

    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            projects,
            new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration()),
            dependencyOverride);

    ProjectVersion dependency = mock(ProjectVersion.class);
    when(dependency.getGroupId()).thenThrow(new IllegalStateException());

    ProjectVersionData projectVersionData = new ProjectVersionData();
    projectVersionData.addDependency(new ProjectVersion());
    projectVersionData.addDependency(dependency);

    StoreProjectVersionData projectData = mock(StoreProjectVersionData.class);
    doThrow(new IllegalStateException())
        .when(projectData)
        .setTransitiveDependenciesReport(Mockito.<VersionDependencyReport>any());
    when(projectData.getVersionData()).thenReturn(projectVersionData);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> refreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(projectData));
    verify(dependency).getGroupId();
    verify(dependencyOverride).overrideWith(isA(List.class), isA(List.class), isA(Function2.class));
    verify(projects).find(null, null, null);
    verify(projectData).getVersionData();
    verify(projectData).setTransitiveDependenciesReport(isA(VersionDependencyReport.class));
  }

  /**
   * Test {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}.
   *
   * <ul>
   *   <li>Given {@link ProjectVersionData#ProjectVersionData()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}
   */
  @Test
  @DisplayName(
      "Test setProjectDataTransitiveDependencies(StoreProjectVersionData); given ProjectVersionData()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RefreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(StoreProjectVersionData)"
  })
  void testSetProjectDataTransitiveDependencies_givenProjectVersionData() {
    // Arrange
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            null, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    StoreProjectVersionData projectData = mock(StoreProjectVersionData.class);
    doThrow(new IllegalStateException())
        .when(projectData)
        .setTransitiveDependenciesReport(Mockito.<VersionDependencyReport>any());
    when(projectData.getVersionData()).thenReturn(new ProjectVersionData());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> refreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(projectData));
    verify(projectData).getVersionData();
    verify(projectData).setTransitiveDependenciesReport(isA(VersionDependencyReport.class));
  }

  /**
   * Test {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}.
   *
   * <ul>
   *   <li>Then calls {@link ProjectVersion#getGroupId()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}
   */
  @Test
  @DisplayName(
      "Test setProjectDataTransitiveDependencies(StoreProjectVersionData); then calls getGroupId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RefreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(StoreProjectVersionData)"
  })
  void testSetProjectDataTransitiveDependencies_thenCallsGetGroupId() {
    // Arrange
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            null, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    ProjectVersion dependency = mock(ProjectVersion.class);
    when(dependency.getGroupId()).thenThrow(new IllegalStateException());

    ProjectVersionData projectVersionData = new ProjectVersionData();
    projectVersionData.addDependency(dependency);

    StoreProjectVersionData projectData = mock(StoreProjectVersionData.class);
    doThrow(new IllegalStateException())
        .when(projectData)
        .setTransitiveDependenciesReport(Mockito.<VersionDependencyReport>any());
    when(projectData.getVersionData()).thenReturn(projectVersionData);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> refreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(projectData));
    verify(dependency).getGroupId();
    verify(projectData).getVersionData();
    verify(projectData).setTransitiveDependenciesReport(isA(VersionDependencyReport.class));
  }

  /**
   * Test {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}.
   *
   * <ul>
   *   <li>Then calls {@link VersionDependencyReport#getTransitiveDependencies()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}
   */
  @Test
  @DisplayName(
      "Test setProjectDataTransitiveDependencies(StoreProjectVersionData); then calls getTransitiveDependencies()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RefreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(StoreProjectVersionData)"
  })
  void testSetProjectDataTransitiveDependencies_thenCallsGetTransitiveDependencies() {
    // Arrange
    VersionDependencyReport versionDependencyReport = mock(VersionDependencyReport.class);
    when(versionDependencyReport.getTransitiveDependencies())
        .thenThrow(new IllegalStateException());
    when(versionDependencyReport.isValid()).thenReturn(true);

    StoreProjectVersionData storeProjectVersionData = mock(StoreProjectVersionData.class);
    when(storeProjectVersionData.getTransitiveDependenciesReport())
        .thenReturn(versionDependencyReport);
    when(storeProjectVersionData.getVersionData()).thenReturn(new ProjectVersionData());
    Optional<StoreProjectVersionData> ofResult = Optional.of(storeProjectVersionData);

    ManageProjectsServiceImpl projects = mock(ManageProjectsServiceImpl.class);
    when(projects.find(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            projects, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    ProjectVersionData projectVersionData = new ProjectVersionData();
    projectVersionData.addDependency(new ProjectVersion());
    projectVersionData.addDependency(mock(ProjectVersion.class));

    StoreProjectVersionData projectData = mock(StoreProjectVersionData.class);
    doThrow(new IllegalStateException())
        .when(projectData)
        .setTransitiveDependenciesReport(Mockito.<VersionDependencyReport>any());
    when(projectData.getVersionData()).thenReturn(projectVersionData);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> refreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(projectData));
    verify(versionDependencyReport).getTransitiveDependencies();
    verify(versionDependencyReport).isValid();
    verify(projects).find(null, null, null);
    verify(storeProjectVersionData, atLeast(1)).getTransitiveDependenciesReport();
    verify(projectData).getVersionData();
    verify(storeProjectVersionData, atLeast(1)).getVersionData();
    verify(projectData).setTransitiveDependenciesReport(isA(VersionDependencyReport.class));
  }

  /**
   * Test {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link
   * RefreshDependenciesServiceImpl#setProjectDataTransitiveDependencies(StoreProjectVersionData)}
   */
  @Test
  @DisplayName(
      "Test setProjectDataTransitiveDependencies(StoreProjectVersionData); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RefreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(StoreProjectVersionData)"
  })
  void testSetProjectDataTransitiveDependencies_thenDoesNotThrow() {
    // Arrange
    RefreshDependenciesServiceImpl refreshDependenciesServiceImpl =
        new RefreshDependenciesServiceImpl(
            null, new TestMavenArtifactsRepository(), mock(DependencyOverride.class));

    // Act and Assert
    assertDoesNotThrow(
        () ->
            refreshDependenciesServiceImpl.setProjectDataTransitiveDependencies(
                new StoreProjectVersionData()));
  }
}
