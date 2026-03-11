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

class TestVoidArtifactRepositoryProvider
{
    private final VoidArtifactRepositoryProvider provider = new VoidArtifactRepositoryProvider(new VoidArtifactRepositoryConfiguration());

    @Test
    void canCreateInstance()
    {
        assertNotNull(provider);
    }

    @Test
    void areValidCoordinatesReturnsFalse()
    {
        assertFalse(provider.areValidCoordinates("org.finos", "test-artifact"));
    }

    @Test
    void getPOMReturnsEmptyModel()
    {
        Model model = provider.getPOM("org.finos", "test-artifact", "1.0.0");
        assertNotNull(model);
    }

    @Test
    void getJarFileReturnsNull()
    {
        File file = provider.getJarFile("org.finos", "test-artifact", "1.0.0");
        assertNull(file);
    }

    @Test
    void getModulesFromPOMReturnsEmptyList()
    {
        List<String> modules = provider.getModulesFromPOM(ArtifactType.ENTITIES, "org.finos", "test-artifact", "1.0.0");
        assertNotNull(modules);
        assertTrue(modules.isEmpty());
    }

    @Test
    void findVersionsReturnsEmptyList()
    {
        List<VersionId> versions = provider.findVersions("org.finos", "test-artifact");
        assertNotNull(versions);
        assertTrue(versions.isEmpty());
    }

    @Test
    void findVersionReturnsEmpty() throws ArtifactRepositoryException
    {
        Optional<String> version = provider.findVersion("org.finos", "test-artifact", "1.0.0");
        assertNotNull(version);
        assertFalse(version.isPresent());
    }

    @Test
    void findFilesReturnsEmptyList()
    {
        List<File> files = provider.findFiles(ArtifactType.ENTITIES, "org.finos", "test-artifact", "1.0.0");
        assertNotNull(files);
        assertTrue(files.isEmpty());
    }

    @Test
    void findDependenciesFilesReturnsEmptyList()
    {
        List<File> files = provider.findDependenciesFiles(ArtifactType.ENTITIES, "org.finos", "test-artifact", "1.0.0");
        assertNotNull(files);
        assertTrue(files.isEmpty());
    }

    @Test
    void findDependenciesByArtifactTypeReturnsEmptySet()
    {
        Set<ArtifactDependency> deps = provider.findDependenciesByArtifactType(ArtifactType.ENTITIES, "org.finos", "test-artifact", "1.0.0");
        assertNotNull(deps);
        assertTrue(deps.isEmpty());
    }

    @Test
    void findDependenciesReturnsEmptySet()
    {
        Set<ArtifactDependency> deps = provider.findDependencies("org.finos", "test-artifact", "1.0.0");
        assertNotNull(deps);
        assertTrue(deps.isEmpty());
    }
}
