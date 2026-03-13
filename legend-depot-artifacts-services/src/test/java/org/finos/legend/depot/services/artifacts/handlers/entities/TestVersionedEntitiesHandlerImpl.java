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

import org.finos.legend.depot.services.api.versionedEntities.ManageVersionedEntitiesService;
import org.finos.legend.depot.services.api.artifacts.handlers.entties.EntityArtifactsProvider;
import org.finos.legend.depot.services.api.artifacts.handlers.entties.VersionedEntityArtifactsProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

public class TestVersionedEntitiesHandlerImpl
{
    public static final String TEST_GROUP_ID = "examples.metadata";
    public static final String TEST_ARTIFACT_ID = "test";
    public static final String TEST_VERSION = "1.0.0";

    @Test
    public void canCreateVersionedEntitiesHandler()
    {
        ManageVersionedEntitiesService versionedEntitiesService = mock(ManageVersionedEntitiesService.class);
        VersionedEntityArtifactsProvider artifactProvider = mock(VersionedEntityArtifactsProvider.class, withSettings().extraInterfaces(EntityArtifactsProvider.class));

        VersionedEntitiesHandlerImpl handler = new VersionedEntitiesHandlerImpl(versionedEntitiesService, artifactProvider);

        Assertions.assertNotNull(handler);
    }

    @Test
    public void canDeleteVersionedEntities()
    {
        ManageVersionedEntitiesService versionedEntitiesService = mock(ManageVersionedEntitiesService.class);
        VersionedEntityArtifactsProvider artifactProvider = mock(VersionedEntityArtifactsProvider.class, withSettings().extraInterfaces(EntityArtifactsProvider.class));
        when(versionedEntitiesService.delete(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION)).thenReturn(5L);

        VersionedEntitiesHandlerImpl handler = new VersionedEntitiesHandlerImpl(versionedEntitiesService, artifactProvider);
        handler.delete(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION);

        verify(versionedEntitiesService).delete(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION);
    }
}
