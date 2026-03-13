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

package org.finos.legend.depot.domain.project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ProjectVersionTest
{
    @Test
    public void canCreateProjectVersionWithDefaultConstructor()
    {
        ProjectVersion projectVersion = new ProjectVersion();
        Assertions.assertNotNull(projectVersion);
    }

    @Test
    public void canCreateProjectVersionWithParameters()
    {
        String groupId = "org.finos.legend";
        String artifactId = "legend-depot";
        String versionId = "1.0.0";

        ProjectVersion projectVersion = new ProjectVersion(groupId, artifactId, versionId);

        Assertions.assertNotNull(projectVersion);
        Assertions.assertEquals(groupId, projectVersion.getGroupId());
        Assertions.assertEquals(artifactId, projectVersion.getArtifactId());
        Assertions.assertEquals(versionId, projectVersion.getVersionId());
    }

    @Test
    public void testEquals()
    {
        ProjectVersion projectVersion1 = new ProjectVersion("org.finos.legend", "legend-depot", "1.0.0");
        ProjectVersion projectVersion2 = new ProjectVersion("org.finos.legend", "legend-depot", "1.0.0");
        ProjectVersion projectVersion3 = new ProjectVersion("org.finos.legend", "legend-depot", "2.0.0");

        Assertions.assertEquals(projectVersion1, projectVersion2);
        Assertions.assertNotEquals(projectVersion1, projectVersion3);
        Assertions.assertNotEquals(projectVersion1, null);
        Assertions.assertNotEquals(projectVersion1, new Object());
    }

    @Test
    public void testHashCode()
    {
        ProjectVersion projectVersion1 = new ProjectVersion("org.finos.legend", "legend-depot", "1.0.0");
        ProjectVersion projectVersion2 = new ProjectVersion("org.finos.legend", "legend-depot", "1.0.0");
        ProjectVersion projectVersion3 = new ProjectVersion("org.finos.legend", "legend-depot", "2.0.0");

        Assertions.assertEquals(projectVersion1.hashCode(), projectVersion2.hashCode());
        Assertions.assertNotEquals(projectVersion1.hashCode(), projectVersion3.hashCode());
    }

    @Test
    public void canGetGav()
    {
        String groupId = "org.finos.legend";
        String artifactId = "legend-depot";
        String versionId = "1.0.0";
        ProjectVersion projectVersion = new ProjectVersion(groupId, artifactId, versionId);

        String gav = projectVersion.getGav();

        Assertions.assertEquals("org.finos.legend:legend-depot:1.0.0", gav);
    }
}
