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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestProjectDependencyReportSerializedGraph
{
    @Test
    public void canGetNodesFromNewGraph()
    {
        ProjectDependencyReport report = new ProjectDependencyReport();
        ProjectDependencyReport.SerializedGraph graph = report.getGraph();

        Assertions.assertNotNull(graph.getNodes());
        Assertions.assertTrue(graph.getNodes().isEmpty());
    }

    @Test
    public void canGetRootNodesFromNewGraph()
    {
        ProjectDependencyReport report = new ProjectDependencyReport();
        ProjectDependencyReport.SerializedGraph graph = report.getGraph();

        Assertions.assertNotNull(graph.getRootNodes());
        Assertions.assertTrue(graph.getRootNodes().isEmpty());
    }
}
