package org.finos.legend.depot.store.model.versionedEntities;

import org.finos.legend.depot.store.model.entities.EntityDefinition;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestStoredVersionedEntity
{
    @Test
    void canCreateWithGroupArtifactVersionAndEntityAttributes()
    {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("path", "test::path");
        EntityDefinition entity = new EntityDefinition("test::path", "meta::pure::metamodel::type::Class", Collections.emptyMap());

        StoredVersionedEntityData stored = new StoredVersionedEntityData("org.finos", "test-artifact", "1.0.0", entity, attributes);

        assertNotNull(stored);
        assertEquals("org.finos", stored.getGroupId());
        assertEquals("test-artifact", stored.getArtifactId());
        assertEquals("1.0.0", stored.getVersionId());
        assertEquals(entity, stored.getEntity());
    }

    @Test
    void canCreateWithGroupArtifactVersion()
    {
        StoredVersionedEntityData stored = new StoredVersionedEntityData("org.finos", "test-artifact", "2.0.0");

        assertNotNull(stored);
        assertEquals("org.finos", stored.getGroupId());
        assertEquals("test-artifact", stored.getArtifactId());
        assertEquals("2.0.0", stored.getVersionId());
        assertNull(stored.getEntity());
    }
}
