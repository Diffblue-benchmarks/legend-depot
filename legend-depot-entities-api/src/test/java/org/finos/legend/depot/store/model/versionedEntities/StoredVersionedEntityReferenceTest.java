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
    private static final String GROUP_ID = "test.group";
    private static final String ARTIFACT_ID = "test-artifact";
    private static final String VERSION_ID = "1.0.0";
    private static final String REFERENCE = "test::Entity";

    @Test
    public void testConstructorWithAllParameters()
    {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("key", "value");

        StoredVersionedEntityReference ref = new StoredVersionedEntityReference(GROUP_ID, ARTIFACT_ID, VERSION_ID, REFERENCE, attributes);

        Assertions.assertEquals(GROUP_ID, ref.getGroupId());
        Assertions.assertEquals(ARTIFACT_ID, ref.getArtifactId());
        Assertions.assertEquals(VERSION_ID, ref.getVersionId());
        Assertions.assertEquals(REFERENCE, ref.getReference());
    }

    @Test
    public void testConstructorWithThreeParameters()
    {
        StoredVersionedEntityReference ref = new StoredVersionedEntityReference(GROUP_ID, ARTIFACT_ID, VERSION_ID);

        Assertions.assertEquals(GROUP_ID, ref.getGroupId());
        Assertions.assertEquals(ARTIFACT_ID, ref.getArtifactId());
        Assertions.assertEquals(VERSION_ID, ref.getVersionId());
        Assertions.assertNull(ref.getReference());
    }

    @Test
    public void testGetReference()
    {
        StoredVersionedEntityReference ref = new StoredVersionedEntityReference(GROUP_ID, ARTIFACT_ID, VERSION_ID, REFERENCE, null);

        Assertions.assertEquals(REFERENCE, ref.getReference());
    }

    @Test
    public void testEqualsReturnsTrueForSameObject()
    {
        StoredVersionedEntityReference ref = new StoredVersionedEntityReference(GROUP_ID, ARTIFACT_ID, VERSION_ID, REFERENCE, null);

        Assertions.assertTrue(ref.equals(ref));
    }

    @Test
    public void testEqualsReturnsTrueForEqualObjects()
    {
        StoredVersionedEntityReference ref1 = new StoredVersionedEntityReference(GROUP_ID, ARTIFACT_ID, VERSION_ID, REFERENCE, null);
        StoredVersionedEntityReference ref2 = new StoredVersionedEntityReference(GROUP_ID, ARTIFACT_ID, VERSION_ID, REFERENCE, null);

        Assertions.assertTrue(ref1.equals(ref2));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentObjects()
    {
        StoredVersionedEntityReference ref1 = new StoredVersionedEntityReference(GROUP_ID, ARTIFACT_ID, VERSION_ID, REFERENCE, null);
        StoredVersionedEntityReference ref2 = new StoredVersionedEntityReference(GROUP_ID, ARTIFACT_ID, VERSION_ID, "other::Entity", null);

        Assertions.assertFalse(ref1.equals(ref2));
    }

    @Test
    public void testHashCodeConsistency()
    {
        StoredVersionedEntityReference ref = new StoredVersionedEntityReference(GROUP_ID, ARTIFACT_ID, VERSION_ID, REFERENCE, null);

        Assertions.assertEquals(ref.hashCode(), ref.hashCode());
    }

    @Test
    public void testHashCodeEqualForEqualObjects()
    {
        StoredVersionedEntityReference ref1 = new StoredVersionedEntityReference(GROUP_ID, ARTIFACT_ID, VERSION_ID, REFERENCE, null);
        StoredVersionedEntityReference ref2 = new StoredVersionedEntityReference(GROUP_ID, ARTIFACT_ID, VERSION_ID, REFERENCE, null);

        Assertions.assertEquals(ref1.hashCode(), ref2.hashCode());
    }
}
