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
    private VoidArtifactRepositoryProvider repository;

    @BeforeEach
    public void setUp()
    {
        ArtifactRepositoryProviderConfiguration config = ArtifactRepositoryProviderConfiguration.voidConfiguration();
        repository = new VoidArtifactRepositoryProvider(config);
    }

    @Test
    public void testAreValidCoordinates()
    {
        boolean result = repository.areValidCoordinates("org.example", "artifact");
        assertFalse(result);
    }

    @Test
    public void testGetPOM()
    {
        Model result = repository.getPOM("org.example", "artifact", "1.0.0");
        assertNotNull(result);
    }

    @Test
    public void testGetJarFile()
    {
        File result = repository.getJarFile("org.example", "artifact", "1.0.0");
        assertNull(result);
    }

    @Test
    public void testGetModulesFromPOM()
    {
        List<String> result = repository.getModulesFromPOM(ArtifactType.ENTITIES, "org.example", "artifact", "1.0.0");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindVersions()
    {
        List<VersionId> result = repository.findVersions("org.example", "artifact");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindVersion() throws ArtifactRepositoryException
    {
        Optional<String> result = repository.findVersion("org.example", "artifact", "1.0.0");
        assertNotNull(result);
        assertFalse(result.isPresent());
    }

    @Test
    public void testFindFiles()
    {
        List<File> result = repository.findFiles(ArtifactType.ENTITIES, "org.example", "artifact", "1.0.0");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindDependenciesFiles()
    {
        List<File> result = repository.findDependenciesFiles(ArtifactType.ENTITIES, "org.example", "artifact", "1.0.0");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindDependenciesByArtifactType()
    {
        Set<ArtifactDependency> result = repository.findDependenciesByArtifactType(ArtifactType.ENTITIES, "org.example", "artifact", "1.0.0");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindDependencies()
    {
        Set<ArtifactDependency> result = repository.findDependencies("org.example", "artifact", "1.0.0");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
