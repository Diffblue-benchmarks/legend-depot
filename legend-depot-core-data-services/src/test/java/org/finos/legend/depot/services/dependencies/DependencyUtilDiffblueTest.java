package org.finos.legend.depot.services.dependencies;

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
   * <p>
   * Method under test: {@link DependencyUtil#overrideWith(List, List, Function2)}
   */
  @Test
  @DisplayName("Test overrideWith(List, List, Function2)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List DependencyUtil.overrideWith(List, List, Function2)"})
  void testOverrideWith() {
    // Arrange
    DependencyUtil dependencyUtil = new DependencyUtil();

    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    dependencies.add(new ProjectVersion("42", "42", "42"));
    dependencies.add(new ProjectVersion("42", "42", ""));

    ArrayList<ProjectVersion> overridingDependencies = new ArrayList<>();
    overridingDependencies.add(new ProjectVersion("42", "42", "1.0.2"));
    Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>> executableFunction = mock(Function2.class);
    when(executableFunction.apply(Mockito.<List<ProjectVersion>>any(), Mockito.<Boolean>any()))
        .thenReturn(new HashSet<>());

    // Act
    List<ProjectVersion> actualOverrideWithResult = dependencyUtil.overrideWith(dependencies, overridingDependencies,
        executableFunction);

    // Assert
    verify(executableFunction, atLeast(1)).apply(Mockito.<List<ProjectVersion>>any(), eq(true));
    assertTrue(dependencies.isEmpty());
    assertTrue(actualOverrideWithResult.isEmpty());
  }

  /**
   * Test {@link DependencyUtil#overrideWith(List, List, Function2)}.
   * <ul>
   *   <li>Given {@link ProjectVersion#ProjectVersion(String, String, String)} with groupId is {@code 42} and artifactId is {@code 42} and {@code Version Id}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DependencyUtil#overrideWith(List, List, Function2)}
   */
  @Test
  @DisplayName("Test overrideWith(List, List, Function2); given ProjectVersion(String, String, String) with groupId is '42' and artifactId is '42' and 'Version Id'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List DependencyUtil.overrideWith(List, List, Function2)"})
  void testOverrideWith_givenProjectVersionWithGroupIdIs42AndArtifactIdIs42AndVersionId() {
    // Arrange
    DependencyUtil dependencyUtil = new DependencyUtil();

    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    dependencies.add(new ProjectVersion("42", "42", "42"));
    dependencies.add(new ProjectVersion("42", "42", "Version Id"));

    ArrayList<ProjectVersion> overridingDependencies = new ArrayList<>();
    overridingDependencies.add(new ProjectVersion("42", "42", "1.0.2"));
    Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>> executableFunction = mock(Function2.class);
    when(executableFunction.apply(Mockito.<List<ProjectVersion>>any(), Mockito.<Boolean>any()))
        .thenReturn(new HashSet<>());

    // Act
    List<ProjectVersion> actualOverrideWithResult = dependencyUtil.overrideWith(dependencies, overridingDependencies,
        executableFunction);

    // Assert
    verify(executableFunction, atLeast(1)).apply(Mockito.<List<ProjectVersion>>any(), eq(true));
    assertTrue(dependencies.isEmpty());
    assertTrue(actualOverrideWithResult.isEmpty());
  }

  /**
   * Test {@link DependencyUtil#overrideWith(List, List, Function2)}.
   * <ul>
   *   <li>Then calls {@link Function2#apply(Object, Object)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DependencyUtil#overrideWith(List, List, Function2)}
   */
  @Test
  @DisplayName("Test overrideWith(List, List, Function2); then calls apply(Object, Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List DependencyUtil.overrideWith(List, List, Function2)"})
  void testOverrideWith_thenCallsApply() {
    // Arrange
    DependencyUtil dependencyUtil = new DependencyUtil();

    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    dependencies.add(new ProjectVersion("42", "42", "42"));

    ArrayList<ProjectVersion> overridingDependencies = new ArrayList<>();
    overridingDependencies.add(new ProjectVersion("42", "42", "1.0.2"));
    Function2<List<ProjectVersion>, Boolean, Set<ProjectVersion>> executableFunction = mock(Function2.class);
    when(executableFunction.apply(Mockito.<List<ProjectVersion>>any(), Mockito.<Boolean>any()))
        .thenReturn(new HashSet<>());

    // Act
    List<ProjectVersion> actualOverrideWithResult = dependencyUtil.overrideWith(dependencies, overridingDependencies,
        executableFunction);

    // Assert
    verify(executableFunction).apply(isA(List.class), eq(true));
    assertTrue(dependencies.isEmpty());
    assertTrue(actualOverrideWithResult.isEmpty());
  }

  /**
   * Test {@link DependencyUtil#overrideWith(List, List, Function2)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link DependencyUtil#overrideWith(List, List, Function2)}
   */
  @Test
  @DisplayName("Test overrideWith(List, List, Function2); when ArrayList(); then ArrayList() Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List DependencyUtil.overrideWith(List, List, Function2)"})
  void testOverrideWith_whenArrayList_thenArrayListEmpty() {
    // Arrange
    DependencyUtil dependencyUtil = new DependencyUtil();
    ArrayList<ProjectVersion> dependencies = new ArrayList<>();

    // Act
    List<ProjectVersion> actualOverrideWithResult = dependencyUtil.overrideWith(dependencies, new ArrayList<>(),
        mock(Function2.class));

    // Assert
    assertTrue(dependencies.isEmpty());
    assertTrue(actualOverrideWithResult.isEmpty());
  }

  /**
   * Test {@link DependencyUtil#overrideWith(List, List, Function2)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link DependencyUtil#overrideWith(List, List, Function2)}
   */
  @Test
  @DisplayName("Test overrideWith(List, List, Function2); when ArrayList(); then ArrayList() Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List DependencyUtil.overrideWith(List, List, Function2)"})
  void testOverrideWith_whenArrayList_thenArrayListEmpty2() {
    // Arrange
    DependencyUtil dependencyUtil = new DependencyUtil();
    ArrayList<ProjectVersion> dependencies = new ArrayList<>();

    ArrayList<ProjectVersion> overridingDependencies = new ArrayList<>();
    overridingDependencies.add(new ProjectVersion("42", "42", "42"));

    // Act
    List<ProjectVersion> actualOverrideWithResult = dependencyUtil.overrideWith(dependencies, overridingDependencies,
        mock(Function2.class));

    // Assert
    assertTrue(dependencies.isEmpty());
    assertTrue(actualOverrideWithResult.isEmpty());
  }

  /**
   * Test {@link DependencyUtil#overrideWith(List, List, Function2)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link DependencyUtil#overrideWith(List, List, Function2)}
   */
  @Test
  @DisplayName("Test overrideWith(List, List, Function2); when ArrayList(); then ArrayList() Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List DependencyUtil.overrideWith(List, List, Function2)"})
  void testOverrideWith_whenArrayList_thenArrayListEmpty3() {
    // Arrange
    DependencyUtil dependencyUtil = new DependencyUtil();
    ArrayList<ProjectVersion> dependencies = new ArrayList<>();

    ArrayList<ProjectVersion> overridingDependencies = new ArrayList<>();
    overridingDependencies.add(new ProjectVersion("42", "42", "42"));
    overridingDependencies.add(new ProjectVersion("42", "42", "42"));

    // Act
    List<ProjectVersion> actualOverrideWithResult = dependencyUtil.overrideWith(dependencies, overridingDependencies,
        mock(Function2.class));

    // Assert
    assertTrue(dependencies.isEmpty());
    assertTrue(actualOverrideWithResult.isEmpty());
  }

  /**
   * Test {@link DependencyUtil#overrideWith(List, List, Function2)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DependencyUtil#overrideWith(List, List, Function2)}
   */
  @Test
  @DisplayName("Test overrideWith(List, List, Function2); when ArrayList(); then return ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List DependencyUtil.overrideWith(List, List, Function2)"})
  void testOverrideWith_whenArrayList_thenReturnArrayList() {
    // Arrange
    DependencyUtil dependencyUtil = new DependencyUtil();

    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    dependencies.add(new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertSame(dependencies, dependencyUtil.overrideWith(dependencies, new ArrayList<>(), mock(Function2.class)));
  }

  /**
   * Test {@link DependencyUtil#overrideWith(List, List, Function2)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link DependencyUtil#overrideWith(List, List, Function2)}
   */
  @Test
  @DisplayName("Test overrideWith(List, List, Function2); when ArrayList(); then return size is two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List DependencyUtil.overrideWith(List, List, Function2)"})
  void testOverrideWith_whenArrayList_thenReturnSizeIsTwo() {
    // Arrange
    DependencyUtil dependencyUtil = new DependencyUtil();

    ArrayList<ProjectVersion> dependencies = new ArrayList<>();
    dependencies.add(new ProjectVersion("42", "42", "42"));
    ProjectVersion projectVersion = new ProjectVersion("42", "42", "42");

    dependencies.add(projectVersion);

    // Act
    List<ProjectVersion> actualOverrideWithResult = dependencyUtil.overrideWith(dependencies, new ArrayList<>(),
        mock(Function2.class));

    // Assert
    assertEquals(2, actualOverrideWithResult.size());
    assertSame(projectVersion, actualOverrideWithResult.get(1));
  }
}
