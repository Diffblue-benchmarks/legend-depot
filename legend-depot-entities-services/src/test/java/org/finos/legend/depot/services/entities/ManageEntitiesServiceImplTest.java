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

package org.finos.legend.depot.services.entities;

import org.finos.legend.depot.services.api.projects.ProjectsService;
import org.finos.legend.depot.store.api.entities.UpdateEntities;
import org.finos.legend.depot.store.model.entities.StoredEntity;
import org.finos.legend.depot.store.model.entities.StoredEntityData;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ManageEntitiesServiceImplTest
{
    private UpdateEntities entities;
    private ProjectsService projects;
    private ManageEntitiesServiceImpl service;

    @BeforeEach
    public void setup()
    {
        entities = mock(UpdateEntities.class);
        projects = mock(ProjectsService.class);
        service = new ManageEntitiesServiceImpl(entities, projects);
    }

    @Test
    public void testConstructor()
    {
        UpdateEntities mockEntities = mock(UpdateEntities.class);
        ProjectsService mockProjects = mock(ProjectsService.class);
        ManageEntitiesServiceImpl testService = new ManageEntitiesServiceImpl(mockEntities, mockProjects);
        Assertions.assertNotNull(testService);
    }

    @Test
    public void canGetStoredEntities()
    {
        List<StoredEntity> expectedEntities = new ArrayList<>();
        StoredEntityData entity = new StoredEntityData("com.example", "artifact", "1.0.0");
        expectedEntities.add(entity);

        when(entities.getStoredEntities(anyString(), anyString(), anyString()))
            .thenReturn(expectedEntities);

        List result = service.getStoredEntities("com.example", "artifact", "1.0.0");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        verify(entities).getStoredEntities("com.example", "artifact", "1.0.0");
    }

    @Test
    public void canGetStoredEntitiesWithEmptyResult()
    {
        List<StoredEntity> emptyList = new ArrayList<>();

        when(entities.getStoredEntities(anyString(), anyString(), anyString()))
            .thenReturn(emptyList);

        List result = service.getStoredEntities("com.example", "artifact", "1.0.0");

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
        verify(entities).getStoredEntities("com.example", "artifact", "1.0.0");
    }

    @Test
    public void canDeleteWithVersion()
    {
        when(entities.delete(anyString(), anyString(), anyString())).thenReturn(5L);

        long result = service.delete("com.example", "artifact", "1.0.0");

        Assertions.assertEquals(5L, result);
        verify(projects).checkExists("com.example", "artifact");
        verify(entities).delete("com.example", "artifact", "1.0.0");
    }

    @Test
    public void canDeleteWithVersionReturningZero()
    {
        when(entities.delete(anyString(), anyString(), anyString())).thenReturn(0L);

        long result = service.delete("com.example", "artifact", "1.0.0");

        Assertions.assertEquals(0L, result);
        verify(projects).checkExists("com.example", "artifact");
        verify(entities).delete("com.example", "artifact", "1.0.0");
    }

    @Test
    public void canDeleteAllVersions()
    {
        when(entities.delete(anyString(), anyString())).thenReturn(10L);

        long result = service.delete("com.example", "artifact");

        Assertions.assertEquals(10L, result);
        verify(projects).checkExists("com.example", "artifact");
        verify(entities).delete("com.example", "artifact");
    }

    @Test
    public void canDeleteAllVersionsReturningZero()
    {
        when(entities.delete(anyString(), anyString())).thenReturn(0L);

        long result = service.delete("com.example", "artifact");

        Assertions.assertEquals(0L, result);
        verify(projects).checkExists("com.example", "artifact");
        verify(entities).delete("com.example", "artifact");
    }

    @Test
    public void canCreateOrUpdate()
    {
        List<Entity> entityList = new ArrayList<>();
        Entity entity = mock(Entity.class);
        entityList.add(entity);

        service.createOrUpdate("com.example", "artifact", "1.0.0", entityList);

        verify(entities).createOrUpdate("com.example", "artifact", "1.0.0", entityList);
    }

    @Test
    public void canCreateOrUpdateWithEmptyList()
    {
        List<Entity> emptyList = new ArrayList<>();

        service.createOrUpdate("com.example", "artifact", "1.0.0", emptyList);

        verify(entities).createOrUpdate("com.example", "artifact", "1.0.0", emptyList);
    }
}
