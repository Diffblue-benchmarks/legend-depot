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

import com.mongodb.client.MongoDatabase;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import com.mongodb.client.MongoClients;
import org.bson.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

public class MongoAdminStoreTest
{
    private static MongoServer server;
    private static MongoAdminStore adminStore;
    private static MongoDatabase database;

    @BeforeAll
    public static void setup()
    {
        server = new MongoServer(new MemoryBackend());
        InetSocketAddress addr = server.bind();
        String connectionString = "mongodb://" + addr.getHostString() + ":" + addr.getPort();
        database = MongoClients.create(connectionString).getDatabase("admin-test");
        adminStore = new MongoAdminStore(database);
    }

    @AfterAll
    public static void teardown()
    {
        if (server != null)
        {
            server.shutdown();
        }
    }

    @Test
    public void testGetName()
    {
        Assertions.assertEquals("admin-test", adminStore.getName());
    }

    @Test
    public void testGetAllCollectionsEmpty()
    {
        List<String> collections = adminStore.getAllCollections();
        Assertions.assertNotNull(collections);
    }

    @Test
    public void testCreateAndGetCollection()
    {
        database.getCollection("test-collection").insertOne(new Document("key", "value"));
        List<String> collections = adminStore.getAllCollections();
        Assertions.assertTrue(collections.contains("test-collection"));
    }

    @Test
    public void testDeleteCollection()
    {
        database.getCollection("to-delete").insertOne(new Document("key", "value"));
        Assertions.assertTrue(adminStore.getAllCollections().contains("to-delete"));

        adminStore.deleteCollection("to-delete");
        Assertions.assertFalse(adminStore.getAllCollections().contains("to-delete"));
    }

    @Test
    public void testGetAllIndexes()
    {
        database.getCollection("indexed-collection").insertOne(new Document("key", "value"));
        Map<String, List<Document>> indexes = adminStore.getAllIndexes();
        Assertions.assertNotNull(indexes);
    }

    @Test
    public void testRegisterAndCreateIndexes()
    {
        List<String> results = adminStore.createIndexes();
        Assertions.assertNotNull(results);
    }

    @Test
    public void testRunPipelineWithDocuments()
    {
        database.getCollection("pipeline-test").insertOne(new Document("name", "test"));
        Document matchStage = new Document("$match", new Document("name", "test"));
        List<Document> result = adminStore.runPipeline("pipeline-test", java.util.Arrays.asList(matchStage));
        Assertions.assertEquals(1, result.size());
    }
}
