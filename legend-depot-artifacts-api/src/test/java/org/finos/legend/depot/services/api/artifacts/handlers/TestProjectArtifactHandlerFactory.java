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

package org.finos.legend.depot.services.api.artifacts.handlers;

import org.finos.legend.depot.domain.artifacts.repository.ArtifactType;
import org.finos.legend.depot.domain.notifications.MetadataNotificationResponse;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestProjectArtifactHandlerFactory
{
    @Test
    void canRegisterAndRetrieveArtifactHandler()
    {
        ProjectArtifactsHandler handler = new ProjectArtifactsHandler()
        {
            @Override
            public MetadataNotificationResponse refreshProjectVersionArtifacts(String groupId, String artifactId, String versionId, List<File> files)
            {
                return null;
            }

            @Override
            public void delete(String groupId, String artifactId, String versionId)
            {
            }
        };

        ProjectArtifactHandlerFactory.registerArtifactHandler(ArtifactType.ENTITIES, handler);

        ProjectArtifactsHandler retrieved = ProjectArtifactHandlerFactory.getArtifactHandler(ArtifactType.ENTITIES);
        assertNotNull(retrieved);
        assertEquals(handler, retrieved);
    }

    @Test
    void canGetSupportedTypes()
    {
        ProjectArtifactsHandler handler = new ProjectArtifactsHandler()
        {
            @Override
            public MetadataNotificationResponse refreshProjectVersionArtifacts(String groupId, String artifactId, String versionId, List<File> files)
            {
                return null;
            }

            @Override
            public void delete(String groupId, String artifactId, String versionId)
            {
            }
        };

        ProjectArtifactHandlerFactory.registerArtifactHandler(ArtifactType.FILE_GENERATIONS, handler);

        Set<ArtifactType> supportedTypes = ProjectArtifactHandlerFactory.getSupportedTypes();
        assertNotNull(supportedTypes);
        assertTrue(supportedTypes.contains(ArtifactType.FILE_GENERATIONS));
    }

    @Test
    void canGetNullForUnregisteredType()
    {
        ProjectArtifactsHandler retrieved = ProjectArtifactHandlerFactory.getArtifactHandler(ArtifactType.VERSIONED_ENTITIES);
        assertNull(retrieved);
    }
}
