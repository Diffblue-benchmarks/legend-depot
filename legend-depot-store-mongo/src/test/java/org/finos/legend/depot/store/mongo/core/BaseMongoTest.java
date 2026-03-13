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

package org.finos.legend.depot.store.mongo.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexModel;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.finos.legend.depot.store.model.HasIdentifier;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static org.finos.legend.depot.store.mongo.core.BaseMongo.ARTIFACT_ID;
import static org.finos.legend.depot.store.mongo.core.BaseMongo.GROUP_ID;
import static org.finos.legend.depot.store.mongo.core.BaseMongo.VERSION_ID;

public class BaseMongoTest extends TestStoreMongo
{
    private static final String COLLECTION_NAME = "test-collection";
    private TestBaseMongo testStore;

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class TestEntity implements HasIdentifier
    {
        private String id;
        private String groupId;
        private String artifactId;
        private String versionId;
        private String data;

        public TestEntity()
        {
        }

        public TestEntity(String id, String groupId, String artifactId, String versionId, String data)
        {
            this.id = id;
            this.groupId = groupId;
            this.artifactId = artifactId;
            this.versionId = versionId;
            this.data = data;
        }

        @Override
        public String getId()
        {
            return id;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public String getGroupId()
        {
            return groupId;
        }

        public void setGroupId(String groupId)
        {
            this.groupId = groupId;
        }

        public String getArtifactId()
        {
            return artifactId;
        }

        public void setArtifactId(String artifactId)
        {
            this.artifactId = artifactId;
        }

        public String getVersionId()
        {
            return versionId;
        }

        public void setVersionId(String versionId)
        {
            this.versionId = versionId;
        }

        public String getData()
        {
            return data;
        }

        public void setData(String data)
        {
            this.data = data;
        }
    }

    static class TestBaseMongo extends BaseMongo<TestEntity>
    {
        private final String collectionName;

        public TestBaseMongo(com.mongodb.client.MongoDatabase database, String collectionName)
        {
            super(database, TestEntity.class);
            this.collectionName = collectionName;
        }

        public TestBaseMongo(com.mongodb.client.MongoDatabase database, String collectionName, ObjectMapper objectMapper)
        {
            super(database, TestEntity.class, objectMapper);
            this.collectionName = collectionName;
        }

        @Override
        protected MongoCollection getCollection()
        {
            return getDatabase().getCollection(collectionName);
        }

        @Override
        protected Bson getKeyFilter(TestEntity data)
        {
            return eq("id", data.getId());
        }

        @Override
        protected void validateNewData(TestEntity data)
        {
            if (data == null || data.getId() == null)
            {
                throw new IllegalArgumentException("Invalid data");
            }
        }
    }

    @BeforeEach
    public void setupData()
    {
        testStore = new TestBaseMongo(getMongoDatabase(), COLLECTION_NAME);
    }

    @Test
    public void canCreateBaseMongoWithDefaultObjectMapper()
    {
        TestBaseMongo store = new TestBaseMongo(getMongoDatabase(), COLLECTION_NAME);
        Assertions.assertNotNull(store);
    }

    @Test
    public void canCreateBaseMongoWithCustomObjectMapper()
    {
        ObjectMapper customMapper = new ObjectMapper();
        TestBaseMongo store = new TestBaseMongo(getMongoDatabase(), COLLECTION_NAME, customMapper);
        Assertions.assertNotNull(store);
    }

    @Test
    public void canBuildDocumentFromEntity()
    {
        TestEntity entity = new TestEntity("test-id", "group1", "artifact1", "1.0.0", "test-data");
        Document doc = BaseMongo.buildDocument(entity);

        Assertions.assertNotNull(doc);
        Assertions.assertFalse(doc.containsKey("_id"));
        Assertions.assertFalse(doc.containsKey("id"));
        Assertions.assertEquals("group1", doc.getString("groupId"));
        Assertions.assertEquals("artifact1", doc.getString("artifactId"));
        Assertions.assertEquals("1.0.0", doc.getString("versionId"));
        Assertions.assertEquals("test-data", doc.getString("data"));
    }

    @Test
    public void canGetDatabase()
    {
        Assertions.assertNotNull(testStore.getDatabase());
    }

    @Test
    public void canGetMongoCollection()
    {
        MongoCollection collection = testStore.getMongoCollection(COLLECTION_NAME);
        Assertions.assertNotNull(collection);
        Assertions.assertEquals(COLLECTION_NAME, collection.getNamespace().getCollectionName());
    }

    @Test
    public void canCreateIndexesIfAbsent()
    {
        IndexModel index1 = BaseMongo.buildIndex("test-index-1", "groupId", "artifactId");
        IndexModel index2 = BaseMongo.buildIndex("test-index-2", "versionId");
        List<IndexModel> indexes = Arrays.asList(index1, index2);

        List<String> result = BaseMongo.createIndexesIfAbsent(getMongoDatabase(), COLLECTION_NAME, indexes);
        Assertions.assertNotNull(result);
    }

    @Test
    public void canSkipExistingIndexes()
    {
        IndexModel index = BaseMongo.buildIndex("test-index-exists", "groupId");
        BaseMongo.createIndexesIfAbsent(getMongoDatabase(), COLLECTION_NAME, Arrays.asList(index));

        List<String> result = BaseMongo.createIndexesIfAbsent(getMongoDatabase(), COLLECTION_NAME, Arrays.asList(index));
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void canGetArtifactAndVersionFilter()
    {
        Bson filter = testStore.getArtifactAndVersionFilter("group1", "artifact1", "1.0.0");
        Assertions.assertNotNull(filter);
    }

    @Test
    public void canGetArtifactFilter()
    {
        Bson filter = testStore.getArtifactFilter("group1", "artifact1");
        Assertions.assertNotNull(filter);
    }

    @Test
    public void canCreateOrUpdateNewEntity()
    {
        TestEntity entity = new TestEntity("new-id", "group1", "artifact1", "1.0.0", "new-data");
        TestEntity result = testStore.createOrUpdate(entity);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("group1", result.getGroupId());
        Assertions.assertEquals("artifact1", result.getArtifactId());
        Assertions.assertEquals("1.0.0", result.getVersionId());
        Assertions.assertEquals("new-data", result.getData());
    }

    @Test
    public void canCreateOrUpdateExistingEntity()
    {
        TestEntity entity = new TestEntity("existing-id", "group1", "artifact1", "1.0.0", "original-data");
        testStore.createOrUpdate(entity);

        entity.setData("updated-data");
        TestEntity result = testStore.createOrUpdate(entity);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("updated-data", result.getData());
    }

    @Test
    public void canCreateOrUpdateMultipleEntities()
    {
        TestEntity entity1 = new TestEntity("id1", "group1", "artifact1", "1.0.0", "data1");
        TestEntity entity2 = new TestEntity("id2", "group2", "artifact2", "2.0.0", "data2");
        List<TestEntity> entities = Arrays.asList(entity1, entity2);

        List<TestEntity> results = testStore.createOrUpdate(entities);

        Assertions.assertEquals(2, results.size());
        Assertions.assertEquals("data1", results.get(0).getData());
        Assertions.assertEquals("data2", results.get(1).getData());
    }

    @Test
    public void canInsertEntity()
    {
        TestEntity entity = new TestEntity("insert-id", "group1", "artifact1", "1.0.0", "insert-data");
        testStore.insert(entity);

        List<TestEntity> all = testStore.getAllStoredEntities();
        Assertions.assertEquals(1, all.size());
        Assertions.assertNotNull(all.get(0).getId());
        Assertions.assertEquals("insert-data", all.get(0).getData());
    }

    @Test
    public void canGetAllStoredEntities()
    {
        TestEntity entity1 = new TestEntity("id1", "group1", "artifact1", "1.0.0", "data1");
        TestEntity entity2 = new TestEntity("id2", "group2", "artifact2", "2.0.0", "data2");
        testStore.insert(entity1);
        testStore.insert(entity2);

        List<TestEntity> results = testStore.getAllStoredEntities();

        Assertions.assertEquals(2, results.size());
    }

    @Test
    public void canGetStoredEntitiesByPage()
    {
        for (int i = 0; i < 10; i++)
        {
            TestEntity entity = new TestEntity("id" + i, "group" + i, "artifact" + i, "1.0.0", "data" + i);
            testStore.insert(entity);
        }

        List<TestEntity> page1 = testStore.getStoredEntitiesByPage(1, 3);
        Assertions.assertEquals(3, page1.size());

        List<TestEntity> page2 = testStore.getStoredEntitiesByPage(2, 3);
        Assertions.assertEquals(3, page2.size());
    }

    @Test
    public void canConvertDocumentToEntity()
    {
        TestEntity entity = new TestEntity("convert-id", "group1", "artifact1", "1.0.0", "convert-data");
        Document doc = BaseMongo.buildDocument(entity);
        doc.put("id", "convert-id");

        TestEntity result = testStore.convert(doc, TestEntity.class);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("convert-id", result.getId());
        Assertions.assertEquals("group1", result.getGroupId());
    }

    @Test
    public void canConvertNullDocument()
    {
        TestEntity result = BaseMongo.convert(new ObjectMapper(), null, TestEntity.class);
        Assertions.assertNull(result);
    }

    @Test
    public void canConvertFindIterable()
    {
        TestEntity entity1 = new TestEntity("id1", "group1", "artifact1", "1.0.0", "data1");
        TestEntity entity2 = new TestEntity("id2", "group2", "artifact2", "2.0.0", "data2");
        testStore.insert(entity1);
        testStore.insert(entity2);

        FindIterable<Document> iterable = testStore.executeFind(eq(GROUP_ID, "group1"));
        List<TestEntity> results = testStore.convert(iterable);

        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals("group1", results.get(0).getGroupId());
    }

    @Test
    public void canBuildIndexWithDefaultOptions()
    {
        IndexModel index = BaseMongo.buildIndex("test-index", "field1", "field2");

        Assertions.assertNotNull(index);
        Assertions.assertEquals("test-index", index.getOptions().getName());
        Assertions.assertFalse(index.getOptions().isUnique());
    }

    @Test
    public void canBuildUniqueIndex()
    {
        IndexModel index = BaseMongo.buildIndex("unique-index", true, "field1");

        Assertions.assertNotNull(index);
        Assertions.assertEquals("unique-index", index.getOptions().getName());
        Assertions.assertTrue(index.getOptions().isUnique());
    }

    @Test
    public void canBuildIndexWithCustomOptions()
    {
        IndexOptions options = new IndexOptions().sparse(true);
        IndexModel index = BaseMongo.buildIndex("custom-index", options, "field1");

        Assertions.assertNotNull(index);
        Assertions.assertEquals("custom-index", index.getOptions().getName());
        Assertions.assertTrue(index.getOptions().isSparse());
    }

    @Test
    public void canFindEntitiesWithFilter()
    {
        TestEntity entity1 = new TestEntity("id1", "group1", "artifact1", "1.0.0", "data1");
        TestEntity entity2 = new TestEntity("id2", "group1", "artifact2", "2.0.0", "data2");
        testStore.insert(entity1);
        testStore.insert(entity2);

        List<TestEntity> results = testStore.find(eq(GROUP_ID, "group1"));

        Assertions.assertEquals(2, results.size());
    }

    @Test
    public void canFindOneEntity()
    {
        TestEntity entity = new TestEntity("unique-id", "group1", "artifact1", "1.0.0", "data1");
        testStore.insert(entity);

        Optional<TestEntity> result = testStore.findOne(eq(GROUP_ID, "group1"));

        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get().getId());
        Assertions.assertEquals("group1", result.get().getGroupId());
    }

    @Test
    public void canFindOneReturnsEmptyWhenNotFound()
    {
        Optional<TestEntity> result = testStore.findOne(eq(GROUP_ID, "nonexistent"));

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void canExecuteFind()
    {
        TestEntity entity = new TestEntity("id1", "group1", "artifact1", "1.0.0", "data1");
        testStore.insert(entity);

        FindIterable<Document> iterable = testStore.executeFind(eq(GROUP_ID, "group1"));

        Assertions.assertNotNull(iterable);
    }

    @Test
    public void canCountDocuments()
    {
        TestEntity entity1 = new TestEntity("id1", "group1", "artifact1", "1.0.0", "data1");
        TestEntity entity2 = new TestEntity("id2", "group1", "artifact2", "2.0.0", "data2");
        testStore.insert(entity1);
        testStore.insert(entity2);

        long count = testStore.count(eq(GROUP_ID, "group1"));

        Assertions.assertEquals(2, count);
    }

    @Test
    public void canDeleteDocuments()
    {
        TestEntity entity1 = new TestEntity("id1", "group1", "artifact1", "1.0.0", "data1");
        TestEntity entity2 = new TestEntity("id2", "group1", "artifact2", "2.0.0", "data2");
        testStore.insert(entity1);
        testStore.insert(entity2);

        long deleted = testStore.delete(eq(GROUP_ID, "group1"));

        Assertions.assertEquals(2, deleted);
        Assertions.assertEquals(0, testStore.getAllStoredEntities().size());
    }

    @Test
    public void findOneThrowsExceptionWhenMultipleMatches()
    {
        TestEntity entity1 = new TestEntity("id1", "group1", "artifact1", "1.0.0", "data1");
        TestEntity entity2 = new TestEntity("id2", "group1", "artifact1", "1.0.0", "data2");
        testStore.insert(entity1);
        testStore.insert(entity2);

        Assertions.assertThrows(IllegalStateException.class, () -> {
            testStore.findOne(eq(GROUP_ID, "group1"));
        });
    }
}
