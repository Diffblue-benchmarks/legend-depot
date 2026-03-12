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

public class TestEntityDefinition
{
    @Test
    public void testConstructorAndGetters()
    {
        Map<String, Object> content = new HashMap<>();
        content.put("key", "value");
        EntityDefinition entity = new EntityDefinition("my::path", "meta::pure::Class", content);

        Assertions.assertEquals("my::path", entity.getPath());
        Assertions.assertEquals("meta::pure::Class", entity.getClassifierPath());
        Assertions.assertEquals(content, entity.getContent());
    }

    @Test
    public void testSetClassifierPath()
    {
        EntityDefinition entity = new EntityDefinition("my::path", "original", Collections.emptyMap());
        entity.setClassifierPath("updated::path");
        Assertions.assertEquals("updated::path", entity.getClassifierPath());
    }

    @Test
    public void testEqualitySameContent()
    {
        Map<String, Object> content = new HashMap<>();
        content.put("key", "value");

        EntityDefinition a = new EntityDefinition("my::path", "meta::pure::Class", content);
        EntityDefinition b = new EntityDefinition("my::path", "meta::pure::Class", content);

        Assertions.assertEquals(a, b);
        Assertions.assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void testNotEqualForDifferentPaths()
    {
        EntityDefinition a = new EntityDefinition("path1", "classifier", Collections.emptyMap());
        EntityDefinition b = new EntityDefinition("path2", "classifier", Collections.emptyMap());
        Assertions.assertNotEquals(a, b);
    }

    @Test
    public void testNullContent()
    {
        EntityDefinition entity = new EntityDefinition("path", "classifier", null);
        Assertions.assertNull(entity.getContent());
    }
}
