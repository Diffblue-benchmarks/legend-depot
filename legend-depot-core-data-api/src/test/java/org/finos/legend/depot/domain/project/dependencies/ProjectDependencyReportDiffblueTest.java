package org.finos.legend.depot.domain.project.dependencies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.collections.api.map.MutableMap;
import org.junit.jupiter.api.Test;

class ProjectDependencyReportDiffblueTest {
  /**
   * Method under test:
   * {@link ProjectDependencyReport#addConflict(String, String, Set)}
   */
  @Test
  void testAddConflict() {
    // Arrange
    ProjectDependencyReport projectDependencyReport = new ProjectDependencyReport();

    // Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> projectDependencyReport.addConflict("42", "42", new HashSet<>()));
  }

  /**
   * Method under test:
   * {@link ProjectDependencyReport#addConflict(String, String, Set)}
   */
  @Test
  void testAddConflict2() {
    // Arrange
    ProjectDependencyReport projectDependencyReport = new ProjectDependencyReport();

    HashSet<String> versions = new HashSet<>();
    versions.add("Conflicts must have more than one version");

    // Act
    projectDependencyReport.addConflict("42", "42", versions);

    // Assert
    List<ProjectDependencyReport.ProjectDependencyConflict> conflicts = projectDependencyReport.getConflicts();
    assertEquals(1, conflicts.size());
    ProjectDependencyReport.ProjectDependencyConflict getResult = conflicts.get(0);
    assertEquals("42", getResult.getArtifactId());
    assertEquals("42", getResult.getGroupId());
    assertSame(versions, getResult.getVersions());
  }

  /**
   * Method under test:
   * {@link ProjectDependencyReport#addConflict(String, String, Set)}
   */
  @Test
  void testAddConflict3() {
    // Arrange
    ProjectDependencyReport projectDependencyReport = new ProjectDependencyReport();

    HashSet<String> versions = new HashSet<>();
    versions.add("42");
    versions.add("Conflicts must have more than one version");

    // Act
    projectDependencyReport.addConflict("42", "42", versions);

    // Assert
    List<ProjectDependencyReport.ProjectDependencyConflict> conflicts = projectDependencyReport.getConflicts();
    assertEquals(1, conflicts.size());
    ProjectDependencyReport.ProjectDependencyConflict getResult = conflicts.get(0);
    assertEquals("42", getResult.getArtifactId());
    assertEquals("42", getResult.getGroupId());
    assertSame(versions, getResult.getVersions());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectDependencyReport#getConflicts()}
   *   <li>{@link ProjectDependencyReport#getGraph()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ProjectDependencyReport projectDependencyReport = new ProjectDependencyReport();

    // Act
    List<ProjectDependencyReport.ProjectDependencyConflict> actualConflicts = projectDependencyReport.getConflicts();
    ProjectDependencyReport.SerializedGraph actualGraph = projectDependencyReport.getGraph();

    // Assert
    assertTrue(actualConflicts.isEmpty());
    assertTrue(actualGraph.getRootNodes().isEmpty());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link ProjectDependencyReport}
   */
  @Test
  void testNewProjectDependencyReport() {
    // Arrange and Act
    ProjectDependencyReport actualProjectDependencyReport = new ProjectDependencyReport();

    // Assert
    ProjectDependencyReport.SerializedGraph graph = actualProjectDependencyReport.getGraph();
    assertTrue(graph.getNodes().toList().isEmpty());
    assertTrue(actualProjectDependencyReport.getConflicts().isEmpty());
    assertTrue(graph.getRootNodes().isEmpty());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectDependencyReport.SerializedGraph#getNodes()}
   *   <li>{@link ProjectDependencyReport.SerializedGraph#getRootNodes()}
   * </ul>
   */
  @Test
  void testSerializedGraphGettersAndSetters() {
    // Arrange
    ProjectDependencyReport.SerializedGraph serializedGraph = new ProjectDependencyReport.SerializedGraph();

    // Act
    MutableMap<String, ProjectDependencyVersionNode> actualNodes = serializedGraph.getNodes();
    Set<String> actualRootNodes = serializedGraph.getRootNodes();

    // Assert
    assertTrue(actualNodes.toList().isEmpty());
    assertTrue(actualRootNodes.isEmpty());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link ProjectDependencyReport.SerializedGraph}
   */
  @Test
  void testSerializedGraphNewSerializedGraph() {
    // Arrange and Act
    ProjectDependencyReport.SerializedGraph actualSerializedGraph = new ProjectDependencyReport.SerializedGraph();

    // Assert
    assertTrue(actualSerializedGraph.getNodes().toList().isEmpty());
    assertTrue(actualSerializedGraph.getRootNodes().isEmpty());
  }
}
