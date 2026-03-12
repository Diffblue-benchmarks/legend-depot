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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class TestDepotEntity
{
    @Test
    public void testDefaultConstructor()
    {
        DepotEntity entity = new DepotEntity();
        Assertions.assertNull(entity.getGroupId());
        Assertions.assertNull(entity.getEntity());
    }

    @Test
    public void testThreeArgConstructor()
    {
        DepotEntity entity = new DepotEntity("org.finos", "artifact", "1.0.0");
        Assertions.assertEquals("org.finos", entity.getGroupId());
        Assertions.assertEquals("artifact", entity.getArtifactId());
        Assertions.assertEquals("1.0.0", entity.getVersionId());
        Assertions.assertNull(entity.getEntity());
    }

    @Test
    public void testFourArgConstructor()
    {
        EntityDefinition def = new EntityDefinition("path", "classifier", Collections.emptyMap());
        DepotEntity entity = new DepotEntity("org.finos", "artifact", "1.0.0", def);
        Assertions.assertNotNull(entity.getEntity());
        Assertions.assertEquals("path", entity.getEntity().getPath());
        Assertions.assertFalse(entity.isVersionedEntity());
    }

    @Test
    public void testEquality()
    {
        DepotEntity a = new DepotEntity("org.finos", "artifact", "1.0.0");
        DepotEntity b = new DepotEntity("org.finos", "artifact", "1.0.0");
        Assertions.assertEquals(a, b);
    }

    @Test
    public void testNotEqualDifferentArtifact()
    {
        DepotEntity a = new DepotEntity("org.finos", "artifact-a", "1.0.0");
        DepotEntity b = new DepotEntity("org.finos", "artifact-b", "1.0.0");
        Assertions.assertNotEquals(a, b);
    }
}
