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

package org.finos.legend.depot.store.mongo.projects;

import org.finos.legend.depot.domain.project.ProjectVersionData;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ProjectsVersionsMongoTest extends TestStoreMongo
{
    private ProjectsVersionsMongo store;

    @BeforeEach
    public void setUp()
    {
        store = new ProjectsVersionsMongo(this.mongoProvider);
    }

    @Test
    public void canCreateProjectsVersionsMongoInstance()
    {
        Assertions.assertNotNull(store);
    }

    @Test
    public void canBuildIndexes()
    {
        List indexes = ProjectsVersionsMongo.buildIndexes();
        Assertions.assertNotNull(indexes);
        Assertions.assertEquals(1, indexes.size());
    }

    @Test
    public void canGetAll()
    {
        StoreProjectVersionData version = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");
        store.createOrUpdate(version);

        List<StoreProjectVersionData> result = store.getAll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void canFindByUpdatedDate()
    {
        long startTime = System.currentTimeMillis();

        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");
        store.createOrUpdate(version1);

        try
        {
            Thread.sleep(100);
        }
        catch (InterruptedException e)
        {
            // ignore
        }

        StoreProjectVersionData version2 = new StoreProjectVersionData("test.group", "test-artifact", "2.0.0");
        store.createOrUpdate(version2);

        long endTime = System.currentTimeMillis();

        List<StoreProjectVersionData> result = store.findByUpdatedDate(startTime, endTime + 1000);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());

        List<StoreProjectVersionData> resultPartial = store.findByUpdatedDate(startTime, startTime + 50);
        Assertions.assertNotNull(resultPartial);
        Assertions.assertTrue(resultPartial.size() <= 2);
    }

    @Test
    public void canFindByGroupIdAndArtifactId()
    {
        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");
        store.createOrUpdate(version1);

        StoreProjectVersionData version2 = new StoreProjectVersionData("test.group", "test-artifact", "2.0.0");
        store.createOrUpdate(version2);

        StoreProjectVersionData version3 = new StoreProjectVersionData("other.group", "other-artifact", "1.0.0");
        store.createOrUpdate(version3);

        List<StoreProjectVersionData> result = store.find("test.group", "test-artifact");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void canFindByGroupIdArtifactIdAndVersion()
    {
        StoreProjectVersionData version = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");
        store.createOrUpdate(version);

        Optional<StoreProjectVersionData> result = store.find("test.group", "test-artifact", "1.0.0");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("test.group", result.get().getGroupId());
        Assertions.assertEquals("test-artifact", result.get().getArtifactId());
        Assertions.assertEquals("1.0.0", result.get().getVersionId());
    }

    @Test
    public void cannotFindWithNullVersionId()
    {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            store.find("test.group", "test-artifact", null);
        });
        Assertions.assertTrue(exception.getMessage().contains("cannot find project version, versionId cannot be null"));
    }

    @Test
    public void cannotFindWithEmptyVersionId()
    {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            store.find("test.group", "test-artifact", "");
        });
        Assertions.assertTrue(exception.getMessage().contains("cannot find project version, versionId cannot be null"));
    }

    @Test
    public void canFindVersionByExcludedFlag()
    {
        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");
        ProjectVersionData versionData1 = new ProjectVersionData();
        versionData1.setExcluded(true);
        version1.setVersionData(versionData1);
        store.createOrUpdate(version1);

        StoreProjectVersionData version2 = new StoreProjectVersionData("test.group", "test-artifact", "2.0.0");
        ProjectVersionData versionData2 = new ProjectVersionData();
        versionData2.setExcluded(false);
        version2.setVersionData(versionData2);
        store.createOrUpdate(version2);

        List<StoreProjectVersionData> result = store.findVersion(true);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.get(0).getVersionData().isExcluded());
    }

    @Test
    public void canGetVersionCount()
    {
        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");
        store.createOrUpdate(version1);

        StoreProjectVersionData version2 = new StoreProjectVersionData("test.group", "test-artifact", "2.0.0");
        store.createOrUpdate(version2);

        long count = store.getVersionCount("test.group", "test-artifact");
        Assertions.assertEquals(2, count);
    }

    @Test
    public void canDeleteByGroupIdAndArtifactId()
    {
        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");
        store.createOrUpdate(version1);

        StoreProjectVersionData version2 = new StoreProjectVersionData("test.group", "test-artifact", "2.0.0");
        store.createOrUpdate(version2);

        long deletedCount = store.delete("test.group", "test-artifact");
        Assertions.assertEquals(2, deletedCount);

        List<StoreProjectVersionData> result = store.find("test.group", "test-artifact");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void canDeleteByGroupIdArtifactIdAndVersion()
    {
        StoreProjectVersionData version1 = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");
        store.createOrUpdate(version1);

        StoreProjectVersionData version2 = new StoreProjectVersionData("test.group", "test-artifact", "2.0.0");
        store.createOrUpdate(version2);

        long deletedCount = store.delete("test.group", "test-artifact", "1.0.0");
        Assertions.assertEquals(1, deletedCount);

        List<StoreProjectVersionData> result = store.find("test.group", "test-artifact");
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("2.0.0", result.get(0).getVersionId());
    }

    @Test
    public void canGetCollection()
    {
        Assertions.assertNotNull(store.getCollection());
    }

    @Test
    public void canValidateValidData()
    {
        StoreProjectVersionData version = new StoreProjectVersionData("test.group", "test-artifact", "1.0.0");
        Assertions.assertDoesNotThrow(() -> store.createOrUpdate(version));
    }

    @Test
    public void cannotValidateInvalidGroupId()
    {
        StoreProjectVersionData version = new StoreProjectVersionData("invalid group", "test-artifact", "1.0.0");
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            store.createOrUpdate(version);
        });
        Assertions.assertTrue(exception.getMessage().contains("invalid groupId"));
    }

    @Test
    public void cannotValidateInvalidArtifactId()
    {
        StoreProjectVersionData version = new StoreProjectVersionData("test.group", "invalid artifact", "1.0.0");
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            store.createOrUpdate(version);
        });
        Assertions.assertTrue(exception.getMessage().contains("invalid"));
    }

    @Test
    public void cannotValidateInvalidVersionId()
    {
        StoreProjectVersionData version = new StoreProjectVersionData("test.group", "test-artifact", "invalid version");
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            store.createOrUpdate(version);
        });
        Assertions.assertTrue(exception.getMessage().contains("invalid versionId"));
    }
}
