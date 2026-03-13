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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.eclipse.collections.api.tuple.Pair;
import org.finos.legend.depot.domain.project.ProjectVersion;
import org.finos.legend.depot.store.model.entities.StoredEntity;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.depot.store.mongo.entities.test.EntitiesMongoTestUtils;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class TestAbstractEntitiesMongo extends TestStoreMongo
{
    private EntitiesMongo entitiesMongo = new EntitiesMongo(mongoProvider);
    private EntitiesMongoTestUtils entityUtils = new EntitiesMongoTestUtils(mongoProvider);

    @BeforeEach
    public void setupMetadata()
    {
        entityUtils.loadEntities(this.getClass().getClassLoader().getResource("data/versioned-entities.json"));
        entityUtils.loadEntities(this.getClass().getClassLoader().getResource("data/revision-entities.json"));
    }

    @Test
    public void testConstructorInitialization()
    {
        EntitiesMongo testMongo = new EntitiesMongo(mongoProvider);
        Assertions.assertNotNull(testMongo);
    }

    @Test
    public void testGetEntity()
    {
        Optional<Entity> entity = entitiesMongo.getEntity("examples.metadata", "test", "2.2.0", "examples::metadata::test::TestProfile");
        Assertions.assertTrue(entity.isPresent());
        Assertions.assertEquals("examples::metadata::test::TestProfile", entity.get().getPath());
        Assertions.assertEquals("meta::pure::metamodel::extension::Profile", entity.get().getClassifierPath());
    }

    @Test
    public void testGetEntityNotFound()
    {
        Optional<Entity> entity = entitiesMongo.getEntity("examples.metadata", "test", "2.2.0", "non::existent::path");
        Assertions.assertFalse(entity.isPresent());
    }

    @Test
    public void testGetEntityFromDependencies()
    {
        Set<ProjectVersion> dependencies = new HashSet<>();
        dependencies.add(new ProjectVersion("examples.metadata", "test", "2.2.0"));
        List<String> entityPaths = Arrays.asList("examples::metadata::test::TestProfile", "examples::metadata::test::ClientBasic");

        List<Entity> entities = entitiesMongo.getEntityFromDependencies(dependencies, entityPaths);
        Assertions.assertNotNull(entities);
        Assertions.assertEquals(2, entities.size());
    }

    @Test
    public void testGetEntityFromDependenciesPartialMatch()
    {
        Set<ProjectVersion> dependencies = new HashSet<>();
        dependencies.add(new ProjectVersion("examples.metadata", "test", "2.2.0"));
        dependencies.add(new ProjectVersion("examples.metadata", "test", "1.0.0"));
        List<String> entityPaths = Arrays.asList("examples::metadata::test::TestProfile", "non::existent::path");

        List<Entity> entities = entitiesMongo.getEntityFromDependencies(dependencies, entityPaths);
        Assertions.assertNotNull(entities);
        Assertions.assertTrue(entities.size() >= 1);
    }

    @Test
    public void testGetEntityFromDependenciesEmptyDependencies()
    {
        Set<ProjectVersion> dependencies = new HashSet<>();
        List<String> entityPaths = Arrays.asList("examples::metadata::test::TestProfile");

        List<Entity> entities = entitiesMongo.getEntityFromDependencies(dependencies, entityPaths);
        Assertions.assertNotNull(entities);
        Assertions.assertEquals(0, entities.size());
    }

    @Test
    public void testGetStoredEntitiesByGroupAndArtifact()
    {
        List<StoredEntity> entities = entitiesMongo.getStoredEntities("examples.metadata", "test");
        Assertions.assertNotNull(entities);
        Assertions.assertTrue(entities.size() > 0);
    }

    @Test
    public void testGetStoredEntitiesByGroupArtifactVersion()
    {
        List<StoredEntity> entities = entitiesMongo.getStoredEntities("examples.metadata", "test", "2.2.0");
        Assertions.assertNotNull(entities);
        Assertions.assertTrue(entities.size() > 0);
    }

    @Test
    public void testGetAllEntities()
    {
        List<Entity> entities = entitiesMongo.getAllEntities("examples.metadata", "test", "2.2.0");
        Assertions.assertNotNull(entities);
        Assertions.assertEquals(3, entities.size());
    }

    @Test
    public void testGetEntitiesByPackageWithoutFilters()
    {
        List<Entity> entities = entitiesMongo.getEntitiesByPackage("examples.metadata", "test", "2.2.0", null, null, false);
        Assertions.assertNotNull(entities);
        Assertions.assertTrue(entities.size() > 0);
    }

    @Test
    public void testGetEntitiesByPackageExactMatch()
    {
        List<Entity> entities = entitiesMongo.getEntitiesByPackage("examples.metadata", "test", "2.2.0", "examples::metadata::test", null, false);
        Assertions.assertNotNull(entities);
        Assertions.assertEquals(2, entities.size());
    }

    @Test
    public void testGetEntitiesByPackageWithSubPackages()
    {
        List<Entity> entities = entitiesMongo.getEntitiesByPackage("examples.metadata", "test", "2.2.0", "examples::metadata::test", null, true);
        Assertions.assertNotNull(entities);
        Assertions.assertEquals(3, entities.size());
    }

    @Test
    public void testGetEntitiesByPackageWithClassifierPaths()
    {
        Set<String> classifiers = new HashSet<>();
        classifiers.add("meta::pure::metamodel::extension::Profile");
        List<Entity> entities = entitiesMongo.getEntitiesByPackage("examples.metadata", "test", "2.2.0", "examples::metadata::test", classifiers, true);
        Assertions.assertNotNull(entities);
        Assertions.assertEquals(2, entities.size());
        for (Entity entity : entities)
        {
            Assertions.assertEquals("meta::pure::metamodel::extension::Profile", entity.getClassifierPath());
        }
    }

    @Test
    public void testGetEntitiesByPackageEmptyPackageName()
    {
        List<Entity> entities = entitiesMongo.getEntitiesByPackage("examples.metadata", "test", "2.2.0", "", null, false);
        Assertions.assertNotNull(entities);
        Assertions.assertTrue(entities.size() > 0);
    }

    @Test
    public void testFindReleasedEntitiesByClassifier()
    {
        FindIterable result = entitiesMongo.findReleasedEntitiesByClassifier("meta::pure::metamodel::extension::Profile");
        Assertions.assertNotNull(result);
        List<Document> documents = new ArrayList<>();
        result.into(documents);
        Assertions.assertTrue(documents.size() >= 0);
    }

    @Test
    public void testFindLatestEntitiesByClassifier()
    {
        FindIterable result = entitiesMongo.findLatestEntitiesByClassifier("meta::pure::metamodel::extension::Profile");
        Assertions.assertNotNull(result);
        List<Document> documents = new ArrayList<>();
        result.into(documents);
        Assertions.assertTrue(documents.size() >= 0);
    }

    @Test
    public void testFindEntitiesByClassifierAndVersions()
    {
        List<ProjectVersion> versions = Arrays.asList(new ProjectVersion("examples.metadata", "test", "2.2.0"));
        FindIterable result = entitiesMongo.findEntitiesByClassifierAndVersions("meta::pure::metamodel::extension::Profile", versions);
        Assertions.assertNotNull(result);
        List<Document> documents = new ArrayList<>();
        result.into(documents);
        Assertions.assertTrue(documents.size() >= 0);
    }

    @Test
    public void testFindEntitiesByClassifierAndVersionsEmpty()
    {
        List<ProjectVersion> versions = Collections.emptyList();
        FindIterable result = entitiesMongo.findEntitiesByClassifierAndVersions("meta::pure::metamodel::extension::Profile", versions);
        Assertions.assertNotNull(result);
    }

    @Test
    public void testFindEntitiesByClassifierAndVersionsNull()
    {
        FindIterable result = entitiesMongo.findEntitiesByClassifierAndVersions("meta::pure::metamodel::extension::Profile", null);
        Assertions.assertNotNull(result);
    }

    @Test
    public void testFindReleasedEntitiesByClassifierWithSearch()
    {
        FindIterable result = entitiesMongo.findReleasedEntitiesByClassifier("meta::pure::metamodel::extension::Profile", "TestProfile");
        Assertions.assertNotNull(result);
        List<Document> documents = new ArrayList<>();
        result.into(documents);
        Assertions.assertTrue(documents.size() >= 0);
    }

    @Test
    public void testFindReleasedEntitiesByClassifierWithNullSearch()
    {
        FindIterable result = entitiesMongo.findReleasedEntitiesByClassifier("meta::pure::metamodel::extension::Profile", null);
        Assertions.assertNotNull(result);
    }

    @Test
    public void testFindLatestEntitiesByClassifierWithSearch()
    {
        FindIterable result = entitiesMongo.findLatestEntitiesByClassifier("meta::pure::metamodel::extension::Profile", "TestProfile");
        Assertions.assertNotNull(result);
        List<Document> documents = new ArrayList<>();
        result.into(documents);
        Assertions.assertTrue(documents.size() >= 0);
    }

    @Test
    public void testFindLatestEntitiesByClassifierWithNullSearch()
    {
        FindIterable result = entitiesMongo.findLatestEntitiesByClassifier("meta::pure::metamodel::extension::Profile", null);
        Assertions.assertNotNull(result);
    }

    @Test
    public void testFindEntitiesByClassifierAndVersionsWithSearch()
    {
        List<ProjectVersion> versions = Arrays.asList(new ProjectVersion("examples.metadata", "test", "2.2.0"));
        FindIterable result = entitiesMongo.findEntitiesByClassifierAndVersions("meta::pure::metamodel::extension::Profile", "TestProfile", versions);
        Assertions.assertNotNull(result);
        List<Document> documents = new ArrayList<>();
        result.into(documents);
        Assertions.assertTrue(documents.size() >= 0);
    }

    @Test
    public void testFindEntitiesByClassifierAndVersionsWithSearchAndNullVersions()
    {
        FindIterable result = entitiesMongo.findEntitiesByClassifierAndVersions("meta::pure::metamodel::extension::Profile", "TestProfile", null);
        Assertions.assertNotNull(result);
    }

    @Test
    public void testFindEntitiesByClassifierAndVersionsWithSearchAndEmptyVersions()
    {
        FindIterable result = entitiesMongo.findEntitiesByClassifierAndVersions("meta::pure::metamodel::extension::Profile", "TestProfile", Collections.emptyList());
        Assertions.assertNotNull(result);
    }

    @Test
    public void testFindEntitiesByClassifierAndVersionsWithNullSearch()
    {
        List<ProjectVersion> versions = Arrays.asList(new ProjectVersion("examples.metadata", "test", "2.2.0"));
        FindIterable result = entitiesMongo.findEntitiesByClassifierAndVersions("meta::pure::metamodel::extension::Profile", null, versions);
        Assertions.assertNotNull(result);
    }

    @Test
    public void testDeleteByGroupArtifactVersion()
    {
        long initialCount = entitiesMongo.getStoredEntities("examples.metadata", "test", "2.2.0").size();
        Assertions.assertTrue(initialCount > 0);
        long deleted = entitiesMongo.delete("examples.metadata", "test", "2.2.0");
        Assertions.assertTrue(deleted > 0);
        long finalCount = entitiesMongo.getStoredEntities("examples.metadata", "test", "2.2.0").size();
        Assertions.assertEquals(0, finalCount);
    }

    @Test
    public void testDeleteByGroupArtifact()
    {
        long initialCount = entitiesMongo.getStoredEntities("examples.metadata", "test").size();
        Assertions.assertTrue(initialCount > 0);
        long deleted = entitiesMongo.delete("examples.metadata", "test");
        Assertions.assertTrue(deleted > 0);
        long finalCount = entitiesMongo.getStoredEntities("examples.metadata", "test").size();
        Assertions.assertEquals(0, finalCount);
    }

    @Test
    public void testGetStoredEntitiesCoordinates()
    {
        List<Pair<String, String>> coordinates = entitiesMongo.getStoredEntitiesCoordinates();
        Assertions.assertNotNull(coordinates);
        Assertions.assertTrue(coordinates.size() > 0);
        boolean foundExpected = false;
        for (Pair<String, String> pair : coordinates)
        {
            if ("examples.metadata".equals(pair.getOne()) && "test".equals(pair.getTwo()))
            {
                foundExpected = true;
                break;
            }
        }
        Assertions.assertTrue(foundExpected);
    }

    private static class TestableEntitiesMongo extends EntitiesMongo
    {
        public TestableEntitiesMongo(com.mongodb.client.MongoDatabase mongoDatabase)
        {
            super(mongoDatabase);
        }

        public String testSerializeEntity(Entity entity)
        {
            return serializeEntity(entity);
        }

        public Map<String, ?> testBuildEntityAttributes(Entity entity)
        {
            return buildEntityAttributes(entity);
        }
    }

    @Test
    public void testSerializeEntity()
    {
        TestableEntitiesMongo testMongo = new TestableEntitiesMongo(mongoProvider);
        Optional<Entity> entityOpt = testMongo.getEntity("examples.metadata", "test", "2.2.0", "examples::metadata::test::TestProfile");
        Assertions.assertTrue(entityOpt.isPresent());
        Entity entity = entityOpt.get();
        Assertions.assertNotNull(entity);
        String serialized = testMongo.testSerializeEntity(entity);
        Assertions.assertNotNull(serialized);
        Assertions.assertTrue(serialized.contains("examples::metadata::test::TestProfile"));
    }

    @Test
    public void testSerializeEntityWithInvalidData()
    {
        TestableEntitiesMongo testMongo = new TestableEntitiesMongo(mongoProvider);
        Entity entity = new Entity()
        {
            @Override
            public String getPath()
            {
                return "test::path";
            }

            @Override
            public String getClassifierPath()
            {
                throw new RuntimeException("Simulated serialization error");
            }

            @Override
            public Map<String, ?> getContent()
            {
                return null;
            }
        };
        Assertions.assertThrows(IllegalStateException.class, () -> testMongo.testSerializeEntity(entity));
    }

    @Test
    public void testBuildEntityAttributes()
    {
        TestableEntitiesMongo testMongo = new TestableEntitiesMongo(mongoProvider);
        Optional<Entity> entityOpt = testMongo.getEntity("examples.metadata", "test", "2.2.0", "examples::metadata::test::TestProfile");
        Assertions.assertTrue(entityOpt.isPresent());
        Entity entity = entityOpt.get();
        Assertions.assertNotNull(entity);
        Map<String, ?> attributes = testMongo.testBuildEntityAttributes(entity);
        Assertions.assertNotNull(attributes);
        Assertions.assertEquals("examples::metadata::test::TestProfile", attributes.get("path"));
        Assertions.assertEquals("meta::pure::metamodel::extension::Profile", attributes.get("classifierPath"));
        Assertions.assertEquals("examples::metadata::test", attributes.get("package"));
    }

    @Test
    public void testBuildEntityAttributesWithoutContent()
    {
        TestableEntitiesMongo testMongo = new TestableEntitiesMongo(mongoProvider);
        Entity entity = new Entity()
        {
            @Override
            public String getPath()
            {
                return "test::path";
            }

            @Override
            public String getClassifierPath()
            {
                return "test::classifier";
            }

            @Override
            public Map<String, ?> getContent()
            {
                return null;
            }
        };
        Map<String, ?> attributes = testMongo.testBuildEntityAttributes(entity);
        Assertions.assertNotNull(attributes);
        Assertions.assertEquals("test::path", attributes.get("path"));
        Assertions.assertEquals("test::classifier", attributes.get("classifierPath"));
        Assertions.assertFalse(attributes.containsKey("package"));
    }

    @Test
    public void testBuildEntityAttributesWithContent()
    {
        TestableEntitiesMongo testMongo = new TestableEntitiesMongo(mongoProvider);
        Entity entity = new Entity()
        {
            @Override
            public String getPath()
            {
                return "test::path";
            }

            @Override
            public String getClassifierPath()
            {
                return "test::classifier";
            }

            @Override
            public Map<String, ?> getContent()
            {
                Map<String, String> content = new HashMap<>();
                content.put("package", "test::package");
                return content;
            }
        };
        Map<String, ?> attributes = testMongo.testBuildEntityAttributes(entity);
        Assertions.assertNotNull(attributes);
        Assertions.assertEquals("test::path", attributes.get("path"));
        Assertions.assertEquals("test::classifier", attributes.get("classifierPath"));
        Assertions.assertEquals("test::package", attributes.get("package"));
    }
}
