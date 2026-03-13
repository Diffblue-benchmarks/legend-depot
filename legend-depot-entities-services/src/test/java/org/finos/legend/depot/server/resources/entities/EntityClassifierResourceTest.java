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

package org.finos.legend.depot.server.resources.entities;

import org.finos.legend.depot.domain.entity.DepotEntity;
import org.finos.legend.depot.domain.version.Scope;
import org.finos.legend.depot.services.api.entities.EntityClassifierService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EntityClassifierResourceTest
{
    private EntityClassifierService graphService;
    private EntityClassifierResource resource;

    @BeforeEach
    public void setup()
    {
        graphService = mock(EntityClassifierService.class);
        resource = new EntityClassifierResource(graphService);
    }

    @Test
    public void testConstructor()
    {
        EntityClassifierService mockService = mock(EntityClassifierService.class);
        EntityClassifierResource testResource = new EntityClassifierResource(mockService);
        Assertions.assertNotNull(testResource);
    }

    @Test
    public void canGetEntitiesByClassifierPath()
    {
        List<DepotEntity> expectedEntities = new ArrayList<>();
        DepotEntity entity = new DepotEntity();
        expectedEntities.add(entity);

        when(graphService.getEntitiesByClassifierPath("meta::pure::metamodel::type::Class", "test", 10, Scope.RELEASES, true))
            .thenReturn(expectedEntities);

        Response response = resource.getEntities("meta::pure::metamodel::type::Class", "test", Scope.RELEASES, 10);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus());
        verify(graphService).getEntitiesByClassifierPath("meta::pure::metamodel::type::Class", "test", 10, Scope.RELEASES, true);
    }

    @Test
    public void canGetEntitiesWithNullSearch()
    {
        List<DepotEntity> expectedEntities = new ArrayList<>();

        when(graphService.getEntitiesByClassifierPath("meta::pure::metamodel::type::Class", null, 10, Scope.RELEASES, true))
            .thenReturn(expectedEntities);

        Response response = resource.getEntities("meta::pure::metamodel::type::Class", null, Scope.RELEASES, 10);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus());
        verify(graphService).getEntitiesByClassifierPath("meta::pure::metamodel::type::Class", null, 10, Scope.RELEASES, true);
    }

    @Test
    public void canGetEntitiesWithNullLimit()
    {
        List<DepotEntity> expectedEntities = new ArrayList<>();

        when(graphService.getEntitiesByClassifierPath("meta::pure::metamodel::type::Class", "test", null, Scope.RELEASES, true))
            .thenReturn(expectedEntities);

        Response response = resource.getEntities("meta::pure::metamodel::type::Class", "test", Scope.RELEASES, null);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus());
        verify(graphService).getEntitiesByClassifierPath("meta::pure::metamodel::type::Class", "test", null, Scope.RELEASES, true);
    }

    @Test
    public void canGetEntitiesWithSnapshotScope()
    {
        List<DepotEntity> expectedEntities = new ArrayList<>();

        when(graphService.getEntitiesByClassifierPath("meta::pure::metamodel::type::Class", "test", 10, Scope.SNAPSHOT, true))
            .thenReturn(expectedEntities);

        Response response = resource.getEntities("meta::pure::metamodel::type::Class", "test", Scope.SNAPSHOT, 10);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus());
        verify(graphService).getEntitiesByClassifierPath("meta::pure::metamodel::type::Class", "test", 10, Scope.SNAPSHOT, true);
    }
}
