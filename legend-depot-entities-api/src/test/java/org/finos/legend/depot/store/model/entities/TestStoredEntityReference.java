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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestStoredEntityReference
{
    @Test
    public void canCreateWithAllParameters()
    {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("key", "value");
        StoredEntityReference entity = new StoredEntityReference(
                "org.finos", "test-artifact", "1.0.0", "ref::path", attributes);

        assertNotNull(entity);
        assertEquals("org.finos", entity.getGroupId());
        assertEquals("test-artifact", entity.getArtifactId());
        assertEquals("1.0.0", entity.getVersionId());
        assertEquals("ref::path", entity.getReference());
        assertEquals("value", entity.getEntityAttributes().get("key"));
    }

    @Test
    public void canCreateWithMinimalParameters()
    {
        StoredEntityReference entity = new StoredEntityReference(
                "org.finos", "test-artifact", "2.0.0");

        assertNotNull(entity);
        assertEquals("org.finos", entity.getGroupId());
        assertEquals("test-artifact", entity.getArtifactId());
        assertEquals("2.0.0", entity.getVersionId());
        assertNull(entity.getReference());
    }

    @Test
    public void testGetReference()
    {
        StoredEntityReference entity = new StoredEntityReference(
                "org.finos", "test-artifact", "1.0.0", "my::reference", Collections.emptyMap());

        assertEquals("my::reference", entity.getReference());
    }

    @Test
    public void testEquals()
    {
        StoredEntityReference entity1 = new StoredEntityReference(
                "org.finos", "test-artifact", "1.0.0", "ref", Collections.emptyMap());
        StoredEntityReference entity2 = new StoredEntityReference(
                "org.finos", "test-artifact", "1.0.0", "ref", Collections.emptyMap());
        StoredEntityReference entity3 = new StoredEntityReference(
                "org.finos", "other-artifact", "1.0.0", "ref", Collections.emptyMap());

        assertEquals(entity1, entity2);
        assertNotEquals(entity1, entity3);
    }

    @Test
    public void testHashCode()
    {
        StoredEntityReference entity1 = new StoredEntityReference(
                "org.finos", "test-artifact", "1.0.0", "ref", Collections.emptyMap());
        StoredEntityReference entity2 = new StoredEntityReference(
                "org.finos", "test-artifact", "1.0.0", "ref", Collections.emptyMap());

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }
}
