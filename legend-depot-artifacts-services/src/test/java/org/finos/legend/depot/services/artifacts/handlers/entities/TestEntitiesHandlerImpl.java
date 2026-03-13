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
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class TestEntitiesHandlerImpl
{
    private static final String TEST_GROUP_ID = "test.group";
    private static final String TEST_ARTIFACT_ID = "test-artifact";
    private static final String TEST_VERSION_ID = "1.0.0";

    private ManageEntitiesService entitiesService;
    private EntityArtifactsProvider artifactProvider;
    private EntitiesHandlerImpl handler;

    @BeforeEach
    public void setUp()
    {
        entitiesService = mock(ManageEntitiesService.class);
        artifactProvider = mock(EntityArtifactsProvider.class);
        handler = new EntitiesHandlerImpl(entitiesService, artifactProvider);
    }

    @Test
    public void canConstructHandlerWithServices()
    {
        EntitiesHandlerImpl newHandler = new EntitiesHandlerImpl(entitiesService, artifactProvider);

        Assertions.assertNotNull(newHandler);
    }

    @Test
    public void canRefreshProjectVersionArtifactsWithEmptyFiles()
    {
        List<File> files = Collections.emptyList();
        when(artifactProvider.extractArtifacts(files)).thenReturn(Collections.emptyList());

        MetadataNotificationResponse response = handler.refreshProjectVersionArtifacts(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID, files);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.hasErrors());
    }

    @Test
    public void canRefreshProjectVersionArtifactsWithFiles()
    {
        List<File> files = Arrays.asList(new File("test1.jar"), new File("test2.jar"));
        Entity mockEntity = mock(Entity.class);
        List<Entity> entities = Arrays.asList(mockEntity);

        when(artifactProvider.extractArtifacts(files)).thenReturn(entities);
        when(artifactProvider.getType()).thenReturn(ArtifactType.ENTITIES);

        MetadataNotificationResponse response = handler.refreshProjectVersionArtifacts(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID, files);

        Assertions.assertNotNull(response);
        verify(entitiesService, times(1)).createOrUpdate(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID, entities);
    }

    @Test
    public void canRefreshProjectVersionArtifactsWithSnapshotVersion()
    {
        String snapshotVersion = "master-SNAPSHOT";
        List<File> files = Arrays.asList(new File("test.jar"));
        Entity mockEntity = mock(Entity.class);
        List<Entity> entities = Arrays.asList(mockEntity);

        when(artifactProvider.extractArtifacts(files)).thenReturn(entities);
        when(artifactProvider.getType()).thenReturn(ArtifactType.ENTITIES);
        when(entitiesService.delete(TEST_GROUP_ID, TEST_ARTIFACT_ID, snapshotVersion)).thenReturn(5L);

        MetadataNotificationResponse response = handler.refreshProjectVersionArtifacts(TEST_GROUP_ID, TEST_ARTIFACT_ID, snapshotVersion, files);

        Assertions.assertNotNull(response);
        verify(entitiesService, times(1)).delete(TEST_GROUP_ID, TEST_ARTIFACT_ID, snapshotVersion);
        verify(entitiesService, times(1)).createOrUpdate(TEST_GROUP_ID, TEST_ARTIFACT_ID, snapshotVersion, entities);
    }

    @Test
    public void canDeleteVersion()
    {
        when(entitiesService.delete(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID)).thenReturn(10L);

        handler.delete(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);

        verify(entitiesService, times(1)).delete(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);
    }

    @Test
    public void canDeleteVersionWithNoEntities()
    {
        when(entitiesService.delete(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID)).thenReturn(0L);

        handler.delete(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);

        verify(entitiesService, times(1)).delete(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID);
    }

    @Test
    public void canHandleRefreshWithNullEntities()
    {
        List<File> files = Arrays.asList(new File("test.jar"));
        when(artifactProvider.extractArtifacts(files)).thenReturn(null);

        MetadataNotificationResponse response = handler.refreshProjectVersionArtifacts(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID, files);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.hasErrors());
    }

    @Test
    public void canHandleRefreshWithException()
    {
        List<File> files = Arrays.asList(new File("test.jar"));
        when(artifactProvider.extractArtifacts(files)).thenThrow(new RuntimeException("Test exception"));

        MetadataNotificationResponse response = handler.refreshProjectVersionArtifacts(TEST_GROUP_ID, TEST_ARTIFACT_ID, TEST_VERSION_ID, files);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.hasErrors());
    }
}
