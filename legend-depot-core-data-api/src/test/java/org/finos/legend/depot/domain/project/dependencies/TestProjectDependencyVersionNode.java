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

package org.finos.legend.depot.domain.project.dependencies;

import org.finos.legend.depot.domain.project.ProjectVersion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestProjectDependencyVersionNode
{
    @Test
    public void canCreateNodeAndAccessFields()
    {
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode("org.finos", "legend-test", "1.0.0");

        Assertions.assertEquals("org.finos", node.getGroupId());
        Assertions.assertEquals("legend-test", node.getArtifactId());
        Assertions.assertEquals("1.0.0", node.getVersionId());
        Assertions.assertNotNull(node.getForwardEdges());
        Assertions.assertTrue(node.getForwardEdges().isEmpty());
        Assertions.assertNotNull(node.getBackEdges());
        Assertions.assertTrue(node.getBackEdges().isEmpty());
        Assertions.assertNull(node.getProjectId());
    }

    @Test
    public void canBuildFromProjectVersion()
    {
        ProjectVersion pv = new ProjectVersion("org.finos", "legend-depot", "2.0.0");
        ProjectDependencyVersionNode node = ProjectDependencyVersionNode.buildFromProjectVersion(pv);

        Assertions.assertEquals("org.finos", node.getGroupId());
        Assertions.assertEquals("legend-depot", node.getArtifactId());
        Assertions.assertEquals("2.0.0", node.getVersionId());
        Assertions.assertNotNull(node.getForwardEdges());
        Assertions.assertNotNull(node.getBackEdges());
    }

    @Test
    public void canSetAndGetProjectId()
    {
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode("org.finos", "legend-test", "1.0.0");
        Assertions.assertNull(node.getProjectId());

        node.setProjectId("PROD-123");
        Assertions.assertEquals("PROD-123", node.getProjectId());
    }

    @Test
    public void canGetGav()
    {
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode("org.finos", "legend-test", "1.0.0");
        Assertions.assertEquals("org.finos:legend-test:1.0.0", node.getGav());
    }

    @Test
    public void canGetCoordinates()
    {
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode("org.finos", "legend-test", "1.0.0");
        Assertions.assertEquals("org.finos:legend-test", node.getCoordinates());
    }

    @Test
    public void canGetId()
    {
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode("org.finos", "legend-test", "1.0.0");
        Assertions.assertEquals("org.finos:legend-test:1.0.0", node.getId());
    }
}
