package org.finos.legend.depot.services.api.artifacts.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ArtifactRepositoryExceptionDiffblueTest {
  /**
   * Test {@link ArtifactRepositoryException#ArtifactRepositoryException(String)}.
   * <ul>
   *   <li>Then return Message is {@code An error occurred}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ArtifactRepositoryException#ArtifactRepositoryException(String)}
   */
  @Test
  @DisplayName("Test new ArtifactRepositoryException(String); then return Message is 'An error occurred'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ArtifactRepositoryException.<init>(String)",
      "void ArtifactRepositoryException.<init>(Throwable)"})
  void testNewArtifactRepositoryException_thenReturnMessageIsAnErrorOccurred() {
    // Arrange and Act
    ArtifactRepositoryException actualArtifactRepositoryException = new ArtifactRepositoryException(
        "An error occurred");

    // Assert
    assertEquals("An error occurred", actualArtifactRepositoryException.getMessage());
    assertNull(actualArtifactRepositoryException.getCause());
    assertEquals(0, actualArtifactRepositoryException.getSuppressed().length);
  }

  /**
   * Test {@link ArtifactRepositoryException#ArtifactRepositoryException(Throwable)}.
   * <ul>
   *   <li>Then return Message is {@code Throwable}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ArtifactRepositoryException#ArtifactRepositoryException(Throwable)}
   */
  @Test
  @DisplayName("Test new ArtifactRepositoryException(Throwable); then return Message is 'java.lang.Throwable'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ArtifactRepositoryException.<init>(String)",
      "void ArtifactRepositoryException.<init>(Throwable)"})
  void testNewArtifactRepositoryException_thenReturnMessageIsJavaLangThrowable() {
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
