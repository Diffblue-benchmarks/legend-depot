package org.finos.legend.depot.domain.artifacts.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ArtifactTypeDiffblueTest {
  /**
   * Test {@link ArtifactType#getModuleName()}.
   *
   * <p>Method under test: {@link ArtifactType#getModuleName()}
   */
  @Test
  @DisplayName("Test getModuleName()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String ArtifactType.getModuleName()"})
  void testGetModuleName() {
    // Arrange, Act and Assert
    assertEquals("entities", ArtifactType.valueOf("ENTITIES").getModuleName());
  }
}
