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

package org.finos.legend.depot.services.api.artifacts.repository;

import org.apache.maven.model.Model;
import org.finos.legend.depot.domain.artifacts.repository.ArtifactDependency;
import org.finos.legend.depot.domain.artifacts.repository.ArtifactType;
import org.finos.legend.sdlc.domain.model.version.VersionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArtifactRepositoryTest
{
    private ArtifactRepository repository;

    @BeforeEach
    public void setUp()
    {
        repository = mock(ArtifactRepository.class);
    }

    @Test
    public void testAreValidCoordinates()
    {
        when(repository.areValidCoordinates("org.example", "my-artifact")).thenReturn(true);

        boolean result = repository.areValidCoordinates("org.example", "my-artifact");

        assertTrue(result);
    }

    @Test
    public void testGetPOM()
    {
        Model model = new Model();
        when(repository.getPOM("org.example", "my-artifact", "1.0.0")).thenReturn(model);

        Model result = repository.getPOM("org.example", "my-artifact", "1.0.0");

        assertNotNull(result);
    }

    @Test
    public void testGetJarFile()
    {
        File file = new File("my-artifact-1.0.0.jar");
        when(repository.getJarFile("org.example", "my-artifact", "1.0.0")).thenReturn(file);

        File result = repository.getJarFile("org.example", "my-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals("my-artifact-1.0.0.jar", result.getName());
    }

    @Test
    public void testGetModulesFromPOM()
    {
        List<String> modules = Arrays.asList("module-a", "module-b");
        when(repository.getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "my-artifact", "1.0.0")).thenReturn(modules);

        List<String> result = repository.getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "my-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testFindVersions() throws ArtifactRepositoryException
    {
        List<VersionId> versions = Collections.emptyList();
        when(repository.findVersions("org.example", "my-artifact")).thenReturn(versions);

        List<VersionId> result = repository.findVersions("org.example", "my-artifact");

        assertNotNull(result);
    }

    @Test
    public void testFindVersion() throws ArtifactRepositoryException
    {
        when(repository.findVersion("org.example", "my-artifact", "1.0.0")).thenReturn(Optional.of("1.0.0"));

        Optional<String> result = repository.findVersion("org.example", "my-artifact", "1.0.0");

        assertTrue(result.isPresent());
        assertEquals("1.0.0", result.get());
    }

    @Test
    public void testFindFiles()
    {
        List<File> files = Collections.singletonList(new File("entities.jar"));
        when(repository.findFiles(ArtifactType.ENTITIES, "org.example", "my-artifact", "1.0.0")).thenReturn(files);

        List<File> result = repository.findFiles(ArtifactType.ENTITIES, "org.example", "my-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testFindDependenciesFiles()
    {
        List<File> files = Collections.singletonList(new File("dep.jar"));
        when(repository.findDependenciesFiles(ArtifactType.ENTITIES, "org.example", "my-artifact", "1.0.0")).thenReturn(files);

        List<File> result = repository.findDependenciesFiles(ArtifactType.ENTITIES, "org.example", "my-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testFindDependenciesByArtifactType()
    {
        Set<ArtifactDependency> deps = new HashSet<>();
        deps.add(new ArtifactDependency("org.dep", "dep-artifact", "2.0.0"));
        when(repository.findDependenciesByArtifactType(ArtifactType.ENTITIES, "org.example", "my-artifact", "1.0.0")).thenReturn(deps);

        Set<ArtifactDependency> result = repository.findDependenciesByArtifactType(ArtifactType.ENTITIES, "org.example", "my-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testFindDependencies()
    {
        Set<ArtifactDependency> deps = new HashSet<>();
        deps.add(new ArtifactDependency("org.dep", "dep-artifact", "2.0.0"));
        when(repository.findDependencies("org.example", "my-artifact", "1.0.0")).thenReturn(deps);

        Set<ArtifactDependency> result = repository.findDependencies("org.example", "my-artifact", "1.0.0");

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
