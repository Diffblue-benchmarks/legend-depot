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
    // Concrete implementation for testing
    private static class TestVersionedData extends VersionedData
    {
        public TestVersionedData()
        {
            super();
        }

        public TestVersionedData(String groupId, String artifactId, String versionId)
        {
            super(groupId, artifactId, versionId);
        }
    }

    @Test
    public void canCreateVersionedDataWithDefaultConstructor()
    {

        TestVersionedData data = new TestVersionedData();

        Assertions.assertNotNull(data);

    }

    @Test
    public void canCreateVersionedDataWithParameters()
    {

        TestVersionedData data = new TestVersionedData("test.group", "test-artifact", "1.0.0");

        Assertions.assertNotNull(data);
        Assertions.assertEquals("test.group", data.getGroupId());
        Assertions.assertEquals("test-artifact", data.getArtifactId());
        Assertions.assertEquals("1.0.0", data.getVersionId());

    }

    @Test
    public void canGetVersionId()
    {

        TestVersionedData data = new TestVersionedData("test.group", "test-artifact", "2.0.0");

        String versionId = data.getVersionId();

        Assertions.assertEquals("2.0.0", versionId);

    }

    @Test
    public void canSetVersionId()
    {

        TestVersionedData data = new TestVersionedData();

        data.setVersionId("3.0.0");

        Assertions.assertEquals("3.0.0", data.getVersionId());

    }

    @Test
    public void testEquals()
    {

        TestVersionedData data1 = new TestVersionedData("test.group", "test-artifact", "1.0.0");
        TestVersionedData data2 = new TestVersionedData("test.group", "test-artifact", "1.0.0");
        TestVersionedData data3 = new TestVersionedData("test.group", "test-artifact", "2.0.0");

        Assertions.assertTrue(data1.equals(data2));
        Assertions.assertFalse(data1.equals(data3));
        Assertions.assertFalse(data1.equals(null));

    }

    @Test
    public void testHashCode()
    {

        TestVersionedData data1 = new TestVersionedData("test.group", "test-artifact", "1.0.0");
        TestVersionedData data2 = new TestVersionedData("test.group", "test-artifact", "1.0.0");

        Assertions.assertEquals(data1.hashCode(), data2.hashCode());

    }
}
