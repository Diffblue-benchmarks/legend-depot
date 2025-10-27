package org.finos.legend.depot.domain.project;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.junit.jupiter.api.Test;

class ProjectValidatorDiffblueTest {
  /**
   * Method under test: {@link ProjectValidator#isValid(StoreProjectData)}
   */
  @Test
  void testIsValid() {
    // Arrange, Act and Assert
    assertFalse(ProjectValidator.isValid(new StoreProjectData("myproject", "42", "42")));
    assertFalse(ProjectValidator.isValid(new StoreProjectData("PROD-9", "42", "42")));
    assertFalse(ProjectValidator.isValid(new StoreProjectData(null, "42", "42")));
    assertFalse(ProjectValidator.isValid(new StoreProjectData("PROD-9", null, "42")));
    assertFalse(ProjectValidator.isValid(new StoreProjectData("PROD-9", "", "42")));
  }

  /**
   * Method under test: {@link ProjectValidator#isValidProjectId(String)}
   */
  @Test
  void testIsValidProjectId() {
    // Arrange, Act and Assert
    assertFalse(ProjectValidator.isValidProjectId("myproject"));
    assertFalse(ProjectValidator.isValidProjectId(null));
    assertTrue(ProjectValidator.isValidProjectId("PROD-9"));
  }
}
