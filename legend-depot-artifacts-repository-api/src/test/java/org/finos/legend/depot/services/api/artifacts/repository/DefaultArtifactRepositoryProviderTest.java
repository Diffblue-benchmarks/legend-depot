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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DefaultArtifactRepositoryProviderTest
{
    private DefaultArtifactRepositoryProvider provider;

    @BeforeEach
    public void setUp()
    {
        provider = new DefaultArtifactRepositoryProvider() {};
    }

    @Test
    public void testAreValidCoordinates()
    {
        UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            provider.areValidCoordinates("group", "artifact");
        });
        Assertions.assertEquals("method not supported", exception.getMessage());
    }

    @Test
    public void testGetPOM()
    {
        UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            provider.getPOM("group", "artifact", "version");
        });
        Assertions.assertEquals("method not supported", exception.getMessage());
    }

    @Test
    public void testGetJarFile()
    {
        UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            provider.getJarFile("group", "artifact", "version");
        });
        Assertions.assertEquals("method not supported", exception.getMessage());
    }

    @Test
    public void testGetModulesFromPOM()
    {
        UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            provider.getModulesFromPOM(ArtifactType.ENTITIES, "group", "artifact", "version");
        });
        Assertions.assertEquals("method not supported", exception.getMessage());
    }

    @Test
    public void testFindVersions()
    {
        UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            provider.findVersions("group", "artifact");
        });
        Assertions.assertEquals("method not supported", exception.getMessage());
    }

    @Test
    public void testFindVersion()
    {
        UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            provider.findVersion("group", "artifact", "versionId");
        });
        Assertions.assertEquals("method not supported", exception.getMessage());
    }

    @Test
    public void testFindFiles()
    {
        UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            provider.findFiles(ArtifactType.ENTITIES, "group", "artifact", "version");
        });
        Assertions.assertEquals("method not supported", exception.getMessage());
    }

    @Test
    public void testFindDependenciesFiles()
    {
        UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            provider.findDependenciesFiles(ArtifactType.ENTITIES, "group", "artifact", "version");
        });
        Assertions.assertEquals("method not supported", exception.getMessage());
    }

    @Test
    public void testFindDependenciesByArtifactType()
    {
        UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            provider.findDependenciesByArtifactType(ArtifactType.ENTITIES, "group", "artifact", "version");
        });
        Assertions.assertEquals("method not supported", exception.getMessage());
    }

    @Test
    public void testFindDependencies()
    {
        UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            provider.findDependencies("group", "artifact", "version");
        });
        Assertions.assertEquals("method not supported", exception.getMessage());
    }
}
