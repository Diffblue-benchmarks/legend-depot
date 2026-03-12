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
    public void testConstructorAndFields()
    {
        ProjectSummary summary = new ProjectSummary("PROD-1", "org.finos", "my-artifact", 5);
        Assertions.assertEquals("PROD-1", summary.projectId);
        Assertions.assertEquals("org.finos", summary.groupId);
        Assertions.assertEquals("my-artifact", summary.artifactId);
        Assertions.assertEquals(5, summary.versions);
    }

    @Test
    public void testGetMavenCoordinates()
    {
        ProjectSummary summary = new ProjectSummary("PROD-1", "org.finos", "my-artifact", 5);
        Assertions.assertEquals("org.finos-my-artifact", summary.getMavenCoordinates());
    }

    @Test
    public void testCompareToOrdering()
    {
        ProjectSummary a = new ProjectSummary("PROD-1", "aaa", "artifact", 1);
        ProjectSummary b = new ProjectSummary("PROD-2", "zzz", "artifact", 2);
        Assertions.assertTrue(a.compareTo(b) > 0);
        Assertions.assertTrue(b.compareTo(a) < 0);
    }

    @Test
    public void testCompareToEqual()
    {
        ProjectSummary a = new ProjectSummary("PROD-1", "org.finos", "artifact", 1);
        ProjectSummary b = new ProjectSummary("PROD-2", "org.finos", "artifact", 2);
        Assertions.assertEquals(0, a.compareTo(b));
    }

    @Test
    public void testEqualsBasedOnVersions()
    {
        ProjectSummary a = new ProjectSummary("PROD-1", "org.finos", "artifact-a", 5);
        ProjectSummary b = new ProjectSummary("PROD-2", "com.example", "artifact-b", 5);
        Assertions.assertEquals(a, b);
    }

    @Test
    public void testNotEqualsForDifferentVersions()
    {
        ProjectSummary a = new ProjectSummary("PROD-1", "org.finos", "artifact", 5);
        ProjectSummary b = new ProjectSummary("PROD-1", "org.finos", "artifact", 10);
        Assertions.assertNotEquals(a, b);
    }

    @Test
    public void testNotEqualsNull()
    {
        ProjectSummary a = new ProjectSummary("PROD-1", "org.finos", "artifact", 5);
        Assertions.assertNotEquals(a, null);
    }
}
