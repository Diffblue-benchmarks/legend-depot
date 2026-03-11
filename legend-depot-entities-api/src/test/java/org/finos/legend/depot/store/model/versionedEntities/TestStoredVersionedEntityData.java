package org.finos.legend.depot.store.model.versionedEntities;

import org.finos.legend.depot.store.model.entities.EntityDefinition;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestStoredVersionedEntityData
{
    @Test
    void canCreateWithAllArguments()
    {
        EntityDefinition entity = new EntityDefinition("test::path", "meta::pure::Class", Collections.singletonMap("key", "value"));
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("attr1", "val1");

        StoredVersionedEntityData data = new StoredVersionedEntityData("org.finos", "artifact", "1.0.0", entity, attributes);

        assertNotNull(data);
        assertEquals("org.finos", data.getGroupId());
        assertEquals("artifact", data.getArtifactId());
        assertEquals("1.0.0", data.getVersionId());
        assertEquals(entity, data.getEntity());
    }

    @Test
    void canCreateWithThreeArguments()
    {
        StoredVersionedEntityData data = new StoredVersionedEntityData("org.finos", "artifact", "1.0.0");

        assertNotNull(data);
        assertEquals("org.finos", data.getGroupId());
        assertEquals("artifact", data.getArtifactId());
        assertEquals("1.0.0", data.getVersionId());
        assertNull(data.getEntity());
    }

    @Test
    void canGetId()
    {
        StoredVersionedEntityData data = new StoredVersionedEntityData("org.finos", "artifact", "1.0.0");
        assertEquals("", data.getId());
    }

    @Test
    void canGetEntity()
    {
        EntityDefinition entity = new EntityDefinition("test::path", "meta::pure::Class", Collections.emptyMap());
        StoredVersionedEntityData data = new StoredVersionedEntityData("org.finos", "artifact", "1.0.0", entity, null);

        assertEquals(entity, data.getEntity());
    }

    @Test
    void canTestEquals()
    {
        EntityDefinition entity = new EntityDefinition("test::path", "meta::pure::Class", Collections.emptyMap());
        StoredVersionedEntityData data1 = new StoredVersionedEntityData("org.finos", "artifact", "1.0.0", entity, null);
        StoredVersionedEntityData data2 = new StoredVersionedEntityData("org.finos", "artifact", "1.0.0", entity, null);
        StoredVersionedEntityData data3 = new StoredVersionedEntityData("org.finos", "other", "1.0.0", entity, null);

        assertEquals(data1, data2);
        assertNotEquals(data1, data3);
    }

    @Test
    void canTestHashCode()
    {
        EntityDefinition entity = new EntityDefinition("test::path", "meta::pure::Class", Collections.emptyMap());
        StoredVersionedEntityData data1 = new StoredVersionedEntityData("org.finos", "artifact", "1.0.0", entity, null);
        StoredVersionedEntityData data2 = new StoredVersionedEntityData("org.finos", "artifact", "1.0.0", entity, null);

        assertEquals(data1.hashCode(), data2.hashCode());
    }
}
