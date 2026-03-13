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

package org.finos.legend.depot.server.resources.versions;

import org.finos.legend.depot.domain.project.ProjectVersionData;
import org.finos.legend.depot.server.resources.versions.ProjectsVersionsResource.ProjectVersionDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class ProjectsVersionsResourceTest
{
    protected ProjectsVersionsResource resource;

    @BeforeEach
    public void setUp()
    {
        resource = new ProjectsVersionsResource(null);
    }

    @Test
    public void canConstructProjectVersionDTOWithNoArgs()
    {
        ProjectVersionDTO dto = resource.new ProjectVersionDTO();

        Assertions.assertNotNull(dto);
    }

    @Test
    public void canConstructProjectVersionDTOWithParameters()
    {
        String groupId = "org.example";
        String artifactId = "artifact1";
        String versionId = "1.0.0";
        ProjectVersionData versionData = new ProjectVersionData();

        ProjectVersionDTO dto = resource.new ProjectVersionDTO(groupId, artifactId, versionId, versionData);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(groupId, dto.getGroupId());
        Assertions.assertEquals(artifactId, dto.getArtifactId());
        Assertions.assertEquals(versionId, dto.getVersionId());
        Assertions.assertEquals(versionData, dto.getVersionData());
    }

    @Test
    public void canGetVersionData()
    {
        ProjectVersionData versionData = new ProjectVersionData();
        ProjectVersionDTO dto = resource.new ProjectVersionDTO("org.example", "artifact1", "1.0.0", versionData);

        ProjectVersionData result = dto.getVersionData();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(versionData, result);
    }

    @Test
    public void canSetVersionData()
    {
        ProjectVersionDTO dto = resource.new ProjectVersionDTO();
        ProjectVersionData versionData = new ProjectVersionData();

        dto.setVersionData(versionData);

        Assertions.assertEquals(versionData, dto.getVersionData());
    }

    @Test
    public void canTestEquals()
    {
        ProjectVersionData versionData = new ProjectVersionData();
        ProjectVersionDTO dto1 = resource.new ProjectVersionDTO("org.example", "artifact1", "1.0.0", versionData);
        ProjectVersionDTO dto2 = resource.new ProjectVersionDTO("org.example", "artifact1", "1.0.0", versionData);
        ProjectVersionDTO dto3 = resource.new ProjectVersionDTO("org.example", "artifact2", "1.0.0", versionData);

        Assertions.assertTrue(dto1.equals(dto2));
        Assertions.assertFalse(dto1.equals(dto3));
    }

    @Test
    public void canTestHashCode()
    {
        ProjectVersionData versionData = new ProjectVersionData();
        ProjectVersionDTO dto1 = resource.new ProjectVersionDTO("org.example", "artifact1", "1.0.0", versionData);
        ProjectVersionDTO dto2 = resource.new ProjectVersionDTO("org.example", "artifact1", "1.0.0", versionData);

        int hashCode1 = dto1.hashCode();
        int hashCode2 = dto2.hashCode();

        Assertions.assertEquals(hashCode1, hashCode2);
    }
}
