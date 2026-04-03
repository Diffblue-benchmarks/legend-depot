// Copyright 2021 Goldman Sachs
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

import org.finos.legend.depot.store.model.entities.EntityDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class StoredVersionedEntityDataTest
{

    @Test
    public void testConstructorWithEntity()
    {
        EntityDefinition entity = new EntityDefinition("some::path", "some::classifier", null);
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("key", "value");

        StoredVersionedEntityData data = new StoredVersionedEntityData("group", "artifact", "1.0.0", entity, attributes);

        Assertions.assertEquals("group", data.getGroupId());
        Assertions.assertEquals("artifact", data.getArtifactId());
        Assertions.assertEquals("1.0.0", data.getVersionId());
        Assertions.assertEquals(entity, data.getEntity());
    }

    @Test
    public void testConstructorWithoutEntity()
    {
        StoredVersionedEntityData data = new StoredVersionedEntityData("group", "artifact", "1.0.0");

        Assertions.assertEquals("group", data.getGroupId());
        Assertions.assertEquals("artifact", data.getArtifactId());
        Assertions.assertEquals("1.0.0", data.getVersionId());
        Assertions.assertNull(data.getEntity());
    }

    @Test
    public void testGetIdReturnsEmpty()
    {
        StoredVersionedEntityData data = new StoredVersionedEntityData("group", "artifact", "1.0.0");

        Assertions.assertEquals("", data.getId());
    }

    @Test
    public void testGetEntity()
    {
        EntityDefinition entity = new EntityDefinition("some::path", "some::classifier", null);
        StoredVersionedEntityData data = new StoredVersionedEntityData("group", "artifact", "1.0.0", entity, null);

        Assertions.assertEquals(entity, data.getEntity());
    }

    @Test
    public void testEquals()
    {
        EntityDefinition entity = new EntityDefinition("some::path", "some::classifier", null);
        StoredVersionedEntityData a = new StoredVersionedEntityData("group", "artifact", "1.0.0", entity, null);
        StoredVersionedEntityData b = new StoredVersionedEntityData("group", "artifact", "1.0.0", entity, null);

        Assertions.assertTrue(a.equals(b));
    }

    @Test
    public void testEqualsNotEqual()
    {
        StoredVersionedEntityData a = new StoredVersionedEntityData("group", "artifact", "1.0.0");
        StoredVersionedEntityData b = new StoredVersionedEntityData("group", "artifact", "2.0.0");

        Assertions.assertFalse(a.equals(b));
    }

    @Test
    public void testEqualsNull()
    {
        StoredVersionedEntityData a = new StoredVersionedEntityData("group", "artifact", "1.0.0");

        Assertions.assertFalse(a.equals(null));
    }

    @Test
    public void testHashCode()
    {
        EntityDefinition entity = new EntityDefinition("some::path", "some::classifier", null);
        StoredVersionedEntityData a = new StoredVersionedEntityData("group", "artifact", "1.0.0", entity, null);
        StoredVersionedEntityData b = new StoredVersionedEntityData("group", "artifact", "1.0.0", entity, null);

        Assertions.assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void testHashCodeDiffers()
    {
        StoredVersionedEntityData a = new StoredVersionedEntityData("group", "artifact", "1.0.0");
        StoredVersionedEntityData b = new StoredVersionedEntityData("group", "artifact", "2.0.0");

        Assertions.assertNotEquals(a.hashCode(), b.hashCode());
    }
}
