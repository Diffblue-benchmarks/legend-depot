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

package org.finos.legend.depot.domain.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestProjectSummary
{
    @Test
    public void canCreateProjectSummary()
    {
        ProjectSummary summary = new ProjectSummary("PROD-1", "org.finos", "legend-depot", 5);

        Assertions.assertEquals("PROD-1", summary.projectId);
        Assertions.assertEquals("org.finos", summary.groupId);
        Assertions.assertEquals("legend-depot", summary.artifactId);
        Assertions.assertEquals(5, summary.versions);
    }

    @Test
    public void canGetMavenCoordinates()
    {
        ProjectSummary summary = new ProjectSummary("PROD-1", "org.finos", "legend-depot", 3);

        Assertions.assertEquals("org.finos-legend-depot", summary.getMavenCoordinates());
    }

    @Test
    public void canCompareProjectSummaries()
    {
        ProjectSummary summary1 = new ProjectSummary("PROD-1", "org.aaa", "artifact1", 1);
        ProjectSummary summary2 = new ProjectSummary("PROD-2", "org.zzz", "artifact2", 2);

        int result = summary1.compareTo(summary2);
        Assertions.assertTrue(result != 0);

        ProjectSummary summary3 = new ProjectSummary("PROD-3", "org.aaa", "artifact1", 3);
        Assertions.assertEquals(0, summary1.compareTo(summary3));
    }

    @Test
    public void testEqualsSameInstance()
    {
        ProjectSummary summary = new ProjectSummary("PROD-1", "org.finos", "legend-depot", 5);

        Assertions.assertTrue(summary.equals(summary));
    }

    @Test
    public void testEqualsNull()
    {
        ProjectSummary summary = new ProjectSummary("PROD-1", "org.finos", "legend-depot", 5);

        Assertions.assertFalse(summary.equals(null));
    }

    @Test
    public void testEqualsDifferentClass()
    {
        ProjectSummary summary = new ProjectSummary("PROD-1", "org.finos", "legend-depot", 5);

        Assertions.assertFalse(summary.equals("not a ProjectSummary"));
    }

    @Test
    public void testEqualsSameVersions()
    {
        ProjectSummary summary1 = new ProjectSummary("PROD-1", "org.finos", "legend-depot", 5);
        ProjectSummary summary2 = new ProjectSummary("PROD-2", "org.other", "other-artifact", 5);

        Assertions.assertTrue(summary1.equals(summary2));
    }

    @Test
    public void testEqualsDifferentVersions()
    {
        ProjectSummary summary1 = new ProjectSummary("PROD-1", "org.finos", "legend-depot", 5);
        ProjectSummary summary2 = new ProjectSummary("PROD-1", "org.finos", "legend-depot", 10);

        Assertions.assertFalse(summary1.equals(summary2));
    }

    @Test
    public void testHashCode()
    {
        ProjectSummary summary1 = new ProjectSummary("PROD-1", "org.finos", "legend-depot", 5);
        ProjectSummary summary2 = new ProjectSummary("PROD-2", "org.other", "other-artifact", 5);

        Assertions.assertEquals(summary1.hashCode(), summary2.hashCode());
    }
}
