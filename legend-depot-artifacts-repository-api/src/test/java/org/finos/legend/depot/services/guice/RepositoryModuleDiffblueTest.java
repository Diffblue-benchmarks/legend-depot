package org.finos.legend.depot.services.guice;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepository;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryException;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryProviderConfiguration;
import org.finos.legend.depot.services.api.artifacts.repository.VoidArtifactRepositoryConfiguration;
import org.finos.legend.depot.services.api.artifacts.repository.VoidArtifactRepositoryProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RepositoryModuleDiffblueTest {
  /**
   * Test {@link RepositoryModule#getArtifactRepository(ArtifactRepositoryProviderConfiguration)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link RepositoryModule#getArtifactRepository(ArtifactRepositoryProviderConfiguration)}
   */
  @Test
  @DisplayName("Test getArtifactRepository(ArtifactRepositoryProviderConfiguration); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ArtifactRepository RepositoryModule.getArtifactRepository(ArtifactRepositoryProviderConfiguration)"})
  void testGetArtifactRepository_whenNull() throws ArtifactRepositoryException {
    // Arrange and Act
    ArtifactRepository actualArtifactRepository = (new RepositoryModule()).getArtifactRepository(null);

    // Assert
    assertTrue(actualArtifactRepository instanceof VoidArtifactRepositoryProvider);
    assertFalse(actualArtifactRepository.areValidCoordinates("Group", "Artifact"));
    assertTrue(actualArtifactRepository.findVersions("Group", "Artifact").isEmpty());
  }

  /**
   * Test {@link RepositoryModule#getArtifactRepository(ArtifactRepositoryProviderConfiguration)}.
   * <ul>
   *   <li>When {@link VoidArtifactRepositoryConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link RepositoryModule#getArtifactRepository(ArtifactRepositoryProviderConfiguration)}
   */
  @Test
  @DisplayName("Test getArtifactRepository(ArtifactRepositoryProviderConfiguration); when VoidArtifactRepositoryConfiguration (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ArtifactRepository RepositoryModule.getArtifactRepository(ArtifactRepositoryProviderConfiguration)"})
  void testGetArtifactRepository_whenVoidArtifactRepositoryConfiguration() throws ArtifactRepositoryException {
    // Arrange
    RepositoryModule repositoryModule = new RepositoryModule();

    // Act
    ArtifactRepository actualArtifactRepository = repositoryModule
        .getArtifactRepository(new VoidArtifactRepositoryConfiguration());

    // Assert
    assertTrue(actualArtifactRepository instanceof VoidArtifactRepositoryProvider);
    assertFalse(actualArtifactRepository.areValidCoordinates("Group", "Artifact"));
    assertTrue(actualArtifactRepository.findVersions("Group", "Artifact").isEmpty());
  }
}
