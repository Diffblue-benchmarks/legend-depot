package org.finos.legend.depot.services.artifacts.handlers.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.finos.legend.depot.domain.artifacts.repository.ArtifactType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class EntityProviderDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link EntityProvider}
   *   <li>{@link EntityProvider#getType()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EntityProvider.<init>()", "ArtifactType EntityProvider.getType()"})
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals(ArtifactType.ENTITIES, new EntityProvider().getType());
  }

  /**
   * Test {@link EntityProvider#extractArtifactsForType(Stream)}.
   *
   * <p>Method under test: {@link EntityProvider#extractArtifactsForType(Stream)}
   */
  @Test
  @DisplayName("Test extractArtifactsForType(Stream)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List EntityProvider.extractArtifactsForType(Stream)"})
  void testExtractArtifactsForType() {
    // Arrange
    EntityProvider entityProvider = new EntityProvider();

    ArrayList<File> fileList = new ArrayList<>();
    fileList.add(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());
    Stream<File> files = fileList.stream();

    // Act and Assert
    assertTrue(entityProvider.extractArtifactsForType(files).isEmpty());
  }

  /**
   * Test {@link EntityProvider#extractArtifactsForType(Stream)}.
   *
   * <p>Method under test: {@link EntityProvider#extractArtifactsForType(Stream)}
   */
  @Test
  @DisplayName("Test extractArtifactsForType(Stream)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List EntityProvider.extractArtifactsForType(Stream)"})
  void testExtractArtifactsForType2() {
    // Arrange
    EntityProvider entityProvider = new EntityProvider();

    ArrayList<File> fileList = new ArrayList<>();
    fileList.add(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());
    fileList.add(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());
    Stream<File> files = fileList.stream();

    // Act and Assert
    assertTrue(entityProvider.extractArtifactsForType(files).isEmpty());
  }

  /**
   * Test {@link EntityProvider#extractArtifactsForType(Stream)}.
   *
   * <p>Method under test: {@link EntityProvider#extractArtifactsForType(Stream)}
   */
  @Test
  @DisplayName("Test extractArtifactsForType(Stream)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List EntityProvider.extractArtifactsForType(Stream)"})
  void testExtractArtifactsForType3() {
    // Arrange
    EntityProvider entityProvider = new EntityProvider();

    ArrayList<File> fileList = new ArrayList<>();
    fileList.add(Paths.get(System.getProperty("java.io.tmpdir"), "").toFile());
    Stream<File> files = fileList.stream();

    // Act and Assert
    assertTrue(entityProvider.extractArtifactsForType(files).isEmpty());
  }

  /**
   * Test {@link EntityProvider#extractArtifactsForType(Stream)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()} stream.
   * </ul>
   *
   * <p>Method under test: {@link EntityProvider#extractArtifactsForType(Stream)}
   */
  @Test
  @DisplayName("Test extractArtifactsForType(Stream); when ArrayList() stream")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List EntityProvider.extractArtifactsForType(Stream)"})
  void testExtractArtifactsForType_whenArrayListStream() {
    // Arrange
    EntityProvider entityProvider = new EntityProvider();

    ArrayList<File> fileList = new ArrayList<>();
    Stream<File> files = fileList.stream();

    // Act and Assert
    assertTrue(entityProvider.extractArtifactsForType(files).isEmpty());
  }

  /**
   * Test {@link EntityProvider#matchesArtifactType(File)}.
   *
   * <p>Method under test: {@link EntityProvider#matchesArtifactType(File)}
   */
  @Test
  @DisplayName("Test matchesArtifactType(File)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean EntityProvider.matchesArtifactType(File)"})
  void testMatchesArtifactType() {
    // Arrange and Act
    boolean actualMatchesArtifactTypeResult =
        new EntityProvider()
            .matchesArtifactType(
                Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());

    // Assert
    assertFalse(actualMatchesArtifactTypeResult);
  }
}
