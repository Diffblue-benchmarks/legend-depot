package org.finos.legend.depot.store.model.admin.artifacts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

class ArtifactFileDiffblueTest {
  /**
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
  void testGettersAndSetters2() {
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
