package org.finos.legend.depot.store.model.versionedEntities;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestStoredVersionedEntityReference
{
    @Test
    void canCreateWithAllArguments()
    {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("key", "value");

        StoredVersionedEntityReference ref = new StoredVersionedEntityReference(
                "org.finos", "artifact", "1.0.0", "someReference", attributes);

        assertEquals("org.finos", ref.getGroupId());
        assertEquals("artifact", ref.getArtifactId());
        assertEquals("1.0.0", ref.getVersionId());
        assertEquals("someReference", ref.getReference());
    }

    @Test
    void canCreateWithThreeArguments()
    {
        StoredVersionedEntityReference ref = new StoredVersionedEntityReference(
                "org.finos", "artifact", "2.0.0");

        assertEquals("org.finos", ref.getGroupId());
        assertEquals("artifact", ref.getArtifactId());
        assertEquals("2.0.0", ref.getVersionId());
        assertNull(ref.getReference());
    }

    @Test
    void canGetReference()
    {
        StoredVersionedEntityReference ref = new StoredVersionedEntityReference(
                "org.finos", "artifact", "1.0.0", "myRef", Collections.emptyMap());

        assertEquals("myRef", ref.getReference());
    }

    @Test
    void testEqualsSameValues()
    {
        StoredVersionedEntityReference ref1 = new StoredVersionedEntityReference(
                "org.finos", "artifact", "1.0.0", "ref", Collections.emptyMap());
        StoredVersionedEntityReference ref2 = new StoredVersionedEntityReference(
                "org.finos", "artifact", "1.0.0", "ref", Collections.emptyMap());

        assertEquals(ref1, ref2);
    }

    @Test
    void testEqualsDifferentValues()
    {
        StoredVersionedEntityReference ref1 = new StoredVersionedEntityReference(
                "org.finos", "artifact", "1.0.0", "ref1", Collections.emptyMap());
        StoredVersionedEntityReference ref2 = new StoredVersionedEntityReference(
                "org.finos", "artifact", "1.0.0", "ref2", Collections.emptyMap());

        assertNotEquals(ref1, ref2);
    }

    @Test
    void testHashCodeConsistentWithEquals()
    {
        StoredVersionedEntityReference ref1 = new StoredVersionedEntityReference(
                "org.finos", "artifact", "1.0.0", "ref", Collections.emptyMap());
        StoredVersionedEntityReference ref2 = new StoredVersionedEntityReference(
                "org.finos", "artifact", "1.0.0", "ref", Collections.emptyMap());

        assertEquals(ref1.hashCode(), ref2.hashCode());
    }

    @Test
    void testHashCodeNotZero()
    {
        StoredVersionedEntityReference ref = new StoredVersionedEntityReference(
                "org.finos", "artifact", "1.0.0", "ref", Collections.emptyMap());

        assertNotNull(ref.hashCode());
    }
}
