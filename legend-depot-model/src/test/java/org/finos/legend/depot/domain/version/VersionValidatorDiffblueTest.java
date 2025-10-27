package org.finos.legend.depot.domain.version;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class VersionValidatorDiffblueTest {
  /**
   * Method under test: {@link VersionValidator#BRANCH_SNAPSHOT(String)}
   */
  @Test
  void testBRANCH_SNAPSHOT() {
    // Arrange, Act and Assert
    assertEquals("janedoe/featurebranch-SNAPSHOT", VersionValidator.BRANCH_SNAPSHOT("janedoe/featurebranch"));
  }

  /**
   * Method under test: {@link VersionValidator#isValid(String)}
   */
  @Test
  void testIsValid() {
    // Arrange, Act and Assert
    assertFalse(VersionValidator.isValid("42"));
    assertFalse(VersionValidator.isValid(null));
    assertFalse(VersionValidator.isValid(""));
    assertTrue(VersionValidator.isValid("-SNAPSHOT"));
    assertTrue(VersionValidator.isValid("1.0.2"));
    assertTrue(VersionValidator.isValid("421.0.2"));
    assertFalse(VersionValidator.isValid("1.0.21.0.2"));
    assertFalse(VersionValidator.isValid("1.0.2Version Id"));
    assertFalse(VersionValidator.isValid("Version Id1.0.2"));
  }

  /**
   * Method under test: {@link VersionValidator#isValidReleaseVersion(String)}
   */
  @Test
  void testIsValidReleaseVersion() {
    // Arrange, Act and Assert
    assertFalse(VersionValidator.isValidReleaseVersion("42"));
    assertFalse(VersionValidator.isValidReleaseVersion(null));
    assertTrue(VersionValidator.isValidReleaseVersion("1.0.2"));
    assertTrue(VersionValidator.isValidReleaseVersion("421.0.2"));
    assertFalse(VersionValidator.isValidReleaseVersion("1.0.21.0.2"));
    assertFalse(VersionValidator.isValidReleaseVersion("1.0.2Invalid version string: \""));
    assertFalse(VersionValidator.isValidReleaseVersion("Invalid version string: \"1.0.2"));
  }

  /**
   * Method under test: {@link VersionValidator#isSnapshotVersion(String)}
   */
  @Test
  void testIsSnapshotVersion() {
    // Arrange, Act and Assert
    assertFalse(VersionValidator.isSnapshotVersion("42"));
    assertTrue(VersionValidator.isSnapshotVersion("-SNAPSHOT"));
  }

  /**
   * Method under test: {@link VersionValidator#isVersionAlias(String)}
   */
  @Test
  void testIsVersionAlias() {
    // Arrange, Act and Assert
    assertFalse(VersionValidator.isVersionAlias("42"));
  }
}
