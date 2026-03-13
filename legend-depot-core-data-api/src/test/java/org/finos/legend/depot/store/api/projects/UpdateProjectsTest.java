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

package org.finos.legend.depot.store.api.projects;

import org.finos.legend.depot.store.model.projects.StoreProjectData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UpdateProjectsTest
{
    private static class TestUpdateProjects implements UpdateProjects
    {
        private final List<StoreProjectData> projects = new ArrayList<>();

        @Override
        public StoreProjectData createOrUpdate(StoreProjectData projectData)
        {
            projects.removeIf(p -> p.getGroupId().equals(projectData.getGroupId()) &&
                                   p.getArtifactId().equals(projectData.getArtifactId()));
            projects.add(projectData);
            return projectData;
        }

        @Override
        public long delete(String groupId, String artifactId)
        {
            long count = projects.stream()
                .filter(p -> p.getGroupId().equals(groupId) && p.getArtifactId().equals(artifactId))
                .count();
            projects.removeIf(p -> p.getGroupId().equals(groupId) && p.getArtifactId().equals(artifactId));
            return count;
        }

        @Override
        public List<StoreProjectData> getAll()
        {
            return new ArrayList<>(projects);
        }

        @Override
        public Optional<StoreProjectData> find(String groupId, String artifactId)
        {
            return projects.stream()
                .filter(p -> p.getGroupId().equals(groupId) && p.getArtifactId().equals(artifactId))
                .findFirst();
        }

        @Override
        public List<StoreProjectData> findByProjectId(String projectId)
        {
            return projects.stream()
                .filter(p -> p.getProjectId().equals(projectId))
                .collect(java.util.stream.Collectors.toList());
        }
    }

    @Test
    public void canCreateOrUpdateProject()
    {
        UpdateProjects updateProjects = new TestUpdateProjects();
        StoreProjectData inputProject = new StoreProjectData("PROD-123", "org.example", "my-artifact", "master", "1.0.0");

        StoreProjectData result = updateProjects.createOrUpdate(inputProject);

        assertNotNull(result);
        assertEquals("PROD-123", result.getProjectId());
        assertEquals("org.example", result.getGroupId());
        assertEquals("my-artifact", result.getArtifactId());
    }

    @Test
    public void canDeleteProject()
    {
        UpdateProjects updateProjects = new TestUpdateProjects();
        StoreProjectData project = new StoreProjectData("PROD-123", "org.example", "my-artifact", "master", "1.0.0");
        updateProjects.createOrUpdate(project);

        long result = updateProjects.delete("org.example", "my-artifact");

        assertEquals(1L, result);
    }
}
