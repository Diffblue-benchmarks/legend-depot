package org.finos.legend.depot.services.api.projects.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ProjectsConfigurationDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProjectsConfiguration#ProjectsConfiguration(String)}
   *   <li>{@link ProjectsConfiguration#getDefaultBranch()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("janedoe/featurebranch", (new ProjectsConfiguration("janedoe/featurebranch")).getDefaultBranch());
  }
}
