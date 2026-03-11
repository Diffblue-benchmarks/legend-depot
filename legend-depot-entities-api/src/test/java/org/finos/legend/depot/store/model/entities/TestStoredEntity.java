package org.finos.legend.depot.store.model.entities;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestStoredEntity
{
    @Test
    void canCreateStoredEntityDataWithEntityAttributes()
    {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("key1", "value1");
        attributes.put("key2", "value2");

        StoredEntityData entity = new StoredEntityData("org.finos", "artifact1", "1.0.0", null, attributes);

        assertEquals("org.finos", entity.getGroupId());
        assertEquals("artifact1", entity.getArtifactId());
        assertEquals("1.0.0", entity.getVersionId());
        assertNotNull(entity.getEntityAttributes());
        assertEquals(2, entity.getEntityAttributes().size());
        assertEquals("value1", entity.getEntityAttributes().get("key1"));
    }

    @Test
    void canCreateStoredEntityDataWithoutEntityAttributes()
    {
        StoredEntityData entity = new StoredEntityData("org.finos", "artifact1", "1.0.0");

        assertEquals("org.finos", entity.getGroupId());
        assertEquals("artifact1", entity.getArtifactId());
        assertEquals("1.0.0", entity.getVersionId());
        assertNull(entity.getEntityAttributes());
    }

    @Test
    void canGetEntityAttributes()
    {
        Map<String, Object> attributes = Collections.singletonMap("classifier", "meta::pure::Class");
        StoredEntityData entity = new StoredEntityData("org.finos", "artifact1", "1.0.0", null, attributes);

        Map<String, ?> result = entity.getEntityAttributes();

        assertNotNull(result);
        assertEquals("meta::pure::Class", result.get("classifier"));
    }

    @Test
    void canGetId()
    {
        StoredEntityData entity = new StoredEntityData("org.finos", "artifact1", "1.0.0");

        assertEquals("", entity.getId());
    }
}
