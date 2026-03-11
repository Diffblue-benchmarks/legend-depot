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

package org.finos.legend.depot.store.mongo.admin;

import com.mongodb.client.model.IndexModel;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestMongoAdminStore extends TestStoreMongo
{
    private MongoAdminStore adminStore = new MongoAdminStore(mongoProvider);

    @Test
    void canCreateAdminStore()
    {
        assertNotNull(adminStore);
        assertEquals("test-db", adminStore.getName());
    }

    @Test
    void canGetAllCollectionsEmpty()
    {
        List<String> collections = adminStore.getAllCollections();
        assertNotNull(collections);
        assertTrue(collections.isEmpty());
    }

    @Test
    void canGetAllCollectionsAfterInsert()
    {
        mongoProvider.getCollection("testCollection").insertOne(new Document("key", "value"));
        List<String> collections = adminStore.getAllCollections();
        assertFalse(collections.isEmpty());
        assertTrue(collections.contains("testCollection"));
    }

    @Test
    void canDeleteCollection()
    {
        mongoProvider.getCollection("toDelete").insertOne(new Document("key", "value"));
        assertTrue(adminStore.getAllCollections().contains("toDelete"));
        adminStore.deleteCollection("toDelete");
        assertFalse(adminStore.getAllCollections().contains("toDelete"));
    }

    @Test
    void canGetAllIndexes()
    {
        mongoProvider.getCollection("indexedCol").insertOne(new Document("key", "value"));
        Map<String, List<Document>> indexes = adminStore.getAllIndexes();
        assertNotNull(indexes);
        assertTrue(indexes.containsKey("indexedCol"));
        assertFalse(indexes.get("indexedCol").isEmpty());
    }

    @Test
    void canRegisterAndCreateIndexes()
    {
        mongoProvider.getCollection("myCol").insertOne(new Document("field1", "value1"));
        List<IndexModel> indexModels = Collections.singletonList(new IndexModel(Indexes.ascending("field1")));
        adminStore.registerIndexes("myCol", indexModels);
        List<String> result = adminStore.createIndexes();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void canRunCommand()
    {
        Document result = adminStore.runCommand(new Document("ping", 1));
        assertNotNull(result);
    }

    @Test
    void canRunPipelineWithDocumentList()
    {
        mongoProvider.getCollection("pipeCol").insertOne(new Document("status", "active"));
        mongoProvider.getCollection("pipeCol").insertOne(new Document("status", "inactive"));

        List<Document> pipeline = Collections.singletonList(
                new Document("$match", new Document("status", "active"))
        );
        List<Document> results = adminStore.runPipeline("pipeCol", pipeline);
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("active", results.get(0).getString("status"));
    }

    @Test
    void canRunPipelineWithJsonString() throws Exception
    {
        mongoProvider.getCollection("jsonPipeCol").insertOne(new Document("name", "test"));

        String jsonPipeline = "[{\"$match\": {\"name\": \"test\"}}]";
        List<Document> results = adminStore.runPipeline("jsonPipeCol", jsonPipeline);
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("test", results.get(0).getString("name"));
    }

    @Test
    void canGetName()
    {
        assertEquals("test-db", adminStore.getName());
    }
}
