package org.finos.legend.depot.services.artifacts.handlers.generations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.finos.legend.depot.domain.artifacts.repository.ArtifactType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class FileGenerationsProviderDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link FileGenerationsProvider}
   *   <li>{@link FileGenerationsProvider#getType()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void FileGenerationsProvider.<init>()",
    "ArtifactType FileGenerationsProvider.getType()"
  })
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals(ArtifactType.FILE_GENERATIONS, new FileGenerationsProvider().getType());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link FileGenerationsProvider}
   *   <li>{@link FileGenerationsProvider#getType()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void FileGenerationsProvider.<init>()",
    "ArtifactType FileGenerationsProvider.getType()"
  })
  void testGettersAndSetters2() {
    // Arrange, Act and Assert
    assertEquals(ArtifactType.FILE_GENERATIONS, new FileGenerationsProvider().getType());
  }
}
