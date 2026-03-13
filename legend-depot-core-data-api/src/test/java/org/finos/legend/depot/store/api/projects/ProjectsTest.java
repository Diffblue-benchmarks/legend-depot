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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProjectsTest
{
    @Test
    public void canGetAll()
    {
        StoreProjectData project1 = new StoreProjectData("proj1", "org.example", "artifact1", "master", "1.0.0");
        StoreProjectData project2 = new StoreProjectData("proj2", "org.example", "artifact2", "main", "2.0.0");
        List<StoreProjectData> expectedProjects = Arrays.asList(project1, project2);

        Projects projects = new Projects()
        {
            @Override
            public List<StoreProjectData> getAll()
            {
                return expectedProjects;
            }

            @Override
            public Optional<StoreProjectData> find(String groupId, String artifactId)
            {
                return Optional.empty();
            }

            @Override
            public List<StoreProjectData> findByProjectId(String projectId)
            {
                return Arrays.asList();
            }
        };

        List<StoreProjectData> result = projects.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedProjects, result);
    }

    @Test
    public void canFindProject()
    {
        StoreProjectData expectedProject = new StoreProjectData("proj1", "org.example", "artifact1", "master", "1.0.0");

        Projects projects = new Projects()
        {
            @Override
            public List<StoreProjectData> getAll()
            {
                return Arrays.asList();
            }

            @Override
            public Optional<StoreProjectData> find(String groupId, String artifactId)
            {
                if ("org.example".equals(groupId) && "artifact1".equals(artifactId))
                {
                    return Optional.of(expectedProject);
                }
                return Optional.empty();
            }

            @Override
            public List<StoreProjectData> findByProjectId(String projectId)
            {
                return Arrays.asList();
            }
        };

        Optional<StoreProjectData> result = projects.find("org.example", "artifact1");

        assertTrue(result.isPresent());
        assertEquals(expectedProject, result.get());
    }

    @Test
    public void canFindByProjectId()
    {
        StoreProjectData project1 = new StoreProjectData("proj1", "org.example", "artifact1", "master", "1.0.0");
        StoreProjectData project2 = new StoreProjectData("proj1", "org.example", "artifact2", "main", "2.0.0");
        List<StoreProjectData> expectedProjects = Arrays.asList(project1, project2);

        Projects projects = new Projects()
        {
            @Override
            public List<StoreProjectData> getAll()
            {
                return Arrays.asList();
            }

            @Override
            public Optional<StoreProjectData> find(String groupId, String artifactId)
            {
                return Optional.empty();
            }

            @Override
            public List<StoreProjectData> findByProjectId(String projectId)
            {
                if ("proj1".equals(projectId))
                {
                    return expectedProjects;
                }
                return Arrays.asList();
            }
        };

        List<StoreProjectData> result = projects.findByProjectId("proj1");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedProjects, result);
    }
}
