package org.finos.legend.depot.services.dependencies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ProjectDependencyGraphWalkerContextDiffblueTest {
  /**
   * Method under test:
   * {@link ProjectDependencyGraphWalkerContext.DependencyProject#DependencyProject(String, String)}
   */
  @Test
  void testDependencyProjectNewDependencyProject() {
    // Arrange and Act
    ProjectDependencyGraphWalkerContext.DependencyProject actualDependencyProject = new ProjectDependencyGraphWalkerContext.DependencyProject(
        "42", "42");

    // Assert
    assertEquals("42", actualDependencyProject.getArtifactId());
    assertEquals("42", actualDependencyProject.getGroupId());
  }
}
