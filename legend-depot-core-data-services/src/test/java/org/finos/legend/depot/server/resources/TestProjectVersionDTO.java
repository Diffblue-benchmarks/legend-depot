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

package org.finos.legend.depot.server.resources;

import org.finos.legend.depot.domain.project.ProjectVersionData;
import org.finos.legend.depot.server.resources.versions.ProjectsVersionsResource;
import org.finos.legend.depot.services.api.projects.ProjectsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

public class TestProjectVersionDTO
{
    private final ProjectsService projectsService = mock(ProjectsService.class);
    private final ProjectsVersionsResource resource = new ProjectsVersionsResource(projectsService);

    @Test
    public void canCreateDefaultProjectVersionDTO()
    {
        ProjectsVersionsResource.ProjectVersionDTO dto = resource.new ProjectVersionDTO();
        Assertions.assertNull(dto.getVersionData());
    }

    @Test
    public void canSetVersionData()
    {
        ProjectsVersionsResource.ProjectVersionDTO dto = resource.new ProjectVersionDTO();
        ProjectVersionData versionData = new ProjectVersionData(new ArrayList<>(), new ArrayList<>());
        dto.setVersionData(versionData);
        Assertions.assertNotNull(dto.getVersionData());
        Assertions.assertEquals(versionData, dto.getVersionData());
    }

    @Test
    public void testEqualsWithSameData()
    {
        ProjectVersionData versionData = new ProjectVersionData(new ArrayList<>(), new ArrayList<>());
        ProjectsVersionsResource.ProjectVersionDTO dto1 = resource.new ProjectVersionDTO("group", "artifact", "1.0.0", versionData);
        ProjectsVersionsResource.ProjectVersionDTO dto2 = resource.new ProjectVersionDTO("group", "artifact", "1.0.0", versionData);
        Assertions.assertEquals(dto1, dto2);
    }

    @Test
    public void testEqualsWithDifferentData()
    {
        ProjectVersionData versionData1 = new ProjectVersionData(new ArrayList<>(), new ArrayList<>());
        ProjectVersionData versionData2 = new ProjectVersionData(new ArrayList<>(), new ArrayList<>(), true, false);
        ProjectsVersionsResource.ProjectVersionDTO dto1 = resource.new ProjectVersionDTO("group", "artifact", "1.0.0", versionData1);
        ProjectsVersionsResource.ProjectVersionDTO dto2 = resource.new ProjectVersionDTO("group", "artifact", "1.0.0", versionData2);
        Assertions.assertNotEquals(dto1, dto2);
    }

    @Test
    public void testHashCodeConsistency()
    {
        ProjectVersionData versionData = new ProjectVersionData(new ArrayList<>(), new ArrayList<>());
        ProjectsVersionsResource.ProjectVersionDTO dto1 = resource.new ProjectVersionDTO("group", "artifact", "1.0.0", versionData);
        ProjectsVersionsResource.ProjectVersionDTO dto2 = resource.new ProjectVersionDTO("group", "artifact", "1.0.0", versionData);
        Assertions.assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
