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
//

package org.finos.legend.depot.services.api.artifacts.repository;

import org.apache.maven.model.Model;
import org.finos.legend.depot.domain.artifacts.repository.ArtifactType;
import org.finos.legend.sdlc.domain.model.version.VersionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VoidArtifactRepositoryProviderTest
{
    private VoidArtifactRepositoryProvider provider;

    @BeforeEach
    public void setUp()
    {
        provider = new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration());
    }

    @Test
    public void testConstructor()
    {
        VoidArtifactRepositoryProvider p = new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration());
        assertNotNull(p);
    }

    @Test
    public void testAreValidCoordinatesReturnsFalse()
    {
        boolean result = provider.areValidCoordinates("org.example", "my-artifact");
        assertFalse(result);
    }

    @Test
    public void testGetPOMReturnsModel()
    {
        Model model = provider.getPOM("org.example", "my-artifact", "1.0.0");
        assertNotNull(model);
    }

    @Test
    public void testGetJarFileReturnsNull()
    {
        File file = provider.getJarFile("org.example", "my-artifact", "1.0.0");
        assertNull(file);
    }

    @Test
    public void testGetModulesFromPOMReturnsEmptyList()
    {
        List<String> modules = provider.getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "my-artifact", "1.0.0");
        assertNotNull(modules);
        assertTrue(modules.isEmpty());
    }

    @Test
    public void testFindVersionsReturnsEmptyList()
    {
        List<VersionId> versions = provider.findVersions("org.example", "my-artifact");
        assertNotNull(versions);
        assertTrue(versions.isEmpty());
    }

    @Test
    public void testFindVersionReturnsEmpty() throws ArtifactRepositoryException
    {
        Optional<String> version = provider.findVersion("org.example", "my-artifact", "1.0.0");
        assertNotNull(version);
        assertFalse(version.isPresent());
    }

    @Test
    public void testFindFilesReturnsEmptyList()
    {
        List<File> files = provider.findFiles(ArtifactType.ENTITIES, "org.example", "my-artifact", "1.0.0");
        assertNotNull(files);
        assertTrue(files.isEmpty());
    }

    @Test
    public void testFindDependenciesFilesReturnsEmptyList()
    {
        List<File> files = provider.findDependenciesFiles(ArtifactType.ENTITIES, "org.example", "my-artifact", "1.0.0");
        assertNotNull(files);
        assertTrue(files.isEmpty());
    }

    @Test
    public void testFindDependenciesByArtifactTypeReturnsEmptySet()
    {
        Set<?> deps = provider.findDependenciesByArtifactType(ArtifactType.ENTITIES, "org.example", "my-artifact", "1.0.0");
        assertNotNull(deps);
        assertTrue(deps.isEmpty());
    }

    @Test
    public void testFindDependenciesReturnsEmptySet()
    {
        Set<?> deps = provider.findDependencies("org.example", "my-artifact", "1.0.0");
        assertNotNull(deps);
        assertTrue(deps.isEmpty());
    }
}
