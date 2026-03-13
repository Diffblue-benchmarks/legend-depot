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

package org.finos.legend.depot.store.mongo.entities;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexModel;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.finos.legend.depot.domain.entity.DepotEntity;
import org.finos.legend.depot.domain.entity.DepotEntityOverview;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.store.model.entities.EntityDefinition;
import org.finos.legend.depot.store.model.entities.StoredEntity;
import org.finos.legend.depot.store.model.entities.StoredEntityData;
import org.finos.legend.depot.store.model.entities.StoredEntityStringData;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntitiesMongoTest extends TestStoreMongo
{
    private EntitiesMongo<StoredEntity> entitiesMongo = new EntitiesMongo(mongoProvider);

    @Test
    public void testConstructorWithDatabase()
    {
        EntitiesMongo<StoredEntity> entities = new EntitiesMongo(mongoProvider);

        Assertions.assertNotNull(entities);
    }

    @Test
    public void testConstructorWithDatabaseAndClass()
    {
        EntitiesMongo<StoredEntityStringData> entities = new EntitiesMongo(mongoProvider, StoredEntityStringData.class);

        Assertions.assertNotNull(entities);
    }

    @Test
    public void testBuildIndexes()
    {
        List<IndexModel> indexes = EntitiesMongo.buildIndexes();

        Assertions.assertNotNull(indexes);
        Assertions.assertEquals(4, indexes.size());
    }

    @Test
    public void testGetCollection()
    {
        MongoCollection collection = entitiesMongo.getCollection();

        Assertions.assertNotNull(collection);
    }

    @Test
    public void testGetKeyFilter()
    {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("path", "test::path::Entity");
        StoredEntityStringData entity = new StoredEntityStringData("org.example", "test-artifact", "1.0.0", "data", attributes);

        Bson filter = entitiesMongo.getKeyFilter(entity);

        Assertions.assertNotNull(filter);
    }

    @Test
    public void testValidateNewDataWithValidEntity()
    {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("path", "test::path::Entity");
        StoredEntityStringData entity = new StoredEntityStringData("org.example", "test-artifact", "1.0.0", "data", attributes);

        Assertions.assertDoesNotThrow(() -> entitiesMongo.validateNewData(entity));
    }

    @Test
    public void testValidateNewDataWithInvalidGroupId()
    {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("path", "test::path::Entity");
        StoredEntityStringData entity = new StoredEntityStringData("", "test-artifact", "1.0.0", "data", attributes);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> entitiesMongo.validateNewData(entity));
        Assertions.assertTrue(exception.getMessage().contains("invalid data"));
    }

    @Test
    public void testValidateNewDataWithInvalidArtifactId()
    {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("path", "test::path::Entity");
        StoredEntityStringData entity = new StoredEntityStringData("org.example", "", "1.0.0", "data", attributes);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> entitiesMongo.validateNewData(entity));
        Assertions.assertTrue(exception.getMessage().contains("invalid data"));
    }

    @Test
    public void testValidateNewDataWithInvalidVersionId()
    {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("path", "test::path::Entity");
        StoredEntityStringData entity = new StoredEntityStringData("org.example", "test-artifact", "", "data", attributes);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> entitiesMongo.validateNewData(entity));
        Assertions.assertTrue(exception.getMessage().contains("invalid data"));
    }

    @Test
    public void testValidateNewDataWithInvalidEntityPath()
    {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("path", "invalid path");
        StoredEntityStringData entity = new StoredEntityStringData("org.example", "test-artifact", "1.0.0", "data", attributes);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> entitiesMongo.validateNewData(entity));
        Assertions.assertTrue(exception.getMessage().contains("invalid data"));
    }

    @Test
    public void testCreateOrUpdateWithEntityDefinitions()
    {
        Entity entity1 = createTestEntity("test::entity::Entity1");
        Entity entity2 = createTestEntity("test::entity::Entity2");
        List<Entity> entities = Arrays.asList(entity1, entity2);

        List<StoredEntity> result = entitiesMongo.createOrUpdate("org.example", "test-artifact", "1.0.0", entities);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testCreateOrUpdateWithStoredEntities()
    {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("path", "test::entity::Entity");
        StoredEntityStringData entity = new StoredEntityStringData("org.example", "test-artifact", "1.0.0", "data", attributes);
        List<StoredEntity> entities = Arrays.asList(entity);

        List<StoredEntity> result = entitiesMongo.createOrUpdate(entities);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void testFindLatestClassifierEntities()
    {
        setupTestData();

        List<DepotEntity> result = entitiesMongo.findLatestClassifierEntities("meta::pure::metamodel::type::Class");

        Assertions.assertNotNull(result);
    }

    @Test
    public void testFindReleasedClassifierEntities()
    {
        setupTestData();

        List<DepotEntity> result = entitiesMongo.findReleasedClassifierEntities("meta::pure::metamodel::type::Class");

        Assertions.assertNotNull(result);
    }

    @Test
    public void testFindClassifierEntitiesByVersions()
    {
        setupTestData();
        ProjectVersion version = new ProjectVersion("org.example", "test-artifact", "1.0.0");
        List<ProjectVersion> versions = Arrays.asList(version);

        List<DepotEntity> result = entitiesMongo.findClassifierEntitiesByVersions("meta::pure::metamodel::type::Class", versions);

        Assertions.assertNotNull(result);
    }

    @Test
    public void testFindLatestClassifierSummaries()
    {
        setupTestData();

        List<DepotEntityOverview> result = entitiesMongo.findLatestClassifierSummaries("meta::pure::metamodel::type::Class");

        Assertions.assertNotNull(result);
    }

    @Test
    public void testFindReleasedClassifierSummaries()
    {
        setupTestData();

        List<DepotEntityOverview> result = entitiesMongo.findReleasedClassifierSummaries("meta::pure::metamodel::type::Class");

        Assertions.assertNotNull(result);
    }

    @Test
    public void testFindClassifierSummariesByVersions()
    {
        setupTestData();
        ProjectVersion version = new ProjectVersion("org.example", "test-artifact", "1.0.0");
        List<ProjectVersion> versions = Arrays.asList(version);

        List<DepotEntityOverview> result = entitiesMongo.findClassifierSummariesByVersions("meta::pure::metamodel::type::Class", versions);

        Assertions.assertNotNull(result);
    }

    @Test
    public void testFindReleasedClassifierEntitiesWithSearchAndLimit()
    {
        setupTestData();

        List<DepotEntity> result = entitiesMongo.findReleasedClassifierEntities("meta::pure::metamodel::type::Class", "Entity", 10);

        Assertions.assertNotNull(result);
    }

    @Test
    public void testFindReleasedClassifierEntitiesWithSearchNoLimit()
    {
        setupTestData();

        List<DepotEntity> result = entitiesMongo.findReleasedClassifierEntities("meta::pure::metamodel::type::Class", "Entity", null);

        Assertions.assertNotNull(result);
    }

    @Test
    public void testFindLatestClassifierEntitiesWithSearchAndLimit()
    {
        setupTestData();

        List<DepotEntity> result = entitiesMongo.findLatestClassifierEntities("meta::pure::metamodel::type::Class", "Entity", 10);

        Assertions.assertNotNull(result);
    }

    @Test
    public void testFindLatestClassifierEntitiesWithSearchNoLimit()
    {
        setupTestData();

        List<DepotEntity> result = entitiesMongo.findLatestClassifierEntities("meta::pure::metamodel::type::Class", "Entity", null);

        Assertions.assertNotNull(result);
    }

    @Test
    public void testFindClassifierEntitiesByVersionsWithSearchAndLimit()
    {
        setupTestData();
        ProjectVersion version = new ProjectVersion("org.example", "test-artifact", "1.0.0");
        List<ProjectVersion> versions = Arrays.asList(version);

        List<DepotEntity> result = entitiesMongo.findClassifierEntitiesByVersions("meta::pure::metamodel::type::Class", versions, "Entity", 10);

        Assertions.assertNotNull(result);
    }

    @Test
    public void testFindClassifierEntitiesByVersionsWithSearchNoLimit()
    {
        setupTestData();
        ProjectVersion version = new ProjectVersion("org.example", "test-artifact", "1.0.0");
        List<ProjectVersion> versions = Arrays.asList(version);

        List<DepotEntity> result = entitiesMongo.findClassifierEntitiesByVersions("meta::pure::metamodel::type::Class", versions, "Entity", null);

        Assertions.assertNotNull(result);
    }

    @Test
    public void testCurateDepotEntityOverview()
    {
        setupTestDataForCuration();
        FindIterable query = entitiesMongo.getCollection().find();

        List<DepotEntityOverview> result = entitiesMongo.curateDepotEntityOverview(query);

        Assertions.assertNotNull(result);
    }

    @Test
    public void testCurateDepotEntity()
    {
        setupTestDataForCuration();
        FindIterable query = entitiesMongo.getCollection().find();

        List<DepotEntity> result = entitiesMongo.curateDepotEntity(query);

        Assertions.assertNotNull(result);
    }

    @Test
    public void testCurateDepotEntityEmptyResult()
    {
        FindIterable query = entitiesMongo.getCollection().find();

        List<DepotEntity> result = entitiesMongo.curateDepotEntity(query);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testResolvedToEntityDefinitionWithStoredEntityData()
    {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("path", "test::entity::Entity");
        attributes.put("classifierPath", "meta::pure::metamodel::type::Class");
        Map<String, Object> content = new HashMap<>();
        content.put("_type", "class");
        content.put("name", "Entity");
        content.put("package", "test::entity");
        EntityDefinition entityDef = new EntityDefinition("test::entity::Entity", "meta::pure::metamodel::type::Class", content);
        StoredEntityData storedEntity = new StoredEntityData("org.example", "test-artifact", "1.0.0", entityDef, attributes);

        Entity result = entitiesMongo.resolvedToEntityDefinition(storedEntity);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("test::entity::Entity", result.getPath());
    }

    @Test
    public void testResolvedToEntityDefinitionWithStoredEntityStringData()
    {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("path", "test::entity::Entity");
        attributes.put("classifierPath", "meta::pure::metamodel::type::Class");
        String jsonData = "{\"path\":\"test::entity::Entity\",\"classifierPath\":\"meta::pure::metamodel::type::Class\",\"content\":{\"_type\":\"class\",\"name\":\"Entity\",\"package\":\"test::entity\"}}";
        StoredEntityStringData storedEntity = new StoredEntityStringData("org.example", "test-artifact", "1.0.0", jsonData, attributes);

        Entity result = entitiesMongo.resolvedToEntityDefinition(storedEntity);

        Assertions.assertNotNull(result);
    }

    @Test
    public void testResolvedToEntityDefinitionWithInvalidJson()
    {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("path", "test::entity::Entity");
        String invalidJson = "invalid json";
        StoredEntityStringData storedEntity = new StoredEntityStringData("org.example", "test-artifact", "1.0.0", invalidJson, attributes);

        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> entitiesMongo.resolvedToEntityDefinition(storedEntity));
        Assertions.assertTrue(exception.getMessage().contains("Error"));
    }

    @Test
    public void testResolvedToEntityDefinitionWithUnknownType()
    {
        StoredEntity unknownEntity = new StoredEntity("org.example", "test-artifact", "1.0.0")
        {
        };

        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> entitiesMongo.resolvedToEntityDefinition(unknownEntity));
        Assertions.assertTrue(exception.getMessage().contains("Unknown stored entity type"));
    }

    private Entity createTestEntity(String path)
    {
        Map<String, Object> content = new HashMap<>();
        content.put("_type", "class");
        content.put("name", path.substring(path.lastIndexOf("::") + 2));
        content.put("package", path.substring(0, path.lastIndexOf("::")));

        return new Entity()
        {
            @Override
            public String getPath()
            {
                return path;
            }

            @Override
            public String getClassifierPath()
            {
                return "meta::pure::metamodel::type::Class";
            }

            @Override
            public Map<String, ?> getContent()
            {
                return content;
            }
        };
    }

    private void setupTestData()
    {
        Entity entity = createTestEntity("test::entity::Entity");
        entitiesMongo.createOrUpdate("org.example", "test-artifact", "1.0.0", Arrays.asList(entity));
    }

    private void setupTestDataForCuration()
    {
        Document doc = new Document();
        doc.put("groupId", "org.example");
        doc.put("artifactId", "test-artifact");
        doc.put("versionId", "1.0.0");
        doc.put("_type", "entityStringData");

        Map<String, Object> entityAttributes = new HashMap<>();
        entityAttributes.put("path", "test::entity::Entity");
        entityAttributes.put("classifierPath", "meta::pure::metamodel::type::Class");
        entityAttributes.put("package", "test::entity");
        doc.put("entityAttributes", entityAttributes);

        String jsonData = "{\"path\":\"test::entity::Entity\",\"classifierPath\":\"meta::pure::metamodel::type::Class\",\"content\":{\"_type\":\"class\",\"name\":\"Entity\",\"package\":\"test::entity\"}}";
        doc.put("data", jsonData);

        entitiesMongo.getCollection().insertOne(doc);
    }
}
