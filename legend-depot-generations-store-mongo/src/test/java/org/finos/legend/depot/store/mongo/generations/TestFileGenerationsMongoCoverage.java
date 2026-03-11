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

package org.finos.legend.depot.store.mongo.generations;

import com.mongodb.client.model.IndexModel;
import org.finos.legend.depot.domain.generation.DepotGeneration;
import org.finos.legend.depot.store.model.generations.StoredFileGeneration;
import org.finos.legend.depot.store.api.generations.UpdateFileGenerations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestFileGenerationsMongoCoverage extends TestGenerationsStoreMongo
{

    private FileGenerationsMongo generations = new FileGenerationsMongo(mongoProvider);
    private static final String TEST_GROUP_ID = "examples.metadata";
    private static final String TEST_ARTIFACT_ID = "test";

    @BeforeEach
    public void loadData()
    {
        setUpFileGenerationFromFile(this.getClass().getClassLoader().getResource("data/file-generations.json"), mongoProvider);
    }

    @Test
    public void canBuildIndexes()
    {
        List<IndexModel> indexes = FileGenerationsMongo.buildIndexes();
        Assertions.assertNotNull(indexes);
        Assertions.assertEquals(2, indexes.size());
    }

    @Test
    public void canCreateOrUpdateAndExerciseKeyFilter()
    {
        StoredFileGeneration gen = new StoredFileGeneration(
                TEST_GROUP_ID, TEST_ARTIFACT_ID, "1.0.0",
                "com::testPath", "avro",
                new DepotGeneration("/test/path/file.avro", "some content"));

        List<StoredFileGeneration> result = generations.createOrUpdate(Collections.singletonList(gen));
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        List<StoredFileGeneration> found = generations.find(TEST_GROUP_ID, TEST_ARTIFACT_ID, "1.0.0");
        Assertions.assertEquals(1, found.size());

        // Update the same record to exercise getKeyFilter matching existing data
        StoredFileGeneration updated = new StoredFileGeneration(
                TEST_GROUP_ID, TEST_ARTIFACT_ID, "1.0.0",
                "com::testPath", "avro",
                new DepotGeneration("/test/path/file.avro", "updated content"));

        List<StoredFileGeneration> updateResult = generations.createOrUpdate(Collections.singletonList(updated));
        Assertions.assertNotNull(updateResult);

        // Should still be 1 record (updated, not duplicated)
        List<StoredFileGeneration> foundAfterUpdate = generations.find(TEST_GROUP_ID, TEST_ARTIFACT_ID, "1.0.0");
        Assertions.assertEquals(1, foundAfterUpdate.size());
    }

    @Test
    public void canFindByTypeAndElementPath()
    {
        List<StoredFileGeneration> result = generations.findByTypeAndElementPath(
                TEST_GROUP_ID, TEST_ARTIFACT_ID, "2.3.3", "avro", "com::avrogen");
        Assertions.assertEquals(3, result.size());

        List<StoredFileGeneration> noResult = generations.findByTypeAndElementPath(
                TEST_GROUP_ID, TEST_ARTIFACT_ID, "2.3.3", "java", "com::avrogen");
        Assertions.assertEquals(0, noResult.size());
    }

    @Test
    public void canDelete()
    {
        Assertions.assertTrue(generations.getAll().size() > 0);

        long deleted = generations.delete(TEST_GROUP_ID, TEST_ARTIFACT_ID, "2.3.3");
        Assertions.assertTrue(deleted > 0);

        List<StoredFileGeneration> remaining = generations.find(TEST_GROUP_ID, TEST_ARTIFACT_ID, "2.3.3");
        Assertions.assertEquals(0, remaining.size());
    }

    @Test
    public void canDeleteNonExistentVersion()
    {
        long deleted = generations.delete(TEST_GROUP_ID, TEST_ARTIFACT_ID, "99.99.99");
        Assertions.assertEquals(0, deleted);
    }
}
