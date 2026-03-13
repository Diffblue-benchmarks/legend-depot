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

package org.finos.legend.depot.store.model.projects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StoreProjectDataTest
{
    @Test
    public void testEvaluateLatestVersionAndUpdateWhenNoCurrentVersion()
    {
        StoreProjectData project = new StoreProjectData("PROD-1", "org.finos", "legend-depot");
        Assertions.assertNull(project.getLatestVersion());

        boolean updated = project.evaluateLatestVersionAndUpdate("1.0.0");
        Assertions.assertTrue(updated);
        Assertions.assertEquals("1.0.0", project.getLatestVersion());
    }

    @Test
    public void testEvaluateLatestVersionAndUpdateWithHigherVersion()
    {
        StoreProjectData project = new StoreProjectData("PROD-1", "org.finos", "legend-depot", "master", "1.0.0");
        boolean updated = project.evaluateLatestVersionAndUpdate("2.0.0");
        Assertions.assertTrue(updated);
        Assertions.assertEquals("2.0.0", project.getLatestVersion());
    }

    @Test
    public void testEvaluateLatestVersionAndUpdateWithLowerVersion()
    {
        StoreProjectData project = new StoreProjectData("PROD-1", "org.finos", "legend-depot", "master", "2.0.0");
        boolean updated = project.evaluateLatestVersionAndUpdate("1.0.0");
        Assertions.assertFalse(updated);
        Assertions.assertEquals("2.0.0", project.getLatestVersion());
    }

    @Test
    public void testEvaluateLatestVersionAndUpdateWithSameVersion()
    {
        StoreProjectData project = new StoreProjectData("PROD-1", "org.finos", "legend-depot", "master", "1.0.0");
        boolean updated = project.evaluateLatestVersionAndUpdate("1.0.0");
        Assertions.assertFalse(updated);
        Assertions.assertEquals("1.0.0", project.getLatestVersion());
    }

    @Test
    public void testEvaluateLatestVersionAndUpdateWithSnapshotVersion()
    {
        StoreProjectData project = new StoreProjectData("PROD-1", "org.finos", "legend-depot");
        boolean updated = project.evaluateLatestVersionAndUpdate("master-SNAPSHOT");
        Assertions.assertFalse(updated);
        Assertions.assertNull(project.getLatestVersion());
    }

    @Test
    public void testConstructorWithThreeArgs()
    {
        StoreProjectData project = new StoreProjectData("PROD-123", "org.finos", "legend-depot");
        Assertions.assertEquals("PROD-123", project.getProjectId());
        Assertions.assertEquals("org.finos", project.getGroupId());
        Assertions.assertEquals("legend-depot", project.getArtifactId());
        Assertions.assertNull(project.getDefaultBranch());
        Assertions.assertNull(project.getLatestVersion());
    }

    @Test
    public void testConstructorWithFiveArgs()
    {
        StoreProjectData project = new StoreProjectData("PROD-123", "org.finos", "legend-depot", "master", "1.0.0");
        Assertions.assertEquals("PROD-123", project.getProjectId());
        Assertions.assertEquals("org.finos", project.getGroupId());
        Assertions.assertEquals("legend-depot", project.getArtifactId());
        Assertions.assertEquals("master", project.getDefaultBranch());
        Assertions.assertEquals("1.0.0", project.getLatestVersion());
    }

    @Test
    public void testEquals()
    {
        StoreProjectData project1 = new StoreProjectData("PROD-1", "org.finos", "legend-depot");
        StoreProjectData project2 = new StoreProjectData("PROD-1", "org.finos", "legend-depot");
        Assertions.assertEquals(project1, project2);
    }

    @Test
    public void testNotEquals()
    {
        StoreProjectData project1 = new StoreProjectData("PROD-1", "org.finos", "legend-depot");
        StoreProjectData project2 = new StoreProjectData("PROD-2", "org.finos", "legend-depot");
        Assertions.assertNotEquals(project1, project2);
    }

    @Test
    public void testEvaluateLatestVersionWithMinorVersionIncrease()
    {
        StoreProjectData project = new StoreProjectData("PROD-1", "org.finos", "legend-depot", "master", "1.0.0");
        boolean updated = project.evaluateLatestVersionAndUpdate("1.1.0");
        Assertions.assertTrue(updated);
        Assertions.assertEquals("1.1.0", project.getLatestVersion());
    }

    @Test
    public void testEvaluateLatestVersionWithPatchVersionIncrease()
    {
        StoreProjectData project = new StoreProjectData("PROD-1", "org.finos", "legend-depot", "master", "1.0.0");
        boolean updated = project.evaluateLatestVersionAndUpdate("1.0.1");
        Assertions.assertTrue(updated);
        Assertions.assertEquals("1.0.1", project.getLatestVersion());
    }
}
