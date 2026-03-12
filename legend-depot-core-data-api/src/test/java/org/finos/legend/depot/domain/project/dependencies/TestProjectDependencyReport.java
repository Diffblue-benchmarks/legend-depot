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

import org.eclipse.collections.api.factory.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class TestProjectDependencyReport
{
    @Test
    public void testNewReportHasEmptyConflictsAndGraph()
    {
        ProjectDependencyReport report = new ProjectDependencyReport();
        Assertions.assertNotNull(report.getConflicts());
        Assertions.assertTrue(report.getConflicts().isEmpty());
        Assertions.assertNotNull(report.getGraph());
        Assertions.assertTrue(report.getGraph().getNodes().isEmpty());
        Assertions.assertTrue(report.getGraph().getRootNodes().isEmpty());
    }

    @Test
    public void testAddConflict()
    {
        ProjectDependencyReport report = new ProjectDependencyReport();
        Set<String> versions = Sets.mutable.with("1.0.0", "2.0.0");
        report.addConflict("org.finos", "artifact", versions);

        Assertions.assertEquals(1, report.getConflicts().size());
        Assertions.assertEquals("org.finos", report.getConflicts().get(0).getGroupId());
        Assertions.assertEquals("artifact", report.getConflicts().get(0).getArtifactId());
        Assertions.assertEquals(2, report.getConflicts().get(0).getVersions().size());
    }

    @Test
    public void testAddConflictWithEmptyVersionsThrows()
    {
        ProjectDependencyReport report = new ProjectDependencyReport();
        Set<String> empty = Sets.mutable.empty();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> report.addConflict("org.finos", "artifact", empty));
    }

    @Test
    public void testRemoveConflict()
    {
        ProjectDependencyReport report = new ProjectDependencyReport();
        Set<String> versions = Sets.mutable.with("1.0.0", "2.0.0");
        report.addConflict("org.finos", "artifact", versions);
        Assertions.assertEquals(1, report.getConflicts().size());

        report.removeConflict(report.getConflicts().get(0));
        Assertions.assertTrue(report.getConflicts().isEmpty());
    }
}
