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
    private static class ConcreteCoordinateData extends CoordinateData
    {
        ConcreteCoordinateData()
        {
            super();
        }

        ConcreteCoordinateData(String groupId, String artifactId)
        {
            super(groupId, artifactId);
        }
    }

    @Test
    public void testDefaultConstructor()
    {
        ConcreteCoordinateData data = new ConcreteCoordinateData();
        Assertions.assertNull(data.getGroupId());
        Assertions.assertNull(data.getArtifactId());
    }

    @Test
    public void testParameterizedConstructor()
    {
        ConcreteCoordinateData data = new ConcreteCoordinateData("org.finos", "legend-depot");
        Assertions.assertEquals("org.finos", data.getGroupId());
        Assertions.assertEquals("legend-depot", data.getArtifactId());
    }

    @Test
    public void testSetArtifactId()
    {
        ConcreteCoordinateData data = new ConcreteCoordinateData("org.finos", "legend-depot");
        data.setArtifactId("new-artifact");
        Assertions.assertEquals("new-artifact", data.getArtifactId());
    }

    @Test
    public void testEquals()
    {
        ConcreteCoordinateData data1 = new ConcreteCoordinateData("org.finos", "legend-depot");
        ConcreteCoordinateData data2 = new ConcreteCoordinateData("org.finos", "legend-depot");
        Assertions.assertTrue(data1.equals(data2));
        ConcreteCoordinateData data3 = new ConcreteCoordinateData("org.other", "other-artifact");
        Assertions.assertFalse(data1.equals(data3));
    }

    @Test
    public void testHashCode()
    {
        ConcreteCoordinateData data1 = new ConcreteCoordinateData("org.finos", "legend-depot");
        ConcreteCoordinateData data2 = new ConcreteCoordinateData("org.finos", "legend-depot");
        Assertions.assertEquals(data1.hashCode(), data2.hashCode());
    }
}
