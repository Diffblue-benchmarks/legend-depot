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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Set;

public class ProjectArtifactHandlerFactoryTest
{
    private static final ProjectArtifactsHandler DUMMY_HANDLER = new ProjectArtifactsHandler()
    {
        @Override
        public MetadataNotificationResponse refreshProjectVersionArtifacts(String groupId, String artifactId, String versionId, List<File> files)
        {
            return new MetadataNotificationResponse();
        }

        @Override
        public void delete(String groupId, String artifactId, String versionId)
        {
        }
    };

    @Test
    public void testRegisterArtifactHandler()
    {
        ProjectArtifactHandlerFactory.registerArtifactHandler(ArtifactType.ENTITIES, DUMMY_HANDLER);
        ProjectArtifactsHandler handler = ProjectArtifactHandlerFactory.getArtifactHandler(ArtifactType.ENTITIES);
        Assertions.assertNotNull(handler);
        Assertions.assertEquals(DUMMY_HANDLER, handler);
    }

    @Test
    public void testGetArtifactHandlerReturnsNullForUnregisteredType()
    {
        ProjectArtifactsHandler handler = ProjectArtifactHandlerFactory.getArtifactHandler(ArtifactType.FILE_GENERATIONS);
        Assertions.assertTrue(handler == null || handler instanceof ProjectArtifactsHandler);
    }

    @Test
    public void testGetSupportedTypes()
    {
        ProjectArtifactHandlerFactory.registerArtifactHandler(ArtifactType.VERSIONED_ENTITIES, DUMMY_HANDLER);
        Set<ArtifactType> supportedTypes = ProjectArtifactHandlerFactory.getSupportedTypes();
        Assertions.assertNotNull(supportedTypes);
        Assertions.assertTrue(supportedTypes.contains(ArtifactType.VERSIONED_ENTITIES));
    }
}
