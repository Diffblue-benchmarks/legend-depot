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
    public void testConstructor()
    {
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode("org.finos", "artifact", "1.0.0");
        Assertions.assertEquals("org.finos", node.getGroupId());
        Assertions.assertEquals("artifact", node.getArtifactId());
        Assertions.assertEquals("1.0.0", node.getVersionId());
        Assertions.assertNotNull(node.getForwardEdges());
        Assertions.assertTrue(node.getForwardEdges().isEmpty());
        Assertions.assertNotNull(node.getBackEdges());
        Assertions.assertTrue(node.getBackEdges().isEmpty());
    }

    @Test
    public void testBuildFromProjectVersion()
    {
        ProjectVersion pv = new ProjectVersion("org.finos", "artifact", "2.0.0");
        ProjectDependencyVersionNode node = ProjectDependencyVersionNode.buildFromProjectVersion(pv);

        Assertions.assertEquals("org.finos", node.getGroupId());
        Assertions.assertEquals("artifact", node.getArtifactId());
        Assertions.assertEquals("2.0.0", node.getVersionId());
    }

    @Test
    public void testGetGav()
    {
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode("org.finos", "artifact", "1.0.0");
        Assertions.assertEquals("org.finos:artifact:1.0.0", node.getGav());
    }

    @Test
    public void testGetCoordinates()
    {
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode("org.finos", "artifact", "1.0.0");
        Assertions.assertEquals("org.finos:artifact", node.getCoordinates());
    }

    @Test
    public void testGetIdReturnsGav()
    {
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode("org.finos", "artifact", "1.0.0");
        Assertions.assertEquals("org.finos:artifact:1.0.0", node.getId());
    }

    @Test
    public void testSetProjectId()
    {
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode("org.finos", "artifact", "1.0.0");
        node.setProjectId("PROD-1");
        Assertions.assertEquals("PROD-1", node.getProjectId());
    }
}
