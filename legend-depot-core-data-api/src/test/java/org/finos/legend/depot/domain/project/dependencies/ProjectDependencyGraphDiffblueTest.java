package org.finos.legend.depot.domain.project.dependencies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Set;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProjectDependencyGraphDiffblueTest {
  /**
   * Test new {@link ProjectDependencyGraph} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link ProjectDependencyGraph}
   */
  @Test
  @DisplayName("Test new ProjectDependencyGraph (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectDependencyGraph.<init>()"})
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

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectDependencyGraph#getBackEdges()}
   *   <li>{@link ProjectDependencyGraph#getForwardEdges()}
   *   <li>{@link ProjectDependencyGraph#getNodes()}
   *   <li>{@link ProjectDependencyGraph#getRootNodes()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MutableMap ProjectDependencyGraph.getBackEdges()",
      "MutableMap ProjectDependencyGraph.getForwardEdges()", "Set ProjectDependencyGraph.getNodes()",
      "Set ProjectDependencyGraph.getRootNodes()"})
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
   * Test {@link ProjectDependencyGraph#hasNode(ProjectVersion)}.
   * <ul>
   *   <li>Given {@link ProjectDependencyGraph} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectDependencyGraph#hasNode(ProjectVersion)}
   */
  @Test
  @DisplayName("Test hasNode(ProjectVersion); given ProjectDependencyGraph (default constructor); then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ProjectDependencyGraph.hasNode(ProjectVersion)"})
  void testHasNode_givenProjectDependencyGraph_thenReturnFalse() {
    // Arrange
    ProjectDependencyGraph projectDependencyGraph = new ProjectDependencyGraph();

    // Act and Assert
    assertFalse(projectDependencyGraph.hasNode(new ProjectVersion("42", "42", "42")));
  }

  /**
   * Test {@link ProjectDependencyGraph#hasNode(ProjectVersion)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectDependencyGraph#hasNode(ProjectVersion)}
   */
  @Test
  @DisplayName("Test hasNode(ProjectVersion); then return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ProjectDependencyGraph.hasNode(ProjectVersion)"})
  void testHasNode_thenReturnTrue() {
    // Arrange
    ProjectDependencyGraph projectDependencyGraph = new ProjectDependencyGraph();
    ProjectVersion node = new ProjectVersion("42", "42", "42");

    projectDependencyGraph.addNode(node, new ProjectVersion("42", "42", "42"));

    // Act and Assert
    assertTrue(projectDependencyGraph.hasNode(new ProjectVersion("42", "42", "42")));
  }

  /**
   * Test {@link ProjectDependencyGraph#addNode(ProjectVersion, ProjectVersion)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectDependencyGraph#addNode(ProjectVersion, ProjectVersion)}
   */
  @Test
  @DisplayName("Test addNode(ProjectVersion, ProjectVersion); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectDependencyGraph.addNode(ProjectVersion, ProjectVersion)"})
  void testAddNode_whenNull() {
    // Arrange
    ProjectDependencyGraph projectDependencyGraph = new ProjectDependencyGraph();

    // Act
    projectDependencyGraph.addNode(new ProjectVersion("42", "42", "42"), null);

    // Assert
    assertEquals(1, projectDependencyGraph.getNodes().size());
  }

  /**
   * Test {@link ProjectDependencyGraph#addNode(ProjectVersion, ProjectVersion)}.
   * <ul>
   *   <li>When {@link ProjectVersion#ProjectVersion(String, String, String)} with groupId is {@code 42} and artifactId is {@code 42} and versionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectDependencyGraph#addNode(ProjectVersion, ProjectVersion)}
   */
  @Test
  @DisplayName("Test addNode(ProjectVersion, ProjectVersion); when ProjectVersion(String, String, String) with groupId is '42' and artifactId is '42' and versionId is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectDependencyGraph.addNode(ProjectVersion, ProjectVersion)"})
  void testAddNode_whenProjectVersionWithGroupIdIs42AndArtifactIdIs42AndVersionIdIs42() {
    // Arrange
    ProjectDependencyGraph projectDependencyGraph = new ProjectDependencyGraph();
    ProjectVersion node = new ProjectVersion("42", "42", "42");

    // Act
    projectDependencyGraph.addNode(node, new ProjectVersion("42", "42", "42"));

    // Assert
    assertEquals(1, projectDependencyGraph.getNodes().size());
  }

  /**
   * Test {@link ProjectDependencyGraph#setEdges(ProjectVersion, ProjectVersion)}.
   * <p>
   * Method under test: {@link ProjectDependencyGraph#setEdges(ProjectVersion, ProjectVersion)}
   */
  @Test
  @DisplayName("Test setEdges(ProjectVersion, ProjectVersion)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectDependencyGraph.setEdges(ProjectVersion, ProjectVersion)"})
  void testSetEdges() {
    // Arrange
    ProjectDependencyGraph projectDependencyGraph = new ProjectDependencyGraph();
    ProjectVersion from = new ProjectVersion("42", "42", "42");

    // Act
    projectDependencyGraph.setEdges(from, new ProjectVersion("42", "42", "42"));

    // Assert
    MutableList<Set<ProjectVersion>> toListResult = projectDependencyGraph.getBackEdges().toList();
    assertEquals(1, toListResult.size());
    assertEquals(1, projectDependencyGraph.getForwardEdges().toList().size());
    assertEquals(1, toListResult.get(0).size());
  }
}
