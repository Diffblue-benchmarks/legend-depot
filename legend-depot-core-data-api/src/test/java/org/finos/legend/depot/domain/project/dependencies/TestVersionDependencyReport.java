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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestVersionDependencyReport
{
    @Test
    public void testDefaultConstructor()
    {
        VersionDependencyReport report = new VersionDependencyReport();

        Assertions.assertNotNull(report.getTransitiveDependencies());
        Assertions.assertTrue(report.getTransitiveDependencies().isEmpty());
        Assertions.assertTrue(report.isValid());
    }

    @Test
    public void testParameterizedConstructor()
    {
        List<ProjectVersion> dependencies = Arrays.asList(
                new ProjectVersion("examples.test", "artifact1", "1.0.0"),
                new ProjectVersion("examples.test", "artifact2", "2.0.0")
        );

        VersionDependencyReport report = new VersionDependencyReport(dependencies, false);

        Assertions.assertEquals(2, report.getTransitiveDependencies().size());
        Assertions.assertFalse(report.isValid());
    }

    @Test
    public void testSetTransitiveDependencies()
    {
        VersionDependencyReport report = new VersionDependencyReport();
        List<ProjectVersion> dependencies = Arrays.asList(
                new ProjectVersion("examples.test", "artifact1", "1.0.0")
        );

        report.setTransitiveDependencies(dependencies);

        Assertions.assertEquals(1, report.getTransitiveDependencies().size());
    }

    @Test
    public void testSetValid()
    {
        VersionDependencyReport report = new VersionDependencyReport();

        report.setValid(false);
        Assertions.assertFalse(report.isValid());

        report.setValid(true);
        Assertions.assertTrue(report.isValid());
    }

    @Test
    public void testEquals()
    {
        List<ProjectVersion> dependencies = Arrays.asList(
                new ProjectVersion("examples.test", "artifact1", "1.0.0")
        );

        VersionDependencyReport report1 = new VersionDependencyReport(dependencies, true);
        VersionDependencyReport report2 = new VersionDependencyReport(dependencies, true);

        Assertions.assertEquals(report1, report2);
    }

    @Test
    public void testHashCode()
    {
        List<ProjectVersion> dependencies = Arrays.asList(
                new ProjectVersion("examples.test", "artifact1", "1.0.0")
        );

        VersionDependencyReport report1 = new VersionDependencyReport(dependencies, true);
        VersionDependencyReport report2 = new VersionDependencyReport(dependencies, true);

        Assertions.assertEquals(report1.hashCode(), report2.hashCode());
    }
}
