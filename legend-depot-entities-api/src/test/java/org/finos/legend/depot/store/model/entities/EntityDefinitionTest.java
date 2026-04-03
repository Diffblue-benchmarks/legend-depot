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

package org.finos.legend.depot.store.model.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class EntityDefinitionTest
{

    @Test
    public void testConstructorAndGetters()
    {
        Map<String, Object> content = new HashMap<>();
        content.put("key", "value");

        EntityDefinition entity = new EntityDefinition("some::path", "some::classifier", content);

        Assertions.assertEquals("some::path", entity.getPath());
        Assertions.assertEquals("some::classifier", entity.getClassifierPath());
        Assertions.assertEquals(content, entity.getContent());
    }

    @Test
    public void testGetPathReturnsNull()
    {
        EntityDefinition entity = new EntityDefinition(null, null, null);

        Assertions.assertNull(entity.getPath());
    }

    @Test
    public void testGetClassifierPath()
    {
        EntityDefinition entity = new EntityDefinition("p", "classifier::path", null);

        Assertions.assertEquals("classifier::path", entity.getClassifierPath());
    }

    @Test
    public void testSetClassifierPath()
    {
        EntityDefinition entity = new EntityDefinition("some::path", "original::classifier", null);

        entity.setClassifierPath("new::classifier");

        Assertions.assertEquals("new::classifier", entity.getClassifierPath());
    }

    @Test
    public void testGetContent()
    {
        Map<String, Object> content = new HashMap<>();
        content.put("name", "test");

        EntityDefinition entity = new EntityDefinition("some::path", "some::classifier", content);

        Assertions.assertEquals(content, entity.getContent());
    }

    @Test
    public void testEquals()
    {
        Map<String, Object> content = new HashMap<>();
        content.put("key", "value");

        EntityDefinition a = new EntityDefinition("some::path", "some::classifier", content);
        EntityDefinition b = new EntityDefinition("some::path", "some::classifier", content);

        Assertions.assertTrue(a.equals(b));
    }

    @Test
    public void testEqualsNotEqual()
    {
        EntityDefinition a = new EntityDefinition("some::path", "some::classifier", null);
        EntityDefinition b = new EntityDefinition("other::path", "some::classifier", null);

        Assertions.assertFalse(a.equals(b));
    }

    @Test
    public void testEqualsNull()
    {
        EntityDefinition a = new EntityDefinition("some::path", "some::classifier", null);

        Assertions.assertFalse(a.equals(null));
    }

    @Test
    public void testHashCode()
    {
        EntityDefinition a = new EntityDefinition("some::path", "some::classifier", null);
        EntityDefinition b = new EntityDefinition("some::path", "some::classifier", null);

        Assertions.assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void testHashCodeDiffers()
    {
        EntityDefinition a = new EntityDefinition("some::path", "classifier::A", null);
        EntityDefinition b = new EntityDefinition("some::path", "classifier::B", null);

        Assertions.assertNotEquals(a.hashCode(), b.hashCode());
    }
}
