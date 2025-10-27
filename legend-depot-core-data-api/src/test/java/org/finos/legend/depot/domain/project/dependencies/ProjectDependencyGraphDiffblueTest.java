package org.finos.legend.depot.domain.project.dependencies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Set;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.junit.jupiter.api.Test;

class ProjectDependencyGraphDiffblueTest {
  /**
   * Method under test: {@link ProjectDependencyGraph#hasNode(ProjectVersion)}
   */
  @Test
  void testHasNode() {
    // Arrange
    ProjectDependencyGraph projectDependencyGraph = new ProjectDependencyGraph();

    // Act and Assert
    assertFalse(projectDependencyGraph.hasNode(new ProjectVersion("42", "42", "42")));
  }

  /**
   * Method under test: {@link ProjectDependencyGraph#hasNode(ProjectVersion)}
   */
  @Test
  void testHasNode2() {
    // Arrange
    ProjectDependencyGraph projectDependencyGraph = new ProjectDependencyGraph();
    ProjectVersion node = new ProjectVersion("42", "42", "42");

    projectDependencyGraph.addNode(node, new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertTrue(projectDependencyGraph.hasNode(new ProjectVersion("42", "42", "42")));
  }

  /**
   * Method under test:
   * {@link ProjectDependencyGraph#addNode(ProjectVersion, ProjectVersion)}
   */
  @Test
  void testAddNode() {
    // Arrange
    ProjectDependencyGraph projectDependencyGraph = new ProjectDependencyGraph();
    ProjectVersion node = new ProjectVersion("42", "42", "42");

    // Act
    projectDependencyGraph.addNode(node, new ProjectVersion("42", "42", "42"));

    // Assert
    assertEquals(1, projectDependencyGraph.getNodes().size());
    assertTrue(projectDependencyGraph.getRootNodes().isEmpty());
  }

  /**
   * Method under test:
   * {@link ProjectDependencyGraph#addNode(ProjectVersion, ProjectVersion)}
   */
  @Test
  void testAddNode2() {
    // Arrange
    ProjectDependencyGraph projectDependencyGraph = new ProjectDependencyGraph();

    // Act
    projectDependencyGraph.addNode(new ProjectVersion("42", "42", "42"), null);

    // Assert
    assertEquals(1, projectDependencyGraph.getNodes().size());
    assertEquals(1, projectDependencyGraph.getRootNodes().size());
  }

  /**
   * Method under test:
   * {@link ProjectDependencyGraph#setEdges(ProjectVersion, ProjectVersion)}
   */
  @Test
  void testSetEdges() {
    // Arrange
    ProjectDependencyGraph projectDependencyGraph = new ProjectDependencyGraph();
    ProjectVersion from = new ProjectVersion("42", "42", "42");

    // Act
    projectDependencyGraph.setEdges(from, new ProjectVersion("42", "42", "42"));

    // Assert
    MutableMap<ProjectVersion, Set<ProjectVersion>> backEdges = projectDependencyGraph.getBackEdges();
    MutableList<Set<ProjectVersion>> toListResult = backEdges.toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, toListResult.get(0).size());
    assertEquals(backEdges, projectDependencyGraph.getForwardEdges());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectDependencyGraph#getBackEdges()}
   *   <li>{@link ProjectDependencyGraph#getForwardEdges()}
   *   <li>{@link ProjectDependencyGraph#getNodes()}
   *   <li>{@link ProjectDependencyGraph#getRootNodes()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ProjectDependencyGraph projectDependencyGraph = new ProjectDependencyGraph();

    // Act
    MutableMap<ProjectVersion, Set<ProjectVersion>> actualBackEdges = projectDependencyGraph.getBackEdges();
    MutableMap<ProjectVersion, Set<ProjectVersion>> actualForwardEdges = projectDependencyGraph.getForwardEdges();
    Set<ProjectVersion> actualNodes = projectDependencyGraph.getNodes();
    Set<ProjectVersion> actualRootNodes = projectDependencyGraph.getRootNodes();

    // Assert
    assertTrue(actualNodes.isEmpty());
    assertTrue(actualRootNodes.isEmpty());
    assertEquals(actualBackEdges, actualForwardEdges);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link ProjectDependencyGraph}
   */
  @Test
  void testNewProjectDependencyGraph() {
    // Arrange and Act
    ProjectDependencyGraph actualProjectDependencyGraph = new ProjectDependencyGraph();

    // Assert
    MutableMap<ProjectVersion, Set<ProjectVersion>> backEdges = actualProjectDependencyGraph.getBackEdges();
    assertTrue(backEdges.toList().isEmpty());
    assertTrue(actualProjectDependencyGraph.getNodes().isEmpty());
    assertTrue(actualProjectDependencyGraph.getRootNodes().isEmpty());
    assertEquals(backEdges, actualProjectDependencyGraph.getForwardEdges());
  }
}
