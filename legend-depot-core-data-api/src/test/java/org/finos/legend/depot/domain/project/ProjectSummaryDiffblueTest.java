package org.finos.legend.depot.domain.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProjectSummaryDiffblueTest {
  /**
   * Test {@link ProjectSummary#ProjectSummary(String, String, String, long)}.
   *
   * <p>Method under test: {@link ProjectSummary#ProjectSummary(String, String, String, long)}
   */
  @Test
  @DisplayName("Test new ProjectSummary(String, String, String, long)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProjectSummary.<init>(String, String, String, long)"})
  void testNewProjectSummary() {
    // Arrange and Act
    ProjectSummary actualProjectSummary =
        new ProjectSummary(
            "\"finos-legend-project-001\"",
            "\"org.finos.legend.depot\"",
            "\"legend-depot-artifact\"",
            1L);

    // Assert
    assertEquals("\"finos-legend-project-001\"", actualProjectSummary.projectId);
    assertEquals("\"legend-depot-artifact\"", actualProjectSummary.artifactId);
    assertEquals("\"org.finos.legend.depot\"", actualProjectSummary.groupId);
    assertEquals(
        "\"org.finos.legend.depot\"-\"legend-depot-artifact\"",
        actualProjectSummary.getMavenCoordinates());
    assertEquals(1L, actualProjectSummary.versions);
  }

  /**
   * Test {@link ProjectSummary#getMavenCoordinates()}.
   *
   * <p>Method under test: {@link ProjectSummary#getMavenCoordinates()}
   */
  @Test
  @DisplayName("Test getMavenCoordinates()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProjectSummary.getMavenCoordinates()"})
  void testGetMavenCoordinates() {
    // Arrange
    ProjectSummary projectSummary =
        new ProjectSummary(
            "\"finos-legend-project-001\"",
            "\"org.finos.legend.depot\"",
            "\"legend-depot-artifact\"",
            1L);

    // Act and Assert
    assertEquals(
        "\"org.finos.legend.depot\"-\"legend-depot-artifact\"",
        projectSummary.getMavenCoordinates());
  }

  /**
   * Test {@link ProjectSummary#compareTo(Object)}.
   *
   * <ul>
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link ProjectSummary#compareTo(Object)}
   */
  @Test
  @DisplayName("Test compareTo(Object); then return zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int ProjectSummary.compareTo(Object)"})
  void testCompareTo_thenReturnZero() {
    // Arrange
    ProjectSummary projectSummary =
        new ProjectSummary(
            "\"finos-legend-project-001\"",
            "\"org.finos.legend.depot\"",
            "\"legend-depot-artifact\"",
            1L);
    ProjectSummary projectSummary2 =
        new ProjectSummary(
            "\"finos-legend-project-001\"",
            "\"org.finos.legend.depot\"",
            "\"legend-depot-artifact\"",
            1L);

    // Act
    int actualCompareToResult = projectSummary.compareTo(projectSummary2);

    // Assert
    assertEquals(0, actualCompareToResult);
  }

  /**
   * Test {@link ProjectSummary#equals(Object)}, and {@link ProjectSummary#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ProjectSummary#equals(Object)}
   *   <li>{@link ProjectSummary#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProjectSummary.equals(Object)", "int ProjectSummary.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ProjectSummary projectSummary =
        new ProjectSummary(
            "\"finos-legend-project-001\"",
            "\"org.finos.legend.depot\"",
            "\"legend-depot-artifact\"",
            1L);
    ProjectSummary projectSummary2 =
        new ProjectSummary(
            "\"finos-legend-project-001\"",
            "\"org.finos.legend.depot\"",
            "\"legend-depot-artifact\"",
            1L);

    // Act and Assert
    assertEquals(projectSummary, projectSummary2);
    assertEquals(projectSummary.hashCode(), projectSummary2.hashCode());
  }

  /**
   * Test {@link ProjectSummary#equals(Object)}, and {@link ProjectSummary#hashCode()}.
   *
   * <ul>
   *   <li>When other is same.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ProjectSummary#equals(Object)}
   *   <li>{@link ProjectSummary#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProjectSummary.equals(Object)", "int ProjectSummary.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ProjectSummary projectSummary =
        new ProjectSummary(
            "\"finos-legend-project-001\"",
            "\"org.finos.legend.depot\"",
            "\"legend-depot-artifact\"",
            1L);

    // Act and Assert
    assertEquals(projectSummary, projectSummary);
    int expectedHashCodeResult = projectSummary.hashCode();
    assertEquals(expectedHashCodeResult, projectSummary.hashCode());
  }

  /**
   * Test {@link ProjectSummary#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link ProjectSummary#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProjectSummary.equals(Object)", "int ProjectSummary.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ProjectSummary projectSummary =
        new ProjectSummary(
            "\"finos-legend-project-001\"",
            "\"org.finos.legend.depot\"",
            "\"legend-depot-artifact\"",
            0L);

    // Act and Assert
    assertNotEquals(
        projectSummary,
        new ProjectSummary(
            "\"finos-legend-project-001\"",
            "\"org.finos.legend.depot\"",
            "\"legend-depot-artifact\"",
            1L));
  }

  /**
   * Test {@link ProjectSummary#equals(Object)}.
   *
   * <ul>
   *   <li>When other is {@code null}.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link ProjectSummary#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProjectSummary.equals(Object)", "int ProjectSummary.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(
        new ProjectSummary(
            "\"finos-legend-project-001\"",
            "\"org.finos.legend.depot\"",
            "\"legend-depot-artifact\"",
            1L),
        null);
  }

  /**
   * Test {@link ProjectSummary#equals(Object)}.
   *
   * <ul>
   *   <li>When other is wrong type.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link ProjectSummary#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProjectSummary.equals(Object)", "int ProjectSummary.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(
        new ProjectSummary(
            "\"finos-legend-project-001\"",
            "\"org.finos.legend.depot\"",
            "\"legend-depot-artifact\"",
            1L),
        "Different type to ProjectSummary");
  }
}
