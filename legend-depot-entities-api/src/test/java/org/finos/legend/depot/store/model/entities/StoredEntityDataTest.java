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

public class StoredEntityDataTest
{
    @Test
    public void testSimpleConstructor()
    {
        StoredEntityData data = new StoredEntityData("org.finos", "legend-depot", "1.0.0");
        Assertions.assertEquals("org.finos", data.getGroupId());
        Assertions.assertEquals("legend-depot", data.getArtifactId());
        Assertions.assertEquals("1.0.0", data.getVersionId());
        Assertions.assertNull(data.getEntity());
        Assertions.assertNull(data.getEntityAttributes());
    }

    @Test
    public void testFullConstructor()
    {
        EntityDefinition entity = new EntityDefinition("model::Person", "meta::pure::metamodel::type::Class", Collections.emptyMap());
        Map<String, String> attrs = new HashMap<>();
        attrs.put("path", "model::Person");
        StoredEntityData data = new StoredEntityData("org.finos", "legend-depot", "1.0.0", entity, attrs);

        Assertions.assertNotNull(data.getEntity());
        Assertions.assertEquals("model::Person", data.getEntity().getPath());
        Assertions.assertNotNull(data.getEntityAttributes());
    }

    @Test
    public void testGetId()
    {
        StoredEntityData data = new StoredEntityData("org.finos", "legend-depot", "1.0.0");
        Assertions.assertEquals("", data.getId());
    }

    @Test
    public void testEquals()
    {
        StoredEntityData data1 = new StoredEntityData("org.finos", "legend-depot", "1.0.0");
        StoredEntityData data2 = new StoredEntityData("org.finos", "legend-depot", "1.0.0");
        Assertions.assertEquals(data1, data2);
    }

    @Test
    public void testNotEquals()
    {
        StoredEntityData data1 = new StoredEntityData("org.finos", "legend-depot", "1.0.0");
        StoredEntityData data2 = new StoredEntityData("org.finos", "legend-depot", "2.0.0");
        Assertions.assertNotEquals(data1, data2);
    }
}
