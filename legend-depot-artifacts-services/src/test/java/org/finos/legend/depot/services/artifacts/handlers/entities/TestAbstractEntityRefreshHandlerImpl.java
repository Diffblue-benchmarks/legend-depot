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
import org.finos.legend.depot.services.api.artifacts.handlers.entties.EntityArtifactsProvider;
import org.finos.legend.depot.services.api.entities.ManageEntitiesService;
import org.finos.legend.depot.store.model.entities.StoredEntity;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestAbstractEntityRefreshHandlerImpl
{
    private ManageEntitiesService<StoredEntity> entitiesService;
    private EntityArtifactsProvider artifactProvider;
    private TestEntityRefreshHandler handler;

    @BeforeEach
    public void setUp()
    {
        entitiesService = mock(ManageEntitiesService.class);
        artifactProvider = mock(EntityArtifactsProvider.class);
        when(artifactProvider.getType()).thenReturn(ArtifactType.ENTITIES);
        handler = new TestEntityRefreshHandler(entitiesService, artifactProvider);
    }

    @Test
    public void canConstructHandler()
    {
        Assertions.assertNotNull(handler);
        Assertions.assertNotNull(handler.getEntitiesApi());
        Assertions.assertNotNull(handler.getLOGGER());
    }

    @Test
    public void canGetLogger()
    {
        Logger logger = handler.getLOGGER();

        Assertions.assertNotNull(logger);
    }

    @Test
    public void canGetEntitiesApi()
    {
        ManageEntitiesService api = handler.getEntitiesApi();

        Assertions.assertNotNull(api);
        Assertions.assertEquals(entitiesService, api);
    }

    @Test
    public void canDeleteByVersion()
    {
        when(entitiesService.delete("test.group", "test-artifact", "1.0.0")).thenReturn(5L);

        long result = handler.deleteByVersion("test.group", "test-artifact", "1.0.0");

        Assertions.assertEquals(5L, result);
        verify(entitiesService, times(1)).delete("test.group", "test-artifact", "1.0.0");
    }

    @Test
    public void canRefreshVersionArtifactsWithEntities()
    {
        String groupId = "test.group";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        List<File> files = new ArrayList<>();
        files.add(new File("test.jar"));

        List<Entity> entities = new ArrayList<>();
        Entity entity = mock(Entity.class);
        entities.add(entity);

        when(artifactProvider.extractArtifacts(files)).thenReturn(entities);

        MetadataNotificationResponse response = handler.refreshVersionArtifacts(groupId, artifactId, versionId, files);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.hasErrors());
        Assertions.assertEquals(1, response.getMessages().size());
        Assertions.assertTrue(response.getMessages().get(0).contains("found [1]"));
        Assertions.assertTrue(response.getMessages().get(0).contains("ENTITIES"));
        verify(artifactProvider, times(1)).extractArtifacts(files);
        verify(entitiesService, times(1)).createOrUpdate(groupId, artifactId, versionId, entities);
    }

    @Test
    public void canRefreshVersionArtifactsWithSnapshotVersion()
    {
        String groupId = "test.group";
        String artifactId = "test-artifact";
        String versionId = "1.0.0-SNAPSHOT";
        List<File> files = new ArrayList<>();
        files.add(new File("test.jar"));

        List<Entity> entities = new ArrayList<>();
        Entity entity = mock(Entity.class);
        entities.add(entity);

        when(artifactProvider.extractArtifacts(files)).thenReturn(entities);
        when(entitiesService.delete(groupId, artifactId, versionId)).thenReturn(3L);

        MetadataNotificationResponse response = handler.refreshVersionArtifacts(groupId, artifactId, versionId, files);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.hasErrors());
        Assertions.assertEquals(3, response.getMessages().size());
        Assertions.assertTrue(response.getMessages().get(0).contains("found [1]"));
        Assertions.assertTrue(response.getMessages().get(1).contains("removing prior"));
        Assertions.assertTrue(response.getMessages().get(2).contains("deleted 3"));
        verify(entitiesService, times(1)).delete(groupId, artifactId, versionId);
        verify(entitiesService, times(1)).createOrUpdate(groupId, artifactId, versionId, entities);
    }

    @Test
    public void canRefreshVersionArtifactsWithNoEntities()
    {
        String groupId = "test.group";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        List<File> files = new ArrayList<>();
        files.add(new File("test.jar"));

        when(artifactProvider.extractArtifacts(files)).thenReturn(Collections.emptyList());

        MetadataNotificationResponse response = handler.refreshVersionArtifacts(groupId, artifactId, versionId, files);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.hasErrors());
        Assertions.assertEquals(1, response.getMessages().size());
        Assertions.assertTrue(response.getMessages().get(0).contains("found 0"));
        verify(artifactProvider, times(1)).extractArtifacts(files);
        verify(entitiesService, times(0)).createOrUpdate(groupId, artifactId, versionId, Collections.emptyList());
    }

    @Test
    public void canRefreshVersionArtifactsWithNullEntities()
    {
        String groupId = "test.group";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        List<File> files = new ArrayList<>();
        files.add(new File("test.jar"));

        when(artifactProvider.extractArtifacts(files)).thenReturn(null);

        MetadataNotificationResponse response = handler.refreshVersionArtifacts(groupId, artifactId, versionId, files);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.hasErrors());
        Assertions.assertEquals(1, response.getMessages().size());
        Assertions.assertTrue(response.getMessages().get(0).contains("found 0"));
        verify(artifactProvider, times(1)).extractArtifacts(files);
    }

    @Test
    public void canHandleExceptionDuringRefresh()
    {
        String groupId = "test.group";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        List<File> files = new ArrayList<>();
        files.add(new File("test.jar"));

        when(artifactProvider.extractArtifacts(files)).thenThrow(new RuntimeException("Test exception"));

        MetadataNotificationResponse response = handler.refreshVersionArtifacts(groupId, artifactId, versionId, files);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.hasErrors());
        Assertions.assertEquals(1, response.getErrors().size());
        Assertions.assertTrue(response.getErrors().get(0).contains("Unexpected exception"));
        Assertions.assertTrue(response.getErrors().get(0).contains("Test exception"));
    }

    private static class TestEntityRefreshHandler extends AbstractEntityRefreshHandlerImpl
    {
        protected TestEntityRefreshHandler(ManageEntitiesService entitiesService, EntityArtifactsProvider artifactProvider)
        {
            super(entitiesService, artifactProvider);
        }
    }
}
