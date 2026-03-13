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
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.services.api.notifications.queue.VoidQueue;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.store.api.projects.UpdateProjects;
import org.finos.legend.depot.store.api.projects.UpdateProjectsVersions;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ManageProjectsServiceImplTest
{
    private UpdateProjectsVersions mockVersions;
    private UpdateProjects mockProjects;
    private ManageProjectsServiceImpl service;

    @BeforeEach
    public void setup()
    {
        mockVersions = Mockito.mock(UpdateProjectsVersions.class);
        mockProjects = Mockito.mock(UpdateProjects.class);
        QueryMetricsRegistry mockMetrics = Mockito.mock(QueryMetricsRegistry.class);
        service = new ManageProjectsServiceImpl(mockVersions, mockProjects, mockMetrics, new VoidQueue(), new ProjectsConfiguration("master"));
    }

    @Test
    public void testGetAll()
    {
        StoreProjectVersionData v1 = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        when(mockVersions.getAll()).thenReturn(Arrays.asList(v1));

        Assertions.assertEquals(1, service.getAll().size());
    }

    @Test
    public void testCreateOrUpdateVersion()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        when(mockVersions.createOrUpdate(data)).thenReturn(data);

        StoreProjectVersionData result = service.createOrUpdate(data);
        Assertions.assertEquals("1.0.0", result.getVersionId());
        verify(mockVersions).createOrUpdate(data);
    }

    @Test
    public void testCreateOrUpdateProject()
    {
        StoreProjectData data = new StoreProjectData("PROD-1", "org.finos", "legend-depot");
        when(mockProjects.createOrUpdate(data)).thenReturn(data);

        StoreProjectData result = service.createOrUpdate(data);
        Assertions.assertEquals("PROD-1", result.getProjectId());
        verify(mockProjects).createOrUpdate(data);
    }

    @Test
    public void testDeleteByGA()
    {
        when(mockVersions.delete("org.finos", "legend-depot")).thenReturn(3L);

        long deleted = service.delete("org.finos", "legend-depot");
        Assertions.assertEquals(3L, deleted);
        verify(mockProjects).delete("org.finos", "legend-depot");
        verify(mockVersions).delete("org.finos", "legend-depot");
    }

    @Test
    public void testDeleteByGAV()
    {
        when(mockVersions.delete("org.finos", "legend-depot", "1.0.0")).thenReturn(1L);

        long deleted = service.delete("org.finos", "legend-depot", "1.0.0");
        Assertions.assertEquals(1L, deleted);
    }

    @Test
    public void testExcludeProjectVersion()
    {
        StoreProjectVersionData excluded = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        excluded.getVersionData().setExcluded(true);
        excluded.getVersionData().setExclusionReason("security");
        when(mockVersions.createOrUpdate(any(StoreProjectVersionData.class))).thenReturn(excluded);

        StoreProjectVersionData result = service.excludeProjectVersion("org.finos", "legend-depot", "1.0.0", "security");
        Assertions.assertTrue(result.getVersionData().isExcluded());
    }

    @Test
    public void testGetProjectsSummary()
    {
        StoreProjectData p1 = new StoreProjectData("PROD-1", "org.finos", "legend-depot");
        StoreProjectData p2 = new StoreProjectData("PROD-2", "org.finos", "legend-engine");
        when(mockProjects.getAll()).thenReturn(Arrays.asList(p1, p2));
        when(mockVersions.getVersionCount("org.finos", "legend-depot")).thenReturn(5L);
        when(mockVersions.getVersionCount("org.finos", "legend-engine")).thenReturn(3L);

        List<ProjectSummary> summary = service.getProjectsSummary();
        Assertions.assertEquals(2, summary.size());
    }
}
