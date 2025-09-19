package org.finos.legend.depot.store.resources.artifacts.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Optional;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepository;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryException;
import org.finos.legend.depot.services.api.artifacts.repository.VoidArtifactRepositoryConfiguration;
import org.finos.legend.depot.services.api.artifacts.repository.VoidArtifactRepositoryProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RepositoryResourceDiffblueTest {
  /**
   * Test {@link RepositoryResource#getRepositoryVersion(String, String, String)}.
   *
   * <ul>
   *   <li>Then return {@link Optional#get()} is {@code An error occurred}.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryResource#getRepositoryVersion(String, String, String)}
   */
  @Test
  @DisplayName(
      "Test getRepositoryVersion(String, String, String); then return get() is 'An error occurred'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional RepositoryResource.getRepositoryVersion(String, String, String)"})
  void testGetRepositoryVersion_thenReturnGetIsAnErrorOccurred()
      throws ArtifactRepositoryException {
    // Arrange
    ArtifactRepository artifactRepository = mock(ArtifactRepository.class);
    when(artifactRepository.findVersion(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ArtifactRepositoryException("An error occurred"));

    // Act
    Optional<String> actualRepositoryVersion =
        new RepositoryResource(artifactRepository).getRepositoryVersion("42", "42", "42");

    // Assert
    verify(artifactRepository).findVersion("42", "42", "42");
    assertEquals("An error occurred", actualRepositoryVersion.get());
    assertTrue(actualRepositoryVersion.isPresent());
  }

  /**
   * Test {@link RepositoryResource#getRepositoryVersion(String, String, String)}.
   *
   * <ul>
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link RepositoryResource#getRepositoryVersion(String, String, String)}
   */
  @Test
  @DisplayName("Test getRepositoryVersion(String, String, String); then return not Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional RepositoryResource.getRepositoryVersion(String, String, String)"})
  void testGetRepositoryVersion_thenReturnNotPresent() {
    // Arrange, Act and Assert
    assertFalse(
        new RepositoryResource(
                new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration()))
            .getRepositoryVersion("42", "42", "42")
            .isPresent());
  }
}
