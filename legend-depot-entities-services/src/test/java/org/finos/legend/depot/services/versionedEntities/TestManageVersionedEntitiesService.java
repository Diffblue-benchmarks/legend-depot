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

package org.finos.legend.depot.services.versionedEntities;

import org.finos.legend.depot.services.TestBaseServices;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.services.api.notifications.queue.Queue;
import org.finos.legend.depot.services.api.projects.ManageProjectsService;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.services.api.versionedEntities.ManageVersionedEntitiesService;
import org.finos.legend.depot.services.projects.ManageProjectsServiceImpl;
import org.finos.legend.depot.store.api.versionedEntities.UpdateVersionedEntities;
import org.finos.legend.depot.store.mongo.versionedEntities.VersionedEntitiesMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class TestManageVersionedEntitiesService extends TestBaseServices
{
    private final QueryMetricsRegistry metrics = mock(QueryMetricsRegistry.class);
    private final Queue queue = mock(Queue.class);
    private ManageProjectsService projectsService;
    protected UpdateVersionedEntities versionedEntitiesStore;
    protected ManageVersionedEntitiesService versionedEntitiesService;

    @BeforeEach
    public void setUpData()
    {
        super.setUpData();
        projectsService = new ManageProjectsServiceImpl(projectsVersionsStore, projectsStore, metrics, queue, new ProjectsConfiguration("master"));
        versionedEntitiesStore = new VersionedEntitiesMongo(mongoProvider);
        versionedEntitiesService = new ManageVersionedEntitiesServiceImpl(versionedEntitiesStore, projectsService);
    }

    @Test
    public void canInstantiateService()
    {
        Assertions.assertNotNull(versionedEntitiesService);
        Assertions.assertTrue(versionedEntitiesService instanceof ManageVersionedEntitiesServiceImpl);
    }
}
