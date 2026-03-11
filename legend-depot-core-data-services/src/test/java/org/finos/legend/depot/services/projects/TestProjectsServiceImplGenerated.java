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
import org.finos.legend.depot.domain.project.dependencies.VersionDependencyReport;
import org.finos.legend.depot.services.TestBaseServices;
import org.finos.legend.depot.services.api.dependencies.DependencyOverride;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.services.api.notifications.queue.Queue;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.services.dependencies.DependencyUtil;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.finos.legend.depot.store.mongo.notifications.queue.NotificationsQueueMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;

public class TestProjectsServiceImplGenerated extends TestBaseServices
{
    private final QueryMetricsRegistry metrics = mock(QueryMetricsRegistry.class);
    private final Queue queue = new NotificationsQueueMongo(mongoProvider);
    private final DependencyOverride dependencyOverride = new DependencyUtil();
    private ProjectsServiceImpl projectsServiceImpl;

    @BeforeEach
    public void setUpData()
    {
        super.setUpData();
        projectsServiceImpl = new ProjectsServiceImpl(projectsVersionsStore, projectsStore, metrics, queue, new ProjectsConfiguration("master"), dependencyOverride);
    }

    @Test
    public void canConstructWithSixArgConstructor()
    {
        Assertions.assertNotNull(projectsServiceImpl);
        List<StoreProjectData> allProjects = projectsServiceImpl.getAllProjectCoordinates();
        Assertions.assertFalse(allProjects.isEmpty());
    }

    @Test
    public void canFindByProjectId()
    {
        List<StoreProjectData> result = projectsServiceImpl.findByProjectId("PROD-A");
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void canFindByProjectIdNotFound()
    {
        List<StoreProjectData> result = projectsServiceImpl.findByProjectId("NONEXISTENT");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void canFindByUpdatedDate()
    {
        List<StoreProjectVersionData> result = projectsServiceImpl.findByUpdatedDate(0L, System.currentTimeMillis());
        Assertions.assertNotNull(result);
    }

    @Test
    public void canFindVersionByExcluded()
    {
        StoreProjectVersionData excluded = projectsServiceImpl.find("examples.metadata", "test", "2.3.1").get();
        excluded.getVersionData().setExcluded(true);
        projectsVersionsStore.createOrUpdate(excluded);

        List<StoreProjectVersionData> result = projectsServiceImpl.findVersion(true);
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void canFindVersionNotExcluded()
    {
        List<StoreProjectVersionData> result = projectsServiceImpl.findVersion(false);
        Assertions.assertNotNull(result);
    }

    @Test
    public void resolveAliasesThrowsForExcludedVersion()
    {
        StoreProjectVersionData versionData = projectsServiceImpl.find("examples.metadata", "test", "2.3.1").get();
        versionData.getVersionData().setExcluded(true);
        versionData.getVersionData().setExclusionReason("test exclusion");
        projectsVersionsStore.createOrUpdate(versionData);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> projectsServiceImpl.resolveAliasesAndCheckVersionExists("examples.metadata", "test", "2.3.1"));
    }

    @Test
    public void canCheckExistsForExistingProject()
    {
        projectsServiceImpl.checkExists("examples.metadata", "test");
    }

    @Test
    public void checkExistsThrowsForNonExistentProject()
    {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> projectsServiceImpl.checkExists("nonexistent.group", "nonexistent-artifact"));
    }

    @Test
    public void getDependenciesThrowsWhenTransitiveReportInvalid()
    {
        StoreProjectVersionData project = projectsServiceImpl.find("examples.metadata", "test", "2.3.1").get();
        project.setTransitiveDependenciesReport(new VersionDependencyReport(Collections.emptyList(), false));
        projectsVersionsStore.createOrUpdate(project);

        List<ProjectVersion> pvs = Arrays.asList(new ProjectVersion("examples.metadata", "test", "2.3.1"));
        Assertions.assertThrows(IllegalStateException.class,
                () -> projectsServiceImpl.getDependencies(pvs, true));
    }

    @Test
    public void getProjectDependencyReportThrowsWhenTransitiveReportInvalid()
    {
        StoreProjectVersionData project = projectsServiceImpl.find("examples.metadata", "test", "2.3.1").get();
        project.setTransitiveDependenciesReport(new VersionDependencyReport(Collections.emptyList(), false));
        projectsVersionsStore.createOrUpdate(project);

        List<ProjectVersion> pvs = Arrays.asList(new ProjectVersion("examples.metadata", "test", "2.3.1"));
        Assertions.assertThrows(IllegalStateException.class,
                () -> projectsServiceImpl.getProjectDependencyReport(pvs));
    }

    @Test
    public void getDependenciesThrowsForNotFoundProject()
    {
        List<ProjectVersion> pvs = Arrays.asList(new ProjectVersion("nonexistent.group", "nonexistent", "1.0.0"));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> projectsServiceImpl.getDependencies(pvs, false));
    }

    @Test
    public void getDependenciesThrowsForExcludedProject()
    {
        StoreProjectVersionData versionData = projectsServiceImpl.find("examples.metadata", "test", "2.3.1").get();
        versionData.getVersionData().setExcluded(true);
        versionData.getVersionData().setExclusionReason("test exclusion");
        projectsVersionsStore.createOrUpdate(versionData);

        List<ProjectVersion> pvs = Arrays.asList(new ProjectVersion("examples.metadata", "test", "2.3.1"));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> projectsServiceImpl.getDependencies(pvs, false));
    }
}
