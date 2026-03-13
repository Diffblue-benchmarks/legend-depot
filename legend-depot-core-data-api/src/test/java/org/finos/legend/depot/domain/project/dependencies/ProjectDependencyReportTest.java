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
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProjectDependencyReportTest
{
    @Test
    public void canGetNodesFromSerializedGraph()
    {
        ProjectDependencyReport report = new ProjectDependencyReport();
        ProjectDependencyReport.SerializedGraph graph = report.getGraph();

        MutableMap<String, ProjectDependencyVersionNode> nodes = graph.getNodes();

        assertNotNull(nodes);
        assertTrue(nodes.isEmpty());
    }

    @Test
    public void canGetRootNodesFromSerializedGraph()
    {
        ProjectDependencyReport report = new ProjectDependencyReport();
        ProjectDependencyReport.SerializedGraph graph = report.getGraph();

        Set<String> rootNodes = graph.getRootNodes();

        assertNotNull(rootNodes);
        assertTrue(rootNodes.isEmpty());
    }

    @Test
    public void nodesMapIsDirectReference()
    {
        ProjectDependencyReport report = new ProjectDependencyReport();
        ProjectDependencyReport.SerializedGraph graph = report.getGraph();

        MutableMap<String, ProjectDependencyVersionNode> nodes1 = graph.getNodes();
        MutableMap<String, ProjectDependencyVersionNode> nodes2 = graph.getNodes();

        assertEquals(nodes1, nodes2);
    }

    @Test
    public void rootNodesSetIsDirectReference()
    {
        ProjectDependencyReport report = new ProjectDependencyReport();
        ProjectDependencyReport.SerializedGraph graph = report.getGraph();

        Set<String> rootNodes1 = graph.getRootNodes();
        Set<String> rootNodes2 = graph.getRootNodes();

        assertEquals(rootNodes1, rootNodes2);
    }
}
