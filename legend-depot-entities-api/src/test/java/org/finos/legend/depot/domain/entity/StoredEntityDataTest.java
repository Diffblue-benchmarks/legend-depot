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

package org.finos.legend.depot.domain.entity;

import org.finos.legend.depot.store.model.entities.EntityDefinition;
import org.finos.legend.depot.store.model.entities.StoredEntityData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

public class StoredEntityDataTest
{
    @Test
    public void testFullConstructorSetsEntityAndAttributes()
    {
        EntityDefinition entityDef = new EntityDefinition("my::path", "my::classifier", null);
        Map<String, String> attributes = Collections.singletonMap("key", "value");

        StoredEntityData data = new StoredEntityData("com.example", "my-artifact", "1.0.0", entityDef, attributes);

        Assertions.assertEquals("com.example", data.getGroupId());
        Assertions.assertEquals("my-artifact", data.getArtifactId());
        Assertions.assertEquals("1.0.0", data.getVersionId());
        Assertions.assertEquals(entityDef, data.getEntity());
        Assertions.assertEquals(attributes, data.getEntityAttributes());
    }

    @Test
    public void testSimpleConstructorSetsGroupArtifactVersion()
    {
        StoredEntityData data = new StoredEntityData("com.example", "my-artifact", "1.0.0");

        Assertions.assertEquals("com.example", data.getGroupId());
        Assertions.assertEquals("my-artifact", data.getArtifactId());
        Assertions.assertEquals("1.0.0", data.getVersionId());
        Assertions.assertNull(data.getEntity());
    }

    @Test
    public void testGetIdReturnsEmptyString()
    {
        StoredEntityData data = new StoredEntityData("g", "a", "1.0.0");

        Assertions.assertEquals("", data.getId());
    }

    @Test
    public void testGetEntityReturnsEntity()
    {
        EntityDefinition entityDef = new EntityDefinition("path::to::Entity", "meta::classifier", null);
        StoredEntityData data = new StoredEntityData("g", "a", "1.0.0", entityDef, null);

        Assertions.assertNotNull(data.getEntity());
        Assertions.assertEquals("path::to::Entity", data.getEntity().getPath());
    }

    @Test
    public void testEqualsWithSameValues()
    {
        EntityDefinition entityDef = new EntityDefinition("p", "c", null);
        StoredEntityData a = new StoredEntityData("g", "a", "1.0.0", entityDef, null);
        StoredEntityData b = new StoredEntityData("g", "a", "1.0.0", entityDef, null);

        Assertions.assertEquals(a, b);
    }

    @Test
    public void testEqualsWithDifferentValues()
    {
        StoredEntityData a = new StoredEntityData("g1", "a", "1.0.0");
        StoredEntityData b = new StoredEntityData("g2", "a", "1.0.0");

        Assertions.assertNotEquals(a, b);
    }

    @Test
    public void testHashCodeConsistency()
    {
        EntityDefinition entityDef = new EntityDefinition("p", "c", null);
        StoredEntityData a = new StoredEntityData("g", "a", "1.0.0", entityDef, null);
        StoredEntityData b = new StoredEntityData("g", "a", "1.0.0", entityDef, null);

        Assertions.assertEquals(a.hashCode(), b.hashCode());
    }
}
