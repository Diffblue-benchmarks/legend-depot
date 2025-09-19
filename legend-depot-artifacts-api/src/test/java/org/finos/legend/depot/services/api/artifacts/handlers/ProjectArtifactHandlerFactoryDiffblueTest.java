package org.finos.legend.depot.services.api.artifacts.handlers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Set;
import org.finos.legend.depot.domain.artifacts.repository.ArtifactType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProjectArtifactHandlerFactoryDiffblueTest {
  /**
   * Test {@link ProjectArtifactHandlerFactory#getSupportedTypes()}.
   *
   * <p>Method under test: {@link ProjectArtifactHandlerFactory#getSupportedTypes()}
   */
  @Test
  @DisplayName("Test getSupportedTypes()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ProjectArtifactHandlerFactory.getSupportedTypes()"})
  void testGetSupportedTypes() {
    // Arrange and Act
    Set<ArtifactType> actualSupportedTypes = ProjectArtifactHandlerFactory.getSupportedTypes();

    // Assert
    assertTrue(actualSupportedTypes.isEmpty());
  }
}
