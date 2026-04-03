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

package org.finos.legend.depot.services.api.entities;

import org.finos.legend.depot.store.model.entities.StoredEntityReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class StoredEntityReferenceTest
{
    @Test
    public void testFullConstructorSetsReference()
    {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("key", "value");
        StoredEntityReference ref = new StoredEntityReference("com.example", "my-artifact", "1.0.0", "my::reference::Path", attrs);

        Assertions.assertEquals("my::reference::Path", ref.getReference());
        Assertions.assertEquals("com.example", ref.getGroupId());
        Assertions.assertEquals("my-artifact", ref.getArtifactId());
        Assertions.assertEquals("1.0.0", ref.getVersionId());
        Assertions.assertEquals(attrs, ref.getEntityAttributes());
    }

    @Test
    public void testThreeArgConstructorCreatesInstance()
    {
        StoredEntityReference ref = new StoredEntityReference("com.example", "my-artifact", "1.0.0");

        Assertions.assertEquals("com.example", ref.getGroupId());
        Assertions.assertEquals("my-artifact", ref.getArtifactId());
        Assertions.assertEquals("1.0.0", ref.getVersionId());
        Assertions.assertNull(ref.getReference());
    }

    @Test
    public void testGetReferenceReturnsNull()
    {
        StoredEntityReference ref = new StoredEntityReference("com.example", "my-artifact", "1.0.0");
        Assertions.assertNull(ref.getReference());
    }

    @Test
    public void testGetReferenceReturnsValue()
    {
        StoredEntityReference ref = new StoredEntityReference("com.example", "my-artifact", "1.0.0", "my::entity", null);
        Assertions.assertEquals("my::entity", ref.getReference());
    }

    @Test
    public void testEqualsReturnsTrueForSameValues()
    {
        StoredEntityReference ref1 = new StoredEntityReference("com.example", "my-artifact", "1.0.0", "my::entity", null);
        StoredEntityReference ref2 = new StoredEntityReference("com.example", "my-artifact", "1.0.0", "my::entity", null);

        Assertions.assertTrue(ref1.equals(ref2));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentValues()
    {
        StoredEntityReference ref1 = new StoredEntityReference("com.example", "my-artifact", "1.0.0", "my::entity", null);
        StoredEntityReference ref2 = new StoredEntityReference("com.example", "my-artifact", "1.0.0", "other::entity", null);

        Assertions.assertFalse(ref1.equals(ref2));
    }

    @Test
    public void testHashCodeConsistency()
    {
        StoredEntityReference ref1 = new StoredEntityReference("com.example", "my-artifact", "1.0.0", "my::entity", null);
        StoredEntityReference ref2 = new StoredEntityReference("com.example", "my-artifact", "1.0.0", "my::entity", null);

        Assertions.assertEquals(ref1.hashCode(), ref2.hashCode());
    }

    @Test
    public void testHashCodeDiffersForDifferentValues()
    {
        StoredEntityReference ref1 = new StoredEntityReference("com.example", "my-artifact", "1.0.0", "my::entity", null);
        StoredEntityReference ref2 = new StoredEntityReference("com.example", "my-artifact", "2.0.0", "my::entity", null);

        Assertions.assertNotEquals(ref1.hashCode(), ref2.hashCode());
    }
}
