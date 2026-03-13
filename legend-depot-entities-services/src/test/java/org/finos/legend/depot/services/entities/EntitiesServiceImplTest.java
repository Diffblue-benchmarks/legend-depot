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
import org.finos.legend.depot.store.api.entities.Entities;
import org.finos.legend.depot.store.model.entities.StoredEntity;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EntitiesServiceImplTest
{
    private Entities mockEntities;
    private ProjectsService mockProjects;
    private EntitiesServiceImpl<StoredEntity> service;

    @BeforeEach
    public void setup()
    {
        mockEntities = Mockito.mock(Entities.class);
        mockProjects = Mockito.mock(ProjectsService.class);
        service = new EntitiesServiceImpl<>(mockEntities, mockProjects);
    }

    @Test
    public void testGetEntitiesResolvesAliases()
    {
        when(mockProjects.resolveAliasesAndCheckVersionExists("org.finos", "legend-depot", "latest")).thenReturn("1.0.0");
        when(mockEntities.getAllEntities("org.finos", "legend-depot", "1.0.0")).thenReturn(Collections.emptyList());

        List<Entity> result = service.getEntities("org.finos", "legend-depot", "latest");

        Assertions.assertTrue(result.isEmpty());
        verify(mockProjects).resolveAliasesAndCheckVersionExists("org.finos", "legend-depot", "latest");
        verify(mockEntities).getAllEntities("org.finos", "legend-depot", "1.0.0");
    }

    @Test
    public void testGetEntitiesReturnsEntities()
    {
        Entity mockEntity = Mockito.mock(Entity.class);
        when(mockProjects.resolveAliasesAndCheckVersionExists("org.finos", "legend-depot", "1.0.0")).thenReturn("1.0.0");
        when(mockEntities.getAllEntities("org.finos", "legend-depot", "1.0.0")).thenReturn(Arrays.asList(mockEntity));

        List<Entity> result = service.getEntities("org.finos", "legend-depot", "1.0.0");

        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void testGetEntitiesByClassifier()
    {
        Entity mockEntity = Mockito.mock(Entity.class);
        when(mockProjects.resolveAliasesAndCheckVersionExists("org.finos", "legend-depot", "1.0.0")).thenReturn("1.0.0");
        when(mockEntities.findEntitiesByClassifier("org.finos", "legend-depot", "1.0.0", "meta::pure::metamodel::type::Class")).thenReturn(Arrays.asList(mockEntity));

        List<Entity> result = service.getEntitiesByClassifier("org.finos", "legend-depot", "1.0.0", "meta::pure::metamodel::type::Class");

        Assertions.assertEquals(1, result.size());
        verify(mockEntities).findEntitiesByClassifier("org.finos", "legend-depot", "1.0.0", "meta::pure::metamodel::type::Class");
    }

    @Test
    public void testGetEntity()
    {
        Entity mockEntity = Mockito.mock(Entity.class);
        when(mockProjects.resolveAliasesAndCheckVersionExists("org.finos", "legend-depot", "1.0.0")).thenReturn("1.0.0");
        when(mockEntities.getEntity("org.finos", "legend-depot", "1.0.0", "model::Person")).thenReturn(Optional.of(mockEntity));

        Optional<Entity> result = service.getEntity("org.finos", "legend-depot", "1.0.0", "model::Person");

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void testGetEntityNotFound()
    {
        when(mockProjects.resolveAliasesAndCheckVersionExists("org.finos", "legend-depot", "1.0.0")).thenReturn("1.0.0");
        when(mockEntities.getEntity("org.finos", "legend-depot", "1.0.0", "model::NotExists")).thenReturn(Optional.empty());

        Optional<Entity> result = service.getEntity("org.finos", "legend-depot", "1.0.0", "model::NotExists");

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testGetEntitiesByPackage()
    {
        when(mockProjects.resolveAliasesAndCheckVersionExists("org.finos", "legend-depot", "1.0.0")).thenReturn("1.0.0");
        when(mockEntities.getEntitiesByPackage("org.finos", "legend-depot", "1.0.0", "model", null, true)).thenReturn(Collections.emptyList());

        List<Entity> result = service.getEntitiesByPackage("org.finos", "legend-depot", "1.0.0", "model", null, true);

        Assertions.assertTrue(result.isEmpty());
        verify(mockEntities).getEntitiesByPackage("org.finos", "legend-depot", "1.0.0", "model", null, true);
    }
}
