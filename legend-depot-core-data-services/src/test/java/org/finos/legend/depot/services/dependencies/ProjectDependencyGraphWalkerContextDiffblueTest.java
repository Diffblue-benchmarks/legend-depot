package org.finos.legend.depot.services.dependencies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.finos.legend.depot.services.dependencies.ProjectDependencyGraphWalkerContext.DependencyProject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProjectDependencyGraphWalkerContextDiffblueTest {
  /**
   * Test DependencyProject {@link DependencyProject#DependencyProject(String, String)}.
   * <p>
   * Method under test: {@link DependencyProject#DependencyProject(String, String)}
   */
  @Test
  @DisplayName("Test DependencyProject new DependencyProject(String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DependencyProject.<init>(String, String)"})
  void testDependencyProjectNewDependencyProject() {
    // Arrange and Act
    DependencyProject actualDependencyProject = new DependencyProject("42", "42");

    // Assert
    assertEquals("42", actualDependencyProject.getArtifactId());
    assertEquals("42", actualDependencyProject.getGroupId());
  }
}
