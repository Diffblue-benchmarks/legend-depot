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

package org.finos.legend.depot.domain.entity;

import org.finos.legend.sdlc.domain.model.entity.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjectVersionEntitiesTest
{
    @Test
    public void testDefaultConstructor()
    {
        ProjectVersionEntities projectVersionEntities = new ProjectVersionEntities();
        Assertions.assertNotNull(projectVersionEntities);
    }

    @Test
    public void testParameterizedConstructor()
    {
        List<Entity> entities = new ArrayList<>();
        ProjectVersionEntities projectVersionEntities = new ProjectVersionEntities("org.example", "test-artifact", "1.0.0", entities);

        Assertions.assertNotNull(projectVersionEntities);
        Assertions.assertEquals("org.example", projectVersionEntities.getGroupId());
        Assertions.assertEquals("test-artifact", projectVersionEntities.getArtifactId());
        Assertions.assertEquals("1.0.0", projectVersionEntities.getVersionId());
        Assertions.assertFalse(projectVersionEntities.isVersionedEntity());
        Assertions.assertEquals(entities, projectVersionEntities.getEntities());
    }

    @Test
    public void testIsVersionedEntity()
    {
        ProjectVersionEntities projectVersionEntities = new ProjectVersionEntities("org.example", "test-artifact", "1.0.0", Collections.emptyList());
        Assertions.assertFalse(projectVersionEntities.isVersionedEntity());
    }

    @Test
    public void testGetEntities()
    {
        List<Entity> entities = new ArrayList<>();
        ProjectVersionEntities projectVersionEntities = new ProjectVersionEntities("org.example", "test-artifact", "1.0.0", entities);

        List<Entity> result = projectVersionEntities.getEntities();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(entities, result);
    }

    @Test
    public void testEquals()
    {
        List<Entity> entities = new ArrayList<>();
        ProjectVersionEntities pve1 = new ProjectVersionEntities("org.example", "test-artifact", "1.0.0", entities);
        ProjectVersionEntities pve2 = new ProjectVersionEntities("org.example", "test-artifact", "1.0.0", entities);
        ProjectVersionEntities pve3 = new ProjectVersionEntities("org.other", "test-artifact", "1.0.0", entities);

        Assertions.assertTrue(pve1.equals(pve2));
        Assertions.assertFalse(pve1.equals(pve3));
        Assertions.assertFalse(pve1.equals(null));
    }

    @Test
    public void testHashCode()
    {
        List<Entity> entities = new ArrayList<>();
        ProjectVersionEntities pve1 = new ProjectVersionEntities("org.example", "test-artifact", "1.0.0", entities);
        ProjectVersionEntities pve2 = new ProjectVersionEntities("org.example", "test-artifact", "1.0.0", entities);

        int hash1 = pve1.hashCode();
        int hash2 = pve2.hashCode();

        Assertions.assertEquals(hash1, hash2);
    }
}
