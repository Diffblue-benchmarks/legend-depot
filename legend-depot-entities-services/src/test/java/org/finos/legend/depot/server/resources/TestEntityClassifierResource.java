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

package org.finos.legend.depot.server.resources;

import org.finos.legend.depot.domain.entity.DepotEntity;
import org.finos.legend.depot.domain.version.Scope;
import org.finos.legend.depot.server.resources.entities.EntityClassifierResource;
import org.finos.legend.depot.services.api.entities.EntityClassifierService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestEntityClassifierResource
{
    private final EntityClassifierService classifierService = mock(EntityClassifierService.class);
    private final EntityClassifierResource resource = new EntityClassifierResource(classifierService);

    @Test
    public void canCreateResource()
    {
        Assertions.assertNotNull(resource);
    }

    @Test
    public void canGetEntitiesByClassifierPath()
    {
        DepotEntity entity = mock(DepotEntity.class);
        List<DepotEntity> entities = Collections.singletonList(entity);
        when(classifierService.getEntitiesByClassifierPath("meta::pure::metamodel::type::Class", "search", 10, Scope.RELEASES, true))
                .thenReturn(entities);

        Response response = resource.getEntities("meta::pure::metamodel::type::Class", "search", Scope.RELEASES, 10);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(classifierService).getEntitiesByClassifierPath("meta::pure::metamodel::type::Class", "search", 10, Scope.RELEASES, true);
    }

    @Test
    public void canGetEntitiesByClassifierPathWithNullParams()
    {
        when(classifierService.getEntitiesByClassifierPath("meta::pure::metamodel::type::Class", null, null, Scope.RELEASES, true))
                .thenReturn(Collections.emptyList());

        Response response = resource.getEntities("meta::pure::metamodel::type::Class", null, Scope.RELEASES, null);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(classifierService).getEntitiesByClassifierPath("meta::pure::metamodel::type::Class", null, null, Scope.RELEASES, true);
    }
}
