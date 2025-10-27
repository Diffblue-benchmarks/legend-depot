package org.finos.legend.depot.domain.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class ProjectVersionDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectVersion#equals(Object)}
   *   <li>{@link ProjectVersion#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ProjectVersion projectVersion = new ProjectVersion("42", "42", "42");
    ProjectVersion projectVersion2 = new ProjectVersion("42", "42", "42");

    // Act and Assert
    assertEquals(projectVersion, projectVersion2);
    int expectedHashCodeResult = projectVersion.hashCode();
    assertEquals(expectedHashCodeResult, projectVersion2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectVersion#equals(Object)}
   *   <li>{@link ProjectVersion#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ProjectVersion projectVersion = new ProjectVersion("42", "42", "42");

    // Act and Assert
    assertEquals(projectVersion, projectVersion);
    int expectedHashCodeResult = projectVersion.hashCode();
    assertEquals(expectedHashCodeResult, projectVersion.hashCode());
  }

  /**
   * Method under test: {@link ProjectVersion#getGav()}
   */
  @Test
  void testGetGav() {
    // Arrange, Act and Assert
    assertEquals("42:42:42", (new ProjectVersion("42", "42", "42")).getGav());
  }

  /**
   * Method under test: {@link ProjectVersion#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ProjectVersion projectVersion = new ProjectVersion("Group Id", "42", "42");

    // Act and Assert
    assertNotEquals(projectVersion, new ProjectVersion("42", "42", "42"));
  }

  /**
   * Method under test: {@link ProjectVersion#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProjectVersion("42", "42", "42"), null);
  }

  /**
   * Method under test: {@link ProjectVersion#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProjectVersion("42", "42", "42"), "Different type to ProjectVersion");
  }

  /**
   * Method under test: {@link ProjectVersion#ProjectVersion()}
   */
  @Test
  void testNewProjectVersion() {
    // Arrange and Act
    ProjectVersion actualProjectVersion = new ProjectVersion();

    // Assert
    assertNull(actualProjectVersion.getArtifactId());
    assertNull(actualProjectVersion.getGroupId());
    assertNull(actualProjectVersion.getVersionId());
  }

  /**
   * Method under test:
   * {@link ProjectVersion#ProjectVersion(String, String, String)}
   */
  @Test
  void testNewProjectVersion2() {
    // Arrange and Act
    ProjectVersion actualProjectVersion = new ProjectVersion("42", "42", "42");

    // Assert
    assertEquals("42", actualProjectVersion.getArtifactId());
    assertEquals("42", actualProjectVersion.getGroupId());
    assertEquals("42", actualProjectVersion.getVersionId());
  }
}
