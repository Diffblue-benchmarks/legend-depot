package org.finos.legend.depot.domain.project;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProjectValidatorDiffblueTest {
  /**
   * Test {@link ProjectValidator#isValid(StoreProjectData)}.
   * <p>
   * Method under test: {@link ProjectValidator#isValid(StoreProjectData)}
   */
  @Test
  @DisplayName("Test isValid(StoreProjectData)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ProjectValidator.isValid(StoreProjectData)"})
  void testIsValid() {
    // Arrange, Act and Assert
    assertFalse(ProjectValidator.isValid(new StoreProjectData("myproject", "42", "42")));
  }

  /**
   * Test {@link ProjectValidator#isValid(StoreProjectData)}.
   * <p>
   * Method under test: {@link ProjectValidator#isValid(StoreProjectData)}
   */
  @Test
  @DisplayName("Test isValid(StoreProjectData)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ProjectValidator.isValid(StoreProjectData)"})
  void testIsValid2() {
    // Arrange, Act and Assert
    assertFalse(ProjectValidator.isValid(new StoreProjectData("PROD-9", "42", "42")));
  }

  /**
   * Test {@link ProjectValidator#isValid(StoreProjectData)}.
   * <p>
   * Method under test: {@link ProjectValidator#isValid(StoreProjectData)}
   */
  @Test
  @DisplayName("Test isValid(StoreProjectData)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ProjectValidator.isValid(StoreProjectData)"})
  void testIsValid3() {
    // Arrange, Act and Assert
    assertFalse(ProjectValidator.isValid(new StoreProjectData(null, "42", "42")));
  }

  /**
   * Test {@link ProjectValidator#isValid(StoreProjectData)}.
   * <p>
   * Method under test: {@link ProjectValidator#isValid(StoreProjectData)}
   */
  @Test
  @DisplayName("Test isValid(StoreProjectData)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ProjectValidator.isValid(StoreProjectData)"})
  void testIsValid4() {
    // Arrange, Act and Assert
    assertFalse(ProjectValidator.isValid(new StoreProjectData("PROD-9", null, "42")));
  }

  /**
   * Test {@link ProjectValidator#isValid(StoreProjectData)}.
   * <p>
   * Method under test: {@link ProjectValidator#isValid(StoreProjectData)}
   */
  @Test
  @DisplayName("Test isValid(StoreProjectData)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ProjectValidator.isValid(StoreProjectData)"})
  void testIsValid5() {
    // Arrange, Act and Assert
    assertFalse(ProjectValidator.isValid(new StoreProjectData("PROD-9", "", "42")));
  }

  /**
   * Test {@link ProjectValidator#isValidProjectId(String)}.
   * <ul>
   *   <li>When {@code myproject}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectValidator#isValidProjectId(String)}
   */
  @Test
  @DisplayName("Test isValidProjectId(String); when 'myproject'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ProjectValidator.isValidProjectId(String)"})
  void testIsValidProjectId_whenMyproject_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(ProjectValidator.isValidProjectId("myproject"));
  }

  /**
   * Test {@link ProjectValidator#isValidProjectId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectValidator#isValidProjectId(String)}
   */
  @Test
  @DisplayName("Test isValidProjectId(String); when 'null'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ProjectValidator.isValidProjectId(String)"})
  void testIsValidProjectId_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(ProjectValidator.isValidProjectId(null));
  }

  /**
   * Test {@link ProjectValidator#isValidProjectId(String)}.
   * <ul>
   *   <li>When {@code PROD-9}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProjectValidator#isValidProjectId(String)}
   */
  @Test
  @DisplayName("Test isValidProjectId(String); when 'PROD-9'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ProjectValidator.isValidProjectId(String)"})
  void testIsValidProjectId_whenProd9_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(ProjectValidator.isValidProjectId("PROD-9"));
  }
}
