package org.finos.legend.depot.services.api.artifacts.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class ArtifactNotFoundExceptionDiffblueTest {
  /**
   * Method under test:
   * {@link ArtifactNotFoundException#ArtifactNotFoundException(String)}
   */
  @Test
  void testNewArtifactNotFoundException() {
    // Arrange and Act
    ArtifactNotFoundException actualArtifactNotFoundException = new ArtifactNotFoundException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualArtifactNotFoundException.getMessage());
    assertNull(actualArtifactNotFoundException.getCause());
    assertEquals(0, actualArtifactNotFoundException.getSuppressed().length);
  }
}
