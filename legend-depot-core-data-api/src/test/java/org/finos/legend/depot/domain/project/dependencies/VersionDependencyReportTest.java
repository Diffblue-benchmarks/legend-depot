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
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VersionDependencyReportTest
{
    @Test
    public void canCreateVersionDependencyReportWithDefaultConstructor()
    {
        VersionDependencyReport report = new VersionDependencyReport();

        assertNotNull(report);
        assertNotNull(report.getTransitiveDependencies());
        assertTrue(report.isValid());
    }

    @Test
    public void canCreateVersionDependencyReportWithParameters()
    {
        List<ProjectVersion> dependencies = Arrays.asList(
                new ProjectVersion("org.example", "artifact1", "1.0.0"),
                new ProjectVersion("org.example", "artifact2", "2.0.0")
        );
        boolean valid = false;

        VersionDependencyReport report = new VersionDependencyReport(dependencies, valid);

        assertNotNull(report);
        assertEquals(dependencies, report.getTransitiveDependencies());
        assertFalse(report.isValid());
    }

    @Test
    public void canGetTransitiveDependencies()
    {
        List<ProjectVersion> dependencies = Arrays.asList(
                new ProjectVersion("org.test", "test-artifact", "1.0.0")
        );
        VersionDependencyReport report = new VersionDependencyReport(dependencies, true);

        List<ProjectVersion> result = report.getTransitiveDependencies();

        assertEquals(dependencies, result);
    }

    @Test
    public void canSetTransitiveDependencies()
    {
        VersionDependencyReport report = new VersionDependencyReport();
        List<ProjectVersion> newDependencies = Arrays.asList(
                new ProjectVersion("org.new", "new-artifact", "3.0.0")
        );

        report.setTransitiveDependencies(newDependencies);

        assertEquals(newDependencies, report.getTransitiveDependencies());
    }

    @Test
    public void canGetValidFlag()
    {
        VersionDependencyReport validReport = new VersionDependencyReport(new ArrayList<>(), true);
        assertTrue(validReport.isValid());

        VersionDependencyReport invalidReport = new VersionDependencyReport(new ArrayList<>(), false);
        assertFalse(invalidReport.isValid());
    }

    @Test
    public void canSetValidFlag()
    {
        VersionDependencyReport report = new VersionDependencyReport();
        assertTrue(report.isValid());

        report.setValid(false);
        assertFalse(report.isValid());

        report.setValid(true);
        assertTrue(report.isValid());
    }

    @Test
    public void testEquals()
    {
        List<ProjectVersion> dependencies = Arrays.asList(
                new ProjectVersion("org.example", "artifact", "1.0.0")
        );

        VersionDependencyReport report1 = new VersionDependencyReport(dependencies, true);
        VersionDependencyReport report2 = new VersionDependencyReport(dependencies, true);
        VersionDependencyReport report3 = new VersionDependencyReport(new ArrayList<>(), false);

        assertTrue(report1.equals(report2));
        assertFalse(report1.equals(report3));
        assertFalse(report1.equals(null));
        assertFalse(report1.equals(new Object()));
    }

    @Test
    public void testHashCode()
    {
        List<ProjectVersion> dependencies = Arrays.asList(
                new ProjectVersion("org.example", "artifact", "1.0.0")
        );

        VersionDependencyReport report1 = new VersionDependencyReport(dependencies, true);
        VersionDependencyReport report2 = new VersionDependencyReport(dependencies, true);

        assertEquals(report1.hashCode(), report2.hashCode());
    }
}
