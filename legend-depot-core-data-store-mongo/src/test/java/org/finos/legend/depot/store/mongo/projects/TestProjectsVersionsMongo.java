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

import com.mongodb.client.model.IndexModel;
import org.finos.legend.depot.store.model.projects.StoreProjectVersionData;
import org.finos.legend.depot.store.mongo.CoreDataMongoStoreTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class TestProjectsVersionsMongo extends CoreDataMongoStoreTests
{
    private ProjectsVersionsMongo projectsVersionsMongo = new ProjectsVersionsMongo(mongoProvider);

    @BeforeEach
    public void setUpTestData()
    {
        setUpProjectsVersionsFromFile(this.getClass().getClassLoader().getResource("data/projectsVersions.json"));
    }

    @Test
    public void canBuildIndexes()
    {
        List<IndexModel> indexes = ProjectsVersionsMongo.buildIndexes();
        Assertions.assertNotNull(indexes);
        Assertions.assertFalse(indexes.isEmpty());
    }

    @Test
    public void canFindThrowsExceptionForNullVersionId()
    {
        Assertions.assertThrows(IllegalArgumentException.class, () -> projectsVersionsMongo.find("examples.metadata", "test", null));
    }

    @Test
    public void canFindThrowsExceptionForEmptyVersionId()
    {
        Assertions.assertThrows(IllegalArgumentException.class, () -> projectsVersionsMongo.find("examples.metadata", "test", ""));
    }

    @Test
    public void canGetVersionCount()
    {
        long count = projectsVersionsMongo.getVersionCount("examples.metadata", "test");
        Assertions.assertEquals(4, count);
    }

    @Test
    public void canGetVersionCountForNonExistentProject()
    {
        long count = projectsVersionsMongo.getVersionCount("non.existent", "artifact");
        Assertions.assertEquals(0, count);
    }

    @Test
    public void canDeleteByGroupAndArtifact()
    {
        List<StoreProjectVersionData> before = projectsVersionsMongo.find("examples.metadata", "test");
        Assertions.assertEquals(4, before.size());

        long deleted = projectsVersionsMongo.delete("examples.metadata", "test");
        Assertions.assertTrue(deleted > 0);

        List<StoreProjectVersionData> after = projectsVersionsMongo.find("examples.metadata", "test");
        Assertions.assertTrue(after.isEmpty());
    }

    @Test
    public void canDeleteByGroupArtifactAndVersion()
    {
        Optional<StoreProjectVersionData> before = projectsVersionsMongo.find("examples.metadata", "test", "2.2.0");
        Assertions.assertTrue(before.isPresent());

        long deleted = projectsVersionsMongo.delete("examples.metadata", "test", "2.2.0");
        Assertions.assertEquals(1, deleted);

        Optional<StoreProjectVersionData> after = projectsVersionsMongo.find("examples.metadata", "test", "2.2.0");
        Assertions.assertFalse(after.isPresent());
    }

    @Test
    public void canValidateNewDataWithInvalidVersionId()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("examples.metadata", "test", "not-a-version");
        Assertions.assertThrows(IllegalArgumentException.class, () -> projectsVersionsMongo.createOrUpdate(data));
    }
}
