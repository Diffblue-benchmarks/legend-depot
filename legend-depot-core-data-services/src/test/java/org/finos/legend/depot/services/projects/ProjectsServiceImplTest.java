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

import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.domain.project.ProjectVersionData;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.services.api.notifications.queue.Queue;
import org.finos.legend.depot.services.api.notifications.queue.VoidQueue;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.store.api.projects.Projects;
import org.finos.legend.depot.store.api.projects.ProjectsVersions;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProjectsServiceImplTest
{
    private ProjectsVersions mockVersions;
    private Projects mockProjects;
    private QueryMetricsRegistry mockMetrics;
    private ProjectsServiceImpl service;

    @BeforeEach
    public void setup()
    {
        mockVersions = Mockito.mock(ProjectsVersions.class);
        mockProjects = Mockito.mock(Projects.class);
        mockMetrics = Mockito.mock(QueryMetricsRegistry.class);
        Queue queue = new VoidQueue();
        ProjectsConfiguration config = new ProjectsConfiguration("master");
        service = new ProjectsServiceImpl(mockVersions, mockProjects, mockMetrics, queue, config, new org.finos.legend.depot.services.dependencies.DependencyUtil());
    }

    @Test
    public void testGetAllProjectCoordinates()
    {
        StoreProjectData p1 = new StoreProjectData("PROD-1", "org.finos", "legend-depot");
        when(mockProjects.getAll()).thenReturn(Arrays.asList(p1));

        Assertions.assertEquals(1, service.getAllProjectCoordinates().size());
    }

    @Test
    public void testFindByProjectId()
    {
        StoreProjectData p1 = new StoreProjectData("PROD-1", "org.finos", "legend-depot");
        when(mockProjects.findByProjectId("PROD-1")).thenReturn(Arrays.asList(p1));

        Assertions.assertEquals(1, service.findByProjectId("PROD-1").size());
    }

    @Test
    public void testGetVersionsExcludesSnapshotsWhenFlagIsFalse()
    {
        StoreProjectVersionData release = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        StoreProjectVersionData snapshot = new StoreProjectVersionData("org.finos", "legend-depot", "master-SNAPSHOT");
        when(mockVersions.find("org.finos", "legend-depot")).thenReturn(Arrays.asList(release, snapshot));

        Assertions.assertEquals(1, service.getVersions("org.finos", "legend-depot", false).size());
        Assertions.assertEquals("1.0.0", service.getVersions("org.finos", "legend-depot", false).get(0));
    }

    @Test
    public void testGetVersionsIncludesSnapshotsWhenFlagIsTrue()
    {
        StoreProjectVersionData release = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        StoreProjectVersionData snapshot = new StoreProjectVersionData("org.finos", "legend-depot", "master-SNAPSHOT");
        when(mockVersions.find("org.finos", "legend-depot")).thenReturn(Arrays.asList(release, snapshot));

        Assertions.assertEquals(2, service.getVersions("org.finos", "legend-depot", true).size());
    }

    @Test
    public void testGetVersionsExcludesExcludedVersions()
    {
        StoreProjectVersionData release = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        StoreProjectVersionData excluded = new StoreProjectVersionData("org.finos", "legend-depot", "2.0.0");
        excluded.getVersionData().setExcluded(true);
        when(mockVersions.find("org.finos", "legend-depot")).thenReturn(Arrays.asList(release, excluded));

        Assertions.assertEquals(1, service.getVersions("org.finos", "legend-depot", false).size());
    }

    @Test
    public void testFindWithLatestAlias()
    {
        StoreProjectData project = new StoreProjectData("PROD-1", "org.finos", "legend-depot", "master", "2.0.0");
        StoreProjectVersionData version = new StoreProjectVersionData("org.finos", "legend-depot", "2.0.0");
        when(mockProjects.find("org.finos", "legend-depot")).thenReturn(Optional.of(project));
        when(mockVersions.find("org.finos", "legend-depot", "2.0.0")).thenReturn(Optional.of(version));

        Optional<StoreProjectVersionData> result = service.find("org.finos", "legend-depot", "latest");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("2.0.0", result.get().getVersionId());
    }

    @Test
    public void testFindWithLatestAliasNoLatestVersion()
    {
        StoreProjectData project = new StoreProjectData("PROD-1", "org.finos", "legend-depot");
        when(mockProjects.find("org.finos", "legend-depot")).thenReturn(Optional.of(project));

        Optional<StoreProjectVersionData> result = service.find("org.finos", "legend-depot", "latest");
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testFindWithHeadAlias()
    {
        StoreProjectData project = new StoreProjectData("PROD-1", "org.finos", "legend-depot", "main", "1.0.0");
        StoreProjectVersionData version = new StoreProjectVersionData("org.finos", "legend-depot", "main-SNAPSHOT");
        when(mockProjects.find("org.finos", "legend-depot")).thenReturn(Optional.of(project));
        when(mockVersions.find("org.finos", "legend-depot", "main-SNAPSHOT")).thenReturn(Optional.of(version));

        Optional<StoreProjectVersionData> result = service.find("org.finos", "legend-depot", "head");
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void testFindWithHeadAliasUsesDefaultBranch()
    {
        StoreProjectData project = new StoreProjectData("PROD-1", "org.finos", "legend-depot");
        StoreProjectVersionData version = new StoreProjectVersionData("org.finos", "legend-depot", "master-SNAPSHOT");
        when(mockProjects.find("org.finos", "legend-depot")).thenReturn(Optional.of(project));
        when(mockVersions.find("org.finos", "legend-depot", "master-SNAPSHOT")).thenReturn(Optional.of(version));

        Optional<StoreProjectVersionData> result = service.find("org.finos", "legend-depot", "head");
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void testFindWithHeadAliasProjectNotFound()
    {
        when(mockProjects.find("org.finos", "legend-depot")).thenReturn(Optional.empty());

        Optional<StoreProjectVersionData> result = service.find("org.finos", "legend-depot", "head");
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testResolveAliasesAndCheckVersionExists()
    {
        StoreProjectVersionData version = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        when(mockVersions.find("org.finos", "legend-depot", "1.0.0")).thenReturn(Optional.of(version));

        String resolved = service.resolveAliasesAndCheckVersionExists("org.finos", "legend-depot", "1.0.0");
        Assertions.assertEquals("1.0.0", resolved);
        verify(mockMetrics).record("org.finos", "legend-depot", "1.0.0");
    }

    @Test
    public void testResolveAliasesThrowsOnNotFound()
    {
        when(mockVersions.find("org.finos", "legend-depot", "9.9.9")).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.resolveAliasesAndCheckVersionExists("org.finos", "legend-depot", "9.9.9"));
    }

    @Test
    public void testResolveAliasesThrowsOnExcluded()
    {
        StoreProjectVersionData version = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        version.getVersionData().setExcluded(true);
        version.getVersionData().setExclusionReason("security");
        when(mockVersions.find("org.finos", "legend-depot", "1.0.0")).thenReturn(Optional.of(version));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.resolveAliasesAndCheckVersionExists("org.finos", "legend-depot", "1.0.0"));
    }

    @Test
    public void testResolveAliasesThrowsOnEvicted()
    {
        StoreProjectVersionData version = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        version.setEvicted(true);
        when(mockVersions.find("org.finos", "legend-depot", "1.0.0")).thenReturn(Optional.of(version));
        StoreProjectData project = new StoreProjectData("PROD-1", "org.finos", "legend-depot");
        when(mockProjects.find("org.finos", "legend-depot")).thenReturn(Optional.of(project));

        Assertions.assertThrows(IllegalStateException.class,
                () -> service.resolveAliasesAndCheckVersionExists("org.finos", "legend-depot", "1.0.0"));
    }

    @Test
    public void testCheckExistsSuccess()
    {
        when(mockProjects.find("org.finos", "legend-depot")).thenReturn(Optional.of(new StoreProjectData("PROD-1", "org.finos", "legend-depot")));
        Assertions.assertDoesNotThrow(() -> service.checkExists("org.finos", "legend-depot"));
    }

    @Test
    public void testCheckExistsThrows()
    {
        when(mockProjects.find("org.finos", "missing")).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.checkExists("org.finos", "missing"));
    }

    @Test
    public void testFindSnapshotVersions()
    {
        StoreProjectVersionData snap = new StoreProjectVersionData("org.finos", "legend-depot", "master-SNAPSHOT");
        StoreProjectVersionData release = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        when(mockVersions.find("org.finos", "legend-depot")).thenReturn(Arrays.asList(snap, release));

        Assertions.assertEquals(1, service.findSnapshotVersions("org.finos", "legend-depot").size());
        Assertions.assertEquals("master-SNAPSHOT", service.findSnapshotVersions("org.finos", "legend-depot").get(0).getVersionId());
    }

    @Test
    public void testFindCoordinates()
    {
        StoreProjectData project = new StoreProjectData("PROD-1", "org.finos", "legend-depot");
        when(mockProjects.find("org.finos", "legend-depot")).thenReturn(Optional.of(project));

        Assertions.assertTrue(service.findCoordinates("org.finos", "legend-depot").isPresent());
    }

    @Test
    public void testFindDirectVersion()
    {
        StoreProjectVersionData version = new StoreProjectVersionData("org.finos", "legend-depot", "1.0.0");
        when(mockVersions.find("org.finos", "legend-depot", "1.0.0")).thenReturn(Optional.of(version));

        Optional<StoreProjectVersionData> result = service.find("org.finos", "legend-depot", "1.0.0");
        Assertions.assertTrue(result.isPresent());
    }
}
