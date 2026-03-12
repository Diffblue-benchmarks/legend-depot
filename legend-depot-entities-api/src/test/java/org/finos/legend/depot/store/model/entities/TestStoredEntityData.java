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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestStoredEntityData
{
    @Test
    public void testSimpleConstructor()
    {
        StoredEntityData stored = new StoredEntityData("org.finos", "artifact", "1.0.0");
        Assertions.assertEquals("org.finos", stored.getGroupId());
        Assertions.assertEquals("artifact", stored.getArtifactId());
        Assertions.assertEquals("1.0.0", stored.getVersionId());
        Assertions.assertNull(stored.getEntity());
    }

    @Test
    public void testFullConstructor()
    {
        EntityDefinition entity = new EntityDefinition("path", "classifier", Collections.emptyMap());
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("classifier", "meta::Class");

        StoredEntityData stored = new StoredEntityData("org.finos", "artifact", "1.0.0", entity, attrs);
        Assertions.assertNotNull(stored.getEntity());
        Assertions.assertEquals("path", stored.getEntity().getPath());
        Assertions.assertNotNull(stored.getEntityAttributes());
    }

    @Test
    public void testGetIdReturnsEmptyString()
    {
        StoredEntityData stored = new StoredEntityData("org.finos", "artifact", "1.0.0");
        Assertions.assertEquals("", stored.getId());
    }

    @Test
    public void testEquality()
    {
        StoredEntityData a = new StoredEntityData("org.finos", "artifact", "1.0.0");
        StoredEntityData b = new StoredEntityData("org.finos", "artifact", "1.0.0");
        Assertions.assertEquals(a, b);
    }

    @Test
    public void testNotEqualDifferentVersion()
    {
        StoredEntityData a = new StoredEntityData("org.finos", "artifact", "1.0.0");
        StoredEntityData b = new StoredEntityData("org.finos", "artifact", "2.0.0");
        Assertions.assertNotEquals(a, b);
    }
}
