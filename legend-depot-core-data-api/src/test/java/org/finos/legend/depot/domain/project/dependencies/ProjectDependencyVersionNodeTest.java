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

public class ProjectDependencyVersionNodeTest
{
    @Test
    public void testConstructor()
    {
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode("org.finos", "legend-depot", "1.0.0");
        Assertions.assertEquals("org.finos", node.getGroupId());
        Assertions.assertEquals("legend-depot", node.getArtifactId());
        Assertions.assertEquals("1.0.0", node.getVersionId());
        Assertions.assertTrue(node.getForwardEdges().isEmpty());
        Assertions.assertTrue(node.getBackEdges().isEmpty());
    }

    @Test
    public void testBuildFromProjectVersion()
    {
        ProjectVersion pv = new ProjectVersion("org.finos", "legend-depot", "2.0.0");
        ProjectDependencyVersionNode node = ProjectDependencyVersionNode.buildFromProjectVersion(pv);

        Assertions.assertEquals("org.finos", node.getGroupId());
        Assertions.assertEquals("legend-depot", node.getArtifactId());
        Assertions.assertEquals("2.0.0", node.getVersionId());
    }

    @Test
    public void testGetGav()
    {
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode("org.finos", "legend-depot", "1.0.0");
        Assertions.assertEquals("org.finos:legend-depot:1.0.0", node.getGav());
    }

    @Test
    public void testGetCoordinates()
    {
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode("org.finos", "legend-depot", "1.0.0");
        Assertions.assertEquals("org.finos:legend-depot", node.getCoordinates());
    }

    @Test
    public void testGetId()
    {
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode("org.finos", "legend-depot", "1.0.0");
        Assertions.assertEquals("org.finos:legend-depot:1.0.0", node.getId());
    }

    @Test
    public void testSetProjectId()
    {
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode("org.finos", "legend-depot", "1.0.0");
        Assertions.assertNull(node.getProjectId());
        node.setProjectId("PROD-123");
        Assertions.assertEquals("PROD-123", node.getProjectId());
    }

    @Test
    public void testForwardAndBackEdges()
    {
        ProjectDependencyVersionNode node = new ProjectDependencyVersionNode("org.finos", "legend-depot", "1.0.0");
        node.getForwardEdges().add("org.finos:legend-sdlc:2.0.0");
        node.getBackEdges().add("org.finos:legend-engine:3.0.0");

        Assertions.assertEquals(1, node.getForwardEdges().size());
        Assertions.assertEquals(1, node.getBackEdges().size());
        Assertions.assertTrue(node.getForwardEdges().contains("org.finos:legend-sdlc:2.0.0"));
        Assertions.assertTrue(node.getBackEdges().contains("org.finos:legend-engine:3.0.0"));
    }
}
