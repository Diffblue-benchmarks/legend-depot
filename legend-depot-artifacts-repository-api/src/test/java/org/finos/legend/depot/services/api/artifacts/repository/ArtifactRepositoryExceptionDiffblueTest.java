package org.finos.legend.depot.services.api.artifacts.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

class ArtifactRepositoryExceptionDiffblueTest {
  /**
   * Method under test:
   * {@link ArtifactRepositoryException#ArtifactRepositoryException(String)}
   */
  @Test
  void testNewArtifactRepositoryException() {
    // Arrange and Act
    ArtifactRepositoryException actualArtifactRepositoryException = new ArtifactRepositoryException(
        "An error occurred");

    // Assert
    assertEquals("An error occurred", actualArtifactRepositoryException.getMessage());
    assertNull(actualArtifactRepositoryException.getCause());
    assertEquals(0, actualArtifactRepositoryException.getSuppressed().length);
  }

  /**
   * Method under test:
   * {@link ArtifactRepositoryException#ArtifactRepositoryException(Throwable)}
   */
  @Test
  void testNewArtifactRepositoryException2() {
    // Arrange
    Throwable cause = new Throwable();

    // Act
    ArtifactRepositoryException actualArtifactRepositoryException = new ArtifactRepositoryException(cause);

    // Assert
    assertEquals("java.lang.Throwable", actualArtifactRepositoryException.getMessage());
    assertEquals(0, actualArtifactRepositoryException.getSuppressed().length);
    assertSame(cause, actualArtifactRepositoryException.getCause());
  }
}
