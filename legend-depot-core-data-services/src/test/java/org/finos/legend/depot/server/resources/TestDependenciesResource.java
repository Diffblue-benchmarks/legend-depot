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
import org.finos.legend.depot.server.resources.dependencies.DependenciesResource;
import org.finos.legend.depot.services.TestBaseServices;
import org.finos.legend.depot.services.projects.ProjectsServiceImpl;
import org.finos.legend.depot.services.api.projects.ProjectsService;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.services.api.notifications.queue.Queue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;

public class TestDependenciesResource extends TestBaseServices
{
    private final QueryMetricsRegistry metrics = mock(QueryMetricsRegistry.class);
    private final Queue queue = mock(Queue.class);
    private final ProjectsService projectsService = new ProjectsServiceImpl(projectsVersionsStore, projectsStore, metrics, queue, new ProjectsConfiguration("master"));
    private final DependenciesResource dependenciesResource = new DependenciesResource(projectsService);

    @Test
    public void canGetProjectDependencies()
    {
        Response response = dependenciesResource.getProjectDependencies("examples.metadata", "test", "2.3.1", false, null);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getEntity());
        Set<ProjectVersion> dependencies = (Set<ProjectVersion>) response.getEntity();
        Assertions.assertNotNull(dependencies);
    }

    @Test
    public void canGetProjectDependenciesTransitive()
    {
        Response response = dependenciesResource.getProjectDependencies("examples.metadata", "test", "2.3.1", true, null);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getEntity());
    }

    @Test
    public void canAnalyzeDependencyTree()
    {
        List<ProjectVersion> projectDependencies = Arrays.asList(new ProjectVersion("examples.metadata", "test", "2.3.1"));
        Response response = dependenciesResource.analyzeDependencyTree(projectDependencies);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getEntity());
    }

    @Test
    public void canGetDependantProjects()
    {
        Response response = dependenciesResource.getDependantProjects("examples.metadata", "test-dependencies", "1.0.0", false);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getEntity());
        List<?> dependants = (List<?>) response.getEntity();
        Assertions.assertNotNull(dependants);
    }

    @Test
    public void canGetDependantProjectsLatestOnly()
    {
        Response response = dependenciesResource.getDependantProjects("examples.metadata", "test-dependencies", "1.0.0", true);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getEntity());
    }

    @Test
    public void canAnalyzeDependencyTreeWithEmptyList()
    {
        List<ProjectVersion> projectDependencies = Collections.emptyList();
        Response response = dependenciesResource.analyzeDependencyTree(projectDependencies);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getEntity());
    }
}
