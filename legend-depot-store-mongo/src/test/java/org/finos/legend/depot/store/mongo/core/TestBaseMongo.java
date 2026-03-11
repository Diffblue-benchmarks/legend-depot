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
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexModel;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.finos.legend.depot.store.model.HasIdentifier;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestBaseMongo extends TestStoreMongo
{
    private static final String COLLECTION_NAME = "test-collection";

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TestData implements HasIdentifier
    {
        private String id;
        private String groupId;
        private String artifactId;
        private String versionId;
        private String name;

        public TestData()
        {
        }

        public TestData(String groupId, String artifactId, String versionId, String name)
        {
            this.groupId = groupId;
            this.artifactId = artifactId;
            this.versionId = versionId;
            this.name = name;
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

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }
    }

    static class TestBaseMongImpl extends BaseMongo<TestData>
    {
        private final String collectionName;

        public TestBaseMongImpl(com.mongodb.client.MongoDatabase mongoDatabase, String collectionName)
        {
            super(mongoDatabase, TestData.class);
            this.collectionName = collectionName;
        }

        public TestBaseMongImpl(com.mongodb.client.MongoDatabase mongoDatabase, String collectionName, ObjectMapper objectMapper)
        {
            super(mongoDatabase, TestData.class, objectMapper);
            this.collectionName = collectionName;
        }

        @Override
        protected MongoCollection getCollection()
        {
            return getMongoCollection(collectionName);
        }

        @Override
        protected Bson getKeyFilter(TestData data)
        {
            return getArtifactAndVersionFilter(data.getGroupId(), data.getArtifactId(), data.getVersionId());
        }

        @Override
        protected void validateNewData(TestData data)
        {
            if (data.getGroupId() == null || data.getArtifactId() == null)
            {
                throw new IllegalArgumentException("groupId and artifactId are required");
            }
        }
    }

    private final TestBaseMongImpl store = new TestBaseMongImpl(mongoProvider, COLLECTION_NAME);

    @Test
    void canInstantiateWithCustomObjectMapper()
    {
        ObjectMapper mapper = new ObjectMapper();
        TestBaseMongImpl customStore = new TestBaseMongImpl(mongoProvider, COLLECTION_NAME, mapper);
        assertNotNull(customStore.getDatabase());
    }

    @Test
    void canGetDatabase()
    {
        assertNotNull(store.getDatabase());
        assertEquals(mongoProvider, store.getDatabase());
    }

    @Test
    void canBuildDocument()
    {
        TestData data = new TestData("org.test", "artifact", "1.0.0", "test-name");
        Document doc = BaseMongo.buildDocument(data);

        assertNotNull(doc);
        assertEquals("org.test", doc.getString("groupId"));
        assertEquals("artifact", doc.getString("artifactId"));
        assertEquals("1.0.0", doc.getString("versionId"));
        assertNull(doc.get("_id"));
        assertNull(doc.get("id"));
    }

    @Test
    void canCreateOrUpdateSingleItem()
    {
        TestData data = new TestData("org.test", "artifact", "1.0.0", "test-name");
        TestData result = store.createOrUpdate(data);

        assertNotNull(result);
        assertEquals("org.test", result.getGroupId());
        assertEquals("artifact", result.getArtifactId());
        assertEquals("1.0.0", result.getVersionId());
    }

    @Test
    void canCreateOrUpdateMultipleItems()
    {
        TestData data1 = new TestData("org.test", "artifact1", "1.0.0", "name1");
        TestData data2 = new TestData("org.test", "artifact2", "2.0.0", "name2");

        List<TestData> results = store.createOrUpdate(Arrays.asList(data1, data2));

        assertEquals(2, results.size());
    }

    @Test
    void canInsertItem()
    {
        TestData data = new TestData("org.test", "artifact", "1.0.0", "test-insert");
        store.insert(data);

        List<TestData> all = store.getAllStoredEntities();
        assertEquals(1, all.size());
        assertEquals("test-insert", all.get(0).getName());
    }

    @Test
    void canGetAllStoredEntities()
    {
        store.insert(new TestData("org.test", "artifact1", "1.0.0", "name1"));
        store.insert(new TestData("org.test", "artifact2", "2.0.0", "name2"));

        List<TestData> all = store.getAllStoredEntities();
        assertEquals(2, all.size());
    }

    @Test
    void canGetStoredEntitiesByPage()
    {
        store.insert(new TestData("org.test", "a1", "1.0", "n1"));
        store.insert(new TestData("org.test", "a2", "2.0", "n2"));
        store.insert(new TestData("org.test", "a3", "3.0", "n3"));

        List<TestData> page1 = store.getStoredEntitiesByPage(1, 2);
        assertEquals(2, page1.size());

        List<TestData> page2 = store.getStoredEntitiesByPage(2, 2);
        assertEquals(1, page2.size());
    }

    @Test
    void canFindWithFilter()
    {
        store.insert(new TestData("org.test", "artifact1", "1.0.0", "name1"));
        store.insert(new TestData("org.test", "artifact2", "2.0.0", "name2"));

        List<TestData> results = store.find(eq("artifactId", "artifact1"));
        assertEquals(1, results.size());
        assertEquals("artifact1", results.get(0).getArtifactId());
    }

    @Test
    void canFindOneWithFilter()
    {
        store.insert(new TestData("org.test", "artifact1", "1.0.0", "name1"));

        Optional<TestData> result = store.findOne(eq("artifactId", "artifact1"));
        assertTrue(result.isPresent());
        assertEquals("artifact1", result.get().getArtifactId());
    }

    @Test
    void findOneReturnsEmptyWhenNoMatch()
    {
        Optional<TestData> result = store.findOne(eq("artifactId", "nonexistent"));
        assertFalse(result.isPresent());
    }

    @Test
    void findOneThrowsWhenMultipleMatches()
    {
        store.insert(new TestData("org.test", "artifact1", "1.0.0", "name1"));
        store.insert(new TestData("org.test", "artifact1", "2.0.0", "name2"));

        assertThrows(IllegalStateException.class, () -> store.findOne(eq("artifactId", "artifact1")));
    }

    @Test
    void canCountWithFilter()
    {
        store.insert(new TestData("org.test", "artifact1", "1.0.0", "name1"));
        store.insert(new TestData("org.test", "artifact1", "2.0.0", "name2"));
        store.insert(new TestData("org.test", "artifact2", "1.0.0", "name3"));

        long count = store.count(eq("artifactId", "artifact1"));
        assertEquals(2, count);
    }

    @Test
    void canDeleteWithFilter()
    {
        store.insert(new TestData("org.test", "artifact1", "1.0.0", "name1"));
        store.insert(new TestData("org.test", "artifact1", "2.0.0", "name2"));
        store.insert(new TestData("org.test", "artifact2", "1.0.0", "name3"));

        long deleted = store.delete(eq("artifactId", "artifact1"));
        assertEquals(2, deleted);

        List<TestData> remaining = store.getAllStoredEntities();
        assertEquals(1, remaining.size());
    }

    @Test
    void canCreateIndexesIfAbsent()
    {
        IndexModel index = BaseMongo.buildIndex("test-index", "groupId", "artifactId");
        List<String> result = BaseMongo.createIndexesIfAbsent(mongoProvider, COLLECTION_NAME, Collections.singletonList(index));

        assertNotNull(result);
    }

    @Test
    void createIndexesIfAbsentSkipsExistingIndexes()
    {
        IndexModel index = BaseMongo.buildIndex("test-index", "groupId", "artifactId");
        BaseMongo.createIndexesIfAbsent(mongoProvider, COLLECTION_NAME, Collections.singletonList(index));

        List<String> result = BaseMongo.createIndexesIfAbsent(mongoProvider, COLLECTION_NAME, Collections.singletonList(index));
        assertTrue(result.isEmpty());
    }

    @Test
    void canBuildIndexWithUniqueness()
    {
        IndexModel index = BaseMongo.buildIndex("unique-index", true, "groupId", "artifactId");
        assertNotNull(index);
        assertEquals("unique-index", index.getOptions().getName());
        assertTrue(index.getOptions().isUnique());
    }

    @Test
    void canBuildIndexWithCustomOptions()
    {
        com.mongodb.client.model.IndexOptions options = new com.mongodb.client.model.IndexOptions().unique(false);
        IndexModel index = BaseMongo.buildIndex("custom-index", options, "groupId");
        assertNotNull(index);
        assertEquals("custom-index", index.getOptions().getName());
    }

    @Test
    void convertReturnsNullForNullDocument()
    {
        TestData result = store.convert(null, TestData.class);
        assertNull(result);
    }

    @Test
    void convertHandlesDocumentWithObjectId()
    {
        Document doc = new Document();
        doc.put("_id", new org.bson.types.ObjectId());
        doc.put("groupId", "org.test");
        doc.put("artifactId", "art");
        doc.put("versionId", "1.0");
        doc.put("name", "test");

        TestData result = store.convert(doc, TestData.class);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("org.test", result.getGroupId());
    }

    @Test
    void canExecuteFind()
    {
        store.insert(new TestData("org.test", "artifact1", "1.0.0", "name1"));

        com.mongodb.client.FindIterable iterable = store.executeFind(eq("artifactId", "artifact1"));
        assertNotNull(iterable);
    }

    @Test
    void canUpdateExistingItem()
    {
        TestData data = new TestData("org.test", "artifact", "1.0.0", "original");
        store.createOrUpdate(data);

        TestData updated = new TestData("org.test", "artifact", "1.0.0", "updated");
        TestData result = store.createOrUpdate(updated);

        assertEquals("updated", result.getName());
        assertEquals(1, store.getAllStoredEntities().size());
    }
}
