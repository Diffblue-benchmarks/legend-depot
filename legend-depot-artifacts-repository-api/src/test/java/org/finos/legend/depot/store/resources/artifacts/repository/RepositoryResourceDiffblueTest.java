package org.finos.legend.depot.store.resources.artifacts.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepository;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryException;
import org.finos.legend.sdlc.domain.model.version.VersionId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RepositoryResourceDiffblueTest {
  @Mock
  private ArtifactRepository artifactRepository;

  @InjectMocks
  private RepositoryResource repositoryResource;

  /**
   * Method under test:
   * {@link RepositoryResource#getRepositoryVersions(String, String)}
   */
  @Test
  void testGetRepositoryVersions() throws ArtifactRepositoryException {
    // Arrange
    when(artifactRepository.findVersions(Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<String> actualRepositoryVersions = repositoryResource.getRepositoryVersions("42", "42");

    // Assert
    verify(artifactRepository).findVersions(eq("42"), eq("42"));
    assertTrue(actualRepositoryVersions.isEmpty());
  }

  /**
   * Method under test:
   * {@link RepositoryResource#getRepositoryVersions(String, String)}
   */
  @Test
  void testGetRepositoryVersions2() throws ArtifactRepositoryException {
    // Arrange
    VersionId versionId = mock(VersionId.class);
    when(versionId.toVersionIdString()).thenReturn("1.0.2");

    ArrayList<VersionId> versionIdList = new ArrayList<>();
    versionIdList.add(versionId);
    when(artifactRepository.findVersions(Mockito.<String>any(), Mockito.<String>any())).thenReturn(versionIdList);

    // Act
    List<String> actualRepositoryVersions = repositoryResource.getRepositoryVersions("42", "42");

    // Assert
    verify(artifactRepository).findVersions(eq("42"), eq("42"));
    verify(versionId).toVersionIdString();
    assertEquals(1, actualRepositoryVersions.size());
    assertEquals("1.0.2", actualRepositoryVersions.get(0));
  }

  /**
   * Method under test:
   * {@link RepositoryResource#getRepositoryVersions(String, String)}
   */
  @Test
  void testGetRepositoryVersions3() throws ArtifactRepositoryException {
    // Arrange
    VersionId versionId = mock(VersionId.class);
    when(versionId.toVersionIdString()).thenReturn("1.0.2");
    VersionId versionId2 = mock(VersionId.class);
    when(versionId2.toVersionIdString()).thenReturn("1.0.2");

    ArrayList<VersionId> versionIdList = new ArrayList<>();
    versionIdList.add(versionId2);
    versionIdList.add(versionId);
    when(artifactRepository.findVersions(Mockito.<String>any(), Mockito.<String>any())).thenReturn(versionIdList);

    // Act
    List<String> actualRepositoryVersions = repositoryResource.getRepositoryVersions("42", "42");

    // Assert
    verify(artifactRepository).findVersions(eq("42"), eq("42"));
    verify(versionId2).toVersionIdString();
    verify(versionId).toVersionIdString();
    assertEquals(2, actualRepositoryVersions.size());
    assertEquals("1.0.2", actualRepositoryVersions.get(0));
    assertEquals("1.0.2", actualRepositoryVersions.get(1));
  }

  /**
   * Method under test:
   * {@link RepositoryResource#getRepositoryVersion(String, String, String)}
   */
  @Test
  void testGetRepositoryVersion() throws ArtifactRepositoryException {
    // Arrange
    Optional<String> ofResult = Optional.of("foo");
    when(artifactRepository.findVersion(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(ofResult);

    // Act
    Optional<String> actualRepositoryVersion = repositoryResource.getRepositoryVersion("42", "42", "42");

    // Assert
    verify(artifactRepository).findVersion(eq("42"), eq("42"), eq("42"));
    assertSame(ofResult, actualRepositoryVersion);
  }

  /**
   * Method under test:
   * {@link RepositoryResource#getRepositoryVersion(String, String, String)}
   */
  @Test
  void testGetRepositoryVersion2() throws ArtifactRepositoryException {
    // Arrange
    when(artifactRepository.findVersion(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ArtifactRepositoryException("An error occurred"));

    // Act
    Optional<String> actualRepositoryVersion = repositoryResource.getRepositoryVersion("42", "42", "42");

    // Assert
    verify(artifactRepository).findVersion(eq("42"), eq("42"), eq("42"));
    assertEquals("An error occurred", actualRepositoryVersion.get());
    assertTrue(actualRepositoryVersion.isPresent());
  }
}
