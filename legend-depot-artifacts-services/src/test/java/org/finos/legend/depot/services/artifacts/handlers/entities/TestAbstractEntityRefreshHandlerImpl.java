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
import org.finos.legend.depot.domain.notifications.MetadataNotificationResponse;
import org.finos.legend.depot.services.api.entities.ManageEntitiesService;
import org.finos.legend.depot.services.api.artifacts.handlers.entties.EntityArtifactsProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

public class TestAbstractEntityRefreshHandlerImpl
{
    private static final String TEST_GROUP_ID = "examples.metadata";
    private static final String TEST_ARTIFACT_ID = "test";
    private static final String TEST_VERSION_ID = "1.0.0";

    private ManageEntitiesService entitiesService;
    private EntityArtifactsProvider artifactsProvider;
    private EntitiesHandlerImpl handler;

    @BeforeEach
    public void setUp()
    {
        entitiesService = mock(ManageEntitiesService.class);
        artifactsProvider = mock(EntityArtifactsProvider.class);
        when(artifactsProvider.getType()).thenReturn(ArtifactType.ENTITIES);
        handler = new EntitiesHandlerImpl(entitiesService, artifactsProvider);
    }

    @Test
    public void testRefreshVersionArtifactsWithEmptyEntityList()
    {
        List<File> files = Collections.singletonList(new File("test.jar"));
        when(artifactsProvider.extractArtifacts(any())).thenReturn(new ArrayList<>());

        MetadataNotificationResponse response = handler.refreshProjectVersionArtifacts(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID, files);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getErrors().isEmpty());
        Assertions.assertTrue(response.getMessages().stream().anyMatch(m -> m.contains("found 0")));
    }

    @Test
    public void testRefreshVersionArtifactsWithNullEntityList()
    {
        List<File> files = Collections.singletonList(new File("test.jar"));
        when(artifactsProvider.extractArtifacts(any())).thenReturn(null);

        MetadataNotificationResponse response = handler.refreshProjectVersionArtifacts(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID, files);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getErrors().isEmpty());
        Assertions.assertTrue(response.getMessages().stream().anyMatch(m -> m.contains("found 0")));
    }

    @Test
    public void testRefreshVersionArtifactsWithException()
    {
        List<File> files = Collections.singletonList(new File("test.jar"));
        when(artifactsProvider.extractArtifacts(any())).thenThrow(new RuntimeException("test error"));

        MetadataNotificationResponse response = handler.refreshProjectVersionArtifacts(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID, files);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.getErrors().isEmpty());
        Assertions.assertTrue(response.getErrors().stream().anyMatch(e -> e.contains("Unexpected exception")));
        Assertions.assertTrue(response.getErrors().stream().anyMatch(e -> e.contains("test error")));
    }
}
