//  Copyright 2021 Goldman Sachs
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//

package org.finos.legend.depot.store.resources.artifacts.repository;

import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepository;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryException;
import org.finos.legend.sdlc.domain.model.version.VersionId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RepositoryResourceTest
{
    private ArtifactRepository artifactRepository;
    private RepositoryResource repositoryResource;

    @BeforeEach
    public void setup()
    {
        artifactRepository = mock(ArtifactRepository.class);
        repositoryResource = new RepositoryResource(artifactRepository);
    }

    @Test
    public void canCreateRepositoryResource()
    {
        ArtifactRepository repository = mock(ArtifactRepository.class);
        RepositoryResource resource = new RepositoryResource(repository);
        Assertions.assertNotNull(resource);
    }

    @Test
    public void canGetRepositoryVersions() throws ArtifactRepositoryException
    {
        VersionId version1 = mock(VersionId.class);
        VersionId version2 = mock(VersionId.class);
        when(version1.toVersionIdString()).thenReturn("1.0.0");
        when(version2.toVersionIdString()).thenReturn("2.0.0");

        List<VersionId> versions = Arrays.asList(version1, version2);
        when(artifactRepository.findVersions("com.example", "my-artifact")).thenReturn(versions);

        List<String> result = repositoryResource.getRepositoryVersions("com.example", "my-artifact");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("1.0.0", result.get(0));
        Assertions.assertEquals("2.0.0", result.get(1));
    }

    @Test
    public void canGetRepositoryVersionsWithEmptyList() throws ArtifactRepositoryException
    {
        when(artifactRepository.findVersions("com.example", "my-artifact")).thenReturn(Arrays.asList());

        List<String> result = repositoryResource.getRepositoryVersions("com.example", "my-artifact");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void canHandleExceptionInGetRepositoryVersions() throws ArtifactRepositoryException
    {
        when(artifactRepository.findVersions("com.example", "my-artifact")).thenThrow(new RuntimeException("Repository error"));

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () ->
        {
            repositoryResource.getRepositoryVersions("com.example", "my-artifact");
        });

        Assertions.assertTrue(exception.getMessage().contains("Repository error"));
    }

    @Test
    public void canGetRepositoryVersion() throws ArtifactRepositoryException
    {
        when(artifactRepository.findVersion("com.example", "my-artifact", "1.0.0")).thenReturn(Optional.of("1.0.0"));

        Optional<String> result = repositoryResource.getRepositoryVersion("com.example", "my-artifact", "1.0.0");

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("1.0.0", result.get());
    }

    @Test
    public void canGetRepositoryVersionWithEmptyResult() throws ArtifactRepositoryException
    {
        when(artifactRepository.findVersion("com.example", "my-artifact", "1.0.0")).thenReturn(Optional.empty());

        Optional<String> result = repositoryResource.getRepositoryVersion("com.example", "my-artifact", "1.0.0");

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void canHandleArtifactRepositoryExceptionInGetRepositoryVersion() throws ArtifactRepositoryException
    {
        String errorMessage = "Artifact not found";
        when(artifactRepository.findVersion("com.example", "my-artifact", "1.0.0"))
            .thenThrow(new ArtifactRepositoryException(errorMessage));

        Optional<String> result = repositoryResource.getRepositoryVersion("com.example", "my-artifact", "1.0.0");

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(errorMessage, result.get());
    }
}
