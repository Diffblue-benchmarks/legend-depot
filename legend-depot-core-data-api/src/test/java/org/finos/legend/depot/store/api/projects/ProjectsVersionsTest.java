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

package org.finos.legend.depot.store.api.projects;

import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProjectsVersionsTest
{
    @Test
    public void canGetAll()
    {
        List<StoreProjectVersionData> expectedVersions = Arrays.asList(
                new StoreProjectVersionData("org.example", "artifact1", "1.0.0"),
                new StoreProjectVersionData("org.example", "artifact2", "2.0.0")
        );
        TestProjectsVersions testService = new TestProjectsVersions();
        testService.allVersionsToReturn = expectedVersions;

        List<StoreProjectVersionData> versions = testService.getAll();

        Assertions.assertNotNull(versions);
        Assertions.assertEquals(expectedVersions, versions);
        Assertions.assertEquals(2, versions.size());
    }

    @Test
    public void canFindByUpdatedDate()
    {
        long updatedFrom = 1000000L;
        long updatedTo = 2000000L;
        List<StoreProjectVersionData> expectedVersions = Arrays.asList(
                new StoreProjectVersionData("org.example", "artifact1", "1.0.0")
        );
        TestProjectsVersions testService = new TestProjectsVersions();
        testService.versionsByDateToReturn = expectedVersions;

        List<StoreProjectVersionData> versions = testService.findByUpdatedDate(updatedFrom, updatedTo);

        Assertions.assertNotNull(versions);
        Assertions.assertEquals(expectedVersions, versions);
        Assertions.assertEquals(updatedFrom, testService.receivedUpdatedFrom);
        Assertions.assertEquals(updatedTo, testService.receivedUpdatedTo);
    }

    @Test
    public void canFindByGroupIdAndArtifactId()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        List<StoreProjectVersionData> expectedVersions = Arrays.asList(
                new StoreProjectVersionData(groupId, artifactId, "1.0.0"),
                new StoreProjectVersionData(groupId, artifactId, "2.0.0")
        );
        TestProjectsVersions testService = new TestProjectsVersions();
        testService.versionsByCoordinatesToReturn = expectedVersions;

        List<StoreProjectVersionData> versions = testService.find(groupId, artifactId);

        Assertions.assertNotNull(versions);
        Assertions.assertEquals(expectedVersions, versions);
        Assertions.assertEquals(groupId, testService.receivedGroupId);
        Assertions.assertEquals(artifactId, testService.receivedArtifactId);
    }

    @Test
    public void canFindByGroupIdArtifactIdAndVersionId()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        StoreProjectVersionData expectedVersion = new StoreProjectVersionData(groupId, artifactId, versionId);
        TestProjectsVersions testService = new TestProjectsVersions();
        testService.versionByFullCoordinatesToReturn = Optional.of(expectedVersion);

        Optional<StoreProjectVersionData> version = testService.find(groupId, artifactId, versionId);

        Assertions.assertTrue(version.isPresent());
        Assertions.assertEquals(expectedVersion, version.get());
        Assertions.assertEquals(groupId, testService.receivedFullGroupId);
        Assertions.assertEquals(artifactId, testService.receivedFullArtifactId);
        Assertions.assertEquals(versionId, testService.receivedVersionId);
    }

    @Test
    public void canGetVersionCount()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        long expectedCount = 5L;
        TestProjectsVersions testService = new TestProjectsVersions();
        testService.versionCountToReturn = expectedCount;

        long count = testService.getVersionCount(groupId, artifactId);

        Assertions.assertEquals(expectedCount, count);
        Assertions.assertEquals(groupId, testService.receivedCountGroupId);
        Assertions.assertEquals(artifactId, testService.receivedCountArtifactId);
    }

    @Test
    public void canFindVersionByExcluded()
    {
        Boolean excluded = true;
        List<StoreProjectVersionData> expectedVersions = Arrays.asList(
                new StoreProjectVersionData("org.example", "artifact1", "1.0.0")
        );
        TestProjectsVersions testService = new TestProjectsVersions();
        testService.versionsByExcludedToReturn = expectedVersions;

        List<StoreProjectVersionData> versions = testService.findVersion(excluded);

        Assertions.assertNotNull(versions);
        Assertions.assertEquals(expectedVersions, versions);
        Assertions.assertEquals(excluded, testService.receivedExcluded);
    }

    private static class TestProjectsVersions implements ProjectsVersions
    {
        List<StoreProjectVersionData> allVersionsToReturn = new ArrayList<>();
        List<StoreProjectVersionData> versionsByDateToReturn = new ArrayList<>();
        long receivedUpdatedFrom;
        long receivedUpdatedTo;
        List<StoreProjectVersionData> versionsByCoordinatesToReturn = new ArrayList<>();
        String receivedGroupId;
        String receivedArtifactId;
        Optional<StoreProjectVersionData> versionByFullCoordinatesToReturn = Optional.empty();
        String receivedFullGroupId;
        String receivedFullArtifactId;
        String receivedVersionId;
        long versionCountToReturn = 0L;
        String receivedCountGroupId;
        String receivedCountArtifactId;
        List<StoreProjectVersionData> versionsByExcludedToReturn = new ArrayList<>();
        Boolean receivedExcluded;

        @Override
        public List<StoreProjectVersionData> getAll()
        {
            return allVersionsToReturn;
        }

        @Override
        public List<StoreProjectVersionData> findByUpdatedDate(long updatedFrom, long updatedTo)
        {
            this.receivedUpdatedFrom = updatedFrom;
            this.receivedUpdatedTo = updatedTo;
            return versionsByDateToReturn;
        }

        @Override
        public List<StoreProjectVersionData> find(String groupId, String artifactId)
        {
            this.receivedGroupId = groupId;
            this.receivedArtifactId = artifactId;
            return versionsByCoordinatesToReturn;
        }

        @Override
        public Optional<StoreProjectVersionData> find(String groupId, String artifactId, String versionId)
        {
            this.receivedFullGroupId = groupId;
            this.receivedFullArtifactId = artifactId;
            this.receivedVersionId = versionId;
            return versionByFullCoordinatesToReturn;
        }

        @Override
        public long getVersionCount(String groupId, String artifactId)
        {
            this.receivedCountGroupId = groupId;
            this.receivedCountArtifactId = artifactId;
            return versionCountToReturn;
        }

        @Override
        public List<StoreProjectVersionData> findVersion(Boolean excluded)
        {
            this.receivedExcluded = excluded;
            return versionsByExcludedToReturn;
        }
    }
}
