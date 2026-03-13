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

public class CoordinateDataTest
{
    private static class TestCoordinateData extends CoordinateData
    {
        public TestCoordinateData()
        {
            super();
        }

        public TestCoordinateData(String groupId, String artifactId)
        {
            super(groupId, artifactId);
        }
    }

    @Test
    public void canCreateWithDefaultConstructor()
    {

        TestCoordinateData coordinate = new TestCoordinateData();

        Assertions.assertNotNull(coordinate);

    }

    @Test
    public void canCreateWithParameterizedConstructor()
    {

        TestCoordinateData coordinate = new TestCoordinateData("org.example", "my-artifact");

        Assertions.assertNotNull(coordinate);
        Assertions.assertEquals("org.example", coordinate.getGroupId());
        Assertions.assertEquals("my-artifact", coordinate.getArtifactId());

    }

    @Test
    public void canGetGroupId()
    {

        TestCoordinateData coordinate = new TestCoordinateData("org.example", "my-artifact");

        String groupId = coordinate.getGroupId();

        Assertions.assertEquals("org.example", groupId);

    }

    @Test
    public void canGetArtifactId()
    {

        TestCoordinateData coordinate = new TestCoordinateData("org.example", "my-artifact");

        String artifactId = coordinate.getArtifactId();

        Assertions.assertEquals("my-artifact", artifactId);

    }

    @Test
    public void canSetArtifactId()
    {

        TestCoordinateData coordinate = new TestCoordinateData("org.example", "my-artifact");

        coordinate.setArtifactId("new-artifact");

        Assertions.assertEquals("new-artifact", coordinate.getArtifactId());

    }

    @Test
    public void testEquals()
    {

        TestCoordinateData coordinate1 = new TestCoordinateData("org.example", "my-artifact");
        TestCoordinateData coordinate2 = new TestCoordinateData("org.example", "my-artifact");
        TestCoordinateData coordinate3 = new TestCoordinateData("org.other", "other-artifact");

        Assertions.assertTrue(coordinate1.equals(coordinate2));
        Assertions.assertFalse(coordinate1.equals(coordinate3));
        Assertions.assertFalse(coordinate1.equals(null));

    }

    @Test
    public void testHashCode()
    {

        TestCoordinateData coordinate1 = new TestCoordinateData("org.example", "my-artifact");
        TestCoordinateData coordinate2 = new TestCoordinateData("org.example", "my-artifact");

        int hashCode1 = coordinate1.hashCode();
        int hashCode2 = coordinate2.hashCode();

        Assertions.assertEquals(hashCode1, hashCode2);

    }
}
