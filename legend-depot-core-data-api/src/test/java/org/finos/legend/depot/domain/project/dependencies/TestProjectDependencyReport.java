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
    public void canAddConflictWithVersions()
    {
        ProjectDependencyReport report = new ProjectDependencyReport();
        Set<String> versions = Sets.mutable.of("1.0.0", "2.0.0");

        report.addConflict("org.example", "test-artifact", versions);

        Assertions.assertEquals(1, report.getConflicts().size());
        ProjectDependencyReport.ProjectDependencyConflict conflict = report.getConflicts().get(0);
        Assertions.assertEquals("org.example", conflict.getGroupId());
        Assertions.assertEquals("test-artifact", conflict.getArtifactId());
        Assertions.assertEquals(versions, conflict.getVersions());
    }

    @Test
    public void canGetVersionsFromConflict()
    {
        ProjectDependencyReport report = new ProjectDependencyReport();
        Set<String> versions = Sets.mutable.of("1.0.0", "2.0.0", "3.0.0");

        report.addConflict("com.example", "another-artifact", versions);

        ProjectDependencyReport.ProjectDependencyConflict conflict = report.getConflicts().get(0);
        Set<String> retrievedVersions = conflict.getVersions();

        Assertions.assertNotNull(retrievedVersions);
        Assertions.assertEquals(3, retrievedVersions.size());
        Assertions.assertTrue(retrievedVersions.contains("1.0.0"));
        Assertions.assertTrue(retrievedVersions.contains("2.0.0"));
        Assertions.assertTrue(retrievedVersions.contains("3.0.0"));
    }
}
