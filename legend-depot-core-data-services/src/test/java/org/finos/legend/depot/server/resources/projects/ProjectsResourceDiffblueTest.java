package org.finos.legend.depot.server.resources.projects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class ProjectsResourceDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectsResource.ProjectVersionProperty#equals(Object)}
   *   <li>{@link ProjectsResource.ProjectVersionProperty#hashCode()}
   * </ul>
   */
  @Test
  void testProjectVersionPropertyEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ProjectsResource.ProjectVersionProperty projectVersionProperty = new ProjectsResource.ProjectVersionProperty(
        "Property Name", "42", "42");
    ProjectsResource.ProjectVersionProperty projectVersionProperty2 = new ProjectsResource.ProjectVersionProperty(
        "Property Name", "42", "42");

    // Act and Assert
    assertEquals(projectVersionProperty, projectVersionProperty2);
    int expectedHashCodeResult = projectVersionProperty.hashCode();
    assertEquals(expectedHashCodeResult, projectVersionProperty2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectsResource.ProjectVersionProperty#equals(Object)}
   *   <li>{@link ProjectsResource.ProjectVersionProperty#hashCode()}
   * </ul>
   */
  @Test
  void testProjectVersionPropertyEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ProjectsResource.ProjectVersionProperty projectVersionProperty = new ProjectsResource.ProjectVersionProperty(
        "Property Name", "42", "42");

    // Act and Assert
    assertEquals(projectVersionProperty, projectVersionProperty);
    int expectedHashCodeResult = projectVersionProperty.hashCode();
    assertEquals(expectedHashCodeResult, projectVersionProperty.hashCode());
  }

  /**
   * Method under test:
   * {@link ProjectsResource.ProjectVersionProperty#equals(Object)}
   */
  @Test
  void testProjectVersionPropertyEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ProjectsResource.ProjectVersionProperty projectVersionProperty = new ProjectsResource.ProjectVersionProperty("42",
        "42", "42");

    // Act and Assert
    assertNotEquals(projectVersionProperty, new ProjectsResource.ProjectVersionProperty("Property Name", "42", "42"));
  }

  /**
   * Method under test:
   * {@link ProjectsResource.ProjectVersionProperty#equals(Object)}
   */
  @Test
  void testProjectVersionPropertyEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProjectsResource.ProjectVersionProperty("Property Name", "42", "42"), null);
  }

  /**
   * Method under test:
   * {@link ProjectsResource.ProjectVersionProperty#equals(Object)}
   */
  @Test
  void testProjectVersionPropertyEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProjectsResource.ProjectVersionProperty("Property Name", "42", "42"),
        "Different type to ProjectVersionProperty");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectsResource.ProjectVersionProperty#ProjectVersionProperty()}
   *   <li>{@link ProjectsResource.ProjectVersionProperty#getProjectVersionId()}
   *   <li>{@link ProjectsResource.ProjectVersionProperty#getPropertyName()}
   *   <li>{@link ProjectsResource.ProjectVersionProperty#getValue()}
   * </ul>
   */
  @Test
  void testProjectVersionPropertyGettersAndSetters() {
    // Arrange and Act
    ProjectsResource.ProjectVersionProperty actualProjectVersionProperty = new ProjectsResource.ProjectVersionProperty();
    String actualProjectVersionId = actualProjectVersionProperty.getProjectVersionId();
    String actualPropertyName = actualProjectVersionProperty.getPropertyName();

    // Assert
    assertNull(actualProjectVersionId);
    assertNull(actualPropertyName);
    assertNull(actualProjectVersionProperty.getValue());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProjectsResource.ProjectVersionProperty#ProjectVersionProperty(String, String, String)}
   *   <li>{@link ProjectsResource.ProjectVersionProperty#getProjectVersionId()}
   *   <li>{@link ProjectsResource.ProjectVersionProperty#getPropertyName()}
   *   <li>{@link ProjectsResource.ProjectVersionProperty#getValue()}
   * </ul>
   */
  @Test
  void testProjectVersionPropertyGettersAndSetters2() {
    // Arrange and Act
    ProjectsResource.ProjectVersionProperty actualProjectVersionProperty = new ProjectsResource.ProjectVersionProperty(
        "Property Name", "42", "42");
    String actualProjectVersionId = actualProjectVersionProperty.getProjectVersionId();
    String actualPropertyName = actualProjectVersionProperty.getPropertyName();

    // Assert
    assertEquals("42", actualProjectVersionId);
    assertEquals("42", actualProjectVersionProperty.getValue());
    assertEquals("Property Name", actualPropertyName);
  }
}
