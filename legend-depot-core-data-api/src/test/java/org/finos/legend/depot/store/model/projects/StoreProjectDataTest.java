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
    public void canCreateStoreProjectDataWithDefaultConstructor()
    {
        StoreProjectData projectData = new StoreProjectData();

        Assertions.assertNotNull(projectData);
    }

    @Test
    public void canCreateStoreProjectDataWithThreeParameters()
    {
        StoreProjectData projectData = new StoreProjectData("project-id", "com.example", "example-artifact");

        Assertions.assertNotNull(projectData);
        Assertions.assertEquals("project-id", projectData.getProjectId());
        Assertions.assertEquals("com.example", projectData.getGroupId());
        Assertions.assertEquals("example-artifact", projectData.getArtifactId());
    }

    @Test
    public void canCreateStoreProjectDataWithAllParameters()
    {
        StoreProjectData projectData = new StoreProjectData(
                "project-id",
                "com.example",
                "example-artifact",
                "master",
                "1.0.0"
        );

        Assertions.assertNotNull(projectData);
        Assertions.assertEquals("project-id", projectData.getProjectId());
        Assertions.assertEquals("com.example", projectData.getGroupId());
        Assertions.assertEquals("example-artifact", projectData.getArtifactId());
        Assertions.assertEquals("master", projectData.getDefaultBranch());
        Assertions.assertEquals("1.0.0", projectData.getLatestVersion());
    }

    @Test
    public void canGetAndSetDefaultBranch()
    {
        StoreProjectData projectData = new StoreProjectData("project-id", "com.example", "example-artifact");

        projectData.setDefaultBranch("main");

        Assertions.assertEquals("main", projectData.getDefaultBranch());
    }

    @Test
    public void canGetProjectId()
    {
        StoreProjectData projectData = new StoreProjectData("project-id", "com.example", "example-artifact");

        Assertions.assertEquals("project-id", projectData.getProjectId());
    }

    @Test
    public void canGetAndSetLatestVersion()
    {
        StoreProjectData projectData = new StoreProjectData("project-id", "com.example", "example-artifact");

        projectData.setLatestVersion("2.0.0");

        Assertions.assertEquals("2.0.0", projectData.getLatestVersion());
    }

    @Test
    public void canEvaluateLatestVersionAndUpdateWhenLatestVersionIsNull()
    {
        StoreProjectData projectData = new StoreProjectData("project-id", "com.example", "example-artifact");

        boolean updated = projectData.evaluateLatestVersionAndUpdate("1.0.0");

        Assertions.assertTrue(updated);
        Assertions.assertEquals("1.0.0", projectData.getLatestVersion());
    }

    @Test
    public void canEvaluateLatestVersionAndUpdateWhenCandidateIsNewer()
    {
        StoreProjectData projectData = new StoreProjectData(
                "project-id",
                "com.example",
                "example-artifact",
                "master",
                "1.0.0"
        );

        boolean updated = projectData.evaluateLatestVersionAndUpdate("2.0.0");

        Assertions.assertTrue(updated);
        Assertions.assertEquals("2.0.0", projectData.getLatestVersion());
    }

    @Test
    public void canEvaluateLatestVersionAndUpdateReturnsFalseWhenCandidateIsOlder()
    {
        StoreProjectData projectData = new StoreProjectData(
                "project-id",
                "com.example",
                "example-artifact",
                "master",
                "2.0.0"
        );

        boolean updated = projectData.evaluateLatestVersionAndUpdate("1.0.0");

        Assertions.assertFalse(updated);
        Assertions.assertEquals("2.0.0", projectData.getLatestVersion());
    }

    @Test
    public void canEvaluateLatestVersionAndUpdateReturnsFalseForSnapshotVersion()
    {
        StoreProjectData projectData = new StoreProjectData(
                "project-id",
                "com.example",
                "example-artifact",
                "master",
                "1.0.0"
        );

        boolean updated = projectData.evaluateLatestVersionAndUpdate("1.0.1-SNAPSHOT");

        Assertions.assertFalse(updated);
        Assertions.assertEquals("1.0.0", projectData.getLatestVersion());
    }

    @Test
    public void canGetId()
    {
        StoreProjectData projectData = new StoreProjectData("project-id", "com.example", "example-artifact");

        String id = projectData.getId();

        Assertions.assertEquals("", id);
    }

    @Test
    public void testEquals()
    {
        StoreProjectData projectData1 = new StoreProjectData(
                "project-id",
                "com.example",
                "example-artifact",
                "master",
                "1.0.0"
        );

        StoreProjectData projectData2 = new StoreProjectData(
                "project-id",
                "com.example",
                "example-artifact",
                "master",
                "1.0.0"
        );

        StoreProjectData projectData3 = new StoreProjectData(
                "different-id",
                "com.example",
                "example-artifact",
                "master",
                "1.0.0"
        );

        Assertions.assertTrue(projectData1.equals(projectData2));
        Assertions.assertFalse(projectData1.equals(projectData3));
        Assertions.assertFalse(projectData1.equals(null));
    }

    @Test
    public void testHashCode()
    {
        StoreProjectData projectData1 = new StoreProjectData(
                "project-id",
                "com.example",
                "example-artifact",
                "master",
                "1.0.0"
        );

        StoreProjectData projectData2 = new StoreProjectData(
                "project-id",
                "com.example",
                "example-artifact",
                "master",
                "1.0.0"
        );

        Assertions.assertEquals(projectData1.hashCode(), projectData2.hashCode());
    }
}
