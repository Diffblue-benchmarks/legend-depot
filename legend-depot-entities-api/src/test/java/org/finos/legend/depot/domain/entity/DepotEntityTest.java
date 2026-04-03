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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DepotEntityTest
{
    @Test
    public void testDefaultConstructor()
    {
        DepotEntity entity = new DepotEntity();

        Assertions.assertNull(entity.getEntity());
        Assertions.assertFalse(entity.isVersionedEntity());
    }

    @Test
    public void testConstructorWithGroupArtifactVersion()
    {
        DepotEntity entity = new DepotEntity("com.example", "my-artifact", "1.0.0");

        Assertions.assertEquals("com.example", entity.getGroupId());
        Assertions.assertEquals("my-artifact", entity.getArtifactId());
        Assertions.assertEquals("1.0.0", entity.getVersionId());
        Assertions.assertNull(entity.getEntity());
    }

    @Test
    public void testConstructorWithEntity()
    {
        EntityDefinition entityDef = new EntityDefinition("my::path", "my::classifier", null);
        DepotEntity depotEntity = new DepotEntity("com.example", "my-artifact", "1.0.0", entityDef);

        Assertions.assertEquals("com.example", depotEntity.getGroupId());
        Assertions.assertEquals("my-artifact", depotEntity.getArtifactId());
        Assertions.assertEquals("1.0.0", depotEntity.getVersionId());
        Assertions.assertEquals(entityDef, depotEntity.getEntity());
        Assertions.assertFalse(depotEntity.isVersionedEntity());
    }

    @Test
    public void testIsVersionedEntityDefaultsFalse()
    {
        DepotEntity entity = new DepotEntity("g", "a", "v", new EntityDefinition("p", "c", null));

        Assertions.assertFalse(entity.isVersionedEntity());
    }

    @Test
    public void testGetEntity()
    {
        EntityDefinition entityDef = new EntityDefinition("path::to::Entity", "meta::classifier", null);
        DepotEntity depotEntity = new DepotEntity("g", "a", "1.0.0", entityDef);

        Assertions.assertNotNull(depotEntity.getEntity());
        Assertions.assertEquals("path::to::Entity", depotEntity.getEntity().getPath());
    }

    @Test
    public void testEqualsAndHashCodeSameValues()
    {
        EntityDefinition entityDef = new EntityDefinition("p", "c", null);
        DepotEntity a = new DepotEntity("g", "a", "1.0.0", entityDef);
        DepotEntity b = new DepotEntity("g", "a", "1.0.0", entityDef);

        Assertions.assertEquals(a, b);
        Assertions.assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void testEqualsDifferentValues()
    {
        DepotEntity a = new DepotEntity("g1", "a", "1.0.0");
        DepotEntity b = new DepotEntity("g2", "a", "1.0.0");

        Assertions.assertNotEquals(a, b);
    }

    @Test
    public void testHashCodeConsistency()
    {
        DepotEntity entity = new DepotEntity("g", "a", "1.0.0");

        Assertions.assertEquals(entity.hashCode(), entity.hashCode());
    }
}
