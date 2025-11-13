package org.finos.legend.depot.services.dependencies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.collections.api.block.function.Function2;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DependencyUtilDiffblueTest {
  /**
   * Test {@link DependencyUtil#overrideWith(List, List, Function2)}.
   *
   * <ul>
   *   <li>Given {@link ProjectVersion#ProjectVersion()} VersionId is {@code 42}.
   *   <li>Then calls {@link Function2#apply(Object, Object)}.
   * </ul>
   *
   * <p>Method under test: {@link DependencyUtil#overrideWith(List, List, Function2)}
   */
  @Test
  @DisplayName(
      "Test overrideWith(List, List, Function2); given ProjectVersion() VersionId is '42'; then calls apply(Object, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List DependencyUtil.overrideWith(List, List, Function2)"})
  void testOverrideWith_givenProjectVersionVersionIdIs42_thenCallsApply() {
    // Arrange
    DependencyUtil dependencyUtil = new DependencyUtil();

    ProjectVersion projectVersion = new ProjectVersion();
    projectVersion.setVersionId("42");

    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    dependencies.add(projectVersion);

    ArrayList<ProjectVersion> overridingDependencies = new ArrayList<>();
    overridingDependencies.add(new ProjectVersion());

    Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>> executableFunction =
        mock(Function2.class);
    when(executableFunction.apply(Mockito.<List<ProjectVersion>>any(), Mockito.<Boolean>any()))
        .thenReturn(new HashSet<>());

    // Act
    List<ProjectVersion> actualOverrideWithResult =
        dependencyUtil.overrideWith(dependencies, overridingDependencies, executableFunction);

    // Assert
    verify(executableFunction).apply(isA(List.class), eq(true));
    assertTrue(dependencies.isEmpty());
    assertTrue(actualOverrideWithResult.isEmpty());
  }

  /**
   * Test {@link DependencyUtil#overrideWith(List, List, Function2)}.
   *
   * <ul>
   *   <li>Given {@link ProjectVersion#ProjectVersion()}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link DependencyUtil#overrideWith(List, List, Function2)}
   */
  @Test
  @DisplayName(
      "Test overrideWith(List, List, Function2); given ProjectVersion(); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List DependencyUtil.overrideWith(List, List, Function2)"})
  void testOverrideWith_givenProjectVersion_thenArrayListEmpty() {
    // Arrange
    DependencyUtil dependencyUtil = new DependencyUtil();
    ArrayList<ProjectVersion> dependencies = new ArrayList<>();

    ArrayList<ProjectVersion> overridingDependencies = new ArrayList<>();
    overridingDependencies.add(new ProjectVersion());

    // Act
    List<ProjectVersion> actualOverrideWithResult =
        dependencyUtil.overrideWith(dependencies, overridingDependencies, mock(Function2.class));

    // Assert
    assertTrue(dependencies.isEmpty());
    assertTrue(actualOverrideWithResult.isEmpty());
  }

  /**
   * Test {@link DependencyUtil#overrideWith(List, List, Function2)}.
   *
   * <ul>
   *   <li>Given {@link ProjectVersion#ProjectVersion()}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link DependencyUtil#overrideWith(List, List, Function2)}
   */
  @Test
  @DisplayName(
      "Test overrideWith(List, List, Function2); given ProjectVersion(); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List DependencyUtil.overrideWith(List, List, Function2)"})
  void testOverrideWith_givenProjectVersion_thenArrayListEmpty2() {
    // Arrange
    DependencyUtil dependencyUtil = new DependencyUtil();
    ArrayList<ProjectVersion> dependencies = new ArrayList<>();

    ArrayList<ProjectVersion> overridingDependencies = new ArrayList<>();
    overridingDependencies.add(new ProjectVersion());
    overridingDependencies.add(new ProjectVersion());

    // Act
    List<ProjectVersion> actualOverrideWithResult =
        dependencyUtil.overrideWith(dependencies, overridingDependencies, mock(Function2.class));

    // Assert
    assertTrue(dependencies.isEmpty());
    assertTrue(actualOverrideWithResult.isEmpty());
  }

  /**
   * Test {@link DependencyUtil#overrideWith(List, List, Function2)}.
   *
   * <ul>
   *   <li>Given {@link ProjectVersion#ProjectVersion()}.
   *   <li>Then return {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link DependencyUtil#overrideWith(List, List, Function2)}
   */
  @Test
  @DisplayName(
      "Test overrideWith(List, List, Function2); given ProjectVersion(); then return ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List DependencyUtil.overrideWith(List, List, Function2)"})
  void testOverrideWith_givenProjectVersion_thenReturnArrayList() {
    // Arrange
    DependencyUtil dependencyUtil = new DependencyUtil();

    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    dependencies.add(new ProjectVersion());

    // Act
    List<ProjectVersion> actualOverrideWithResult =
        dependencyUtil.overrideWith(dependencies, new ArrayList<>(), mock(Function2.class));

    // Assert
    assertSame(dependencies, actualOverrideWithResult);
  }

  /**
   * Test {@link DependencyUtil#overrideWith(List, List, Function2)}.
   *
   * <ul>
   *   <li>Given {@link ProjectVersion#ProjectVersion()}.
   *   <li>Then return size is two.
   * </ul>
   *
   * <p>Method under test: {@link DependencyUtil#overrideWith(List, List, Function2)}
   */
  @Test
  @DisplayName(
      "Test overrideWith(List, List, Function2); given ProjectVersion(); then return size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List DependencyUtil.overrideWith(List, List, Function2)"})
  void testOverrideWith_givenProjectVersion_thenReturnSizeIsTwo() {
    // Arrange
    DependencyUtil dependencyUtil = new DependencyUtil();

    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    dependencies.add(new ProjectVersion());
    ProjectVersion projectVersion = new ProjectVersion();
    dependencies.add(projectVersion);

    // Act
    List<ProjectVersion> actualOverrideWithResult =
        dependencyUtil.overrideWith(dependencies, new ArrayList<>(), mock(Function2.class));

    // Assert
    assertEquals(2, actualOverrideWithResult.size());
    assertSame(projectVersion, actualOverrideWithResult.get(1));
  }

  /**
   * Test {@link DependencyUtil#overrideWith(List, List, Function2)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link DependencyUtil#overrideWith(List, List, Function2)}
   */
  @Test
  @DisplayName("Test overrideWith(List, List, Function2); when ArrayList(); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List DependencyUtil.overrideWith(List, List, Function2)"})
  void testOverrideWith_whenArrayList_thenArrayListEmpty() {
    // Arrange
    DependencyUtil dependencyUtil = new DependencyUtil();
    ArrayList<ProjectVersion> dependencies = new ArrayList<>();

    // Act
    List<ProjectVersion> actualOverrideWithResult =
        dependencyUtil.overrideWith(dependencies, new ArrayList<>(), mock(Function2.class));

    // Assert
    assertTrue(dependencies.isEmpty());
    assertTrue(actualOverrideWithResult.isEmpty());
  }
}
