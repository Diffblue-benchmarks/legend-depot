package org.finos.legend.depot.domain.project.dependencies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Set;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.junit.jupiter.api.Test;

class ProjectDependencyVersionNodeDiffblueTest {
  /**
   * Method under test:
   * {@link ProjectDependencyVersionNode#buildFromProjectVersion(ProjectVersion)}
   */
  @Test
  void testBuildFromProjectVersion() {
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
   * Method under test: {@link ProjectDependencyVersionNode#getGav()}
   */
  @Test
  void testGetGav() {
    // Arrange, Act and Assert
    assertEquals("42:42:42", (new ProjectDependencyVersionNode("42", "42", "42")).getGav());
  }

  /**
   * Method under test: {@link ProjectDependencyVersionNode#getCoordinates()}
   */
  @Test
  void testGetCoordinates() {
    // Arrange, Act and Assert
    assertEquals("42:42", (new ProjectDependencyVersionNode("42", "42", "42")).getCoordinates());
  }

  /**
   * Method under test: {@link ProjectDependencyVersionNode#getId()}
   */
  @Test
  void testGetId() {
    // Arrange, Act and Assert
    assertEquals("42:42:42", (new ProjectDependencyVersionNode("42", "42", "42")).getId());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectDependencyVersionNode#setProjectId(String)}
   *   <li>{@link ProjectDependencyVersionNode#getBackEdges()}
   *   <li>{@link ProjectDependencyVersionNode#getForwardEdges()}
   *   <li>{@link ProjectDependencyVersionNode#getProjectId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ProjectDependencyVersionNode projectDependencyVersionNode = new ProjectDependencyVersionNode("42", "42", "42");

    // Act
    projectDependencyVersionNode.setProjectId("myproject");
    Set<String> actualBackEdges = projectDependencyVersionNode.getBackEdges();
    Set<String> actualForwardEdges = projectDependencyVersionNode.getForwardEdges();

    // Assert that nothing has changed
    assertEquals("myproject", projectDependencyVersionNode.getProjectId());
    assertTrue(actualBackEdges.isEmpty());
    assertTrue(actualForwardEdges.isEmpty());
  }

  /**
   * Method under test:
   * {@link ProjectDependencyVersionNode#ProjectDependencyVersionNode(String, String, String)}
   */
  @Test
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
}
