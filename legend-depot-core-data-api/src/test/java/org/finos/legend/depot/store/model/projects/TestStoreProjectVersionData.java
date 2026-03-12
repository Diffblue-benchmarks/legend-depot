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

package org.finos.legend.depot.store.model.projects;

import org.finos.legend.depot.domain.project.ProjectVersionData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class TestStoreProjectVersionData
{
    @Test
    public void testThreeArgConstructorSetsCreated()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "artifact", "1.0.0");
        Assertions.assertNotNull(data.getCreated());
        Assertions.assertFalse(data.isEvicted());
        Assertions.assertNotNull(data.getVersionData());
        Assertions.assertNotNull(data.getTransitiveDependenciesReport());
    }

    @Test
    public void testDefaultConstructor()
    {
        StoreProjectVersionData data = new StoreProjectVersionData();
        Assertions.assertNull(data.getGroupId());
        Assertions.assertNull(data.getCreated());
    }

    @Test
    public void testSetEvicted()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "artifact", "1.0.0");
        data.setEvicted(true);
        Assertions.assertTrue(data.isEvicted());
    }

    @Test
    public void testSetUpdated()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "artifact", "1.0.0");
        Date now = new Date();
        data.setUpdated(now);
        Assertions.assertEquals(now, data.getUpdated());
    }

    @Test
    public void testSetVersionData()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "artifact", "1.0.0");
        ProjectVersionData pvd = new ProjectVersionData();
        pvd.setDeprecated(true);
        data.setVersionData(pvd);
        Assertions.assertTrue(data.getVersionData().isDeprecated());
    }

    @Test
    public void testGetIdReturnsEmptyString()
    {
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "artifact", "1.0.0");
        Assertions.assertEquals("", data.getId());
    }

    @Test
    public void testFiveArgConstructor()
    {
        ProjectVersionData pvd = new ProjectVersionData();
        pvd.setExcluded(true);
        StoreProjectVersionData data = new StoreProjectVersionData("org.finos", "artifact", "1.0.0", true, pvd);
        Assertions.assertTrue(data.isEvicted());
        Assertions.assertTrue(data.getVersionData().isExcluded());
    }

    @Test
    public void testEqualitySameInstance()
    {
        StoreProjectVersionData a = new StoreProjectVersionData("org.finos", "artifact", "1.0.0");
        Assertions.assertEquals(a, a);
    }
}
