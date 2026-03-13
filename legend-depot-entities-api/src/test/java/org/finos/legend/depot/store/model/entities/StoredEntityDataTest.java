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

package org.finos.legend.depot.store.model.entities;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StoredEntityDataTest
{
    @Test
    public void canCreateStoredEntityDataWithFullConstructor()
    {
        Map<String, String> content = new HashMap<>();
        content.put("key", "value");
        EntityDefinition entity = new EntityDefinition("test::path", "meta::test", content);

        Map<String, String> attributes = new HashMap<>();
        attributes.put("attr1", "value1");

        StoredEntityData storedEntity = new StoredEntityData("groupId", "artifactId", "versionId", entity, attributes);

        assertNotNull(storedEntity);
        assertEquals("groupId", storedEntity.getGroupId());
        assertEquals("artifactId", storedEntity.getArtifactId());
        assertEquals("versionId", storedEntity.getVersionId());
        assertEquals(entity, storedEntity.getEntity());
        assertEquals(attributes, storedEntity.getEntityAttributes());
    }

    @Test
    public void canCreateStoredEntityDataWithSimpleConstructor()
    {
        StoredEntityData storedEntity = new StoredEntityData("groupId", "artifactId", "versionId");

        assertNotNull(storedEntity);
        assertEquals("groupId", storedEntity.getGroupId());
        assertEquals("artifactId", storedEntity.getArtifactId());
        assertEquals("versionId", storedEntity.getVersionId());
        assertNull(storedEntity.getEntity());
    }

    @Test
    public void canGetId()
    {
        StoredEntityData storedEntity = new StoredEntityData("groupId", "artifactId", "versionId");

        String id = storedEntity.getId();

        assertNotNull(id);
        assertEquals("", id);
    }

    @Test
    public void canGetEntity()
    {
        Map<String, String> content = new HashMap<>();
        content.put("key", "value");
        EntityDefinition entity = new EntityDefinition("test::path", "meta::test", content);

        StoredEntityData storedEntity = new StoredEntityData("groupId", "artifactId", "versionId", entity, null);

        EntityDefinition retrievedEntity = storedEntity.getEntity();

        assertNotNull(retrievedEntity);
        assertEquals(entity, retrievedEntity);
    }

    @Test
    public void testEquals()
    {
        Map<String, String> content = new HashMap<>();
        content.put("key", "value");
        EntityDefinition entity1 = new EntityDefinition("test::path", "meta::test", content);
        EntityDefinition entity2 = new EntityDefinition("test::path", "meta::test", content);

        StoredEntityData storedEntity1 = new StoredEntityData("groupId", "artifactId", "versionId", entity1, null);
        StoredEntityData storedEntity2 = new StoredEntityData("groupId", "artifactId", "versionId", entity2, null);
        StoredEntityData storedEntity3 = new StoredEntityData("differentGroupId", "artifactId", "versionId", entity1, null);

        assertTrue(storedEntity1.equals(storedEntity1));
        assertTrue(storedEntity1.equals(storedEntity2));
        assertFalse(storedEntity1.equals(storedEntity3));
        assertFalse(storedEntity1.equals(null));
        assertFalse(storedEntity1.equals("not a StoredEntityData"));
    }

    @Test
    public void testHashCode()
    {
        Map<String, String> content = new HashMap<>();
        content.put("key", "value");
        EntityDefinition entity1 = new EntityDefinition("test::path", "meta::test", content);
        EntityDefinition entity2 = new EntityDefinition("test::path", "meta::test", content);

        StoredEntityData storedEntity1 = new StoredEntityData("groupId", "artifactId", "versionId", entity1, null);
        StoredEntityData storedEntity2 = new StoredEntityData("groupId", "artifactId", "versionId", entity2, null);
        StoredEntityData storedEntity3 = new StoredEntityData("differentGroupId", "artifactId", "versionId", entity1, null);

        assertEquals(storedEntity1.hashCode(), storedEntity2.hashCode());
        assertNotEquals(storedEntity1.hashCode(), storedEntity3.hashCode());
    }
}
