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

import org.eclipse.collections.api.map.MutableMap;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class ProjectDependencyGraphTest
{
    @Test
    public void canCreateEmptyGraph()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();

        Assertions.assertNotNull(graph);
        Assertions.assertNotNull(graph.getNodes());
        Assertions.assertTrue(graph.getNodes().isEmpty());
        Assertions.assertNotNull(graph.getRootNodes());
        Assertions.assertTrue(graph.getRootNodes().isEmpty());
    }

    @Test
    public void canGetBackEdges()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();

        MutableMap<ProjectVersion, Set<ProjectVersion>> backEdges = graph.getBackEdges();

        Assertions.assertNotNull(backEdges);
        Assertions.assertTrue(backEdges.isEmpty());
    }

    @Test
    public void canGetForwardEdges()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();

        MutableMap<ProjectVersion, Set<ProjectVersion>> forwardEdges = graph.getForwardEdges();

        Assertions.assertNotNull(forwardEdges);
        Assertions.assertTrue(forwardEdges.isEmpty());
    }

    @Test
    public void canGetNodes()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();

        Set<ProjectVersion> nodes = graph.getNodes();

        Assertions.assertNotNull(nodes);
        Assertions.assertTrue(nodes.isEmpty());
    }

    @Test
    public void canGetRootNodes()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();

        Set<ProjectVersion> rootNodes = graph.getRootNodes();

        Assertions.assertNotNull(rootNodes);
        Assertions.assertTrue(rootNodes.isEmpty());
    }

    @Test
    public void canCheckHasNode()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();
        ProjectVersion node = new ProjectVersion("org.example", "test-artifact", "1.0.0");

        boolean hasNode = graph.hasNode(node);

        Assertions.assertFalse(hasNode);
    }

    @Test
    public void canAddNodeWithoutParent()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();
        ProjectVersion node = new ProjectVersion("org.example", "test-artifact", "1.0.0");

        graph.addNode(node, null);

        Assertions.assertTrue(graph.hasNode(node));
        Assertions.assertEquals(1, graph.getNodes().size());
        Assertions.assertTrue(graph.getRootNodes().contains(node));
        Assertions.assertEquals(1, graph.getRootNodes().size());
    }

    @Test
    public void canAddNodeWithParent()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();
        ProjectVersion parent = new ProjectVersion("org.example", "parent-artifact", "1.0.0");
        ProjectVersion child = new ProjectVersion("org.example", "child-artifact", "1.0.0");

        graph.addNode(parent, null);
        graph.addNode(child, parent);

        Assertions.assertTrue(graph.hasNode(child));
        Assertions.assertEquals(2, graph.getNodes().size());
        Assertions.assertFalse(graph.getRootNodes().contains(child));
        Assertions.assertEquals(1, graph.getRootNodes().size());
    }

    @Test
    public void canSetEdges()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();
        ProjectVersion from = new ProjectVersion("org.example", "from-artifact", "1.0.0");
        ProjectVersion to = new ProjectVersion("org.example", "to-artifact", "1.0.0");

        graph.setEdges(from, to);

        Assertions.assertTrue(graph.getForwardEdges().containsKey(from));
        Assertions.assertTrue(graph.getForwardEdges().get(from).contains(to));
        Assertions.assertTrue(graph.getBackEdges().containsKey(to));
        Assertions.assertTrue(graph.getBackEdges().get(to).contains(from));
    }

    @Test
    public void canSetMultipleEdgesFromSameNode()
    {
        ProjectDependencyGraph graph = new ProjectDependencyGraph();
        ProjectVersion from = new ProjectVersion("org.example", "from-artifact", "1.0.0");
        ProjectVersion to1 = new ProjectVersion("org.example", "to-artifact-1", "1.0.0");
        ProjectVersion to2 = new ProjectVersion("org.example", "to-artifact-2", "1.0.0");

        graph.setEdges(from, to1);
        graph.setEdges(from, to2);

        Assertions.assertTrue(graph.getForwardEdges().containsKey(from));
        Assertions.assertEquals(2, graph.getForwardEdges().get(from).size());
        Assertions.assertTrue(graph.getForwardEdges().get(from).contains(to1));
        Assertions.assertTrue(graph.getForwardEdges().get(from).contains(to2));
    }
}
