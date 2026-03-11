package org.finos.legend.depot.store.model.entities;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestEntityDefinition
{
    @Test
    void canCreateWithAllParameters()
    {
        Map<String, Object> content = new HashMap<>();
        content.put("key", "value");

        EntityDefinition entity = new EntityDefinition("test::path", "meta::pure::Class", content);

        assertNotNull(entity);
        assertEquals("test::path", entity.getPath());
        assertEquals("meta::pure::Class", entity.getClassifierPath());
        assertEquals(content, entity.getContent());
    }

    @Test
    void canCreateWithNullValues()
    {
        EntityDefinition entity = new EntityDefinition(null, null, null);

        assertNull(entity.getPath());
        assertNull(entity.getClassifierPath());
        assertNull(entity.getContent());
    }

    @Test
    void canSetClassifierPath()
    {
        EntityDefinition entity = new EntityDefinition("test::path", "meta::pure::Class", Collections.emptyMap());

        entity.setClassifierPath("meta::pure::Enumeration");

        assertEquals("meta::pure::Enumeration", entity.getClassifierPath());
    }

    @Test
    void testEqualsWithSameData()
    {
        EntityDefinition entity1 = new EntityDefinition("test::path", "meta::pure::Class", Collections.emptyMap());
        EntityDefinition entity2 = new EntityDefinition("test::path", "meta::pure::Class", Collections.emptyMap());

        assertEquals(entity1, entity2);
    }

    @Test
    void testEqualsWithDifferentPath()
    {
        EntityDefinition entity1 = new EntityDefinition("test::path1", "meta::pure::Class", Collections.emptyMap());
        EntityDefinition entity2 = new EntityDefinition("test::path2", "meta::pure::Class", Collections.emptyMap());

        assertNotEquals(entity1, entity2);
    }

    @Test
    void testEqualsWithDifferentContent()
    {
        Map<String, Object> content1 = new HashMap<>();
        content1.put("key", "value1");
        Map<String, Object> content2 = new HashMap<>();
        content2.put("key", "value2");

        EntityDefinition entity1 = new EntityDefinition("test::path", "meta::pure::Class", content1);
        EntityDefinition entity2 = new EntityDefinition("test::path", "meta::pure::Class", content2);

        assertNotEquals(entity1, entity2);
    }

    @Test
    void testHashCodeConsistentWithEquals()
    {
        EntityDefinition entity1 = new EntityDefinition("test::path", "meta::pure::Class", Collections.emptyMap());
        EntityDefinition entity2 = new EntityDefinition("test::path", "meta::pure::Class", Collections.emptyMap());

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }
}
