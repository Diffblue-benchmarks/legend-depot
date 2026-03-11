package org.finos.legend.depot.store.model.entities;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestStoredEntityStringData
{
    @Test
    void canCreateWithAllParameters()
    {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("path", "test::path");

        StoredEntityStringData stored = new StoredEntityStringData("org.finos", "test-artifact", "1.0.0", "{\"key\":\"value\"}", attributes);

        assertNotNull(stored);
        assertEquals("org.finos", stored.getGroupId());
        assertEquals("test-artifact", stored.getArtifactId());
        assertEquals("1.0.0", stored.getVersionId());
        assertEquals("{\"key\":\"value\"}", stored.getData());
        assertEquals(attributes, stored.getEntityAttributes());
    }

    @Test
    void canCreateWithGroupArtifactVersion()
    {
        StoredEntityStringData stored = new StoredEntityStringData("org.finos", "test-artifact", "2.0.0");

        assertNotNull(stored);
        assertEquals("org.finos", stored.getGroupId());
        assertEquals("test-artifact", stored.getArtifactId());
        assertEquals("2.0.0", stored.getVersionId());
        assertNull(stored.getData());
    }

    @Test
    void testEqualsWithSameData()
    {
        StoredEntityStringData stored1 = new StoredEntityStringData("org.finos", "test-artifact", "1.0.0", "data", null);
        StoredEntityStringData stored2 = new StoredEntityStringData("org.finos", "test-artifact", "1.0.0", "data", null);

        assertEquals(stored1, stored2);
    }

    @Test
    void testEqualsWithDifferentData()
    {
        StoredEntityStringData stored1 = new StoredEntityStringData("org.finos", "test-artifact", "1.0.0", "data1", null);
        StoredEntityStringData stored2 = new StoredEntityStringData("org.finos", "test-artifact", "1.0.0", "data2", null);

        assertNotEquals(stored1, stored2);
    }

    @Test
    void testHashCodeConsistentWithEquals()
    {
        StoredEntityStringData stored1 = new StoredEntityStringData("org.finos", "test-artifact", "1.0.0", "data", null);
        StoredEntityStringData stored2 = new StoredEntityStringData("org.finos", "test-artifact", "1.0.0", "data", null);

        assertEquals(stored1.hashCode(), stored2.hashCode());
    }
}
