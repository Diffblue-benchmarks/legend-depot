package org.finos.legend.depot.services.api.artifacts.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class VoidArtifactRepositoryConfigurationDiffblueTest {
  /**
   * Test new {@link VoidArtifactRepositoryConfiguration} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link
   * VoidArtifactRepositoryConfiguration}
   */
  @Test
  @DisplayName("Test new VoidArtifactRepositoryConfiguration (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VoidArtifactRepositoryConfiguration.<init>()"})
  void testNewVoidArtifactRepositoryConfiguration() {
    // Arrange, Act and Assert
    assertEquals("void configuration", new VoidArtifactRepositoryConfiguration().getName());
  }

  /**
   * Test {@link VoidArtifactRepositoryConfiguration#initialiseArtifactRepositoryProvider()}.
   *
   * <p>Method under test: {@link
   * VoidArtifactRepositoryConfiguration#initialiseArtifactRepositoryProvider()}
   */
  @Test
  @DisplayName("Test initialiseArtifactRepositoryProvider()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepository VoidArtifactRepositoryConfiguration.initialiseArtifactRepositoryProvider()"
  })
  void testInitialiseArtifactRepositoryProvider() {
    // Arrange, Act and Assert
    assertNull(new VoidArtifactRepositoryConfiguration().initialiseArtifactRepositoryProvider());
  }
}
