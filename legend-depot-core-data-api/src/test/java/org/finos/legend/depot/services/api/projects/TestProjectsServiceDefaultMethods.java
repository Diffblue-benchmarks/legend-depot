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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class TestProjectsServiceDefaultMethods
{
    private final List<String> capturedGetVersionsArgs = new java.util.ArrayList<>();
    private final List<Object> capturedGetDependenciesArgs = new java.util.ArrayList<>();
    private final List<List<ProjectVersion>> capturedGetDependencyReportArgs = new java.util.ArrayList<>();
    private final List<Boolean> capturedGetDependantProjectsLatestOnly = new java.util.ArrayList<>();

    private final ProjectsService service = new ProjectsService()
    {
        @Override
        public List<StoreProjectData> getAllProjectCoordinates()
        {
            return Collections.emptyList();
        }

        @Override
        public List<StoreProjectVersionData> findByUpdatedDate(long updatedFrom, long updatedTo)
        {
            return Collections.emptyList();
        }

        @Override
        public List<String> getVersions(String groupId, String artifactId, boolean includeSnapshots)
        {
            capturedGetVersionsArgs.add(groupId);
            capturedGetVersionsArgs.add(artifactId);
            capturedGetVersionsArgs.add(String.valueOf(includeSnapshots));
            return Arrays.asList("1.0.0", "2.0.0");
        }

        @Override
        public List<StoreProjectData> findByProjectId(String projectId)
        {
            return Collections.emptyList();
        }

        @Override
        public List<StoreProjectVersionData> find(String groupId, String artifactId)
        {
            return Collections.emptyList();
        }

        @Override
        public List<StoreProjectVersionData> findVersion(Boolean excluded)
        {
            return Collections.emptyList();
        }

        @Override
        public List<StoreProjectVersionData> findSnapshotVersions(String groupId, String artifactId)
        {
            return Collections.emptyList();
        }

        @Override
        public Optional<StoreProjectVersionData> find(String groupId, String artifactId, String versionId)
        {
            return Optional.empty();
        }

        @Override
        public String resolveAliasesAndCheckVersionExists(String groupId, String artifactId, String versionId)
        {
            return versionId;
        }

        @Override
        public Optional<StoreProjectData> findCoordinates(String groupId, String artifactId)
        {
            return Optional.empty();
        }

        @Override
        public Set<ProjectVersion> getDependencies(List<ProjectVersion> projectVersions, boolean transitive)
        {
            capturedGetDependenciesArgs.add(projectVersions);
            capturedGetDependenciesArgs.add(transitive);
            return new HashSet<>(projectVersions);
        }

        @Override
        public ProjectDependencyReport getProjectDependencyReport(List<ProjectVersion> projectVersions)
        {
            capturedGetDependencyReportArgs.add(projectVersions);
            return new ProjectDependencyReport();
        }

        @Override
        public List<ProjectDependencyWithPlatformVersions> getDependantProjects(String groupId, String artifactId, String versionId, boolean latestOnly)
        {
            capturedGetDependantProjectsLatestOnly.add(latestOnly);
            return Collections.emptyList();
        }

        @Override
        public void checkExists(String groupId, String artifactId) throws IllegalArgumentException
        {
        }
    };

    @Test
    public void testGetVersionsDefaultDelegatesToOverloadWithFalse()
    {
        List<String> result = service.getVersions("org.finos", "test-artifact");

        Assertions.assertEquals(Arrays.asList("1.0.0", "2.0.0"), result);
        Assertions.assertEquals("org.finos", capturedGetVersionsArgs.get(0));
        Assertions.assertEquals("test-artifact", capturedGetVersionsArgs.get(1));
        Assertions.assertEquals("false", capturedGetVersionsArgs.get(2));
    }

    @Test
    public void testGetDependenciesDefaultDelegatesToListOverload()
    {
        Set<ProjectVersion> result = service.getDependencies("org.finos", "test-artifact", "1.0.0", true);

        Assertions.assertEquals(1, result.size());
        ProjectVersion captured = result.iterator().next();
        Assertions.assertEquals("org.finos", captured.getGroupId());
        Assertions.assertEquals("test-artifact", captured.getArtifactId());
        Assertions.assertEquals("1.0.0", captured.getVersionId());
        Assertions.assertEquals(true, capturedGetDependenciesArgs.get(1));
    }

    @Test
    public void testGetProjectDependencyReportDefaultDelegatesToListOverload()
    {
        ProjectDependencyReport result = service.getProjectDependencyReport("org.finos", "test-artifact", "1.0.0");

        Assertions.assertNotNull(result);
        List<ProjectVersion> capturedVersions = capturedGetDependencyReportArgs.get(0);
        Assertions.assertEquals(1, capturedVersions.size());
        Assertions.assertEquals("org.finos", capturedVersions.get(0).getGroupId());
        Assertions.assertEquals("test-artifact", capturedVersions.get(0).getArtifactId());
        Assertions.assertEquals("1.0.0", capturedVersions.get(0).getVersionId());
    }

    @Test
    public void testGetDependantProjectsDefaultDelegatesToOverloadWithFalse()
    {
        List<ProjectDependencyWithPlatformVersions> result = service.getDependantProjects("org.finos", "test-artifact", "1.0.0");

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
        Assertions.assertEquals(false, capturedGetDependantProjectsLatestOnly.get(0));
    }
}
