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

package org.finos.legend.depot.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VersionedDataTest
{
    static class ConcreteVersionedData extends VersionedData
    {
        public ConcreteVersionedData()
        {
            super();
        }

        public ConcreteVersionedData(String groupId, String artifactId, String versionId)
        {
            super(groupId, artifactId, versionId);
        }
    }

    @Test
    public void testDefaultConstructor()
    {
        ConcreteVersionedData data = new ConcreteVersionedData();
        Assertions.assertNull(data.getVersionId());
    }

    @Test
    public void testParameterizedConstructor()
    {
        ConcreteVersionedData data = new ConcreteVersionedData("com.example", "my-artifact", "1.0.0");
        Assertions.assertEquals("com.example", data.getGroupId());
        Assertions.assertEquals("my-artifact", data.getArtifactId());
        Assertions.assertEquals("1.0.0", data.getVersionId());
    }

    @Test
    public void testGetVersionId()
    {
        ConcreteVersionedData data = new ConcreteVersionedData("g", "a", "2.0.0");
        Assertions.assertEquals("2.0.0", data.getVersionId());
    }

    @Test
    public void testSetVersionId()
    {
        ConcreteVersionedData data = new ConcreteVersionedData();
        data.setVersionId("3.0.0");
        Assertions.assertEquals("3.0.0", data.getVersionId());
    }

    @Test
    public void testEquals()
    {
        ConcreteVersionedData data1 = new ConcreteVersionedData("g", "a", "1.0.0");
        ConcreteVersionedData data2 = new ConcreteVersionedData("g", "a", "1.0.0");
        Assertions.assertTrue(data1.equals(data2));
        ConcreteVersionedData data3 = new ConcreteVersionedData("g", "a", "2.0.0");
        Assertions.assertFalse(data1.equals(data3));
    }

    @Test
    public void testHashCode()
    {
        ConcreteVersionedData data1 = new ConcreteVersionedData("g", "a", "1.0.0");
        ConcreteVersionedData data2 = new ConcreteVersionedData("g", "a", "1.0.0");
        Assertions.assertEquals(data1.hashCode(), data2.hashCode());
    }
}
