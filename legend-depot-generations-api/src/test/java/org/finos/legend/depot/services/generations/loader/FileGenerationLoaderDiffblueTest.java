package org.finos.legend.depot.services.generations.loader;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.finos.legend.depot.domain.generation.DepotGeneration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class FileGenerationLoaderDiffblueTest {
  /**
   * Test {@link FileGenerationLoader#newFileGenerationsLoader(File)} with {@code File}.
   * <p>
   * Method under test: {@link FileGenerationLoader#newFileGenerationsLoader(File)}
   */
  @Test
  @DisplayName("Test newFileGenerationsLoader(File) with 'File'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FileGenerationLoader FileGenerationLoader.newFileGenerationsLoader(File)"})
  void testNewFileGenerationsLoaderWithFile() {
    // Arrange, Act and Assert
    Stream<DepotGeneration> allFileGenerations = FileGenerationLoader
        .newFileGenerationsLoader(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile())
        .getAllFileGenerations();
    assertTrue(allFileGenerations.limit(5).collect(Collectors.toList()).isEmpty());
  }

  /**
   * Test {@link FileGenerationLoader#newFileGenerationsLoader(Path)} with {@code Path}.
   * <p>
   * Method under test: {@link FileGenerationLoader#newFileGenerationsLoader(Path)}
   */
  @Test
  @DisplayName("Test newFileGenerationsLoader(Path) with 'Path'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FileGenerationLoader FileGenerationLoader.newFileGenerationsLoader(Path)"})
  void testNewFileGenerationsLoaderWithPath() {
    // Arrange, Act and Assert
    Stream<DepotGeneration> allFileGenerations = FileGenerationLoader
        .newFileGenerationsLoader(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"))
        .getAllFileGenerations();
    assertTrue(allFileGenerations.limit(5).collect(Collectors.toList()).isEmpty());
  }

  /**
   * Test {@link FileGenerationLoader#getAllFileGenerations()}.
   * <p>
   * Method under test: {@link FileGenerationLoader#getAllFileGenerations()}
   */
  @Test
  @DisplayName("Test getAllFileGenerations()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Stream FileGenerationLoader.getAllFileGenerations()"})
  void testGetAllFileGenerations() {
    // Arrange and Act
    Stream<DepotGeneration> actualAllFileGenerations = FileGenerationLoader
        .newFileGenerationsLoader(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"))
        .getAllFileGenerations();

    // Assert
    assertTrue(actualAllFileGenerations.limit(5).collect(Collectors.toList()).isEmpty());
  }
}
