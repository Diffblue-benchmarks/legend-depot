package org.finos.legend.depot.services.guice;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepository;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryException;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryProviderConfiguration;
import org.finos.legend.depot.services.api.artifacts.repository.VoidArtifactRepositoryConfiguration;
import org.finos.legend.depot.services.api.artifacts.repository.VoidArtifactRepositoryProvider;
import org.junit.jupiter.api.Test;

class RepositoryModuleDiffblueTest {
  /**
   * Method under test:
   * {@link RepositoryModule#getArtifactRepository(ArtifactRepositoryProviderConfiguration)}
   */
  @Test
  void testGetArtifactRepository() throws ArtifactRepositoryException {
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

  /**
   * Method under test:
   * {@link RepositoryModule#getArtifactRepository(ArtifactRepositoryProviderConfiguration)}
   */
  @Test
  void testGetArtifactRepository2() throws ArtifactRepositoryException {
    // Arrange and Act
    ArtifactRepository actualArtifactRepository = (new RepositoryModule()).getArtifactRepository(null);

    // Assert
    assertTrue(actualArtifactRepository instanceof VoidArtifactRepositoryProvider);
    assertFalse(actualArtifactRepository.areValidCoordinates("Group", "Artifact"));
    assertTrue(actualArtifactRepository.findVersions("Group", "Artifact").isEmpty());
  }
}
