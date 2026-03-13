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

package org.finos.legend.depot.store.model.versionedEntities;

import org.finos.legend.depot.store.model.entities.EntityDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class StoredVersionedEntityDataTest
{
    @Test
    public void canCreateWithFullConstructor()
    {
        Map<String, Object> entityContent = new HashMap<>();
        entityContent.put("key1", "value1");

        EntityDefinition entityDefinition = new EntityDefinition("test.path", "test.classifier", entityContent);

        Map<String, Object> entityAttributes = new HashMap<>();
        entityAttributes.put("attr1", "attrValue1");

        StoredVersionedEntityData entity = new StoredVersionedEntityData(
            "com.example",
            "myartifact",
            "1.0.0",
            entityDefinition,
            entityAttributes
        );

        Assertions.assertNotNull(entity);
        Assertions.assertEquals("com.example", entity.getGroupId());
        Assertions.assertEquals("myartifact", entity.getArtifactId());
        Assertions.assertEquals("1.0.0", entity.getVersionId());
        Assertions.assertEquals(entityDefinition, entity.getEntity());
        Assertions.assertNotNull(entity.getEntityAttributes());
    }

    @Test
    public void canCreateWithSimpleConstructor()
    {
        StoredVersionedEntityData entity = new StoredVersionedEntityData(
            "com.example",
            "myartifact",
            "2.0.0"
        );

        Assertions.assertNotNull(entity);
        Assertions.assertEquals("com.example", entity.getGroupId());
        Assertions.assertEquals("myartifact", entity.getArtifactId());
        Assertions.assertEquals("2.0.0", entity.getVersionId());
    }

    @Test
    public void testGetId()
    {
        StoredVersionedEntityData entity = new StoredVersionedEntityData(
            "com.example",
            "myartifact",
            "1.0.0"
        );

        String id = entity.getId();

        Assertions.assertNotNull(id);
        Assertions.assertEquals("", id);
    }

    @Test
    public void canGetEntity()
    {
        Map<String, Object> entityContent = new HashMap<>();
        entityContent.put("key1", "value1");

        EntityDefinition entityDefinition = new EntityDefinition("test.path", "test.classifier", entityContent);

        StoredVersionedEntityData entity = new StoredVersionedEntityData(
            "com.example",
            "myartifact",
            "1.0.0",
            entityDefinition,
            null
        );

        EntityDefinition result = entity.getEntity();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(entityDefinition, result);
        Assertions.assertEquals("test.path", result.getPath());
        Assertions.assertEquals("test.classifier", result.getClassifierPath());
    }

    @Test
    public void testEquals()
    {
        Map<String, Object> entityContent = new HashMap<>();
        entityContent.put("key1", "value1");

        EntityDefinition entityDefinition1 = new EntityDefinition("test.path", "test.classifier", entityContent);
        EntityDefinition entityDefinition2 = new EntityDefinition("test.path", "test.classifier", entityContent);

        StoredVersionedEntityData entity1 = new StoredVersionedEntityData(
            "com.example",
            "myartifact",
            "1.0.0",
            entityDefinition1,
            null
        );

        StoredVersionedEntityData entity2 = new StoredVersionedEntityData(
            "com.example",
            "myartifact",
            "1.0.0",
            entityDefinition2,
            null
        );

        StoredVersionedEntityData entity3 = new StoredVersionedEntityData(
            "com.different",
            "myartifact",
            "1.0.0",
            entityDefinition1,
            null
        );

        Assertions.assertTrue(entity1.equals(entity1));
        Assertions.assertTrue(entity1.equals(entity2));
        Assertions.assertFalse(entity1.equals(entity3));
        Assertions.assertFalse(entity1.equals(null));
        Assertions.assertFalse(entity1.equals("not an entity"));
    }

    @Test
    public void testHashCode()
    {
        Map<String, Object> entityContent = new HashMap<>();
        entityContent.put("key1", "value1");

        EntityDefinition entityDefinition1 = new EntityDefinition("test.path", "test.classifier", entityContent);
        EntityDefinition entityDefinition2 = new EntityDefinition("test.path", "test.classifier", entityContent);

        StoredVersionedEntityData entity1 = new StoredVersionedEntityData(
            "com.example",
            "myartifact",
            "1.0.0",
            entityDefinition1,
            null
        );

        StoredVersionedEntityData entity2 = new StoredVersionedEntityData(
            "com.example",
            "myartifact",
            "1.0.0",
            entityDefinition2,
            null
        );

        StoredVersionedEntityData entity3 = new StoredVersionedEntityData(
            "com.different",
            "myartifact",
            "1.0.0",
            entityDefinition1,
            null
        );

        int hashCode1 = entity1.hashCode();
        int hashCode2 = entity2.hashCode();
        int hashCode3 = entity3.hashCode();

        Assertions.assertEquals(hashCode1, hashCode2);
        Assertions.assertNotEquals(hashCode1, hashCode3);
    }
}
