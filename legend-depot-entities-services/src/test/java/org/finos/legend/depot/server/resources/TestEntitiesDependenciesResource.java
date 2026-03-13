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

import org.finos.legend.depot.domain.entity.ProjectVersionEntities;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.domain.project.dependencies.VersionDependencyReport;
import org.finos.legend.depot.server.resources.entities.EntitiesDependenciesResource;
import org.finos.legend.depot.services.TestBaseServices;
import org.finos.legend.depot.services.api.entities.EntitiesService;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.services.api.notifications.queue.Queue;
import org.finos.legend.depot.services.api.projects.ProjectsService;
import org.finos.legend.depot.services.entities.EntitiesServiceImpl;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.services.projects.ProjectsServiceImpl;
import org.finos.legend.depot.store.api.entities.UpdateEntities;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.finos.legend.depot.store.mongo.entities.EntitiesMongo;
import org.finos.legend.depot.store.mongo.entities.test.EntitiesMongoTestUtils;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;

public class TestEntitiesDependenciesResource extends TestBaseServices
{
    private final QueryMetricsRegistry metrics = mock(QueryMetricsRegistry.class);
    private final Queue queue = mock(Queue.class);
    private EntitiesMongoTestUtils entityUtils = new EntitiesMongoTestUtils(mongoProvider);
    private UpdateEntities entitiesStore = new EntitiesMongo(mongoProvider);
    private ProjectsService projectsService = new ProjectsServiceImpl(projectsVersionsStore, projectsStore, metrics, queue, new ProjectsConfiguration("master"));
    private EntitiesService entitiesService = new EntitiesServiceImpl(entitiesStore, projectsService);
    private EntitiesDependenciesResource entitiesDependenciesResource;

    @BeforeEach
    public void setUpData()
    {
        super.setUpData();
        entitiesDependenciesResource = new EntitiesDependenciesResource(entitiesService);

        StoreProjectVersionData project1 = projectsVersionsStore.find("examples.metadata", "test-dependencies", "1.0.0").get();
        ProjectVersion pv = new ProjectVersion("example.services.test", "test", "2.0.1");
        project1.getVersionData().addDependency(pv);
        projectsVersionsStore.createOrUpdate(project1);

        StoreProjectVersionData project2 = projectsVersionsStore.find("examples.metadata", "test", "2.3.1").get();
        project2.setTransitiveDependenciesReport(new VersionDependencyReport(Collections.singletonList(pv), true));
        projectsVersionsStore.createOrUpdate(project2);

        entityUtils.loadEntities("PROD-A", "2.3.1");
        entityUtils.loadEntities("PROD-B", "1.0.0");
        entityUtils.loadEntities("PROD-C", "2.0.1");
    }

    @Test
    public void canCreateEntitiesDependenciesResource()
    {
        EntitiesDependenciesResource resource = new EntitiesDependenciesResource(entitiesService);
        Assertions.assertNotNull(resource);
    }

    @Test
    public void canGetEntitiesFromDependencies()
    {
        Response response = entitiesDependenciesResource.getEntitiesFromDependencies("examples.metadata", "test", "2.3.1", false, false, null);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        List<ProjectVersionEntities> dependencyList = (List<ProjectVersionEntities>) response.getEntity();
        Assertions.assertNotNull(dependencyList);
        Assertions.assertEquals(1, dependencyList.size());
    }

    @Test
    public void canGetEntitiesFromDependenciesTransitive()
    {
        Response response = entitiesDependenciesResource.getEntitiesFromDependencies("examples.metadata", "test", "2.3.1", true, false, null);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        List<ProjectVersionEntities> dependencyList = (List<ProjectVersionEntities>) response.getEntity();
        Assertions.assertNotNull(dependencyList);
        Assertions.assertEquals(2, dependencyList.size());
    }

    @Test
    public void canGetEntitiesFromDependenciesIncludeOrigin()
    {
        Response response = entitiesDependenciesResource.getEntitiesFromDependencies("examples.metadata", "test", "2.3.1", true, true, null);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        List<ProjectVersionEntities> dependencyList = (List<ProjectVersionEntities>) response.getEntity();
        Assertions.assertNotNull(dependencyList);
        Assertions.assertEquals(3, dependencyList.size());
    }

    @Test
    public void canGetEntitiesFromDependenciesByClassifier()
    {
        Response response = entitiesDependenciesResource.getEntitiesFromDependenciesByClassifier("examples.metadata", "test-dependencies", "1.0.0", "meta::pure::metamodel::function::ConcreteFunctionDefinition", false, false, null);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        List<Entity> entityList = (List<Entity>) response.getEntity();
        Assertions.assertNotNull(entityList);
        Assertions.assertFalse(entityList.isEmpty());
    }

    @Test
    public void canGetEntitiesFromDependenciesByClassifierTransitive()
    {
        Response response = entitiesDependenciesResource.getEntitiesFromDependenciesByClassifier("examples.metadata", "test-dependencies", "1.0.0", "meta::pure::metamodel::function::ConcreteFunctionDefinition", true, false, null);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        List<Entity> entityList = (List<Entity>) response.getEntity();
        Assertions.assertNotNull(entityList);
        Assertions.assertFalse(entityList.isEmpty());
    }

    @Test
    public void canGetEntitiesFromDependenciesByClassifierIncludeOrigin()
    {
        Response response = entitiesDependenciesResource.getEntitiesFromDependenciesByClassifier("examples.metadata", "test-dependencies", "1.0.0", "meta::pure::metamodel::function::ConcreteFunctionDefinition", true, true, null);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        List<Entity> entityList = (List<Entity>) response.getEntity();
        Assertions.assertNotNull(entityList);
        Assertions.assertFalse(entityList.isEmpty());
    }

    @Test
    public void canGetEntityFromDependencies()
    {
        List<String> entityPaths = Arrays.asList("examples::metadata::test::dependency::Dependency");

        Response response = entitiesDependenciesResource.getEntityFromDependencies("examples.metadata", "test", "2.3.1", entityPaths, false, null);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        List<Entity> entityList = (List<Entity>) response.getEntity();
        Assertions.assertNotNull(entityList);
        Assertions.assertFalse(entityList.isEmpty());
    }

    @Test
    public void canGetEntityFromDependenciesIncludeOrigin()
    {
        List<String> entityPaths = Arrays.asList("examples::metadata::test::ClientBasic");

        Response response = entitiesDependenciesResource.getEntityFromDependencies("examples.metadata", "test", "2.3.1", entityPaths, true, null);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        List<Entity> entityList = (List<Entity>) response.getEntity();
        Assertions.assertNotNull(entityList);
        Assertions.assertFalse(entityList.isEmpty());
    }

    @Test
    public void canGetAllEntitiesFromDependencies()
    {
        List<ProjectVersion> projectVersions = Arrays.asList(
            new ProjectVersion("examples.metadata", "test", "2.3.1"),
            new ProjectVersion("examples.metadata", "test-dependencies", "1.0.0")
        );

        Response response = entitiesDependenciesResource.getAllEntitiesFromDependencies(projectVersions, false, false);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        List<ProjectVersionEntities> dependencyList = (List<ProjectVersionEntities>) response.getEntity();
        Assertions.assertNotNull(dependencyList);
        Assertions.assertFalse(dependencyList.isEmpty());
    }

    @Test
    public void canGetAllEntitiesFromDependenciesTransitive()
    {
        List<ProjectVersion> projectVersions = Arrays.asList(
            new ProjectVersion("examples.metadata", "test", "2.3.1"),
            new ProjectVersion("examples.metadata", "test-dependencies", "1.0.0")
        );

        Response response = entitiesDependenciesResource.getAllEntitiesFromDependencies(projectVersions, true, false);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        List<ProjectVersionEntities> dependencyList = (List<ProjectVersionEntities>) response.getEntity();
        Assertions.assertNotNull(dependencyList);
        Assertions.assertFalse(dependencyList.isEmpty());
    }

    @Test
    public void canGetAllEntitiesFromDependenciesIncludeOrigin()
    {
        List<ProjectVersion> projectVersions = Arrays.asList(
            new ProjectVersion("examples.metadata", "test", "2.3.1"),
            new ProjectVersion("examples.metadata", "test-dependencies", "1.0.0")
        );

        Response response = entitiesDependenciesResource.getAllEntitiesFromDependencies(projectVersions, true, true);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        List<ProjectVersionEntities> dependencyList = (List<ProjectVersionEntities>) response.getEntity();
        Assertions.assertNotNull(dependencyList);
        Assertions.assertFalse(dependencyList.isEmpty());
    }
}
