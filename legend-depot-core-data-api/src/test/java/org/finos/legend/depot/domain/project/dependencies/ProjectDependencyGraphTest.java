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

public class ProjectDependencyGraphTest
{
    @Test
    public void testAddRootNode()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();
        ProjectVersion root = new ProjectVersion("org.finos", "legend-depot", "1.0.0");
        graph.addNode(root, null);

        Assertions.assertTrue(graph.hasNode(root));
        Assertions.assertTrue(graph.getRootNodes().contains(root));
        Assertions.assertEquals(1, graph.getNodes().size());
        Assertions.assertEquals(1, graph.getRootNodes().size());
    }

    @Test
    public void testAddChildNode()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();
        ProjectVersion root = new ProjectVersion("org.finos", "legend-depot", "1.0.0");
        ProjectVersion child = new ProjectVersion("org.finos", "legend-sdlc", "2.0.0");

        graph.addNode(root, null);
        graph.addNode(child, root);

        Assertions.assertTrue(graph.hasNode(root));
        Assertions.assertTrue(graph.hasNode(child));
        Assertions.assertTrue(graph.getRootNodes().contains(root));
        Assertions.assertFalse(graph.getRootNodes().contains(child));
        Assertions.assertEquals(2, graph.getNodes().size());
    }

    @Test
    public void testSetEdges()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();
        ProjectVersion from = new ProjectVersion("org.finos", "legend-depot", "1.0.0");
        ProjectVersion to = new ProjectVersion("org.finos", "legend-sdlc", "2.0.0");

        graph.addNode(from, null);
        graph.addNode(to, from);
        graph.setEdges(from, to);

        Assertions.assertTrue(graph.getForwardEdges().get(from).contains(to));
        Assertions.assertTrue(graph.getBackEdges().get(to).contains(from));
    }

    @Test
    public void testSetMultipleEdgesFromSameNode()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();
        ProjectVersion root = new ProjectVersion("org.finos", "legend-depot", "1.0.0");
        ProjectVersion dep1 = new ProjectVersion("org.finos", "legend-sdlc", "2.0.0");
        ProjectVersion dep2 = new ProjectVersion("org.finos", "legend-engine", "3.0.0");

        graph.addNode(root, null);
        graph.addNode(dep1, root);
        graph.addNode(dep2, root);
        graph.setEdges(root, dep1);
        graph.setEdges(root, dep2);

        Assertions.assertEquals(2, graph.getForwardEdges().get(root).size());
        Assertions.assertTrue(graph.getForwardEdges().get(root).contains(dep1));
        Assertions.assertTrue(graph.getForwardEdges().get(root).contains(dep2));
    }

    @Test
    public void testHasNodeReturnsFalseForMissingNode()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();
        ProjectVersion node = new ProjectVersion("org.finos", "legend-depot", "1.0.0");
        Assertions.assertFalse(graph.hasNode(node));
    }

    @Test
    public void testEmptyGraph()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();
        Assertions.assertTrue(graph.getNodes().isEmpty());
        Assertions.assertTrue(graph.getRootNodes().isEmpty());
        Assertions.assertTrue(graph.getForwardEdges().isEmpty());
        Assertions.assertTrue(graph.getBackEdges().isEmpty());
    }
}
