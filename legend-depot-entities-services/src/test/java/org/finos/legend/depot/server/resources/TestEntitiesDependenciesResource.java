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

import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.server.resources.entities.EntitiesDependenciesResource;
import org.finos.legend.depot.services.api.entities.EntitiesService;
import org.finos.legend.depot.store.model.entities.StoredEntity;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class TestEntitiesDependenciesResource
{
    private EntitiesService<StoredEntity> entitiesService;
    private EntitiesDependenciesResource resource;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void setUp()
    {
        entitiesService = mock(EntitiesService.class);
        resource = new EntitiesDependenciesResource(entitiesService);
    }

    @Test
    public void canCreateResource()
    {
        Assertions.assertNotNull(resource);
    }

    @Test
    public void canGetEntitiesFromDependencies()
    {
        when(entitiesService.getDependenciesEntities("examples.metadata", "test", "2.3.0", false, false))
                .thenReturn(Collections.emptyList());

        Response response = resource.getEntitiesFromDependencies("examples.metadata", "test", "2.3.0", false, false, null);

        Assertions.assertNotNull(response);
        verify(entitiesService).getDependenciesEntities("examples.metadata", "test", "2.3.0", false, false);
    }

    @Test
    public void canGetEntitiesFromDependenciesByClassifier()
    {
        when(entitiesService.getDependenciesEntitiesByClassifier("examples.metadata", "test", "2.3.0", "meta::pure::metamodel::type::Class", false, false))
                .thenReturn(Collections.emptyList());

        Response response = resource.getEntitiesFromDependenciesByClassifier("examples.metadata", "test", "2.3.0", "meta::pure::metamodel::type::Class", false, false, null);

        Assertions.assertNotNull(response);
        verify(entitiesService).getDependenciesEntitiesByClassifier("examples.metadata", "test", "2.3.0", "meta::pure::metamodel::type::Class", false, false);
    }

    @Test
    public void canGetEntitiesFromDependenciesByClassifierWithNullClassifier()
    {
        when(entitiesService.getDependenciesEntitiesByClassifier("examples.metadata", "test", "2.3.0", null, false, false))
                .thenReturn(Collections.emptyList());

        Response response = resource.getEntitiesFromDependenciesByClassifier("examples.metadata", "test", "2.3.0", null, false, false, null);

        Assertions.assertNotNull(response);
    }

    @Test
    public void canGetEntityFromDependencies()
    {
        List<String> entityPaths = Arrays.asList("examples::metadata::test::TestProfile");
        when(entitiesService.getEntityFromDependencies("examples.metadata", "test", "2.3.0", entityPaths, false))
                .thenReturn(Collections.emptyList());

        Response response = resource.getEntityFromDependencies("examples.metadata", "test", "2.3.0", entityPaths, false, null);

        Assertions.assertNotNull(response);
        verify(entitiesService).getEntityFromDependencies("examples.metadata", "test", "2.3.0", entityPaths, false);
    }

    @Test
    public void canGetAllEntitiesFromDependencies()
    {
        List<ProjectVersion> projectDependencies = Arrays.asList(new ProjectVersion("examples.metadata", "test", "2.3.0"));
        when(entitiesService.getDependenciesEntities(projectDependencies, false, false))
                .thenReturn(Collections.emptyList());

        Response response = resource.getAllEntitiesFromDependencies(projectDependencies, false, false);

        Assertions.assertNotNull(response);
        verify(entitiesService).getDependenciesEntities(projectDependencies, false, false);
    }
}
