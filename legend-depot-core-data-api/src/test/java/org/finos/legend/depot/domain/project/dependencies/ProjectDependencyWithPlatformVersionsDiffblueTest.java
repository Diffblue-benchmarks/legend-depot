package org.finos.legend.depot.domain.project.dependencies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.domain.project.Property;
import org.junit.jupiter.api.Test;

class ProjectDependencyWithPlatformVersionsDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectDependencyWithPlatformVersions#equals(Object)}
   *   <li>{@link ProjectDependencyWithPlatformVersions#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ProjectVersion dependency = new ProjectVersion("42", "42", "42");

    ProjectDependencyWithPlatformVersions projectDependencyWithPlatformVersions = new ProjectDependencyWithPlatformVersions(
        "42", "42", "42", dependency, new ArrayList<>());
    ProjectVersion dependency2 = new ProjectVersion("42", "42", "42");

    ProjectDependencyWithPlatformVersions projectDependencyWithPlatformVersions2 = new ProjectDependencyWithPlatformVersions(
        "42", "42", "42", dependency2, new ArrayList<>());

    // Act and Assert
    assertEquals(projectDependencyWithPlatformVersions, projectDependencyWithPlatformVersions2);
    int expectedHashCodeResult = projectDependencyWithPlatformVersions.hashCode();
    assertEquals(expectedHashCodeResult, projectDependencyWithPlatformVersions2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectDependencyWithPlatformVersions#equals(Object)}
   *   <li>{@link ProjectDependencyWithPlatformVersions#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ProjectVersion dependency = new ProjectVersion("42", "42", "42");

    ProjectDependencyWithPlatformVersions projectDependencyWithPlatformVersions = new ProjectDependencyWithPlatformVersions(
        "42", "42", "42", dependency, new ArrayList<>());

    // Act and Assert
    assertEquals(projectDependencyWithPlatformVersions, projectDependencyWithPlatformVersions);
    int expectedHashCodeResult = projectDependencyWithPlatformVersions.hashCode();
    assertEquals(expectedHashCodeResult, projectDependencyWithPlatformVersions.hashCode());
  }

  /**
   * Method under test:
   * {@link ProjectDependencyWithPlatformVersions#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ProjectVersion dependency = new ProjectVersion("42", "42", "42");

    ProjectDependencyWithPlatformVersions projectDependencyWithPlatformVersions = new ProjectDependencyWithPlatformVersions(
        "Group Id", "42", "42", dependency, new ArrayList<>());
    ProjectVersion dependency2 = new ProjectVersion("42", "42", "42");

    // Act and Assert
    assertNotEquals(projectDependencyWithPlatformVersions,
        new ProjectDependencyWithPlatformVersions("42", "42", "42", dependency2, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link ProjectDependencyWithPlatformVersions#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ProjectVersion dependency = mock(ProjectVersion.class);
    ProjectDependencyWithPlatformVersions projectDependencyWithPlatformVersions = new ProjectDependencyWithPlatformVersions(
        "42", "42", "42", dependency, new ArrayList<>());
    ProjectVersion dependency2 = new ProjectVersion("42", "42", "42");

    // Act and Assert
    assertNotEquals(projectDependencyWithPlatformVersions,
        new ProjectDependencyWithPlatformVersions("42", "42", "42", dependency2, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link ProjectDependencyWithPlatformVersions#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ProjectVersion dependency = new ProjectVersion("42", "42", "42");

    // Act and Assert
    assertNotEquals(new ProjectDependencyWithPlatformVersions("42", "42", "42", dependency, new ArrayList<>()), null);
  }

  /**
   * Method under test:
   * {@link ProjectDependencyWithPlatformVersions#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ProjectVersion dependency = new ProjectVersion("42", "42", "42");

    // Act and Assert
    assertNotEquals(new ProjectDependencyWithPlatformVersions("42", "42", "42", dependency, new ArrayList<>()),
        "Different type to ProjectDependencyWithPlatformVersions");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProjectDependencyWithPlatformVersions#ProjectDependencyWithPlatformVersions(String, String, String, ProjectVersion, List)}
   *   <li>{@link ProjectDependencyWithPlatformVersions#getDependency()}
   *   <li>{@link ProjectDependencyWithPlatformVersions#getPlatformsVersion()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ProjectVersion dependency = new ProjectVersion("42", "42", "42");

    ArrayList<Property> platformsVersion = new ArrayList<>();

    // Act
    ProjectDependencyWithPlatformVersions actualProjectDependencyWithPlatformVersions = new ProjectDependencyWithPlatformVersions(
        "42", "42", "42", dependency, platformsVersion);
    ProjectVersion actualDependency = actualProjectDependencyWithPlatformVersions.getDependency();
    List<Property> actualPlatformsVersion = actualProjectDependencyWithPlatformVersions.getPlatformsVersion();

    // Assert
    assertEquals("42", actualProjectDependencyWithPlatformVersions.getArtifactId());
    assertEquals("42", actualProjectDependencyWithPlatformVersions.getGroupId());
    assertEquals("42", actualProjectDependencyWithPlatformVersions.getVersionId());
    assertTrue(actualPlatformsVersion.isEmpty());
    assertSame(platformsVersion, actualPlatformsVersion);
    assertSame(dependency, actualDependency);
  }
}
