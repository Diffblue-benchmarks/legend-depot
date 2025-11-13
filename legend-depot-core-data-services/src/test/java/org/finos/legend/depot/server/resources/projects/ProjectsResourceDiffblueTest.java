package org.finos.legend.depot.server.resources.projects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.finos.legend.depot.server.resources.projects.ProjectsResource.ProjectVersionProperty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProjectsResourceDiffblueTest {
  /**
   * Test ProjectVersionProperty {@link ProjectVersionProperty#equals(Object)}, and {@link
   * ProjectVersionProperty#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ProjectVersionProperty#equals(Object)}
   *   <li>{@link ProjectVersionProperty#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName(
      "Test ProjectVersionProperty equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ProjectVersionProperty.equals(Object)",
    "int ProjectVersionProperty.hashCode()"
  })
  void testProjectVersionPropertyEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ProjectVersionProperty projectVersionProperty =
        new ProjectVersionProperty("Property Name", "42", "42");
    ProjectVersionProperty projectVersionProperty2 =
        new ProjectVersionProperty("Property Name", "42", "42");

    // Act and Assert
    assertEquals(projectVersionProperty, projectVersionProperty2);
    assertEquals(projectVersionProperty.hashCode(), projectVersionProperty2.hashCode());
  }

  /**
   * Test ProjectVersionProperty {@link ProjectVersionProperty#equals(Object)}, and {@link
   * ProjectVersionProperty#hashCode()}.
   *
   * <ul>
   *   <li>When other is same.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ProjectVersionProperty#equals(Object)}
   *   <li>{@link ProjectVersionProperty#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName(
      "Test ProjectVersionProperty equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ProjectVersionProperty.equals(Object)",
    "int ProjectVersionProperty.hashCode()"
  })
  void testProjectVersionPropertyEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ProjectVersionProperty projectVersionProperty =
        new ProjectVersionProperty("Property Name", "42", "42");

    // Act and Assert
    assertEquals(projectVersionProperty, projectVersionProperty);
    int expectedHashCodeResult = projectVersionProperty.hashCode();
    assertEquals(expectedHashCodeResult, projectVersionProperty.hashCode());
  }

  /**
   * Test ProjectVersionProperty {@link ProjectVersionProperty#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link ProjectVersionProperty#equals(Object)}
   */
  @Test
  @DisplayName(
      "Test ProjectVersionProperty equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ProjectVersionProperty.equals(Object)",
    "int ProjectVersionProperty.hashCode()"
  })
  void testProjectVersionPropertyEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ProjectVersionProperty projectVersionProperty = new ProjectVersionProperty("42", "42", "42");

    // Act and Assert
    assertNotEquals(
        projectVersionProperty, new ProjectVersionProperty("Property Name", "42", "42"));
  }

  /**
   * Test ProjectVersionProperty {@link ProjectVersionProperty#equals(Object)}.
   *
   * <ul>
   *   <li>When other is {@code null}.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link ProjectVersionProperty#equals(Object)}
   */
  @Test
  @DisplayName(
      "Test ProjectVersionProperty equals(Object); when other is 'null'; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ProjectVersionProperty.equals(Object)",
    "int ProjectVersionProperty.hashCode()"
  })
  void testProjectVersionPropertyEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProjectVersionProperty("Property Name", "42", "42"), null);
  }

  /**
   * Test ProjectVersionProperty {@link ProjectVersionProperty#equals(Object)}.
   *
   * <ul>
   *   <li>When other is wrong type.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link ProjectVersionProperty#equals(Object)}
   */
  @Test
  @DisplayName(
      "Test ProjectVersionProperty equals(Object); when other is wrong type; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ProjectVersionProperty.equals(Object)",
    "int ProjectVersionProperty.hashCode()"
  })
  void testProjectVersionPropertyEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(
        new ProjectVersionProperty("Property Name", "42", "42"),
        "Different type to ProjectVersionProperty");
  }

  /**
   * Test ProjectVersionProperty getters and setters.
   *
   * <ul>
   *   <li>Then return ProjectVersionId is {@code 42}.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ProjectVersionProperty#ProjectVersionProperty(String, String, String)}
   *   <li>{@link ProjectVersionProperty#getProjectVersionId()}
   *   <li>{@link ProjectVersionProperty#getPropertyName()}
   *   <li>{@link ProjectVersionProperty#getValue()}
   * </ul>
   */
  @Test
  @DisplayName(
      "Test ProjectVersionProperty getters and setters; then return ProjectVersionId is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProjectVersionProperty.<init>()",
    "void ProjectVersionProperty.<init>(String, String, String)",
    "String ProjectVersionProperty.getProjectVersionId()",
    "String ProjectVersionProperty.getPropertyName()",
    "String ProjectVersionProperty.getValue()"
  })
  void testProjectVersionPropertyGettersAndSetters_thenReturnProjectVersionIdIs42() {
    // Arrange and Act
    ProjectVersionProperty actualProjectVersionProperty =
        new ProjectVersionProperty("Property Name", "42", "42");
    String actualProjectVersionId = actualProjectVersionProperty.getProjectVersionId();
    String actualPropertyName = actualProjectVersionProperty.getPropertyName();

    // Assert
    assertEquals("42", actualProjectVersionId);
    assertEquals("42", actualProjectVersionProperty.getValue());
    assertEquals("Property Name", actualPropertyName);
  }

  /**
   * Test ProjectVersionProperty getters and setters.
   *
   * <ul>
   *   <li>Then return ProjectVersionId is {@code null}.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ProjectVersionProperty#ProjectVersionProperty()}
   *   <li>{@link ProjectVersionProperty#getProjectVersionId()}
   *   <li>{@link ProjectVersionProperty#getPropertyName()}
   *   <li>{@link ProjectVersionProperty#getValue()}
   * </ul>
   */
  @Test
  @DisplayName(
      "Test ProjectVersionProperty getters and setters; then return ProjectVersionId is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProjectVersionProperty.<init>()",
    "void ProjectVersionProperty.<init>(String, String, String)",
    "String ProjectVersionProperty.getProjectVersionId()",
    "String ProjectVersionProperty.getPropertyName()",
    "String ProjectVersionProperty.getValue()"
  })
  void testProjectVersionPropertyGettersAndSetters_thenReturnProjectVersionIdIsNull() {
    // Arrange and Act
    ProjectVersionProperty actualProjectVersionProperty = new ProjectVersionProperty();
    String actualProjectVersionId = actualProjectVersionProperty.getProjectVersionId();
    String actualPropertyName = actualProjectVersionProperty.getPropertyName();

    // Assert
    assertNull(actualProjectVersionId);
    assertNull(actualPropertyName);
    assertNull(actualProjectVersionProperty.getValue());
  }
}
