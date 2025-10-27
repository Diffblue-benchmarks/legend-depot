package org.finos.legend.depot.domain.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class ProjectSummaryDiffblueTest {
  /**
   * Method under test: {@link ProjectSummary#getMavenCoordinates()}
   */
  @Test
  void testGetMavenCoordinates() {
    // Arrange, Act and Assert
    assertEquals("42-42", (new ProjectSummary("myproject", "42", "42", 1L)).getMavenCoordinates());
  }

  /**
   * Method under test: {@link ProjectSummary#compareTo(Object)}
   */
  @Test
  void testCompareTo() {
    // Arrange
    ProjectSummary projectSummary = new ProjectSummary("myproject", "42", "42", 1L);

    // Act and Assert
    assertEquals(0, projectSummary.compareTo(new ProjectSummary("myproject", "42", "42", 1L)));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectSummary#equals(Object)}
   *   <li>{@link ProjectSummary#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ProjectSummary projectSummary = new ProjectSummary("myproject", "42", "42", 1L);
    ProjectSummary projectSummary2 = new ProjectSummary("myproject", "42", "42", 1L);

    // Act and Assert
    assertEquals(projectSummary, projectSummary2);
    int expectedHashCodeResult = projectSummary.hashCode();
    assertEquals(expectedHashCodeResult, projectSummary2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectSummary#equals(Object)}
   *   <li>{@link ProjectSummary#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ProjectSummary projectSummary = new ProjectSummary("myproject", "42", "42", 1L);

    // Act and Assert
    assertEquals(projectSummary, projectSummary);
    int expectedHashCodeResult = projectSummary.hashCode();
    assertEquals(expectedHashCodeResult, projectSummary.hashCode());
  }

  /**
   * Method under test: {@link ProjectSummary#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ProjectSummary projectSummary = new ProjectSummary("myproject", "42", "42", 0L);

    // Act and Assert
    assertNotEquals(projectSummary, new ProjectSummary("myproject", "42", "42", 1L));
  }

  /**
   * Method under test: {@link ProjectSummary#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProjectSummary("myproject", "42", "42", 1L), null);
  }

  /**
   * Method under test: {@link ProjectSummary#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProjectSummary("myproject", "42", "42", 1L), "Different type to ProjectSummary");
  }

  /**
   * Method under test:
   * {@link ProjectSummary#ProjectSummary(String, String, String, long)}
   */
  @Test
  void testNewProjectSummary() {
    // Arrange and Act
    ProjectSummary actualProjectSummary = new ProjectSummary("myproject", "42", "42", 1L);

    // Assert
    assertEquals("42", actualProjectSummary.artifactId);
    assertEquals("42", actualProjectSummary.groupId);
    assertEquals("42-42", actualProjectSummary.getMavenCoordinates());
    assertEquals("myproject", actualProjectSummary.projectId);
    assertEquals(1L, actualProjectSummary.versions);
  }
}
