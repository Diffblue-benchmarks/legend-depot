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
import org.finos.legend.depot.domain.project.dependencies.ProjectDependencyGraph;
import org.finos.legend.depot.domain.project.dependencies.ProjectDependencyReport;
import org.finos.legend.depot.domain.project.dependencies.VersionDependencyReport;
import org.finos.legend.depot.services.TestBaseServices;
import org.finos.legend.depot.services.api.dependencies.DependencyOverride;
import org.finos.legend.depot.services.api.metrics.query.QueryMetricsRegistry;
import org.finos.legend.depot.services.api.notifications.queue.Queue;
import org.finos.legend.depot.services.api.projects.configuration.ProjectsConfiguration;
import org.finos.legend.depot.services.dependencies.DependencyUtil;
import org.finos.legend.depot.services.dependencies.ProjectDependencyGraphWalkerContext;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.finos.legend.depot.store.mongo.notifications.queue.NotificationsQueueMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.mock;

public class TestProjectsServiceImpl extends TestBaseServices
{
    private final QueryMetricsRegistry metrics = mock(QueryMetricsRegistry.class);
    private final Queue queue = new NotificationsQueueMongo(mongoProvider);
    private final DependencyOverride dependencyOverride = new DependencyUtil();
    private ProjectsServiceImpl projectsService;

    @BeforeEach
    public void setUpData()
    {
        super.setUpData();
        projectsService = new ProjectsServiceImpl(projectsVersionsStore, projectsStore, metrics, queue, new ProjectsConfiguration("master"), dependencyOverride);
    }

    @Test
    public void testConstructorWithAllParameters()
    {
        ProjectsServiceImpl service = new ProjectsServiceImpl(projectsVersionsStore, projectsStore, metrics, queue, new ProjectsConfiguration("master"), dependencyOverride);
        Assertions.assertNotNull(service);
        List<StoreProjectData> projects = service.getAllProjectCoordinates();
        Assertions.assertNotNull(projects);
    }

    @Test
    public void testConstructorWithoutDependencyOverride()
    {
        ProjectsServiceImpl service = new ProjectsServiceImpl(projectsVersionsStore, projectsStore, metrics, queue, new ProjectsConfiguration("master"));
        Assertions.assertNotNull(service);
        List<StoreProjectData> projects = service.getAllProjectCoordinates();
        Assertions.assertNotNull(projects);
    }

    @Test
    public void canGetAllProjectCoordinates()
    {
        List<StoreProjectData> projects = projectsService.getAllProjectCoordinates();
        Assertions.assertNotNull(projects);
        Assertions.assertFalse(projects.isEmpty());
        Assertions.assertEquals(3, projects.size());
    }

    @Test
    public void canGetVersions()
    {
        List<String> versions = projectsService.getVersions("examples.metadata", "test", false);
        Assertions.assertNotNull(versions);
        Assertions.assertEquals(2, versions.size());
        Assertions.assertFalse(versions.contains("master-SNAPSHOT"));
    }

    @Test
    public void canGetVersionsIncludingSnapshots()
    {
        List<String> versions = projectsService.getVersions("examples.metadata", "test", true);
        Assertions.assertNotNull(versions);
        Assertions.assertEquals(3, versions.size());
        Assertions.assertTrue(versions.contains("master-SNAPSHOT"));
    }

    @Test
    public void canFindByProjectId()
    {
        List<StoreProjectData> projects = projectsService.findByProjectId("PROD-A");
        Assertions.assertNotNull(projects);
        Assertions.assertFalse(projects.isEmpty());
        Assertions.assertEquals("examples.metadata", projects.get(0).getGroupId());
    }

    @Test
    public void canFindByUpdatedDate()
    {
        long fromDate = 0L;
        long toDate = System.currentTimeMillis();
        List<StoreProjectVersionData> projects = projectsService.findByUpdatedDate(fromDate, toDate);
        Assertions.assertNotNull(projects);
    }

    @Test
    public void canFindByGroupIdAndArtifactId()
    {
        List<StoreProjectVersionData> projects = projectsService.find("examples.metadata", "test");
        Assertions.assertNotNull(projects);
        Assertions.assertFalse(projects.isEmpty());
    }

    @Test
    public void canFindVersionWithExcludedFlag()
    {
        List<StoreProjectVersionData> excludedProjects = projectsService.findVersion(true);
        Assertions.assertNotNull(excludedProjects);

        List<StoreProjectVersionData> nonExcludedProjects = projectsService.findVersion(false);
        Assertions.assertNotNull(nonExcludedProjects);
    }

    @Test
    public void canFindSnapshotVersions()
    {
        List<StoreProjectVersionData> snapshots = projectsService.findSnapshotVersions("examples.metadata", "test");
        Assertions.assertNotNull(snapshots);
        Assertions.assertFalse(snapshots.isEmpty());
        Assertions.assertTrue(snapshots.stream().allMatch(v -> v.getVersionId().endsWith("-SNAPSHOT")));
    }

    @Test
    public void canFindCoordinates()
    {
        Optional<StoreProjectData> project = projectsService.findCoordinates("examples.metadata", "test");
        Assertions.assertTrue(project.isPresent());
        Assertions.assertEquals("examples.metadata", project.get().getGroupId());
        Assertions.assertEquals("test", project.get().getArtifactId());
    }

    @Test
    public void canFindSpecificVersion()
    {
        Optional<StoreProjectVersionData> project = projectsService.find("examples.metadata", "test", "2.3.1");
        Assertions.assertTrue(project.isPresent());
        Assertions.assertEquals("2.3.1", project.get().getVersionId());
    }

    @Test
    public void canFindLatestVersion()
    {
        Optional<StoreProjectVersionData> project = projectsService.find("examples.metadata", "test", "latest");
        Assertions.assertTrue(project.isPresent());
        Assertions.assertEquals("2.3.1", project.get().getVersionId());
    }

    @Test
    public void canFindHeadVersion()
    {
        Optional<StoreProjectVersionData> project = projectsService.find("examples.metadata", "test", "head");
        Assertions.assertTrue(project.isPresent());
        Assertions.assertTrue(project.get().getVersionId().contains("SNAPSHOT"));
    }

    @Test
    public void canResolveAliasesAndCheckVersionExists()
    {
        String version = projectsService.resolveAliasesAndCheckVersionExists("examples.metadata", "test", "2.3.1");
        Assertions.assertEquals("2.3.1", version);
    }

    @Test
    public void resolveAliasesThrowsExceptionForNonExistentVersion()
    {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> projectsService.resolveAliasesAndCheckVersionExists("examples.metadata", "test", "999.0.0"));
    }

    @Test
    public void resolveAliasesThrowsExceptionForExcludedVersion()
    {
        StoreProjectVersionData versionData = new StoreProjectVersionData("examples.metadata", "test", "5.0.0");
        versionData.getVersionData().setExcluded(true);
        versionData.getVersionData().setExclusionReason("test exclusion");
        projectsVersionsStore.createOrUpdate(versionData);

        Assertions.assertThrows(IllegalArgumentException.class,
            () -> projectsService.resolveAliasesAndCheckVersionExists("examples.metadata", "test", "5.0.0"));
    }

    @Test
    public void resolveAliasesRestoresEvictedVersion()
    {
        StoreProjectVersionData versionData = new StoreProjectVersionData("examples.metadata", "test", "4.0.0");
        versionData.setEvicted(true);
        projectsVersionsStore.createOrUpdate(versionData);

        Assertions.assertThrows(IllegalStateException.class,
            () -> projectsService.resolveAliasesAndCheckVersionExists("examples.metadata", "test", "4.0.0"));
    }

    @Test
    public void canCheckExists()
    {
        Assertions.assertDoesNotThrow(() -> projectsService.checkExists("examples.metadata", "test"));
    }

    @Test
    public void checkExistsThrowsExceptionForNonExistentProject()
    {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> projectsService.checkExists("non.existent", "project"));
    }

    @Test
    public void canGetDependencies()
    {
        StoreProjectVersionData project = projectsVersionsStore.find("examples.metadata", "test-dependencies", "1.0.0").get();
        ProjectVersion dependency = new ProjectVersion("example.services.test", "test", "1.0.0");
        project.getVersionData().addDependency(dependency);
        project.setTransitiveDependenciesReport(new VersionDependencyReport(Collections.singletonList(dependency), true));
        projectsVersionsStore.createOrUpdate(project);
        projectsVersionsStore.createOrUpdate(new StoreProjectVersionData("example.services.test", "test", "1.0.0"));

        List<ProjectVersion> projectVersions = Collections.singletonList(new ProjectVersion("examples.metadata", "test-dependencies", "1.0.0"));
        Set<ProjectVersion> dependencies = projectsService.getDependencies(projectVersions, false);

        Assertions.assertNotNull(dependencies);
        Assertions.assertFalse(dependencies.isEmpty());
    }

    @Test
    public void canGetTransitiveDependencies()
    {
        StoreProjectVersionData project1 = projectsVersionsStore.find("examples.metadata", "test-dependencies", "1.0.0").get();
        ProjectVersion dependency1 = new ProjectVersion("example.services.test", "test", "1.0.0");
        project1.getVersionData().addDependency(dependency1);

        StoreProjectVersionData project2 = new StoreProjectVersionData("example.services.test", "test", "1.0.0");
        ProjectVersion dependency2 = new ProjectVersion("example.services.test", "test-dep", "1.0.0");
        project2.getVersionData().addDependency(dependency2);
        project2.setTransitiveDependenciesReport(new VersionDependencyReport(Collections.singletonList(dependency2), true));

        project1.setTransitiveDependenciesReport(new VersionDependencyReport(Arrays.asList(dependency1, dependency2), true));

        projectsVersionsStore.createOrUpdate(project1);
        projectsVersionsStore.createOrUpdate(project2);
        projectsVersionsStore.createOrUpdate(new StoreProjectVersionData("example.services.test", "test-dep", "1.0.0"));

        List<ProjectVersion> projectVersions = Collections.singletonList(new ProjectVersion("examples.metadata", "test-dependencies", "1.0.0"));
        Set<ProjectVersion> dependencies = projectsService.getDependencies(projectVersions, true);

        Assertions.assertNotNull(dependencies);
        Assertions.assertFalse(dependencies.isEmpty());
    }

    @Test
    public void getDependenciesThrowsExceptionForInvalidTransitiveDependencies()
    {
        StoreProjectVersionData project = projectsVersionsStore.find("examples.metadata", "test-dependencies", "1.0.0").get();
        ProjectVersion dependency = new ProjectVersion("example.services.test", "test", "1.0.0");
        project.getVersionData().addDependency(dependency);
        project.setTransitiveDependenciesReport(new VersionDependencyReport(Collections.emptyList(), false));
        projectsVersionsStore.createOrUpdate(project);
        projectsVersionsStore.createOrUpdate(new StoreProjectVersionData("example.services.test", "test", "1.0.0"));

        List<ProjectVersion> projectVersions = Collections.singletonList(new ProjectVersion("examples.metadata", "test-dependencies", "1.0.0"));

        Assertions.assertThrows(IllegalStateException.class,
            () -> projectsService.getDependencies(projectVersions, true));
    }

    @Test
    public void canBuildDependencyGraph()
    {
        StoreProjectVersionData project1 = projectsVersionsStore.find("examples.metadata", "test", "2.3.1").get();
        ProjectVersion dependency1 = new ProjectVersion("examples.metadata", "test-dependencies", "1.0.0");
        project1.getVersionData().addDependency(dependency1);
        projectsVersionsStore.createOrUpdate(project1);

        ProjectDependencyGraph graph = new ProjectDependencyGraph();
        ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();
        ProjectVersion parent = new ProjectVersion("examples.metadata", "test", "2.3.1");

        projectsService.buildDependencyGraph(graph, null, Collections.singletonList(parent), context);

        Assertions.assertFalse(graph.getNodes().isEmpty());
    }

    @Test
    public void canGetProjectDependencyReport()
    {
        StoreProjectVersionData project1 = projectsVersionsStore.find("examples.metadata", "test", "2.3.1").get();
        ProjectVersion dependency1 = new ProjectVersion("examples.metadata", "test-dependencies", "1.0.0");
        project1.getVersionData().addDependency(dependency1);
        project1.setTransitiveDependenciesReport(new VersionDependencyReport(Collections.singletonList(dependency1), true));
        projectsVersionsStore.createOrUpdate(project1);

        StoreProjectVersionData project2 = projectsVersionsStore.find("examples.metadata", "test-dependencies", "1.0.0").get();
        project2.setTransitiveDependenciesReport(new VersionDependencyReport(Collections.emptyList(), true));
        projectsVersionsStore.createOrUpdate(project2);

        List<ProjectVersion> projectVersions = Collections.singletonList(new ProjectVersion("examples.metadata", "test", "2.3.1"));
        ProjectDependencyReport report = projectsService.getProjectDependencyReport(projectVersions);

        Assertions.assertNotNull(report);
        Assertions.assertNotNull(report.getGraph());
    }

    @Test
    public void canBuildReportFromGraph()
    {
        StoreProjectVersionData project1 = projectsVersionsStore.find("examples.metadata", "test", "2.3.1").get();
        ProjectVersion dependency1 = new ProjectVersion("examples.metadata", "test-dependencies", "1.0.0");
        project1.getVersionData().addDependency(dependency1);
        project1.setTransitiveDependenciesReport(new VersionDependencyReport(Collections.singletonList(dependency1), true));
        projectsVersionsStore.createOrUpdate(project1);

        StoreProjectVersionData project2 = projectsVersionsStore.find("examples.metadata", "test-dependencies", "1.0.0").get();
        project2.setTransitiveDependenciesReport(new VersionDependencyReport(Collections.emptyList(), true));
        projectsVersionsStore.createOrUpdate(project2);

        ProjectDependencyGraph graph = new ProjectDependencyGraph();
        ProjectDependencyGraphWalkerContext context = new ProjectDependencyGraphWalkerContext();
        ProjectVersion parent = new ProjectVersion("examples.metadata", "test", "2.3.1");

        projectsService.buildDependencyGraph(graph, null, Collections.singletonList(parent), context);

        List<ProjectVersion> projectVersions = Collections.singletonList(new ProjectVersion("examples.metadata", "test", "2.3.1"));
        context.getProjectData("examples.metadata", "test", "2.3.1");

        ProjectDependencyReport report = projectsService.buildReportFromGraph(graph, context);

        Assertions.assertNotNull(report);
        Assertions.assertNotNull(report.getGraph());
    }

    @Test
    public void canGetDependantProjects()
    {
        StoreProjectVersionData project = projectsVersionsStore.find("examples.metadata", "test", "2.3.1").get();
        ProjectVersion dependency = new ProjectVersion("examples.metadata", "test-dependencies", "1.0.0");
        project.getVersionData().addDependency(dependency);
        projectsVersionsStore.createOrUpdate(project);

        List<org.finos.legend.depot.domain.project.dependencies.ProjectDependencyWithPlatformVersions> dependants =
            projectsService.getDependantProjects("examples.metadata", "test-dependencies", "1.0.0", false);

        Assertions.assertNotNull(dependants);
        Assertions.assertFalse(dependants.isEmpty());
    }

    @Test
    public void canGetDependantProjectsWithAllVersions()
    {
        StoreProjectVersionData project = projectsVersionsStore.find("examples.metadata", "test", "2.3.1").get();
        ProjectVersion dependency = new ProjectVersion("examples.metadata", "test-dependencies", "1.0.0");
        project.getVersionData().addDependency(dependency);
        projectsVersionsStore.createOrUpdate(project);

        List<org.finos.legend.depot.domain.project.dependencies.ProjectDependencyWithPlatformVersions> dependants =
            projectsService.getDependantProjects("examples.metadata", "test-dependencies", "ALL", false);

        Assertions.assertNotNull(dependants);
        Assertions.assertFalse(dependants.isEmpty());
    }

    @Test
    public void canGetDependantProjectsLatestOnly()
    {
        StoreProjectVersionData project1 = projectsVersionsStore.find("examples.metadata", "test", "2.3.1").get();
        ProjectVersion dependency = new ProjectVersion("examples.metadata", "test-dependencies", "1.0.0");
        project1.getVersionData().addDependency(dependency);
        projectsVersionsStore.createOrUpdate(project1);

        StoreProjectVersionData project2 = projectsVersionsStore.find("examples.metadata", "test", "2.2.0").get();
        project2.getVersionData().addDependency(dependency);
        projectsVersionsStore.createOrUpdate(project2);

        List<org.finos.legend.depot.domain.project.dependencies.ProjectDependencyWithPlatformVersions> dependants =
            projectsService.getDependantProjects("examples.metadata", "test-dependencies", "1.0.0", true);

        Assertions.assertNotNull(dependants);
    }
}
