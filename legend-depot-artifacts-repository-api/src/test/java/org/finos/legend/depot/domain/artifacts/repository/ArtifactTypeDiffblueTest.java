package org.finos.legend.depot.domain.artifacts.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ArtifactTypeDiffblueTest {
  /**
   * Method under test: {@link ArtifactType#getModuleName()}
   */
  @Test
  void testGetModuleName() {
    // Arrange, Act and Assert
    assertEquals("entities", ArtifactType.valueOf("ENTITIES").getModuleName());
  }
}
