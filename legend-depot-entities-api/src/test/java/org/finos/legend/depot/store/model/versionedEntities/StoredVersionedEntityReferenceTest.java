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

package org.finos.legend.depot.store.model.versionedEntities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class StoredVersionedEntityReferenceTest
{
    @Test
    public void testConstructorWithFiveParameters()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        String reference = "test.reference.path";
        Map<String, String> entityAttributes = new HashMap<>();
        entityAttributes.put("key1", "value1");
        entityAttributes.put("key2", "value2");

        StoredVersionedEntityReference entityRef = new StoredVersionedEntityReference(groupId, artifactId, versionId, reference, entityAttributes);

        Assertions.assertNotNull(entityRef);
        Assertions.assertEquals(groupId, entityRef.getGroupId());
        Assertions.assertEquals(artifactId, entityRef.getArtifactId());
        Assertions.assertEquals(versionId, entityRef.getVersionId());
        Assertions.assertEquals(reference, entityRef.getReference());
        Assertions.assertEquals(entityAttributes, entityRef.getEntityAttributes());
    }

    @Test
    public void testConstructorWithFiveParametersNullReference()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        String reference = null;
        Map<String, String> entityAttributes = new HashMap<>();

        StoredVersionedEntityReference entityRef = new StoredVersionedEntityReference(groupId, artifactId, versionId, reference, entityAttributes);

        Assertions.assertNotNull(entityRef);
        Assertions.assertNull(entityRef.getReference());
    }

    @Test
    public void testConstructorWithThreeParameters()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";

        StoredVersionedEntityReference entityRef = new StoredVersionedEntityReference(groupId, artifactId, versionId);

        Assertions.assertNotNull(entityRef);
        Assertions.assertEquals(groupId, entityRef.getGroupId());
        Assertions.assertEquals(artifactId, entityRef.getArtifactId());
        Assertions.assertEquals(versionId, entityRef.getVersionId());
        Assertions.assertNull(entityRef.getReference());
    }

    @Test
    public void testGetReference()
    {
        String reference = "test.reference.path";
        StoredVersionedEntityReference entityRef = new StoredVersionedEntityReference("org.example", "test-artifact", "1.0.0", reference, new HashMap<>());

        String result = entityRef.getReference();

        Assertions.assertEquals(reference, result);
    }

    @Test
    public void testGetReferenceWhenNull()
    {
        StoredVersionedEntityReference entityRef = new StoredVersionedEntityReference("org.example", "test-artifact", "1.0.0");

        String result = entityRef.getReference();

        Assertions.assertNull(result);
    }

    @Test
    public void testEquals()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        String reference = "test.reference.path";
        Map<String, String> entityAttributes = new HashMap<>();

        StoredVersionedEntityReference entityRef1 = new StoredVersionedEntityReference(groupId, artifactId, versionId, reference, entityAttributes);
        StoredVersionedEntityReference entityRef2 = new StoredVersionedEntityReference(groupId, artifactId, versionId, reference, entityAttributes);

        Assertions.assertTrue(entityRef1.equals(entityRef2));
        Assertions.assertTrue(entityRef2.equals(entityRef1));
    }

    @Test
    public void testEqualsSameObject()
    {
        StoredVersionedEntityReference entityRef = new StoredVersionedEntityReference("org.example", "test-artifact", "1.0.0", "test.ref", new HashMap<>());

        Assertions.assertTrue(entityRef.equals(entityRef));
    }

    @Test
    public void testNotEquals()
    {
        StoredVersionedEntityReference entityRef1 = new StoredVersionedEntityReference("org.example", "test-artifact", "1.0.0", "test.ref1", new HashMap<>());
        StoredVersionedEntityReference entityRef2 = new StoredVersionedEntityReference("org.example", "test-artifact", "1.0.0", "test.ref2", new HashMap<>());

        Assertions.assertFalse(entityRef1.equals(entityRef2));
    }

    @Test
    public void testEqualsWithNull()
    {
        StoredVersionedEntityReference entityRef = new StoredVersionedEntityReference("org.example", "test-artifact", "1.0.0", "test.ref", new HashMap<>());

        Assertions.assertFalse(entityRef.equals(null));
    }

    @Test
    public void testEqualsWithDifferentClass()
    {
        StoredVersionedEntityReference entityRef = new StoredVersionedEntityReference("org.example", "test-artifact", "1.0.0", "test.ref", new HashMap<>());

        Assertions.assertFalse(entityRef.equals("not a StoredVersionedEntityReference"));
    }

    @Test
    public void testHashCode()
    {
        String groupId = "org.example";
        String artifactId = "test-artifact";
        String versionId = "1.0.0";
        String reference = "test.reference.path";
        Map<String, String> entityAttributes = new HashMap<>();

        StoredVersionedEntityReference entityRef1 = new StoredVersionedEntityReference(groupId, artifactId, versionId, reference, entityAttributes);
        StoredVersionedEntityReference entityRef2 = new StoredVersionedEntityReference(groupId, artifactId, versionId, reference, entityAttributes);

        int hashCode1 = entityRef1.hashCode();
        int hashCode2 = entityRef2.hashCode();

        Assertions.assertEquals(hashCode1, hashCode2);
    }

    @Test
    public void testHashCodeDifferentObjects()
    {
        StoredVersionedEntityReference entityRef1 = new StoredVersionedEntityReference("org.example", "test-artifact", "1.0.0", "test.ref1", new HashMap<>());
        StoredVersionedEntityReference entityRef2 = new StoredVersionedEntityReference("org.example", "test-artifact", "1.0.0", "test.ref2", new HashMap<>());

        int hashCode1 = entityRef1.hashCode();
        int hashCode2 = entityRef2.hashCode();

        Assertions.assertNotEquals(hashCode1, hashCode2);
    }
}
