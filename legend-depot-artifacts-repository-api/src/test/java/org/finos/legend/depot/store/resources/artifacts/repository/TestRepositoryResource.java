package org.finos.legend.depot.store.resources.artifacts.repository;

import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepository;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryException;
import org.finos.legend.sdlc.domain.model.version.VersionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class TestRepositoryResource
{
    private ArtifactRepository artifactRepository;
    private RepositoryResource resource;

    @BeforeEach
    void setUp()
    {
        artifactRepository = Mockito.mock(ArtifactRepository.class);
        resource = new RepositoryResource(artifactRepository);
    }

    @Test
    void canCreateRepositoryResource()
    {
        assertNotNull(resource);
    }

    @Test
    void canGetRepositoryVersions() throws ArtifactRepositoryException
    {
        VersionId v1 = VersionId.parseVersionId("1.0.0");
        VersionId v2 = VersionId.parseVersionId("2.0.0");
        when(artifactRepository.findVersions("org.example", "my-artifact"))
                .thenReturn(Arrays.asList(v1, v2));

        List<String> versions = resource.getRepositoryVersions("org.example", "my-artifact");

        assertNotNull(versions);
        assertEquals(2, versions.size());
        assertEquals("1.0.0", versions.get(0));
        assertEquals("2.0.0", versions.get(1));
    }

    @Test
    void canGetRepositoryVersionsEmpty() throws ArtifactRepositoryException
    {
        when(artifactRepository.findVersions("org.example", "my-artifact"))
                .thenReturn(Collections.emptyList());

        List<String> versions = resource.getRepositoryVersions("org.example", "my-artifact");

        assertNotNull(versions);
        assertTrue(versions.isEmpty());
    }

    @Test
    void canGetRepositoryVersion() throws ArtifactRepositoryException
    {
        when(artifactRepository.findVersion("org.example", "my-artifact", "1.0.0"))
                .thenReturn(Optional.of("1.0.0"));

        Optional<String> version = resource.getRepositoryVersion("org.example", "my-artifact", "1.0.0");

        assertNotNull(version);
        assertTrue(version.isPresent());
        assertEquals("1.0.0", version.get());
    }

    @Test
    void canGetRepositoryVersionNotFound() throws ArtifactRepositoryException
    {
        when(artifactRepository.findVersion("org.example", "my-artifact", "999.0.0"))
                .thenThrow(new ArtifactRepositoryException("version not found"));

        Optional<String> version = resource.getRepositoryVersion("org.example", "my-artifact", "999.0.0");

        assertNotNull(version);
        assertTrue(version.isPresent());
        assertEquals("version not found", version.get());
    }
}
