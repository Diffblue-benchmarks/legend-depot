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

import org.finos.legend.depot.domain.artifacts.repository.ArtifactType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestVoidArtifactRepositoryProvider
{
    private final VoidArtifactRepositoryProvider provider = new VoidArtifactRepositoryProvider(ArtifactRepositoryProviderConfiguration.voidConfiguration());

    @Test
    public void testAreValidCoordinatesReturnsFalse()
    {
        Assertions.assertFalse(provider.areValidCoordinates("org.finos", "artifact"));
    }

    @Test
    public void testGetPOMReturnsEmptyModel()
    {
        Assertions.assertNotNull(provider.getPOM("org.finos", "artifact", "1.0.0"));
    }

    @Test
    public void testGetJarFileReturnsNull()
    {
        Assertions.assertNull(provider.getJarFile("org.finos", "artifact", "1.0.0"));
    }

    @Test
    public void testGetModulesFromPOMReturnsEmptyList()
    {
        Assertions.assertTrue(provider.getModulesFromPOM(ArtifactType.ENTITIES, "org.finos", "artifact", "1.0.0").isEmpty());
    }

    @Test
    public void testFindVersionsReturnsEmptyList()
    {
        Assertions.assertTrue(provider.findVersions("org.finos", "artifact").isEmpty());
    }

    @Test
    public void testFindVersionReturnsEmpty() throws ArtifactRepositoryException
    {
        Assertions.assertFalse(provider.findVersion("org.finos", "artifact", "1.0.0").isPresent());
    }

    @Test
    public void testFindFilesReturnsEmptyList()
    {
        Assertions.assertTrue(provider.findFiles(ArtifactType.ENTITIES, "org.finos", "artifact", "1.0.0").isEmpty());
    }

    @Test
    public void testFindDependenciesFilesReturnsEmptyList()
    {
        Assertions.assertTrue(provider.findDependenciesFiles(ArtifactType.ENTITIES, "org.finos", "artifact", "1.0.0").isEmpty());
    }

    @Test
    public void testFindDependenciesByArtifactTypeReturnsEmptySet()
    {
        Assertions.assertTrue(provider.findDependenciesByArtifactType(ArtifactType.ENTITIES, "org.finos", "artifact", "1.0.0").isEmpty());
    }

    @Test
    public void testFindDependenciesReturnsEmptySet()
    {
        Assertions.assertTrue(provider.findDependencies("org.finos", "artifact", "1.0.0").isEmpty());
    }
}
