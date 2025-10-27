package org.finos.legend.depot.services.artifacts.handlers;

import static org.junit.jupiter.api.Assertions.assertNull;
import java.io.File;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class ManifestLoaderDiffblueTest {
  /**
   * Method under test: {@link ManifestLoader#readManifest(File)}
   */
  @Test
  void testReadManifest() {
    // Arrange, Act and Assert
    assertNull(ManifestLoader.readManifest(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
    assertNull(ManifestLoader.readManifest(null));
  }
}
