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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class StoredEntityStringDataTest
{
    @Test
    public void testFullConstructorSetsFields()
    {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("key", "value");
        StoredEntityStringData entity = new StoredEntityStringData("group1", "artifact1", "1.0.0", "entityData", attributes);

        Assertions.assertEquals("group1", entity.getGroupId());
        Assertions.assertEquals("artifact1", entity.getArtifactId());
        Assertions.assertEquals("1.0.0", entity.getVersionId());
        Assertions.assertEquals("entityData", entity.getData());
        Assertions.assertNotNull(entity.getEntityAttributes());
    }

    @Test
    public void testShortConstructorSetsFields()
    {
        StoredEntityStringData entity = new StoredEntityStringData("group1", "artifact1", "1.0.0");

        Assertions.assertEquals("group1", entity.getGroupId());
        Assertions.assertEquals("artifact1", entity.getArtifactId());
        Assertions.assertEquals("1.0.0", entity.getVersionId());
        Assertions.assertNull(entity.getData());
    }

    @Test
    public void testGetDataReturnsData()
    {
        StoredEntityStringData entity = new StoredEntityStringData("group1", "artifact1", "1.0.0", "someData", null);

        Assertions.assertEquals("someData", entity.getData());
    }

    @Test
    public void testGetDataReturnsNullWhenNotSet()
    {
        StoredEntityStringData entity = new StoredEntityStringData("group1", "artifact1", "1.0.0");

        Assertions.assertNull(entity.getData());
    }

    @Test
    public void testEqualsReturnsTrueForSameObject()
    {
        StoredEntityStringData entity = new StoredEntityStringData("group1", "artifact1", "1.0.0", "data", null);

        Assertions.assertTrue(entity.equals(entity));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentData()
    {
        StoredEntityStringData entity1 = new StoredEntityStringData("group1", "artifact1", "1.0.0", "data1", null);
        StoredEntityStringData entity2 = new StoredEntityStringData("group1", "artifact1", "1.0.0", "data2", null);

        Assertions.assertFalse(entity1.equals(entity2));
    }

    @Test
    public void testEqualsReturnsFalseForNull()
    {
        StoredEntityStringData entity = new StoredEntityStringData("group1", "artifact1", "1.0.0", "data", null);

        Assertions.assertFalse(entity.equals(null));
    }

    @Test
    public void testHashCodeIsConsistent()
    {
        StoredEntityStringData entity = new StoredEntityStringData("group1", "artifact1", "1.0.0", "data", null);

        Assertions.assertEquals(entity.hashCode(), entity.hashCode());
    }

    @Test
    public void testHashCodeEqualForEqualObjects()
    {
        StoredEntityStringData entity1 = new StoredEntityStringData("group1", "artifact1", "1.0.0", "data", null);
        StoredEntityStringData entity2 = new StoredEntityStringData("group1", "artifact1", "1.0.0", "data", null);

        Assertions.assertEquals(entity1.hashCode(), entity2.hashCode());
    }
}
