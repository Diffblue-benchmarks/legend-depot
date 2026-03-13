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

public class ProjectSummaryTest
{
    @Test
    public void testGetMavenCoordinates()
    {
        ProjectSummary summary = new ProjectSummary("PROD-1", "org.finos", "legend-depot", 5);
        Assertions.assertEquals("org.finos-legend-depot", summary.getMavenCoordinates());
    }

    @Test
    public void testCompareTo()
    {
        ProjectSummary summary1 = new ProjectSummary("PROD-1", "aaa", "artifact1", 5);
        ProjectSummary summary2 = new ProjectSummary("PROD-2", "zzz", "artifact2", 10);
        Assertions.assertTrue(summary1.compareTo(summary2) > 0);
        Assertions.assertTrue(summary2.compareTo(summary1) < 0);
    }

    @Test
    public void testEquals()
    {
        ProjectSummary summary1 = new ProjectSummary("PROD-1", "org.finos", "legend-depot", 5);
        ProjectSummary summary2 = new ProjectSummary("PROD-2", "com.example", "other", 5);
        Assertions.assertEquals(summary1, summary2);
    }

    @Test
    public void testNotEquals()
    {
        ProjectSummary summary1 = new ProjectSummary("PROD-1", "org.finos", "legend-depot", 5);
        ProjectSummary summary2 = new ProjectSummary("PROD-1", "org.finos", "legend-depot", 10);
        Assertions.assertNotEquals(summary1, summary2);
    }

    @Test
    public void testFields()
    {
        ProjectSummary summary = new ProjectSummary("PROD-123", "org.finos", "legend-depot", 42);
        Assertions.assertEquals("PROD-123", summary.projectId);
        Assertions.assertEquals("org.finos", summary.groupId);
        Assertions.assertEquals("legend-depot", summary.artifactId);
        Assertions.assertEquals(42, summary.versions);
    }
}
