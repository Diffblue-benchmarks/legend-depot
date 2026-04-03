// Copyright 2021 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.finos.legend.depot.store.resources.artifacts.repository;

import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepository;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepositoryException;
import org.finos.legend.sdlc.domain.model.version.VersionId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestRepositoryResource
{
    @Mock
    private ArtifactRepository artifactRepository;

    private RepositoryResource repositoryResource;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
        repositoryResource = new RepositoryResource(artifactRepository);
    }

    @Test
    public void testGetRepositoryVersionsReturnsVersionStrings() throws ArtifactRepositoryException
    {
        VersionId v1 = mock(VersionId.class);
        VersionId v2 = mock(VersionId.class);
        when(v1.toVersionIdString()).thenReturn("1.0.0");
        when(v2.toVersionIdString()).thenReturn("2.0.0");
        when(artifactRepository.findVersions("test.group", "test-artifact")).thenReturn(Arrays.asList(v1, v2));

        List<String> result = repositoryResource.getRepositoryVersions("test.group", "test-artifact");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.contains("1.0.0"));
        Assertions.assertTrue(result.contains("2.0.0"));
    }

    @Test
    public void testGetRepositoryVersionsReturnsEmptyList() throws ArtifactRepositoryException
    {
        when(artifactRepository.findVersions("test.group", "test-artifact")).thenReturn(Collections.emptyList());

        List<String> result = repositoryResource.getRepositoryVersions("test.group", "test-artifact");

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetRepositoryVersionsThrowsRuntimeExceptionOnError() throws ArtifactRepositoryException
    {
        when(artifactRepository.findVersions("test.group", "test-artifact")).thenThrow(new ArtifactRepositoryException("connection error"));

        Assertions.assertThrows(RuntimeException.class, () -> repositoryResource.getRepositoryVersions("test.group", "test-artifact"));
    }

    @Test
    public void testGetRepositoryVersionReturnsVersion() throws ArtifactRepositoryException
    {
        when(artifactRepository.findVersion("test.group", "test-artifact", "1.0.0")).thenReturn(Optional.of("1.0.0"));

        Optional<String> result = repositoryResource.getRepositoryVersion("test.group", "test-artifact", "1.0.0");

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("1.0.0", result.get());
    }

    @Test
    public void testGetRepositoryVersionReturnsEmptyWhenNotFound() throws ArtifactRepositoryException
    {
        when(artifactRepository.findVersion("test.group", "test-artifact", "9.9.9")).thenReturn(Optional.empty());

        Optional<String> result = repositoryResource.getRepositoryVersion("test.group", "test-artifact", "9.9.9");

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testGetRepositoryVersionReturnsErrorMessageOnArtifactRepositoryException() throws ArtifactRepositoryException
    {
        when(artifactRepository.findVersion("test.group", "test-artifact", "1.0.0")).thenThrow(new ArtifactRepositoryException("artifact not found"));

        Optional<String> result = repositoryResource.getRepositoryVersion("test.group", "test-artifact", "1.0.0");

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertTrue(result.get().contains("artifact not found"));
    }
}
