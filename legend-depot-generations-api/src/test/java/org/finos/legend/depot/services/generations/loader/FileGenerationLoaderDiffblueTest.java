package org.finos.legend.depot.services.generations.loader;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.finos.legend.depot.domain.generation.DepotGeneration;
import org.junit.jupiter.api.Test;

class FileGenerationLoaderDiffblueTest {
  /**
   * Method under test:
   * {@link FileGenerationLoader#newFileGenerationsLoader(File)}
   */
  @Test
  void testNewFileGenerationsLoader() {
    // Arrange, Act and Assert
    Stream<DepotGeneration> allFileGenerations = FileGenerationLoader
        .newFileGenerationsLoader(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile())
        .getAllFileGenerations();
    assertTrue(allFileGenerations.limit(5).collect(Collectors.toList()).isEmpty());
  }

  /**
   * Method under test:
   * {@link FileGenerationLoader#newFileGenerationsLoader(Path)}
   */
  @Test
  void testNewFileGenerationsLoader2() {
    // Arrange, Act and Assert
    Stream<DepotGeneration> allFileGenerations = FileGenerationLoader
        .newFileGenerationsLoader(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"))
        .getAllFileGenerations();
    assertTrue(allFileGenerations.limit(5).collect(Collectors.toList()).isEmpty());
  }

  /**
   * Method under test: {@link FileGenerationLoader#getAllFileGenerations()}
   */
  @Test
  void testGetAllFileGenerations() {
    // Arrange and Act
    Stream<DepotGeneration> actualAllFileGenerations = FileGenerationLoader
        .newFileGenerationsLoader(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"))
        .getAllFileGenerations();

    // Assert
    assertTrue(actualAllFileGenerations.limit(5).collect(Collectors.toList()).isEmpty());
  }
}
