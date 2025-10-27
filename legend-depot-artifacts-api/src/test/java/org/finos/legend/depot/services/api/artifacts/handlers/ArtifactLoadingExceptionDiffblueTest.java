package org.finos.legend.depot.services.api.artifacts.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class ArtifactLoadingExceptionDiffblueTest {
  /**
   * Method under test:
   * {@link ArtifactLoadingException#ArtifactLoadingException(String)}
   */
  @Test
  void testNewArtifactLoadingException() {
    // Arrange and Act
    ArtifactLoadingException actualArtifactLoadingException = new ArtifactLoadingException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualArtifactLoadingException.getMessage());
    assertNull(actualArtifactLoadingException.getCause());
    assertEquals(0, actualArtifactLoadingException.getSuppressed().length);
  }
}
