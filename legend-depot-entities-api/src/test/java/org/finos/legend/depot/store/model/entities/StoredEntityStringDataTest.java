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
    public void testConstructorWithAllParameters()
    {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("key1", "value1");
        attributes.put("key2", "value2");

        StoredEntityStringData entity = new StoredEntityStringData(
                "org.example",
                "test-artifact",
                "1.0.0",
                "test data content",
                attributes
        );

        Assertions.assertNotNull(entity);
        Assertions.assertEquals("org.example", entity.getGroupId());
        Assertions.assertEquals("test-artifact", entity.getArtifactId());
        Assertions.assertEquals("1.0.0", entity.getVersionId());
        Assertions.assertEquals("test data content", entity.getData());
        Assertions.assertEquals(attributes, entity.getEntityAttributes());
    }

    @Test
    public void testConstructorWithThreeParameters()
    {
        StoredEntityStringData entity = new StoredEntityStringData(
                "org.example",
                "test-artifact",
                "1.0.0"
        );

        Assertions.assertNotNull(entity);
        Assertions.assertEquals("org.example", entity.getGroupId());
        Assertions.assertEquals("test-artifact", entity.getArtifactId());
        Assertions.assertEquals("1.0.0", entity.getVersionId());
    }

    @Test
    public void testGetData()
    {
        StoredEntityStringData entity = new StoredEntityStringData(
                "org.example",
                "test-artifact",
                "1.0.0",
                "test data",
                null
        );

        String data = entity.getData();
        Assertions.assertEquals("test data", data);
    }

    @Test
    public void testEquals()
    {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("key1", "value1");

        StoredEntityStringData entity1 = new StoredEntityStringData(
                "org.example",
                "test-artifact",
                "1.0.0",
                "test data",
                attributes
        );

        StoredEntityStringData entity2 = new StoredEntityStringData(
                "org.example",
                "test-artifact",
                "1.0.0",
                "test data",
                attributes
        );

        StoredEntityStringData entity3 = new StoredEntityStringData(
                "org.other",
                "test-artifact",
                "1.0.0",
                "test data",
                attributes
        );

        Assertions.assertTrue(entity1.equals(entity2));
        Assertions.assertFalse(entity1.equals(entity3));
        Assertions.assertFalse(entity1.equals(null));
    }

    @Test
    public void testHashCode()
    {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("key1", "value1");

        StoredEntityStringData entity1 = new StoredEntityStringData(
                "org.example",
                "test-artifact",
                "1.0.0",
                "test data",
                attributes
        );

        StoredEntityStringData entity2 = new StoredEntityStringData(
                "org.example",
                "test-artifact",
                "1.0.0",
                "test data",
                attributes
        );

        int hash1 = entity1.hashCode();
        int hash2 = entity2.hashCode();

        Assertions.assertEquals(hash1, hash2);
    }
}
