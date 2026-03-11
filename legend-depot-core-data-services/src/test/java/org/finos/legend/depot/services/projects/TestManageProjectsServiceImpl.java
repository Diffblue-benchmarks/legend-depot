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

package org.finos.legend.depot.services.projects;

import org.finos.legend.depot.domain.project.ProjectSummary;
import org.finos.legend.depot.services.TestBaseServices;
import org.finos.legend.depot.services.api.dependencies.DependencyOverride;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.services.api.notifications.queue.Queue;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.store.mongo.notifications.queue.NotificationsQueueMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;

public class TestManageProjectsServiceImpl extends TestBaseServices
{
    private final QueryMetricsRegistry metrics = mock(QueryMetricsRegistry.class);
    private final Queue queue = new NotificationsQueueMongo(mongoProvider);
    private final DependencyOverride dependencyOverride = mock(DependencyOverride.class);
    private ManageProjectsServiceImpl manageProjectsService = new ManageProjectsServiceImpl(projectsVersionsStore, projectsStore, metrics, queue, new ProjectsConfiguration("master"), dependencyOverride);

    @BeforeEach
    public void setUpData()
    {
        super.setUpData();
    }

    @Test
    public void canCreateServiceWithDependencyOverride()
    {
        Assertions.assertNotNull(manageProjectsService);
        Assertions.assertFalse(manageProjectsService.getAll().isEmpty());
    }

    @Test
    public void canGetProjectsSummary()
    {
        List<ProjectSummary> summary = manageProjectsService.getProjectsSummary();
        Assertions.assertNotNull(summary);
        Assertions.assertFalse(summary.isEmpty());
    }

    @Test
    public void canGetProjectsSummaryWithCorrectData()
    {
        List<ProjectSummary> summary = manageProjectsService.getProjectsSummary();
        Assertions.assertNotNull(summary);
        for (ProjectSummary ps : summary)
        {
            Assertions.assertNotNull(ps.groupId);
            Assertions.assertNotNull(ps.artifactId);
        }
    }
}
