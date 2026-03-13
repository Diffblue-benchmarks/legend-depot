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

package org.finos.legend.depot.services.api.projects;

import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.domain.project.dependencies.ProjectDependencyReport;
import org.finos.legend.depot.domain.project.dependencies.ProjectDependencyWithPlatformVersions;
import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProjectsServiceTest
{
    @Test
    public void canGetVersionsWithDefaultMethod()
    {
        List<String> expectedVersions = Arrays.asList("1.0.0", "2.0.0");
        TestProjectsService testService = new TestProjectsService();
        testService.versionsToReturn = expectedVersions;

        List<String> versions = testService.getVersions("org.example", "test-artifact");

        Assertions.assertNotNull(versions);
        Assertions.assertEquals(expectedVersions, versions);
        Assertions.assertFalse(testService.includeSnapshotsFlag);
    }

    @Test
    public void canGetDependenciesWithDefaultMethod()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        boolean transitive = true;

        Set<ProjectVersion> expectedDependencies = new HashSet<>();
        expectedDependencies.add(new ProjectVersion("org.dep", "dep-artifact", "1.0.0"));

        TestProjectsService testService = new TestProjectsService();
        testService.dependenciesToReturn = expectedDependencies;

        Set<ProjectVersion> dependencies = testService.getDependencies(groupId, artifactId, versionId, transitive);

        Assertions.assertNotNull(dependencies);
        Assertions.assertEquals(expectedDependencies, dependencies);
        Assertions.assertEquals(1, testService.projectVersionsReceived.size());
        Assertions.assertEquals(transitive, testService.transitiveFlag);
    }

    @Test
    public void canGetDependenciesNonTransitiveWithDefaultMethod()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        boolean transitive = false;

        Set<ProjectVersion> expectedDependencies = new HashSet<>();
        expectedDependencies.add(new ProjectVersion("org.dep", "dep-artifact", "1.0.0"));

        TestProjectsService testService = new TestProjectsService();
        testService.dependenciesToReturn = expectedDependencies;

        Set<ProjectVersion> dependencies = testService.getDependencies(groupId, artifactId, versionId, transitive);

        Assertions.assertNotNull(dependencies);
        Assertions.assertEquals(expectedDependencies, dependencies);
        Assertions.assertEquals(1, testService.projectVersionsReceived.size());
        Assertions.assertEquals(transitive, testService.transitiveFlag);
    }

    @Test
    public void canGetProjectDependencyReportWithDefaultMethod()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";

        ProjectDependencyReport expectedReport = new ProjectDependencyReport();

        TestProjectsService testService = new TestProjectsService();
        testService.reportToReturn = expectedReport;

        ProjectDependencyReport report = testService.getProjectDependencyReport(groupId, artifactId, versionId);

        Assertions.assertNotNull(report);
        Assertions.assertEquals(expectedReport, report);
        Assertions.assertEquals(1, testService.reportProjectVersionsReceived.size());
    }

    @Test
    public void canGetDependantProjectsWithDefaultMethod()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";

        List<ProjectDependencyWithPlatformVersions> expectedDependants = new ArrayList<>();

        TestProjectsService testService = new TestProjectsService();
        testService.dependantsToReturn = expectedDependants;

        List<ProjectDependencyWithPlatformVersions> dependants = testService.getDependantProjects(groupId, artifactId, versionId);

        Assertions.assertNotNull(dependants);
        Assertions.assertEquals(expectedDependants, dependants);
        Assertions.assertFalse(testService.latestOnlyFlag);
    }

    private static class TestProjectsService implements ProjectsService
    {
        List<String> versionsToReturn = new ArrayList<>();
        boolean includeSnapshotsFlag;
        Set<ProjectVersion> dependenciesToReturn = new HashSet<>();
        List<ProjectVersion> projectVersionsReceived = new ArrayList<>();
        boolean transitiveFlag;
        ProjectDependencyReport reportToReturn;
        List<ProjectVersion> reportProjectVersionsReceived = new ArrayList<>();
        List<ProjectDependencyWithPlatformVersions> dependantsToReturn = new ArrayList<>();
        boolean latestOnlyFlag;

        @Override
        public List<StoreProjectData> getAllProjectCoordinates()
        {
            return new ArrayList<>();
        }

        @Override
        public List<StoreProjectVersionData> findByUpdatedDate(long updatedFrom, long updatedTo)
        {
            return new ArrayList<>();
        }

        @Override
        public List<String> getVersions(String groupId, String artifactId, boolean includeSnapshots)
        {
            this.includeSnapshotsFlag = includeSnapshots;
            return versionsToReturn;
        }

        @Override
        public List<StoreProjectData> findByProjectId(String projectId)
        {
            return new ArrayList<>();
        }

        @Override
        public List<StoreProjectVersionData> find(String groupId, String artifactId)
        {
            return new ArrayList<>();
        }

        @Override
        public List<StoreProjectVersionData> findVersion(Boolean excluded)
        {
            return new ArrayList<>();
        }

        @Override
        public List<StoreProjectVersionData> findSnapshotVersions(String groupId, String artifactId)
        {
            return new ArrayList<>();
        }

        @Override
        public Optional<StoreProjectVersionData> find(String groupId, String artifactId, String versionId)
        {
            return Optional.empty();
        }

        @Override
        public String resolveAliasesAndCheckVersionExists(String groupId, String artifactId, String versionId)
        {
            return null;
        }

        @Override
        public Optional<StoreProjectData> findCoordinates(String groupId, String artifactId)
        {
            return Optional.empty();
        }

        @Override
        public Set<ProjectVersion> getDependencies(List<ProjectVersion> projectVersions, boolean transitive)
        {
            this.projectVersionsReceived.addAll(projectVersions);
            this.transitiveFlag = transitive;
            return dependenciesToReturn;
        }

        @Override
        public ProjectDependencyReport getProjectDependencyReport(List<ProjectVersion> projectVersions)
        {
            this.reportProjectVersionsReceived.addAll(projectVersions);
            return reportToReturn;
        }

        @Override
        public List<ProjectDependencyWithPlatformVersions> getDependantProjects(String groupId, String artifactId, String versionId, boolean latestOnly)
        {
            this.latestOnlyFlag = latestOnly;
            return dependantsToReturn;
        }

        @Override
        public void checkExists(String groupId, String artifactId) throws IllegalArgumentException
        {
        }
    }
}
