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

package org.finos.legend.depot.services.artifacts.handlers.entities;

import org.finos.legend.depot.domain.artifacts.repository.ArtifactType;
import org.finos.legend.depot.services.api.artifacts.repository.ArtifactRepository;
import org.finos.legend.depot.services.artifacts.repository.maven.TestMavenArtifactsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

public class TestVersionedEntityProvider
{
    public static final String TEST_GROUP_ID = "examples.metadata";
    private final ArtifactRepository repository = new TestMavenArtifactsRepository();
    private final VersionedEntityProvider versionedEntityProvider = new VersionedEntityProvider();

    @Test
    public void canInstantiateVersionedEntityProvider()
    {
        VersionedEntityProvider provider = new VersionedEntityProvider();
        Assertions.assertNotNull(provider);
    }

    @Test
    public void canGetType()
    {
        ArtifactType type = versionedEntityProvider.getType();
        Assertions.assertNotNull(type);
        Assertions.assertEquals(ArtifactType.VERSIONED_ENTITIES, type);
    }

    @Test
    public void canMatchVersionedEntitiesArtifact()
    {
        List<File> files = repository.findFiles(ArtifactType.VERSIONED_ENTITIES, TEST_GROUP_ID, "test", "2.0.0");
        Assertions.assertNotNull(files);
        Assertions.assertFalse(files.isEmpty());

        File versionedEntitiesFile = files.get(0);
        boolean matches = versionedEntityProvider.matchesArtifactType(versionedEntitiesFile);
        Assertions.assertTrue(matches);
    }

    @Test
    public void cannotMatchNonVersionedEntitiesArtifact()
    {
        List<File> files = repository.findFiles(ArtifactType.ENTITIES, TEST_GROUP_ID, "test", "2.0.0");
        Assertions.assertNotNull(files);
        Assertions.assertFalse(files.isEmpty());

        File entitiesFile = files.get(0);
        boolean matches = versionedEntityProvider.matchesArtifactType(entitiesFile);
        Assertions.assertFalse(matches);
    }
}
