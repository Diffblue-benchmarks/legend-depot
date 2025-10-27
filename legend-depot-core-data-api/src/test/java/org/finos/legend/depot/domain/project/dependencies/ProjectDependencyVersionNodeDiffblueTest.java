package org.finos.legend.depot.domain.project.dependencies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Set;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProjectDependencyVersionNodeDiffblueTest {
  /**
   * Test {@link ProjectDependencyVersionNode#ProjectDependencyVersionNode(String, String, String)}.
   * <p>
   * Method under test: {@link ProjectDependencyVersionNode#ProjectDependencyVersionNode(String, String, String)}
   */
  @Test
  @DisplayName("Test new ProjectDependencyVersionNode(String, String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectDependencyVersionNode.<init>(String, String, String)"})
  void testNewProjectDependencyVersionNode() {
    // Arrange and Act
    ProjectDependencyVersionNode actualProjectDependencyVersionNode = new ProjectDependencyVersionNode("42", "42",
        "42");

    // Assert
    assertEquals("42", actualProjectDependencyVersionNode.getArtifactId());
    assertEquals("42", actualProjectDependencyVersionNode.getGroupId());
    assertEquals("42", actualProjectDependencyVersionNode.getVersionId());
    assertEquals("42:42", actualProjectDependencyVersionNode.getCoordinates());
    assertEquals("42:42:42", actualProjectDependencyVersionNode.getGav());
    assertEquals("42:42:42", actualProjectDependencyVersionNode.getId());
    assertNull(actualProjectDependencyVersionNode.getProjectId());
    assertTrue(actualProjectDependencyVersionNode.getBackEdges().isEmpty());
    assertTrue(actualProjectDependencyVersionNode.getForwardEdges().isEmpty());
  }

  /**
   * Test {@link ProjectDependencyVersionNode#buildFromProjectVersion(ProjectVersion)}.
   * <ul>
   *   <li>Then return ArtifactId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectDependencyVersionNode#buildFromProjectVersion(ProjectVersion)}
   */
  @Test
  @DisplayName("Test buildFromProjectVersion(ProjectVersion); then return ArtifactId is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ProjectDependencyVersionNode ProjectDependencyVersionNode.buildFromProjectVersion(ProjectVersion)"})
  void testBuildFromProjectVersion_thenReturnArtifactIdIs42() {
    // Arrange and Act
    ProjectDependencyVersionNode actualBuildFromProjectVersionResult = ProjectDependencyVersionNode
        .buildFromProjectVersion(new ProjectVersion("42", "42", "42"));

    // Assert
    assertEquals("42", actualBuildFromProjectVersionResult.getArtifactId());
    assertEquals("42", actualBuildFromProjectVersionResult.getGroupId());
    assertEquals("42", actualBuildFromProjectVersionResult.getVersionId());
    assertEquals("42:42", actualBuildFromProjectVersionResult.getCoordinates());
    assertEquals("42:42:42", actualBuildFromProjectVersionResult.getGav());
    assertEquals("42:42:42", actualBuildFromProjectVersionResult.getId());
    assertNull(actualBuildFromProjectVersionResult.getProjectId());
    assertTrue(actualBuildFromProjectVersionResult.getBackEdges().isEmpty());
    assertTrue(actualBuildFromProjectVersionResult.getForwardEdges().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectDependencyVersionNode#setProjectId(String)}
   *   <li>{@link ProjectDependencyVersionNode#getBackEdges()}
   *   <li>{@link ProjectDependencyVersionNode#getForwardEdges()}
   *   <li>{@link ProjectDependencyVersionNode#getProjectId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set ProjectDependencyVersionNode.getBackEdges()",
      "Set ProjectDependencyVersionNode.getForwardEdges()", "String ProjectDependencyVersionNode.getProjectId()",
      "void ProjectDependencyVersionNode.setProjectId(String)"})
  void testGettersAndSetters() {
    // Arrange
    ProjectDependencyVersionNode projectDependencyVersionNode = new ProjectDependencyVersionNode("42", "42", "42");

    // Act
    projectDependencyVersionNode.setProjectId("myproject");
    Set<String> actualBackEdges = projectDependencyVersionNode.getBackEdges();
    Set<String> actualForwardEdges = projectDependencyVersionNode.getForwardEdges();

    // Assert
    assertEquals("myproject", projectDependencyVersionNode.getProjectId());
    assertTrue(actualBackEdges.isEmpty());
    assertTrue(actualForwardEdges.isEmpty());
  }

  /**
   * Test {@link ProjectDependencyVersionNode#getGav()}.
   * <p>
   * Method under test: {@link ProjectDependencyVersionNode#getGav()}
   */
  @Test
  @DisplayName("Test getGav()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProjectDependencyVersionNode.getGav()"})
  void testGetGav() {
    // Arrange, Act and Assert
    assertEquals("42:42:42", (new ProjectDependencyVersionNode("42", "42", "42")).getGav());
  }

  /**
   * Test {@link ProjectDependencyVersionNode#getCoordinates()}.
   * <p>
   * Method under test: {@link ProjectDependencyVersionNode#getCoordinates()}
   */
  @Test
  @DisplayName("Test getCoordinates()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProjectDependencyVersionNode.getCoordinates()"})
  void testGetCoordinates() {
    // Arrange, Act and Assert
    assertEquals("42:42", (new ProjectDependencyVersionNode("42", "42", "42")).getCoordinates());
  }

  /**
   * Test {@link ProjectDependencyVersionNode#getId()}.
   * <p>
   * Method under test: {@link ProjectDependencyVersionNode#getId()}
   */
  @Test
  @DisplayName("Test getId()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProjectDependencyVersionNode.getId()"})
  void testGetId() {
    // Arrange, Act and Assert
    assertEquals("42:42:42", (new ProjectDependencyVersionNode("42", "42", "42")).getId());
  }
}
