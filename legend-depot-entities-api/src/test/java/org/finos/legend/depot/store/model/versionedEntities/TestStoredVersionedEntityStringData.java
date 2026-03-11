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

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestStoredVersionedEntityStringData
{
    @Test
    public void canCreateWithAllParameters()
    {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("key", "value");
        StoredVersionedEntityStringData entity = new StoredVersionedEntityStringData(
                "org.finos", "test-artifact", "1.0.0", "{\"entity\":\"data\"}", attributes);

        assertNotNull(entity);
        assertEquals("org.finos", entity.getGroupId());
        assertEquals("test-artifact", entity.getArtifactId());
        assertEquals("1.0.0", entity.getVersionId());
        assertEquals("{\"entity\":\"data\"}", entity.getData());
    }

    @Test
    public void canCreateWithMinimalParameters()
    {
        StoredVersionedEntityStringData entity = new StoredVersionedEntityStringData(
                "org.finos", "test-artifact", "2.0.0");

        assertNotNull(entity);
        assertEquals("org.finos", entity.getGroupId());
        assertEquals("test-artifact", entity.getArtifactId());
        assertEquals("2.0.0", entity.getVersionId());
        assertNull(entity.getData());
    }

    @Test
    public void testGetData()
    {
        StoredVersionedEntityStringData entity = new StoredVersionedEntityStringData(
                "org.finos", "test-artifact", "1.0.0", "someData", Collections.emptyMap());

        assertEquals("someData", entity.getData());
    }

    @Test
    public void testEquals()
    {
        StoredVersionedEntityStringData entity1 = new StoredVersionedEntityStringData(
                "org.finos", "test-artifact", "1.0.0", "data", Collections.emptyMap());
        StoredVersionedEntityStringData entity2 = new StoredVersionedEntityStringData(
                "org.finos", "test-artifact", "1.0.0", "data", Collections.emptyMap());
        StoredVersionedEntityStringData entity3 = new StoredVersionedEntityStringData(
                "org.finos", "other-artifact", "1.0.0", "data", Collections.emptyMap());

        assertEquals(entity1, entity2);
        assertNotEquals(entity1, entity3);
    }

    @Test
    public void testHashCode()
    {
        StoredVersionedEntityStringData entity1 = new StoredVersionedEntityStringData(
                "org.finos", "test-artifact", "1.0.0", "data", Collections.emptyMap());
        StoredVersionedEntityStringData entity2 = new StoredVersionedEntityStringData(
                "org.finos", "test-artifact", "1.0.0", "data", Collections.emptyMap());

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }
}
