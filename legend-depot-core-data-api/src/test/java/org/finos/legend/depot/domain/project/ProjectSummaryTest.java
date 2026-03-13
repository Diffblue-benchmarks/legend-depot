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
    public void canCreateProjectSummary()
    {
        ProjectSummary summary = new ProjectSummary("project1", "com.example", "myartifact", 5L);

        Assertions.assertEquals("project1", summary.projectId);
        Assertions.assertEquals("com.example", summary.groupId);
        Assertions.assertEquals("myartifact", summary.artifactId);
        Assertions.assertEquals(5L, summary.versions);
    }

    @Test
    public void canGetMavenCoordinates()
    {
        ProjectSummary summary = new ProjectSummary("project1", "com.example", "myartifact", 5L);

        String mavenCoordinates = summary.getMavenCoordinates();

        Assertions.assertEquals("com.example-myartifact", mavenCoordinates);
    }

    @Test
    public void canCompareProjectSummaries()
    {
        ProjectSummary summary1 = new ProjectSummary("project1", "com.aaa", "artifact-a", 5L);
        ProjectSummary summary2 = new ProjectSummary("project2", "com.zzz", "artifact-b", 10L);

        int result = summary1.compareTo(summary2);

        Assertions.assertTrue(result > 0);
    }

    @Test
    public void canCompareEqualProjectSummaries()
    {
        ProjectSummary summary1 = new ProjectSummary("project1", "com.example", "artifact-a", 5L);
        ProjectSummary summary2 = new ProjectSummary("project2", "com.example", "artifact-b", 10L);

        int result = summary1.compareTo(summary2);

        Assertions.assertEquals(0, result);
    }

    @Test
    public void testEqualsSameObject()
    {
        ProjectSummary summary = new ProjectSummary("project1", "com.example", "myartifact", 5L);

        Assertions.assertTrue(summary.equals(summary));
    }

    @Test
    public void testEqualsNull()
    {
        ProjectSummary summary = new ProjectSummary("project1", "com.example", "myartifact", 5L);

        Assertions.assertFalse(summary.equals(null));
    }

    @Test
    public void testEqualsDifferentClass()
    {
        ProjectSummary summary = new ProjectSummary("project1", "com.example", "myartifact", 5L);

        Assertions.assertFalse(summary.equals("not a ProjectSummary"));
    }

    @Test
    public void testEqualsSameVersions()
    {
        ProjectSummary summary1 = new ProjectSummary("project1", "com.example", "myartifact", 5L);
        ProjectSummary summary2 = new ProjectSummary("project2", "com.different", "differentartifact", 5L);

        Assertions.assertTrue(summary1.equals(summary2));
    }

    @Test
    public void testEqualsDifferentVersions()
    {
        ProjectSummary summary1 = new ProjectSummary("project1", "com.example", "myartifact", 5L);
        ProjectSummary summary2 = new ProjectSummary("project1", "com.example", "myartifact", 10L);

        Assertions.assertFalse(summary1.equals(summary2));
    }

    @Test
    public void testHashCode()
    {
        ProjectSummary summary1 = new ProjectSummary("project1", "com.example", "myartifact", 5L);
        ProjectSummary summary2 = new ProjectSummary("project2", "com.different", "differentartifact", 5L);

        Assertions.assertEquals(summary1.hashCode(), summary2.hashCode());
    }

    @Test
    public void testHashCodeDifferentVersions()
    {
        ProjectSummary summary1 = new ProjectSummary("project1", "com.example", "myartifact", 5L);
        ProjectSummary summary2 = new ProjectSummary("project1", "com.example", "myartifact", 10L);

        Assertions.assertNotEquals(summary1.hashCode(), summary2.hashCode());
    }
}
