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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.model.IndexModel;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestMongoAdminStore extends TestStoreMongo
{
    private final MongoAdminStore mongoAdminStore = new MongoAdminStore(mongoProvider);

    @Test
    public void canGetName()
    {
        String name = mongoAdminStore.getName();
        Assertions.assertNotNull(name);
        Assertions.assertEquals("test-db", name);
    }

    @Test
    public void canGetAllCollections()
    {
        mongoProvider.getCollection("testCollection1").insertOne(new Document("field", "value1"));
        mongoProvider.getCollection("testCollection2").insertOne(new Document("field", "value2"));

        List<String> collections = mongoAdminStore.getAllCollections();
        Assertions.assertNotNull(collections);
        Assertions.assertTrue(collections.contains("testCollection1"));
        Assertions.assertTrue(collections.contains("testCollection2"));
    }

    @Test
    public void canDeleteCollection()
    {
        mongoProvider.getCollection("toDelete").insertOne(new Document("field", "value"));
        List<String> collectionsBefore = mongoAdminStore.getAllCollections();
        Assertions.assertTrue(collectionsBefore.contains("toDelete"));

        mongoAdminStore.deleteCollection("toDelete");

        List<String> collectionsAfter = mongoAdminStore.getAllCollections();
        Assertions.assertFalse(collectionsAfter.contains("toDelete"));
    }

    @Test
    public void canGetAllIndexes()
    {
        mongoProvider.getCollection("testCollection").insertOne(new Document("field1", "value1").append("field2", "value2"));
        mongoProvider.getCollection("testCollection").createIndex(Indexes.ascending("field1"));

        Map<String, List<Document>> indexes = mongoAdminStore.getAllIndexes();
        Assertions.assertNotNull(indexes);
        Assertions.assertTrue(indexes.containsKey("testCollection"));
        List<Document> collectionIndexes = indexes.get("testCollection");
        Assertions.assertNotNull(collectionIndexes);
        Assertions.assertTrue(collectionIndexes.size() > 0);
    }

    @Test
    public void canRegisterIndexes()
    {
        IndexModel indexModel = new IndexModel(Indexes.ascending("field1"), new IndexOptions().name("registered_index"));
        List<IndexModel> indexes = Arrays.asList(indexModel);

        mongoAdminStore.registerIndexes("testCollection", indexes);

        Assertions.assertNotNull(mongoAdminStore);
    }

    @Test
    public void canCreateIndexes()
    {
        mongoProvider.getCollection("testCollection").insertOne(new Document("field1", "value1"));

        IndexModel indexModel = new IndexModel(Indexes.ascending("field1"), new IndexOptions().name("created_index"));
        mongoAdminStore.registerIndexes("testCollection", Arrays.asList(indexModel));

        List<String> results = mongoAdminStore.createIndexes();
        Assertions.assertNotNull(results);
    }

    @Test
    public void canRunCommand()
    {
        Document command = new Document("ping", 1);
        Document result = mongoAdminStore.runCommand(command);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1.0, result.get("ok"));
    }

    @Test
    public void canRunPipeline()
    {
        mongoProvider.getCollection("testCollection").insertOne(new Document("groupId", "group1").append("artifactId", "artifact1"));
        mongoProvider.getCollection("testCollection").insertOne(new Document("groupId", "group1").append("artifactId", "artifact2"));
        mongoProvider.getCollection("testCollection").insertOne(new Document("groupId", "group2").append("artifactId", "artifact3"));

        List<Document> pipeline = Arrays.asList(
                new Document("$limit", 1000L),
                new Document("$project",
                        new Document("groupId", "$groupId")
                                .append("artifactId", "$artifactId"))
        );

        List<Document> result = mongoAdminStore.runPipeline("testCollection", pipeline);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void canRunPipelineAsJson() throws JsonProcessingException
    {
        mongoProvider.getCollection("testCollection").insertOne(new Document("groupId", "group1").append("artifactId", "artifact1"));
        mongoProvider.getCollection("testCollection").insertOne(new Document("groupId", "group1").append("artifactId", "artifact2"));

        String pipeline = "[\n" +
                "  {\n" +
                "    \"$limit\": 1000\n" +
                "  }, {\n" +
                "  \"$project\": {\n" +
                "    \"groupId\": \"$groupId\",\n" +
                "    \"artifactId\": \"$artifactId\"\n" +
                "  }\n" +
                "}\n" +
                "]";

        List<Document> result = mongoAdminStore.runPipeline("testCollection", pipeline);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }
}
