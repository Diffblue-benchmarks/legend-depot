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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestStoreProjectData
{
    @Test
    public void testNoArgConstructor()
    {
        StoreProjectData data = new StoreProjectData();
        Assertions.assertNull(data.getProjectId());
        Assertions.assertNull(data.getDefaultBranch());
        Assertions.assertNull(data.getLatestVersion());
    }

    @Test
    public void testGetAndSetDefaultBranch()
    {
        StoreProjectData data = new StoreProjectData("PROD-1", "examples.test", "metadata");
        Assertions.assertNull(data.getDefaultBranch());

        data.setDefaultBranch("main");
        Assertions.assertEquals("main", data.getDefaultBranch());
    }

    @Test
    public void testGetProjectId()
    {
        StoreProjectData data = new StoreProjectData("PROD-1", "examples.test", "metadata");
        Assertions.assertEquals("PROD-1", data.getProjectId());
    }

    @Test
    public void testGetId()
    {
        StoreProjectData data = new StoreProjectData("PROD-1", "examples.test", "metadata");
        Assertions.assertEquals("", data.getId());
    }

    @Test
    public void testEquals()
    {
        StoreProjectData data1 = new StoreProjectData("PROD-1", "examples.test", "metadata");
        StoreProjectData data2 = new StoreProjectData("PROD-1", "examples.test", "metadata");
        StoreProjectData data3 = new StoreProjectData("PROD-2", "examples.test", "metadata");

        Assertions.assertEquals(data1, data2);
        Assertions.assertNotEquals(data1, data3);
    }

    @Test
    public void testHashCode()
    {
        StoreProjectData data1 = new StoreProjectData("PROD-1", "examples.test", "metadata");
        StoreProjectData data2 = new StoreProjectData("PROD-1", "examples.test", "metadata");

        Assertions.assertEquals(data1.hashCode(), data2.hashCode());
    }
}
