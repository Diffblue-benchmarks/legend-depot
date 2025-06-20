package org.finos.legend.depot.services.api.artifacts.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ArtifactLoadingExceptionDiffblueTest {
  /**
   * Test {@link ArtifactLoadingException#ArtifactLoadingException(String)}.
   * <p>
   * Method under test: {@link ArtifactLoadingException#ArtifactLoadingException(String)}
   */
  @Test
  @DisplayName("Test new ArtifactLoadingException(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ArtifactLoadingException.<init>(String)"})
  void testNewArtifactLoadingException() {
    // Arrange and Act
    ArtifactLoadingException actualArtifactLoadingException = new ArtifactLoadingException("An error occurred");

    // Assert
    assertEquals("An error occurred", actualArtifactLoadingException.getMessage());
    assertNull(actualArtifactLoadingException.getCause());
    assertEquals(0, actualArtifactLoadingException.getSuppressed().length);
  }
}
