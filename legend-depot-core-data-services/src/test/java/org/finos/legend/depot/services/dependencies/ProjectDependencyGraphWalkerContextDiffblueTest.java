package org.finos.legend.depot.services.dependencies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.collections.api.block.function.Function0;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.services.dependencies.ProjectDependencyGraphWalkerContext.DependencyProject;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
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

  /**
   * Test new {@link ProjectDependencyGraphWalkerContext} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link ProjectDependencyGraphWalkerContext}
   */
  @Test
  @DisplayName("Test new ProjectDependencyGraphWalkerContext (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectDependencyGraphWalkerContext.<init>()"})
  void testNewProjectDependencyGraphWalkerContext() {
    // Arrange and Act
    ProjectDependencyGraphWalkerContext actualProjectDependencyGraphWalkerContext = new ProjectDependencyGraphWalkerContext();

    // Assert
    assertTrue(actualProjectDependencyGraphWalkerContext.getProjectVersionToDependencyMap().toList().isEmpty());
    assertTrue(actualProjectDependencyGraphWalkerContext.getProjectToVersions().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectDependencyGraphWalkerContext#getProjectToVersions()}
   *   <li>{@link ProjectDependencyGraphWalkerContext#getProjectVersionToDependencyMap()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ProjectDependencyGraphWalkerContext.getProjectToVersions()",
      "org.eclipse.collections.api.map.ConcurrentMutableMap ProjectDependencyGraphWalkerContext.getProjectVersionToDependencyMap()"})
  void testGettersAndSetters() {
    // Arrange
    ProjectDependencyGraphWalkerContext projectDependencyGraphWalkerContext = new ProjectDependencyGraphWalkerContext();

    // Act
    Map<DependencyProject, Set<ProjectVersion>> actualProjectToVersions = projectDependencyGraphWalkerContext
        .getProjectToVersions();

    // Assert
    assertTrue(projectDependencyGraphWalkerContext.getProjectVersionToDependencyMap().toList().isEmpty());
    assertTrue(actualProjectToVersions.isEmpty());
  }

  /**
   * Test {@link ProjectDependencyGraphWalkerContext#addVersionToProject(String, String, ProjectVersion)}.
   * <p>
   * Method under test: {@link ProjectDependencyGraphWalkerContext#addVersionToProject(String, String, ProjectVersion)}
   */
  @Test
  @DisplayName("Test addVersionToProject(String, String, ProjectVersion)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProjectDependencyGraphWalkerContext.addVersionToProject(String, String, ProjectVersion)"})
  void testAddVersionToProject() {
    // Arrange
    ProjectDependencyGraphWalkerContext projectDependencyGraphWalkerContext = new ProjectDependencyGraphWalkerContext();

    // Act
    projectDependencyGraphWalkerContext.addVersionToProject("42", "42", new ProjectVersion("42", "42", "42"));

    // Assert
    assertEquals(1, projectDependencyGraphWalkerContext.getProjectToVersions().size());
  }

  /**
   * Test {@link ProjectDependencyGraphWalkerContext#getProjectDataPutIfAbsent(String, String, String, Function0)}.
   * <p>
   * Method under test: {@link ProjectDependencyGraphWalkerContext#getProjectDataPutIfAbsent(String, String, String, Function0)}
   */
  @Test
  @DisplayName("Test getProjectDataPutIfAbsent(String, String, String, Function0)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "StoreProjectVersionData ProjectDependencyGraphWalkerContext.getProjectDataPutIfAbsent(String, String, String, Function0)"})
  void testGetProjectDataPutIfAbsent() {
    // Arrange
    ProjectDependencyGraphWalkerContext projectDependencyGraphWalkerContext = new ProjectDependencyGraphWalkerContext();
    Function0<StoreProjectVersionData> projectDataGetter = mock(Function0.class);
    StoreProjectVersionData storeProjectVersionData = new StoreProjectVersionData();
    when(projectDataGetter.value()).thenReturn(storeProjectVersionData);

    // Act
    StoreProjectVersionData actualProjectDataPutIfAbsent = projectDependencyGraphWalkerContext
        .getProjectDataPutIfAbsent("42", "42", "42", projectDataGetter);

    // Assert
    verify(projectDataGetter).value();
    assertSame(storeProjectVersionData, actualProjectDataPutIfAbsent);
  }

  /**
   * Test {@link ProjectDependencyGraphWalkerContext#getProjectData(String, String, String)}.
   * <p>
   * Method under test: {@link ProjectDependencyGraphWalkerContext#getProjectData(String, String, String)}
   */
  @Test
  @DisplayName("Test getProjectData(String, String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "StoreProjectVersionData ProjectDependencyGraphWalkerContext.getProjectData(String, String, String)"})
  void testGetProjectData() {
    // Arrange, Act and Assert
    assertNull(new ProjectDependencyGraphWalkerContext().getProjectData("42", "42", "42"));
  }

  /**
   * Test {@link ProjectDependencyGraphWalkerContext#getProjectDataDependencies(List, boolean)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectDependencyGraphWalkerContext#getProjectDataDependencies(List, boolean)}
   */
  @Test
  @DisplayName("Test getProjectDataDependencies(List, boolean); when ArrayList(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set ProjectDependencyGraphWalkerContext.getProjectDataDependencies(List, boolean)"})
  void testGetProjectDataDependencies_whenArrayList_thenReturnEmpty() {
    // Arrange
    ProjectDependencyGraphWalkerContext projectDependencyGraphWalkerContext = new ProjectDependencyGraphWalkerContext();

    // Act and Assert
    assertTrue(projectDependencyGraphWalkerContext.getProjectDataDependencies(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Test {@link ProjectDependencyGraphWalkerContext#getProjectDataDependencies(List, boolean)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectDependencyGraphWalkerContext#getProjectDataDependencies(List, boolean)}
   */
  @Test
  @DisplayName("Test getProjectDataDependencies(List, boolean); when ArrayList(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Set ProjectDependencyGraphWalkerContext.getProjectDataDependencies(List, boolean)"})
  void testGetProjectDataDependencies_whenArrayList_thenReturnEmpty2() {
    // Arrange
    ProjectDependencyGraphWalkerContext projectDependencyGraphWalkerContext = new ProjectDependencyGraphWalkerContext();

    // Act and Assert
    assertTrue(projectDependencyGraphWalkerContext.getProjectDataDependencies(new ArrayList<>(), false).isEmpty());
  }
}
