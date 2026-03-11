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
    private static class ConcreteVersionedData extends VersionedData
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
    public void canCreateWithNoArgConstructor()
    {
        ConcreteVersionedData data = new ConcreteVersionedData();
        Assertions.assertNull(data.getVersionId());
        Assertions.assertNull(data.getGroupId());
        Assertions.assertNull(data.getArtifactId());
    }

    @Test
    public void canCreateWithArgConstructor()
    {
        ConcreteVersionedData data = new ConcreteVersionedData("org.test", "artifact", "1.0.0");
        Assertions.assertEquals("org.test", data.getGroupId());
        Assertions.assertEquals("artifact", data.getArtifactId());
        Assertions.assertEquals("1.0.0", data.getVersionId());
    }

    @Test
    public void canSetVersionId()
    {
        ConcreteVersionedData data = new ConcreteVersionedData("org.test", "artifact", "1.0.0");
        data.setVersionId("2.0.0");
        Assertions.assertEquals("2.0.0", data.getVersionId());
    }

    @Test
    public void testEqualsAndHashCode()
    {
        ConcreteVersionedData data1 = new ConcreteVersionedData("org.test", "artifact", "1.0.0");
        ConcreteVersionedData data2 = new ConcreteVersionedData("org.test", "artifact", "1.0.0");
        ConcreteVersionedData data3 = new ConcreteVersionedData("org.test", "artifact", "2.0.0");

        Assertions.assertEquals(data1, data2);
        Assertions.assertEquals(data1.hashCode(), data2.hashCode());
        Assertions.assertNotEquals(data1, data3);
    }
}
