// Copyright 2024 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.finos.legend.depot.store.model.versionedEntities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class StoredVersionedEntityStringDataTest
{
    @Test
    public void testFiveArgConstructorSetsAllFields()
    {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("path", "model::MyClass");
        StoredVersionedEntityStringData entity = new StoredVersionedEntityStringData(
                "org.example", "my-artifact", "1.0.0", "{\"content\":\"value\"}", attrs);

        Assertions.assertEquals("org.example", entity.getGroupId());
        Assertions.assertEquals("my-artifact", entity.getArtifactId());
        Assertions.assertEquals("1.0.0", entity.getVersionId());
        Assertions.assertEquals("{\"content\":\"value\"}", entity.getData());
        Assertions.assertEquals(attrs, entity.getEntityAttributes());
    }

    @Test
    public void testFiveArgConstructorWithNullData()
    {
        StoredVersionedEntityStringData entity = new StoredVersionedEntityStringData(
                "org.example", "my-artifact", "1.0.0", null, null);

        Assertions.assertEquals("org.example", entity.getGroupId());
        Assertions.assertEquals("my-artifact", entity.getArtifactId());
        Assertions.assertEquals("1.0.0", entity.getVersionId());
        Assertions.assertNull(entity.getData());
        Assertions.assertNull(entity.getEntityAttributes());
    }

    @Test
    public void testThreeArgConstructorSetsCoordinates()
    {
        StoredVersionedEntityStringData entity = new StoredVersionedEntityStringData(
                "org.finos", "depot-api", "2.0.0");

        Assertions.assertEquals("org.finos", entity.getGroupId());
        Assertions.assertEquals("depot-api", entity.getArtifactId());
        Assertions.assertEquals("2.0.0", entity.getVersionId());
    }

    @Test
    public void testThreeArgConstructorDataIsNull()
    {
        StoredVersionedEntityStringData entity = new StoredVersionedEntityStringData(
                "org.finos", "depot-api", "2.0.0");

        Assertions.assertNull(entity.getData());
    }

    @Test
    public void testThreeArgConstructorEntityAttributesIsNull()
    {
        StoredVersionedEntityStringData entity = new StoredVersionedEntityStringData(
                "org.finos", "depot-api", "2.0.0");

        Assertions.assertNull(entity.getEntityAttributes());
    }

    @Test
    public void testGetDataReturnsCorrectValue()
    {
        String data = "{\"_type\":\"class\",\"name\":\"MyClass\"}";
        StoredVersionedEntityStringData entity = new StoredVersionedEntityStringData(
                "org.example", "artifact", "1.0.0", data, null);

        Assertions.assertEquals(data, entity.getData());
    }

    @Test
    public void testGetDataReturnsNullWhenNotSet()
    {
        StoredVersionedEntityStringData entity = new StoredVersionedEntityStringData(
                "org.example", "artifact", "1.0.0");

        Assertions.assertNull(entity.getData());
    }

    @Test
    public void testEqualsWithSameObject()
    {
        StoredVersionedEntityStringData entity = new StoredVersionedEntityStringData(
                "org.example", "artifact", "1.0.0", "data", null);

        Assertions.assertTrue(entity.equals(entity));
    }

    @Test
    public void testEqualsWithEqualObjects()
    {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("key", "value");
        StoredVersionedEntityStringData entity1 = new StoredVersionedEntityStringData(
                "org.example", "artifact", "1.0.0", "data", attrs);
        StoredVersionedEntityStringData entity2 = new StoredVersionedEntityStringData(
                "org.example", "artifact", "1.0.0", "data", attrs);

        Assertions.assertTrue(entity1.equals(entity2));
    }

    @Test
    public void testEqualsWithDifferentGroupId()
    {
        StoredVersionedEntityStringData entity1 = new StoredVersionedEntityStringData(
                "org.example", "artifact", "1.0.0", "data", null);
        StoredVersionedEntityStringData entity2 = new StoredVersionedEntityStringData(
                "org.other", "artifact", "1.0.0", "data", null);

        Assertions.assertNotEquals(entity1, entity2);
    }

    @Test
    public void testEqualsWithDifferentArtifactId()
    {
        StoredVersionedEntityStringData entity1 = new StoredVersionedEntityStringData(
                "org.example", "artifact-a", "1.0.0", "data", null);
        StoredVersionedEntityStringData entity2 = new StoredVersionedEntityStringData(
                "org.example", "artifact-b", "1.0.0", "data", null);

        Assertions.assertNotEquals(entity1, entity2);
    }

    @Test
    public void testEqualsWithDifferentVersionId()
    {
        StoredVersionedEntityStringData entity1 = new StoredVersionedEntityStringData(
                "org.example", "artifact", "1.0.0", "data", null);
        StoredVersionedEntityStringData entity2 = new StoredVersionedEntityStringData(
                "org.example", "artifact", "2.0.0", "data", null);

        Assertions.assertNotEquals(entity1, entity2);
    }

    @Test
    public void testEqualsWithDifferentData()
    {
        StoredVersionedEntityStringData entity1 = new StoredVersionedEntityStringData(
                "org.example", "artifact", "1.0.0", "data-one", null);
        StoredVersionedEntityStringData entity2 = new StoredVersionedEntityStringData(
                "org.example", "artifact", "1.0.0", "data-two", null);

        Assertions.assertNotEquals(entity1, entity2);
    }

    @Test
    public void testEqualsWithNull()
    {
        StoredVersionedEntityStringData entity = new StoredVersionedEntityStringData(
                "org.example", "artifact", "1.0.0", "data", null);

        Assertions.assertNotEquals(entity, null);
    }

    @Test
    public void testHashCodeIsConsistent()
    {
        StoredVersionedEntityStringData entity = new StoredVersionedEntityStringData(
                "org.example", "artifact", "1.0.0", "data", null);

        int hash1 = entity.hashCode();
        int hash2 = entity.hashCode();

        Assertions.assertEquals(hash1, hash2);
    }

    @Test
    public void testHashCodeEqualObjectsHaveSameHash()
    {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("key", "value");
        StoredVersionedEntityStringData entity1 = new StoredVersionedEntityStringData(
                "org.example", "artifact", "1.0.0", "data", attrs);
        StoredVersionedEntityStringData entity2 = new StoredVersionedEntityStringData(
                "org.example", "artifact", "1.0.0", "data", attrs);

        Assertions.assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void testHashCodeDifferentObjectsLikelyDifferentHash()
    {
        StoredVersionedEntityStringData entity1 = new StoredVersionedEntityStringData(
                "org.example", "artifact", "1.0.0", "data-one", null);
        StoredVersionedEntityStringData entity2 = new StoredVersionedEntityStringData(
                "org.other", "artifact", "2.0.0", "data-two", null);

        Assertions.assertNotEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void testIsNotNullAfterConstruction()
    {
        StoredVersionedEntityStringData entity = new StoredVersionedEntityStringData(
                "org.example", "artifact", "1.0.0");

        Assertions.assertNotNull(entity);
    }
}
