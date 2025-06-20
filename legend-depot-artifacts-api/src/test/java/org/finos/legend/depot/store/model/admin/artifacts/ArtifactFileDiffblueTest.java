package org.finos.legend.depot.store.model.admin.artifacts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ArtifactFileDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ArtifactFile#ArtifactFile()}
   *   <li>{@link ArtifactFile#setCheckSum(String)}
   *   <li>{@link ArtifactFile#setPath(String)}
   *   <li>{@link ArtifactFile#getCheckSum()}
   *   <li>{@link ArtifactFile#getId()}
   *   <li>{@link ArtifactFile#getPath()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ArtifactFile.<init>()", "void ArtifactFile.<init>(String, String)",
      "String ArtifactFile.getCheckSum()", "String ArtifactFile.getId()", "String ArtifactFile.getPath()",
      "ArtifactFile ArtifactFile.setCheckSum(String)", "ArtifactFile ArtifactFile.setPath(String)"})
  void testGettersAndSetters() {
    // Arrange and Act
    ArtifactFile actualArtifactFile = new ArtifactFile();
    ArtifactFile actualSetCheckSumResult = actualArtifactFile.setCheckSum("Check Sum");
    ArtifactFile actualSetPathResult = actualArtifactFile.setPath("New Path");
    String actualCheckSum = actualArtifactFile.getCheckSum();
    String actualId = actualArtifactFile.getId();

    // Assert
    assertEquals("Check Sum", actualCheckSum);
    assertEquals("New Path", actualArtifactFile.getPath());
    assertNull(actualId);
    assertSame(actualArtifactFile, actualSetCheckSumResult);
    assertSame(actualArtifactFile, actualSetPathResult);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Path}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ArtifactFile#ArtifactFile(String, String)}
   *   <li>{@link ArtifactFile#setCheckSum(String)}
   *   <li>{@link ArtifactFile#setPath(String)}
   *   <li>{@link ArtifactFile#getCheckSum()}
   *   <li>{@link ArtifactFile#getId()}
   *   <li>{@link ArtifactFile#getPath()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when 'Path'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ArtifactFile.<init>()", "void ArtifactFile.<init>(String, String)",
      "String ArtifactFile.getCheckSum()", "String ArtifactFile.getId()", "String ArtifactFile.getPath()",
      "ArtifactFile ArtifactFile.setCheckSum(String)", "ArtifactFile ArtifactFile.setPath(String)"})
  void testGettersAndSetters_whenPath() {
    // Arrange and Act
    ArtifactFile actualArtifactFile = new ArtifactFile("Path", "Check Sum");
    ArtifactFile actualSetCheckSumResult = actualArtifactFile.setCheckSum("Check Sum");
    ArtifactFile actualSetPathResult = actualArtifactFile.setPath("New Path");
    String actualCheckSum = actualArtifactFile.getCheckSum();
    String actualId = actualArtifactFile.getId();

    // Assert
    assertEquals("Check Sum", actualCheckSum);
    assertEquals("New Path", actualArtifactFile.getPath());
    assertNull(actualId);
    assertSame(actualArtifactFile, actualSetCheckSumResult);
    assertSame(actualArtifactFile, actualSetPathResult);
  }
}
