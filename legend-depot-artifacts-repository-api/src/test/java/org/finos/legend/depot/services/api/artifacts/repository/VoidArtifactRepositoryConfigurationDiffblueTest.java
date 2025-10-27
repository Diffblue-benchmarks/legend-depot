package org.finos.legend.depot.services.api.artifacts.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class VoidArtifactRepositoryConfigurationDiffblueTest {
  /**
   * Method under test:
   * {@link VoidArtifactRepositoryConfiguration#initialiseArtifactRepositoryProvider()}
   */
  @Test
  void testInitialiseArtifactRepositoryProvider() {
    // Arrange, Act and Assert
    assertNull((new VoidArtifactRepositoryConfiguration()).initialiseArtifactRepositoryProvider());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link VoidArtifactRepositoryConfiguration}
   */
  @Test
  void testNewVoidArtifactRepositoryConfiguration() {
    // Arrange, Act and Assert
    assertEquals("void configuration", (new VoidArtifactRepositoryConfiguration()).getName());
  }
}
