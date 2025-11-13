package org.finos.legend.depot.services.artifacts.handlers;

import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.File;
import java.nio.file.Paths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ManifestLoaderDiffblueTest {
  /**
   * Test {@link ManifestLoader#readManifest(File)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ManifestLoader#readManifest(File)}
   */
  @Test
  @DisplayName("Test readManifest(File); when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.jar.Manifest ManifestLoader.readManifest(File)"})
  void testReadManifest_whenNull() {
    // Arrange, Act and Assert
    assertNull(ManifestLoader.readManifest(null));
  }

  /**
   * Test {@link ManifestLoader#readManifest(File)}.
   *
   * <ul>
   *   <li>When Property is {@code java.io.tmpdir} is {@code test.txt} toFile.
   * </ul>
   *
   * <p>Method under test: {@link ManifestLoader#readManifest(File)}
   */
  @Test
  @DisplayName("Test readManifest(File); when Property is 'java.io.tmpdir' is 'test.txt' toFile")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.jar.Manifest ManifestLoader.readManifest(File)"})
  void testReadManifest_whenPropertyIsJavaIoTmpdirIsTestTxtToFile() {
    // Arrange, Act and Assert
    assertNull(
        ManifestLoader.readManifest(
            Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }
}
