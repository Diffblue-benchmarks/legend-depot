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

package org.finos.legend.depot.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProjectVersionEntitiesTest
{
    @Test
    public void testDefaultConstructor()
    {
        ProjectVersionEntities entities = new ProjectVersionEntities();
        Assertions.assertNotNull(entities);
    }

    @Test
    public void testParameterizedConstructor()
    {
        ProjectVersionEntities entities = new ProjectVersionEntities("group.id", "artifact-id", "1.0.0", Collections.emptyList());
        Assertions.assertNotNull(entities);
        Assertions.assertEquals("group.id", entities.getGroupId());
        Assertions.assertEquals("artifact-id", entities.getArtifactId());
        Assertions.assertEquals("1.0.0", entities.getVersionId());
        Assertions.assertEquals(Collections.emptyList(), entities.getEntities());
    }

    @Test
    public void testIsVersionedEntityIsFalseByDefault()
    {
        ProjectVersionEntities entities = new ProjectVersionEntities("group.id", "artifact-id", "1.0.0", Collections.emptyList());
        Assertions.assertFalse(entities.isVersionedEntity());
    }

    @Test
    public void testGetEntities()
    {
        List<org.finos.legend.sdlc.domain.model.entity.Entity> entityList = Collections.emptyList();
        ProjectVersionEntities pve = new ProjectVersionEntities("g", "a", "v", entityList);
        Assertions.assertEquals(entityList, pve.getEntities());
    }

    @Test
    public void testEqualsWithSameObject()
    {
        ProjectVersionEntities entities = new ProjectVersionEntities("g", "a", "v", Collections.emptyList());
        Assertions.assertTrue(entities.equals(entities));
    }

    @Test
    public void testEqualsWithEqualObjects()
    {
        List<org.finos.legend.sdlc.domain.model.entity.Entity> list = Collections.emptyList();
        ProjectVersionEntities e1 = new ProjectVersionEntities("g", "a", "v", list);
        ProjectVersionEntities e2 = new ProjectVersionEntities("g", "a", "v", list);
        Assertions.assertTrue(e1.equals(e2));
    }

    @Test
    public void testEqualsWithNull()
    {
        ProjectVersionEntities entities = new ProjectVersionEntities("g", "a", "v", Collections.emptyList());
        Assertions.assertFalse(entities.equals(null));
    }

    @Test
    public void testHashCodeConsistency()
    {
        ProjectVersionEntities e1 = new ProjectVersionEntities("g", "a", "v", Collections.emptyList());
        ProjectVersionEntities e2 = new ProjectVersionEntities("g", "a", "v", Collections.emptyList());
        Assertions.assertEquals(e1.hashCode(), e2.hashCode());
    }
}
