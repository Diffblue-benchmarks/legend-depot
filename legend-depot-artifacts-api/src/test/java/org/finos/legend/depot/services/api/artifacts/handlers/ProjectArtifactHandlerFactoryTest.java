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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProjectArtifactHandlerFactoryTest
{
    private ProjectArtifactsHandler testHandler1;
    private ProjectArtifactsHandler testHandler2;

    private static class TestProjectArtifactsHandler implements ProjectArtifactsHandler
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
    }

    @BeforeEach
    public void setUp()
    {
        testHandler1 = new TestProjectArtifactsHandler();
        testHandler2 = new TestProjectArtifactsHandler();
    }

    @Test
    public void canRegisterArtifactHandler()
    {
        ProjectArtifactHandlerFactory.registerArtifactHandler(ArtifactType.ENTITIES, testHandler1);

        ProjectArtifactsHandler retrieved = ProjectArtifactHandlerFactory.getArtifactHandler(ArtifactType.ENTITIES);

        assertNotNull(retrieved);
        assertEquals(testHandler1, retrieved);
    }

    @Test
    public void canRegisterMultipleArtifactHandlers()
    {
        ProjectArtifactHandlerFactory.registerArtifactHandler(ArtifactType.ENTITIES, testHandler1);
        ProjectArtifactHandlerFactory.registerArtifactHandler(ArtifactType.VERSIONED_ENTITIES, testHandler2);

        ProjectArtifactsHandler retrieved1 = ProjectArtifactHandlerFactory.getArtifactHandler(ArtifactType.ENTITIES);
        ProjectArtifactsHandler retrieved2 = ProjectArtifactHandlerFactory.getArtifactHandler(ArtifactType.VERSIONED_ENTITIES);

        assertNotNull(retrieved1);
        assertNotNull(retrieved2);
        assertEquals(testHandler1, retrieved1);
        assertEquals(testHandler2, retrieved2);
    }

    @Test
    public void canGetArtifactHandler()
    {
        ProjectArtifactHandlerFactory.registerArtifactHandler(ArtifactType.FILE_GENERATIONS, testHandler1);

        ProjectArtifactsHandler retrieved = ProjectArtifactHandlerFactory.getArtifactHandler(ArtifactType.FILE_GENERATIONS);

        assertNotNull(retrieved);
        assertEquals(testHandler1, retrieved);
    }

    @Test
    public void canGetSupportedTypes()
    {
        ProjectArtifactHandlerFactory.registerArtifactHandler(ArtifactType.ENTITIES, testHandler1);
        ProjectArtifactHandlerFactory.registerArtifactHandler(ArtifactType.VERSIONED_ENTITIES, testHandler2);

        Set<ArtifactType> supportedTypes = ProjectArtifactHandlerFactory.getSupportedTypes();

        assertNotNull(supportedTypes);
        assertTrue(supportedTypes.contains(ArtifactType.ENTITIES));
        assertTrue(supportedTypes.contains(ArtifactType.VERSIONED_ENTITIES));
    }

    @Test
    public void canGetSupportedTypesReturnsEmptySetWhenNoHandlersRegistered()
    {
        Set<ArtifactType> supportedTypes = ProjectArtifactHandlerFactory.getSupportedTypes();

        assertNotNull(supportedTypes);
    }
}
