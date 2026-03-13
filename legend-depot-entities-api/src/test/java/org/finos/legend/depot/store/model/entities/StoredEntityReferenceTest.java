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

public class StoredEntityReferenceTest
{
    @Test
    public void testConstructorWithAllParameters()
    {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("key1", "value1");
        attributes.put("key2", "value2");

        StoredEntityReference entity = new StoredEntityReference(
                "org.example",
                "test-artifact",
                "1.0.0",
                "test::reference::path",
                attributes
        );

        Assertions.assertNotNull(entity);
        Assertions.assertEquals("org.example", entity.getGroupId());
        Assertions.assertEquals("test-artifact", entity.getArtifactId());
        Assertions.assertEquals("1.0.0", entity.getVersionId());
        Assertions.assertEquals("test::reference::path", entity.getReference());
        Assertions.assertEquals(attributes, entity.getEntityAttributes());
    }

    @Test
    public void testConstructorWithThreeParameters()
    {
        StoredEntityReference entity = new StoredEntityReference(
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
    public void testGetReference()
    {
        StoredEntityReference entity = new StoredEntityReference(
                "org.example",
                "test-artifact",
                "1.0.0",
                "test::reference",
                null
        );

        String reference = entity.getReference();
        Assertions.assertEquals("test::reference", reference);
    }

    @Test
    public void testEquals()
    {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("key1", "value1");

        StoredEntityReference entity1 = new StoredEntityReference(
                "org.example",
                "test-artifact",
                "1.0.0",
                "test::reference",
                attributes
        );

        StoredEntityReference entity2 = new StoredEntityReference(
                "org.example",
                "test-artifact",
                "1.0.0",
                "test::reference",
                attributes
        );

        StoredEntityReference entity3 = new StoredEntityReference(
                "org.other",
                "test-artifact",
                "1.0.0",
                "test::reference",
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

        StoredEntityReference entity1 = new StoredEntityReference(
                "org.example",
                "test-artifact",
                "1.0.0",
                "test::reference",
                attributes
        );

        StoredEntityReference entity2 = new StoredEntityReference(
                "org.example",
                "test-artifact",
                "1.0.0",
                "test::reference",
                attributes
        );

        int hash1 = entity1.hashCode();
        int hash2 = entity2.hashCode();

        Assertions.assertEquals(hash1, hash2);
    }
}
