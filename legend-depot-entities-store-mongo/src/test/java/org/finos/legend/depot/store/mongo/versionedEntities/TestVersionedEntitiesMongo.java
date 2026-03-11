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

package org.finos.legend.depot.store.mongo.versionedEntities;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexModel;
import org.finos.legend.depot.store.model.entities.EntityDefinition;
import org.finos.legend.depot.store.model.versionedEntities.StoredVersionedEntity;
import org.finos.legend.depot.store.mongo.TestStoreMongo;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestVersionedEntitiesMongo extends TestStoreMongo
{
    private VersionedEntitiesMongo versionedEntitiesMongo = new VersionedEntitiesMongo(mongoProvider);

    @Test
    public void canInstantiateVersionedEntitiesMongo()
    {
        Assertions.assertNotNull(versionedEntitiesMongo);
    }

    @Test
    public void canBuildIndexes()
    {
        List<IndexModel> indexes = VersionedEntitiesMongo.buildIndexes();
        Assertions.assertNotNull(indexes);
        Assertions.assertEquals(4, indexes.size());
    }

    @Test
    public void canGetCollection()
    {
        MongoCollection collection = getMongoDatabase().getCollection(VersionedEntitiesMongo.COLLECTION);
        Assertions.assertNotNull(collection);
    }

    @Test
    public void canCreateOrUpdateVersionedEntities()
    {
        Map<String, Object> content = new HashMap<>();
        content.put("_type", "class");
        content.put("name", "TestClass");
        content.put("package", "examples::metadata::test");

        Entity entity = new EntityDefinition(
                "examples::metadata::test::TestClass",
                "meta::pure::metamodel::type::Class",
                content
        );

        List<StoredVersionedEntity> result = versionedEntitiesMongo.createOrUpdate(
                "examples.metadata", "test", "1.0.0", Arrays.asList(entity));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        List<Entity> entities = versionedEntitiesMongo.getAllEntities("examples.metadata", "test", "1.0.0");
        Assertions.assertNotNull(entities);
        Assertions.assertEquals(1, entities.size());
        Assertions.assertEquals("examples::metadata::test::TestClass", entities.get(0).getPath());
        Assertions.assertEquals("meta::pure::metamodel::type::Class", entities.get(0).getClassifierPath());
    }

    @Test
    public void canCreateOrUpdateMultipleVersionedEntities()
    {
        Map<String, Object> content1 = new HashMap<>();
        content1.put("_type", "class");
        content1.put("name", "TestClass1");
        content1.put("package", "examples::metadata::test");

        Map<String, Object> content2 = new HashMap<>();
        content2.put("_type", "class");
        content2.put("name", "TestClass2");
        content2.put("package", "examples::metadata::test");

        Entity entity1 = new EntityDefinition(
                "examples::metadata::test::TestClass1",
                "meta::pure::metamodel::type::Class",
                content1
        );
        Entity entity2 = new EntityDefinition(
                "examples::metadata::test::TestClass2",
                "meta::pure::metamodel::type::Class",
                content2
        );

        List<StoredVersionedEntity> result = versionedEntitiesMongo.createOrUpdate(
                "examples.metadata", "test", "1.0.0", Arrays.asList(entity1, entity2));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());

        List<Entity> entities = versionedEntitiesMongo.getAllEntities("examples.metadata", "test", "1.0.0");
        Assertions.assertNotNull(entities);
        Assertions.assertEquals(2, entities.size());
    }

    @Test
    public void createOrUpdateWithEmptyListReturnsEmptyResult()
    {
        List<StoredVersionedEntity> result = versionedEntitiesMongo.createOrUpdate(
                "examples.metadata", "test", "1.0.0", Collections.emptyList());

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }
}
