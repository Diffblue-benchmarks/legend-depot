package org.finos.legend.depot.services.api.artifacts.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ArtifactNotFoundExceptionDiffblueTest {
  /**
   * Test {@link ArtifactNotFoundException#ArtifactNotFoundException(String)}.
   * <p>
   * Method under test: {@link ArtifactNotFoundException#ArtifactNotFoundException(String)}
   */
  @Test
  @DisplayName("Test new ArtifactNotFoundException(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ArtifactNotFoundException.<init>(String)"})
  void testNewArtifactNotFoundException() {
    // Arrange and Act
    ArtifactNotFoundException actualArtifactNotFoundException = new ArtifactNotFoundException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualArtifactNotFoundException.getMessage());
    assertNull(actualArtifactNotFoundException.getCause());
    assertEquals(0, actualArtifactNotFoundException.getSuppressed().length);
  }
}
