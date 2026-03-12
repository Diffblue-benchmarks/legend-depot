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

import java.util.Arrays;
import java.util.Collections;

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
    public void testConstructorWithDependencies()
    {
        ProjectVersion dep = new ProjectVersion("org.finos", "artifact", "1.0.0");
        VersionDependencyReport report = new VersionDependencyReport(Arrays.asList(dep), false);

        Assertions.assertEquals(1, report.getTransitiveDependencies().size());
        Assertions.assertFalse(report.isValid());
    }

    @Test
    public void testSetValid()
    {
        VersionDependencyReport report = new VersionDependencyReport();
        report.setValid(false);
        Assertions.assertFalse(report.isValid());
    }

    @Test
    public void testSetTransitiveDependencies()
    {
        VersionDependencyReport report = new VersionDependencyReport();
        ProjectVersion dep1 = new ProjectVersion("org.finos", "a", "1.0.0");
        ProjectVersion dep2 = new ProjectVersion("org.finos", "b", "2.0.0");
        report.setTransitiveDependencies(Arrays.asList(dep1, dep2));

        Assertions.assertEquals(2, report.getTransitiveDependencies().size());
    }

    @Test
    public void testEquality()
    {
        ProjectVersion dep = new ProjectVersion("org.finos", "artifact", "1.0.0");
        VersionDependencyReport a = new VersionDependencyReport(Arrays.asList(dep), true);
        VersionDependencyReport b = new VersionDependencyReport(Arrays.asList(dep), true);
        Assertions.assertEquals(a, b);
    }

    @Test
    public void testInequalityDifferentValid()
    {
        VersionDependencyReport a = new VersionDependencyReport(Collections.emptyList(), true);
        VersionDependencyReport b = new VersionDependencyReport(Collections.emptyList(), false);
        Assertions.assertNotEquals(a, b);
    }
}
