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

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StoredVersionedEntityStringDataTest
{
    @Test
    public void canCreateStoredVersionedEntityStringDataWithFullConstructor()
    {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("attr1", "value1");

        StoredVersionedEntityStringData entity = new StoredVersionedEntityStringData(
                "groupId",
                "artifactId",
                "versionId",
                "test data",
                attributes
        );

        assertNotNull(entity);
        assertEquals("groupId", entity.getGroupId());
        assertEquals("artifactId", entity.getArtifactId());
        assertEquals("versionId", entity.getVersionId());
        assertEquals("test data", entity.getData());
        assertEquals(attributes, entity.getEntityAttributes());
    }

    @Test
    public void canCreateStoredVersionedEntityStringDataWithSimpleConstructor()
    {
        StoredVersionedEntityStringData entity = new StoredVersionedEntityStringData(
                "groupId",
                "artifactId",
                "versionId"
        );

        assertNotNull(entity);
        assertEquals("groupId", entity.getGroupId());
        assertEquals("artifactId", entity.getArtifactId());
        assertEquals("versionId", entity.getVersionId());
        assertNull(entity.getData());
    }

    @Test
    public void canGetData()
    {
        Map<String, String> attributes = new HashMap<>();
        StoredVersionedEntityStringData entity = new StoredVersionedEntityStringData(
                "groupId",
                "artifactId",
                "versionId",
                "test data",
                attributes
        );

        String data = entity.getData();

        assertNotNull(data);
        assertEquals("test data", data);
    }

    @Test
    public void testEquals()
    {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("attr1", "value1");

        StoredVersionedEntityStringData entity1 = new StoredVersionedEntityStringData(
                "groupId",
                "artifactId",
                "versionId",
                "test data",
                attributes
        );
        StoredVersionedEntityStringData entity2 = new StoredVersionedEntityStringData(
                "groupId",
                "artifactId",
                "versionId",
                "test data",
                attributes
        );
        StoredVersionedEntityStringData entity3 = new StoredVersionedEntityStringData(
                "differentGroupId",
                "artifactId",
                "versionId",
                "test data",
                attributes
        );

        assertTrue(entity1.equals(entity1));
        assertTrue(entity1.equals(entity2));
        assertFalse(entity1.equals(entity3));
        assertFalse(entity1.equals(null));
        assertFalse(entity1.equals("not a StoredVersionedEntityStringData"));
    }

    @Test
    public void testHashCode()
    {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("attr1", "value1");

        StoredVersionedEntityStringData entity1 = new StoredVersionedEntityStringData(
                "groupId",
                "artifactId",
                "versionId",
                "test data",
                attributes
        );
        StoredVersionedEntityStringData entity2 = new StoredVersionedEntityStringData(
                "groupId",
                "artifactId",
                "versionId",
                "test data",
                attributes
        );
        StoredVersionedEntityStringData entity3 = new StoredVersionedEntityStringData(
                "differentGroupId",
                "artifactId",
                "versionId",
                "test data",
                attributes
        );

        assertEquals(entity1.hashCode(), entity2.hashCode());
        assertNotEquals(entity1.hashCode(), entity3.hashCode());
    }
}
