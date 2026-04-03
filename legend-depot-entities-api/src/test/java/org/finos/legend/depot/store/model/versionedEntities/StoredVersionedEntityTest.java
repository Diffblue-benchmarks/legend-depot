// Copyright 2021 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.finos.legend.depot.store.model.versionedEntities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

public class StoredVersionedEntityTest
{
    @Test
    public void testConstructorWithEntityAttributesSetsFields()
    {
        Map<String, String> attributes = Collections.singletonMap("key", "value");

        StoredVersionedEntityReference entity = new StoredVersionedEntityReference("com.example", "my-artifact", "1.0.0", "some-reference", attributes);

        Assertions.assertEquals("com.example", entity.getGroupId());
        Assertions.assertEquals("my-artifact", entity.getArtifactId());
        Assertions.assertEquals("1.0.0", entity.getVersionId());
        Assertions.assertEquals(attributes, entity.getEntityAttributes());
    }

    @Test
    public void testConstructorWithoutEntityAttributesSetsFields()
    {
        StoredVersionedEntityReference entity = new StoredVersionedEntityReference("com.example", "my-artifact", "1.0.0");

        Assertions.assertEquals("com.example", entity.getGroupId());
        Assertions.assertEquals("my-artifact", entity.getArtifactId());
        Assertions.assertEquals("1.0.0", entity.getVersionId());
        Assertions.assertNull(entity.getEntityAttributes());
    }
}
