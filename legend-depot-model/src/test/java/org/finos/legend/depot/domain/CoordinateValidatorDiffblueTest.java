package org.finos.legend.depot.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class CoordinateValidatorDiffblueTest {
  /**
   * Method under test: {@link CoordinateValidator#isValidArtifactId(String)}
   */
  @Test
  void testIsValidArtifactId() {
    // Arrange, Act and Assert
    assertFalse(CoordinateValidator.isValidArtifactId("42"));
    assertFalse(CoordinateValidator.isValidArtifactId(null));
    assertFalse(CoordinateValidator.isValidArtifactId(""));
    assertTrue(CoordinateValidator.isValidArtifactId("lll-lll-lll"));
  }

  /**
   * Method under test: {@link CoordinateValidator#isValidGroupId(String)}
   */
  @Test
  void testIsValidGroupId() {
    // Arrange, Act and Assert
    assertFalse(CoordinateValidator.isValidGroupId("42"));
    assertFalse(CoordinateValidator.isValidGroupId(null));
    assertFalse(CoordinateValidator.isValidGroupId(""));
  }
}
