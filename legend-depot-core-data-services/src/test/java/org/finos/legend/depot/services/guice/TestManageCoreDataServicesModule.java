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

package org.finos.legend.depot.services.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.services.api.notifications.queue.Queue;
import org.finos.legend.depot.services.api.projects.ManageProjectsService;
import org.finos.legend.depot.services.api.projects.ProjectsService;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.services.projects.ManageProjectsServiceImpl;
import org.finos.legend.depot.store.api.projects.Projects;
import org.finos.legend.depot.store.api.projects.ProjectsVersions;
import org.finos.legend.depot.store.api.projects.UpdateProjects;
import org.finos.legend.depot.store.api.projects.UpdateProjectsVersions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class TestManageCoreDataServicesModule
{
    @Test
    public void canInstallModuleAndResolveBindings()
    {
        UpdateProjectsVersions mockProjectsVersions = mock(UpdateProjectsVersions.class);
        UpdateProjects mockProjects = mock(UpdateProjects.class);

        Injector injector = Guice.createInjector(new AbstractModule()
        {
            @Override
            protected void configure()
            {
                bind(UpdateProjectsVersions.class).toInstance(mockProjectsVersions);
                bind(UpdateProjects.class).toInstance(mockProjects);
                bind(ProjectsVersions.class).toInstance(mockProjectsVersions);
                bind(Projects.class).toInstance(mockProjects);
                bind(QueryMetricsRegistry.class).annotatedWith(Names.named("queryMetricsRegistry")).toInstance(mock(QueryMetricsRegistry.class));
                bind(Queue.class).toInstance(mock(Queue.class));
                bind(ProjectsConfiguration.class).toInstance(new ProjectsConfiguration("master"));
                install(new ManageCoreDataServicesModule());
            }
        });

        ManageProjectsService manageProjectsService = injector.getInstance(ManageProjectsService.class);
        assertNotNull(manageProjectsService);
        assertTrue(manageProjectsService instanceof ManageProjectsServiceImpl);

        ProjectsService projectsService = injector.getInstance(ProjectsService.class);
        assertNotNull(projectsService);
    }
}
