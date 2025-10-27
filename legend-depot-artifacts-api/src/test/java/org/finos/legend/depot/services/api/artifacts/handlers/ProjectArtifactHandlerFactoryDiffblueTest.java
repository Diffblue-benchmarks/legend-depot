package org.finos.legend.depot.services.api.artifacts.handlers;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Set;
import org.finos.legend.depot.domain.artifacts.repository.ArtifactType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectArtifactHandlerFactoryDiffblueTest {
  @InjectMocks
  private ProjectArtifactHandlerFactory projectArtifactHandlerFactory;

  /**
   * Method under test:
   * {@link ProjectArtifactHandlerFactory#getArtifactHandler(ArtifactType)}
   */
  @Test
  void testGetArtifactHandler() {
    // Arrange, Act and Assert
    assertNull(ProjectArtifactHandlerFactory.getArtifactHandler(ArtifactType.ENTITIES));
  }

  /**
   * Method under test: {@link ProjectArtifactHandlerFactory#getSupportedTypes()}
   */
  @Test
  void testGetSupportedTypes() {
    // Arrange and Act
    Set<ArtifactType> actualSupportedTypes = ProjectArtifactHandlerFactory.getSupportedTypes();

    // Assert
    assertTrue(actualSupportedTypes.isEmpty());
  }
}
