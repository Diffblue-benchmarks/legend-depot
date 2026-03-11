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

package org.finos.legend.depot.domain.entity;

import org.finos.legend.depot.store.model.entities.EntityDefinition;
import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestDepotEntity
{
    @Test
    void canCreateWithDefaultConstructor()
    {
        DepotEntity depotEntity = new DepotEntity();

        assertNotNull(depotEntity);
        assertNull(depotEntity.getEntity());
        assertFalse(depotEntity.isVersionedEntity());
    }

    @Test
    void canCreateWithGroupArtifactVersion()
    {
        DepotEntity depotEntity = new DepotEntity("org.finos", "test-artifact", "1.0.0");

        assertEquals("org.finos", depotEntity.getGroupId());
        assertEquals("test-artifact", depotEntity.getArtifactId());
        assertEquals("1.0.0", depotEntity.getVersionId());
        assertNull(depotEntity.getEntity());
    }

    @Test
    void canCreateWithEntity()
    {
        Entity entity = new EntityDefinition("test::path", "meta::pure::metamodel::type::Class", Collections.emptyMap());
        DepotEntity depotEntity = new DepotEntity("org.finos", "test-artifact", "1.0.0", entity);

        assertEquals("org.finos", depotEntity.getGroupId());
        assertEquals("test-artifact", depotEntity.getArtifactId());
        assertEquals("1.0.0", depotEntity.getVersionId());
        assertEquals(entity, depotEntity.getEntity());
        assertFalse(depotEntity.isVersionedEntity());
    }

    @Test
    void testEquals()
    {
        Entity entity = new EntityDefinition("test::path", "meta::pure::metamodel::type::Class", Collections.emptyMap());
        DepotEntity depotEntity1 = new DepotEntity("org.finos", "test-artifact", "1.0.0", entity);
        DepotEntity depotEntity2 = new DepotEntity("org.finos", "test-artifact", "1.0.0", entity);
        DepotEntity depotEntity3 = new DepotEntity("org.finos", "other-artifact", "1.0.0", entity);

        assertEquals(depotEntity1, depotEntity2);
        assertNotEquals(depotEntity1, depotEntity3);
    }

    @Test
    void testHashCode()
    {
        Entity entity = new EntityDefinition("test::path", "meta::pure::metamodel::type::Class", Collections.emptyMap());
        DepotEntity depotEntity1 = new DepotEntity("org.finos", "test-artifact", "1.0.0", entity);
        DepotEntity depotEntity2 = new DepotEntity("org.finos", "test-artifact", "1.0.0", entity);

        assertEquals(depotEntity1.hashCode(), depotEntity2.hashCode());
    }
}
