//  Copyright 2022 Goldman Sachs
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

public class TestProjectDependencyGraph
{
    @Test
    public void canCreateEmptyGraph()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();

        Assertions.assertNotNull(graph.getNodes());
        Assertions.assertTrue(graph.getNodes().isEmpty());
        Assertions.assertNotNull(graph.getRootNodes());
        Assertions.assertTrue(graph.getRootNodes().isEmpty());
        Assertions.assertNotNull(graph.getForwardEdges());
        Assertions.assertTrue(graph.getForwardEdges().isEmpty());
        Assertions.assertNotNull(graph.getBackEdges());
        Assertions.assertTrue(graph.getBackEdges().isEmpty());
    }

    @Test
    public void canAddNodeWithNullParent()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();
        ProjectVersion node = new ProjectVersion("org.finos", "test-artifact", "1.0.0");

        graph.addNode(node, null);

        Assertions.assertTrue(graph.hasNode(node));
        Assertions.assertTrue(graph.getRootNodes().contains(node));
        Assertions.assertEquals(1, graph.getNodes().size());
        Assertions.assertEquals(1, graph.getRootNodes().size());
    }

    @Test
    public void canAddNodeWithParent()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();
        ProjectVersion parent = new ProjectVersion("org.finos", "parent-artifact", "1.0.0");
        ProjectVersion child = new ProjectVersion("org.finos", "child-artifact", "2.0.0");

        graph.addNode(parent, null);
        graph.addNode(child, parent);

        Assertions.assertTrue(graph.hasNode(parent));
        Assertions.assertTrue(graph.hasNode(child));
        Assertions.assertTrue(graph.getRootNodes().contains(parent));
        Assertions.assertFalse(graph.getRootNodes().contains(child));
    }

    @Test
    public void canSetEdges()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();
        ProjectVersion from = new ProjectVersion("org.finos", "from-artifact", "1.0.0");
        ProjectVersion to = new ProjectVersion("org.finos", "to-artifact", "2.0.0");

        graph.setEdges(from, to);

        Assertions.assertTrue(graph.getForwardEdges().containsKey(from));
        Assertions.assertTrue(graph.getForwardEdges().get(from).contains(to));
        Assertions.assertTrue(graph.getBackEdges().containsKey(to));
        Assertions.assertTrue(graph.getBackEdges().get(to).contains(from));
    }

    @Test
    public void hasNodeReturnsFalseForMissingNode()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();
        ProjectVersion node = new ProjectVersion("org.finos", "missing", "1.0.0");

        Assertions.assertFalse(graph.hasNode(node));
    }
}
