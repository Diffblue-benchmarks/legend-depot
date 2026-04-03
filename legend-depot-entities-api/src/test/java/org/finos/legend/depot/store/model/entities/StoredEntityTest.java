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

public class StoredEntityTest
{
    @Test
    public void testFullConstructorSetsEntityAttributes()
    {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("key", "value");
        StoredEntityData entity = new StoredEntityData("group1", "artifact1", "1.0.0", null, attributes);

        Assertions.assertEquals("group1", entity.getGroupId());
        Assertions.assertEquals("artifact1", entity.getArtifactId());
        Assertions.assertEquals("1.0.0", entity.getVersionId());
        Assertions.assertNotNull(entity.getEntityAttributes());
        Assertions.assertEquals("value", entity.getEntityAttributes().get("key"));
    }

    @Test
    public void testFullConstructorWithNullEntityAttributes()
    {
        StoredEntityData entity = new StoredEntityData("group1", "artifact1", "1.0.0", null, null);

        Assertions.assertEquals("group1", entity.getGroupId());
        Assertions.assertEquals("artifact1", entity.getArtifactId());
        Assertions.assertEquals("1.0.0", entity.getVersionId());
        Assertions.assertNull(entity.getEntityAttributes());
    }

    @Test
    public void testShortConstructorSetsFields()
    {
        StoredEntityData entity = new StoredEntityData("group1", "artifact1", "1.0.0");

        Assertions.assertEquals("group1", entity.getGroupId());
        Assertions.assertEquals("artifact1", entity.getArtifactId());
        Assertions.assertEquals("1.0.0", entity.getVersionId());
    }

    @Test
    public void testShortConstructorEntityAttributesIsNull()
    {
        StoredEntityData entity = new StoredEntityData("group1", "artifact1", "1.0.0");

        Assertions.assertNull(entity.getEntityAttributes());
    }

    @Test
    public void testGetEntityAttributesReturnsAttributes()
    {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("foo", "bar");
        StoredEntityData entity = new StoredEntityData("group1", "artifact1", "1.0.0", null, attributes);

        Map<String, ?> result = entity.getEntityAttributes();

        Assertions.assertNotNull(result);
        Assertions.assertEquals("bar", result.get("foo"));
    }

    @Test
    public void testGetIdReturnsEmptyString()
    {
        StoredEntityData entity = new StoredEntityData("group1", "artifact1", "1.0.0");

        Assertions.assertEquals("", entity.getId());
    }
}
