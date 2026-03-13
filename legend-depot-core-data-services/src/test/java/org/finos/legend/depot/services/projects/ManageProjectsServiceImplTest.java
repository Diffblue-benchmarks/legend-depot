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
import org.finos.legend.depot.services.api.dependencies.DependencyOverride;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.services.api.notifications.queue.Queue;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.store.api.projects.UpdateProjects;
import org.finos.legend.depot.store.api.projects.UpdateProjectsVersions;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ManageProjectsServiceImplTest
{
    protected UpdateProjectsVersions projectsVersionsStore;
    protected UpdateProjects projectsStore;
    protected QueryMetricsRegistry metricsRegistry;
    protected Queue queue;
    protected ProjectsConfiguration configuration;
    protected DependencyOverride dependencyOverride;
    protected ManageProjectsServiceImpl service;

    @BeforeEach
    public void setUp()
    {
        projectsVersionsStore = mock(UpdateProjectsVersions.class);
        projectsStore = mock(UpdateProjects.class);
        metricsRegistry = mock(QueryMetricsRegistry.class);
        queue = mock(Queue.class);
        configuration = mock(ProjectsConfiguration.class);
        dependencyOverride = mock(DependencyOverride.class);
    }

    @Test
    public void canConstructWithAllParameters()
    {
        service = new ManageProjectsServiceImpl(projectsVersionsStore, projectsStore, metricsRegistry, queue, configuration, dependencyOverride);

        Assertions.assertNotNull(service);
    }

    @Test
    public void canConstructWithoutDependencyOverride()
    {
        service = new ManageProjectsServiceImpl(projectsVersionsStore, projectsStore, metricsRegistry, queue, configuration);

        Assertions.assertNotNull(service);
    }

    @Test
    public void canGetAll()
    {
        service = new ManageProjectsServiceImpl(projectsVersionsStore, projectsStore, metricsRegistry, queue, configuration);
        List<StoreProjectVersionData> expectedVersions = Arrays.asList(
                new StoreProjectVersionData("org.example", "artifact1", "1.0.0"),
                new StoreProjectVersionData("org.example", "artifact2", "2.0.0")
        );
        when(projectsVersionsStore.getAll()).thenReturn(expectedVersions);

        List<StoreProjectVersionData> versions = service.getAll();

        Assertions.assertNotNull(versions);
        Assertions.assertEquals(expectedVersions, versions);
        verify(projectsVersionsStore).getAll();
    }

    @Test
    public void canCreateOrUpdateProjectVersionData()
    {
        service = new ManageProjectsServiceImpl(projectsVersionsStore, projectsStore, metricsRegistry, queue, configuration);
        StoreProjectVersionData projectData = new StoreProjectVersionData("org.example", "artifact1", "1.0.0");
        when(projectsVersionsStore.createOrUpdate(any(StoreProjectVersionData.class))).thenReturn(projectData);

        StoreProjectVersionData result = service.createOrUpdate(projectData);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(projectData, result);
        verify(projectsVersionsStore).createOrUpdate(projectData);
    }

    @Test
    public void canCreateOrUpdateProjectData()
    {
        service = new ManageProjectsServiceImpl(projectsVersionsStore, projectsStore, metricsRegistry, queue, configuration);
        StoreProjectData projectData = new StoreProjectData("project1", "org.example", "artifact1");
        when(projectsStore.createOrUpdate(any(StoreProjectData.class))).thenReturn(projectData);

        StoreProjectData result = service.createOrUpdate(projectData);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(projectData, result);
        verify(projectsStore).createOrUpdate(projectData);
    }

    @Test
    public void canDeleteByGroupIdAndArtifactId()
    {
        service = new ManageProjectsServiceImpl(projectsVersionsStore, projectsStore, metricsRegistry, queue, configuration);
        String groupId = "org.example";
        String artifactId = "artifact1";
        when(projectsVersionsStore.delete(eq(groupId), eq(artifactId))).thenReturn(5L);

        long result = service.delete(groupId, artifactId);

        Assertions.assertEquals(5L, result);
        verify(projectsStore).delete(groupId, artifactId);
        verify(projectsVersionsStore).delete(groupId, artifactId);
    }

    @Test
    public void canDeleteByGroupIdArtifactIdAndVersionId()
    {
        service = new ManageProjectsServiceImpl(projectsVersionsStore, projectsStore, metricsRegistry, queue, configuration);
        String groupId = "org.example";
        String artifactId = "artifact1";
        String versionId = "1.0.0";
        when(projectsVersionsStore.delete(eq(groupId), eq(artifactId), eq(versionId))).thenReturn(1L);

        long result = service.delete(groupId, artifactId, versionId);

        Assertions.assertEquals(1L, result);
        verify(projectsVersionsStore).delete(groupId, artifactId, versionId);
    }

    @Test
    public void canExcludeProjectVersion()
    {
        service = new ManageProjectsServiceImpl(projectsVersionsStore, projectsStore, metricsRegistry, queue, configuration);
        String groupId = "org.example";
        String artifactId = "artifact1";
        String versionId = "1.0.0";
        String exclusionReason = "Security vulnerability";
        StoreProjectVersionData expectedResult = new StoreProjectVersionData(groupId, artifactId, versionId);
        expectedResult.getVersionData().setExcluded(true);
        expectedResult.getVersionData().setExclusionReason(exclusionReason);
        when(projectsVersionsStore.createOrUpdate(any(StoreProjectVersionData.class))).thenReturn(expectedResult);

        StoreProjectVersionData result = service.excludeProjectVersion(groupId, artifactId, versionId, exclusionReason);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getVersionData().isExcluded());
        Assertions.assertEquals(exclusionReason, result.getVersionData().getExclusionReason());
        verify(projectsVersionsStore).createOrUpdate(any(StoreProjectVersionData.class));
    }

    @Test
    public void canGetProjectsSummary()
    {
        service = new ManageProjectsServiceImpl(projectsVersionsStore, projectsStore, metricsRegistry, queue, configuration);
        StoreProjectData project1 = new StoreProjectData("project1", "org.example", "artifact1");
        StoreProjectData project2 = new StoreProjectData("project2", "org.example", "artifact2");
        List<StoreProjectData> projects = Arrays.asList(project1, project2);
        when(projectsStore.getAll()).thenReturn(projects);
        when(projectsVersionsStore.getVersionCount(eq("org.example"), eq("artifact1"))).thenReturn(3L);
        when(projectsVersionsStore.getVersionCount(eq("org.example"), eq("artifact2"))).thenReturn(2L);

        List<ProjectSummary> summaries = service.getProjectsSummary();

        Assertions.assertNotNull(summaries);
        Assertions.assertEquals(2, summaries.size());
        verify(projectsStore).getAll();
        verify(projectsVersionsStore).getVersionCount("org.example", "artifact1");
        verify(projectsVersionsStore).getVersionCount("org.example", "artifact2");
    }
}
