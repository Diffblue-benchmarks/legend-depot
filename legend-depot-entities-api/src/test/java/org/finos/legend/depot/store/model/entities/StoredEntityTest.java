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

package org.finos.legend.depot.store.model.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class StoredEntityTest
{
    // Concrete test implementation to test the abstract StoredEntity class
    private static class TestStoredEntity extends StoredEntity
    {
        public TestStoredEntity(String groupId, String artifactId, String versionId, Map<String, ?> entityAttributes)
        {
            super(groupId, artifactId, versionId, entityAttributes);
        }

        public TestStoredEntity(String groupId, String artifactId, String versionId)
        {
            super(groupId, artifactId, versionId);
        }
    }

    @Test
    public void canCreateStoredEntityWithEntityAttributes()
    {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("key1", "value1");
        attributes.put("key2", "value2");

        TestStoredEntity entity = new TestStoredEntity("test.group", "test-artifact", "1.0.0", attributes);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals("test.group", entity.getGroupId());
        Assertions.assertEquals("test-artifact", entity.getArtifactId());
        Assertions.assertEquals("1.0.0", entity.getVersionId());
        Assertions.assertEquals(attributes, entity.getEntityAttributes());
    }

    @Test
    public void canCreateStoredEntityWithoutEntityAttributes()
    {
        TestStoredEntity entity = new TestStoredEntity("test.group", "test-artifact", "2.0.0");

        Assertions.assertNotNull(entity);
        Assertions.assertEquals("test.group", entity.getGroupId());
        Assertions.assertEquals("test-artifact", entity.getArtifactId());
        Assertions.assertEquals("2.0.0", entity.getVersionId());
    }

    @Test
    public void canGetEntityAttributes()
    {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("attribute1", "value1");

        TestStoredEntity entity = new TestStoredEntity("test.group", "test-artifact", "1.0.0", attributes);

        Map<String, ?> retrievedAttributes = entity.getEntityAttributes();
        Assertions.assertNotNull(retrievedAttributes);
        Assertions.assertEquals(attributes, retrievedAttributes);
        Assertions.assertEquals("value1", retrievedAttributes.get("attribute1"));
    }

    @Test
    public void canGetEntityAttributesWhenNull()
    {
        TestStoredEntity entity = new TestStoredEntity("test.group", "test-artifact", "1.0.0");

        Map<String, ?> retrievedAttributes = entity.getEntityAttributes();
        Assertions.assertNull(retrievedAttributes);
    }

    @Test
    public void canGetId()
    {
        TestStoredEntity entity = new TestStoredEntity("test.group", "test-artifact", "1.0.0");

        String id = entity.getId();
        Assertions.assertNotNull(id);
        Assertions.assertEquals("", id);
    }
}
