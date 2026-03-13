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

public class EntityDefinitionTest
{
    @Test
    public void testConstructorAndGetters()
    {
        Map<String, Object> content = new HashMap<>();
        content.put("_type", "class");
        content.put("name", "Person");
        EntityDefinition entity = new EntityDefinition("model::Person", "meta::pure::metamodel::type::Class", content);

        Assertions.assertEquals("model::Person", entity.getPath());
        Assertions.assertEquals("meta::pure::metamodel::type::Class", entity.getClassifierPath());
        Assertions.assertNotNull(entity.getContent());
        Assertions.assertEquals("class", entity.getContent().get("_type"));
    }

    @Test
    public void testSetClassifierPath()
    {
        EntityDefinition entity = new EntityDefinition("model::Person", "meta::pure::metamodel::type::Class", Collections.emptyMap());
        entity.setClassifierPath("meta::pure::metamodel::type::Enumeration");
        Assertions.assertEquals("meta::pure::metamodel::type::Enumeration", entity.getClassifierPath());
    }

    @Test
    public void testEqualsSameContent()
    {
        Map<String, Object> content = new HashMap<>();
        content.put("key", "value");

        EntityDefinition entity1 = new EntityDefinition("model::Person", "meta::pure::metamodel::type::Class", content);
        EntityDefinition entity2 = new EntityDefinition("model::Person", "meta::pure::metamodel::type::Class", content);
        Assertions.assertEquals(entity1, entity2);
    }

    @Test
    public void testNotEquals()
    {
        EntityDefinition entity1 = new EntityDefinition("model::Person", "meta::pure::metamodel::type::Class", Collections.emptyMap());
        EntityDefinition entity2 = new EntityDefinition("model::Address", "meta::pure::metamodel::type::Class", Collections.emptyMap());
        Assertions.assertNotEquals(entity1, entity2);
    }

    @Test
    public void testNullContent()
    {
        EntityDefinition entity = new EntityDefinition("model::Person", "meta::pure::metamodel::type::Class", null);
        Assertions.assertNull(entity.getContent());
    }
}
