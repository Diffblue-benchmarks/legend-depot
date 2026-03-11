package org.finos.legend.depot.store.model.entities;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestStoredEntityData
{
    @Test
    void canCreateWithAllArguments()
    {
        Map<String, Object> content = Collections.singletonMap("key", "value");
        EntityDefinition entity = new EntityDefinition("test::path", "meta::pure::Class", content);
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("attr1", "val1");

        StoredEntityData stored = new StoredEntityData("org.finos", "artifact", "1.0.0", entity, attributes);

        assertNotNull(stored);
        assertEquals("org.finos", stored.getGroupId());
        assertEquals("artifact", stored.getArtifactId());
        assertEquals("1.0.0", stored.getVersionId());
        assertEquals(entity, stored.getEntity());
        assertEquals(attributes, stored.getEntityAttributes());
    }

    @Test
    void canCreateWithThreeArguments()
    {
        StoredEntityData stored = new StoredEntityData("org.finos", "artifact", "1.0.0");

        assertNotNull(stored);
        assertEquals("org.finos", stored.getGroupId());
        assertEquals("artifact", stored.getArtifactId());
        assertEquals("1.0.0", stored.getVersionId());
        assertNull(stored.getEntity());
    }

    @Test
    void canGetId()
    {
        StoredEntityData stored = new StoredEntityData("org.finos", "artifact", "1.0.0");

        assertEquals("", stored.getId());
    }

    @Test
    void canGetEntity()
    {
        EntityDefinition entity = new EntityDefinition("test::path", "meta::pure::Class", Collections.emptyMap());
        StoredEntityData stored = new StoredEntityData("org.finos", "artifact", "1.0.0", entity, null);

        assertEquals(entity, stored.getEntity());
    }

    @Test
    void testEquals()
    {
        EntityDefinition entity = new EntityDefinition("test::path", "meta::pure::Class", Collections.emptyMap());
        StoredEntityData stored1 = new StoredEntityData("org.finos", "artifact", "1.0.0", entity, null);
        StoredEntityData stored2 = new StoredEntityData("org.finos", "artifact", "1.0.0", entity, null);
        StoredEntityData stored3 = new StoredEntityData("org.finos", "other", "1.0.0", entity, null);

        assertEquals(stored1, stored2);
        assertNotEquals(stored1, stored3);
        assertNotEquals(stored1, null);
        assertNotEquals(stored1, "string");
    }

    @Test
    void testHashCode()
    {
        EntityDefinition entity = new EntityDefinition("test::path", "meta::pure::Class", Collections.emptyMap());
        StoredEntityData stored1 = new StoredEntityData("org.finos", "artifact", "1.0.0", entity, null);
        StoredEntityData stored2 = new StoredEntityData("org.finos", "artifact", "1.0.0", entity, null);

        assertEquals(stored1.hashCode(), stored2.hashCode());
    }
}
