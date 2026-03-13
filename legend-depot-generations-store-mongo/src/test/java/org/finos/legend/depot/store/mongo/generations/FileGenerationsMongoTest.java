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

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexModel;
import org.finos.legend.depot.domain.generation.DepotGeneration;
import org.finos.legend.depot.store.model.generations.StoredFileGeneration;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileGenerationsMongoTest extends TestStoreMongo
{
    private FileGenerationsMongo fileGenerationsMongo;

    @BeforeEach
    public void setUp()
    {
        fileGenerationsMongo = new FileGenerationsMongo(getMongoDatabase());
    }

    @AfterEach
    public void tearDown()
    {
        if (fileGenerationsMongo != null)
        {
            getMongoDatabase().getCollection(FileGenerationsMongo.COLLECTION).drop();
        }
    }

    private StoredFileGeneration createTestFileGeneration(String groupId, String artifactId, String versionId, String path, String type, String filePath)
    {
        DepotGeneration depotGeneration = new DepotGeneration(filePath, "test content");
        return new StoredFileGeneration(groupId, artifactId, versionId, path, type, depotGeneration);
    }

    @Test
    public void canConstructFileGenerationsMongo()
    {
        FileGenerationsMongo store = new FileGenerationsMongo(getMongoDatabase());
        assertNotNull(store);
    }

    @Test
    public void canGetCollection()
    {
        MongoCollection collection = fileGenerationsMongo.getCollection();
        assertNotNull(collection);
        assertEquals(FileGenerationsMongo.COLLECTION, collection.getNamespace().getCollectionName());
    }

    @Test
    public void canGetAll()
    {
        StoredFileGeneration generation1 = createTestFileGeneration("test.group", "test-artifact", "1.0.0", "/path/to/element", "avro", "/file/path1.avro");
        StoredFileGeneration generation2 = createTestFileGeneration("test.group", "test-artifact", "1.0.0", "/path/to/element2", "java", "/file/path2.java");

        fileGenerationsMongo.createOrUpdate(generation1);
        fileGenerationsMongo.createOrUpdate(generation2);

        List<StoredFileGeneration> allGenerations = fileGenerationsMongo.getAll();
        assertNotNull(allGenerations);
        assertEquals(2, allGenerations.size());
    }

    @Test
    public void canBuildIndexes()
    {
        List<IndexModel> indexes = FileGenerationsMongo.buildIndexes();
        assertNotNull(indexes);
        assertEquals(2, indexes.size());
    }

    @Test
    public void canValidateNewData()
    {
        StoredFileGeneration generation = createTestFileGeneration("test.group", "test-artifact", "1.0.0", "/path/to/element", "avro", "/file/path.avro");
        fileGenerationsMongo.validateNewData(generation);
    }

    @Test
    public void canFindByGroupArtifactVersion()
    {
        StoredFileGeneration generation1 = createTestFileGeneration("test.group", "test-artifact", "1.0.0", "/path/to/element", "avro", "/file/path1.avro");
        StoredFileGeneration generation2 = createTestFileGeneration("test.group", "test-artifact", "1.0.0", "/path/to/element2", "java", "/file/path2.java");
        StoredFileGeneration generation3 = createTestFileGeneration("test.group", "other-artifact", "1.0.0", "/path/to/element3", "avro", "/file/path3.avro");

        fileGenerationsMongo.createOrUpdate(generation1);
        fileGenerationsMongo.createOrUpdate(generation2);
        fileGenerationsMongo.createOrUpdate(generation3);

        List<StoredFileGeneration> results = fileGenerationsMongo.find("test.group", "test-artifact", "1.0.0");
        assertNotNull(results);
        assertEquals(2, results.size());
    }

    @Test
    public void canFindByElementPath()
    {
        StoredFileGeneration generation1 = createTestFileGeneration("test.group", "test-artifact", "1.0.0", "/path/to/element", "avro", "/file/path1.avro");
        StoredFileGeneration generation2 = createTestFileGeneration("test.group", "test-artifact", "1.0.0", "/path/to/element", "java", "/file/path2.java");
        StoredFileGeneration generation3 = createTestFileGeneration("test.group", "test-artifact", "1.0.0", "/path/to/other", "avro", "/file/path3.avro");

        fileGenerationsMongo.createOrUpdate(generation1);
        fileGenerationsMongo.createOrUpdate(generation2);
        fileGenerationsMongo.createOrUpdate(generation3);

        List<StoredFileGeneration> results = fileGenerationsMongo.findByElementPath("test.group", "test-artifact", "1.0.0", "/path/to/element");
        assertNotNull(results);
        assertEquals(2, results.size());
    }

    @Test
    public void canFindByFilePath()
    {
        StoredFileGeneration generation1 = createTestFileGeneration("test.group", "test-artifact", "1.0.0", "/path/to/element", "avro", "/file/path1.avro");
        StoredFileGeneration generation2 = createTestFileGeneration("test.group", "test-artifact", "1.0.0", "/path/to/element2", "java", "/file/path2.java");

        fileGenerationsMongo.createOrUpdate(generation1);
        fileGenerationsMongo.createOrUpdate(generation2);

        Optional<StoredFileGeneration> result = fileGenerationsMongo.findByFilePath("test.group", "test-artifact", "1.0.0", "/file/path1.avro");
        assertTrue(result.isPresent());
        assertEquals("/file/path1.avro", result.get().getFile().getPath());
    }

    @Test
    public void canFindByFilePathReturnsEmpty()
    {
        Optional<StoredFileGeneration> result = fileGenerationsMongo.findByFilePath("test.group", "test-artifact", "1.0.0", "/nonexistent/path");
        assertFalse(result.isPresent());
    }

    @Test
    public void canFindByType()
    {
        StoredFileGeneration generation1 = createTestFileGeneration("test.group", "test-artifact", "1.0.0", "/path/to/element", "avro", "/file/path1.avro");
        StoredFileGeneration generation2 = createTestFileGeneration("test.group", "test-artifact", "1.0.0", "/path/to/element2", "avro", "/file/path2.avro");
        StoredFileGeneration generation3 = createTestFileGeneration("test.group", "test-artifact", "1.0.0", "/path/to/element3", "java", "/file/path3.java");

        fileGenerationsMongo.createOrUpdate(generation1);
        fileGenerationsMongo.createOrUpdate(generation2);
        fileGenerationsMongo.createOrUpdate(generation3);

        List<StoredFileGeneration> results = fileGenerationsMongo.findByType("test.group", "test-artifact", "1.0.0", "avro");
        assertNotNull(results);
        assertEquals(2, results.size());
    }

    @Test
    public void canFindByTypeAndElementPath()
    {
        StoredFileGeneration generation1 = createTestFileGeneration("test.group", "test-artifact", "1.0.0", "/path/to/element", "avro", "/file/path1.avro");
        StoredFileGeneration generation2 = createTestFileGeneration("test.group", "test-artifact", "1.0.0", "/path/to/element", "java", "/file/path2.java");
        StoredFileGeneration generation3 = createTestFileGeneration("test.group", "test-artifact", "1.0.0", "/path/to/other", "avro", "/file/path3.avro");

        fileGenerationsMongo.createOrUpdate(generation1);
        fileGenerationsMongo.createOrUpdate(generation2);
        fileGenerationsMongo.createOrUpdate(generation3);

        List<StoredFileGeneration> results = fileGenerationsMongo.findByTypeAndElementPath("test.group", "test-artifact", "1.0.0", "avro", "/path/to/element");
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("avro", results.get(0).getType());
        assertEquals("/path/to/element", results.get(0).getPath());
    }

    @Test
    public void canDeleteByGroupArtifactVersion()
    {
        StoredFileGeneration generation1 = createTestFileGeneration("test.group", "test-artifact", "1.0.0", "/path/to/element", "avro", "/file/path1.avro");
        StoredFileGeneration generation2 = createTestFileGeneration("test.group", "test-artifact", "1.0.0", "/path/to/element2", "java", "/file/path2.java");
        StoredFileGeneration generation3 = createTestFileGeneration("test.group", "other-artifact", "1.0.0", "/path/to/element3", "avro", "/file/path3.avro");

        fileGenerationsMongo.createOrUpdate(generation1);
        fileGenerationsMongo.createOrUpdate(generation2);
        fileGenerationsMongo.createOrUpdate(generation3);

        long deletedCount = fileGenerationsMongo.delete("test.group", "test-artifact", "1.0.0");
        assertEquals(2, deletedCount);

        List<StoredFileGeneration> remaining = fileGenerationsMongo.find("test.group", "test-artifact", "1.0.0");
        assertTrue(remaining.isEmpty());

        List<StoredFileGeneration> otherRemaining = fileGenerationsMongo.find("test.group", "other-artifact", "1.0.0");
        assertEquals(1, otherRemaining.size());
    }

    @Test
    public void canDeleteReturnsZeroWhenNoMatches()
    {
        long deletedCount = fileGenerationsMongo.delete("test.group", "nonexistent-artifact", "1.0.0");
        assertEquals(0, deletedCount);
    }
}
