package org.finos.legend.depot.services.artifacts.handlers.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.File;
import java.nio.file.Paths;
import org.finos.legend.depot.domain.artifacts.repository.ArtifactType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class VersionedEntityProviderDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link VersionedEntityProvider}
   *   <li>{@link VersionedEntityProvider#getType()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VersionedEntityProvider.<init>()",
    "ArtifactType VersionedEntityProvider.getType()"
  })
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals(ArtifactType.VERSIONED_ENTITIES, new VersionedEntityProvider().getType());
  }

  /**
   * Test {@link VersionedEntityProvider#matchesArtifactType(File)}.
   *
   * <p>Method under test: {@link VersionedEntityProvider#matchesArtifactType(File)}
   */
  @Test
  @DisplayName("Test matchesArtifactType(File)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VersionedEntityProvider.matchesArtifactType(File)"})
  void testMatchesArtifactType() {
    // Arrange and Act
    boolean actualMatchesArtifactTypeResult =
        new VersionedEntityProvider()
            .matchesArtifactType(
                Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());

    // Assert
    assertFalse(actualMatchesArtifactTypeResult);
  }
}
