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

public class DepotEntityOverviewTest
{

    @Test
    public void testConstructorAndGetters()
    {
        DepotEntityOverview overview = new DepotEntityOverview("group", "artifact", "1.0.0", "some::path", "some::classifier");

        Assertions.assertEquals("some::path", overview.getPath());
        Assertions.assertEquals("some::classifier", overview.getClassifierPath());
    }

    @Test
    public void testGetPathReturnsNull()
    {
        DepotEntityOverview overview = new DepotEntityOverview("group", "artifact", "1.0.0", null, null);

        Assertions.assertNull(overview.getPath());
    }

    @Test
    public void testGetClassifierPathReturnsNull()
    {
        DepotEntityOverview overview = new DepotEntityOverview("group", "artifact", "1.0.0", null, null);

        Assertions.assertNull(overview.getClassifierPath());
    }

    @Test
    public void testEquals()
    {
        DepotEntityOverview a = new DepotEntityOverview("group", "artifact", "1.0.0", "some::path", "some::classifier");
        DepotEntityOverview b = new DepotEntityOverview("group", "artifact", "1.0.0", "some::path", "some::classifier");

        Assertions.assertTrue(a.equals(b));
    }

    @Test
    public void testEqualsNotEqual()
    {
        DepotEntityOverview a = new DepotEntityOverview("group", "artifact", "1.0.0", "some::path", "some::classifier");
        DepotEntityOverview b = new DepotEntityOverview("group", "artifact", "1.0.0", "other::path", "some::classifier");

        Assertions.assertFalse(a.equals(b));
    }

    @Test
    public void testHashCode()
    {
        DepotEntityOverview a = new DepotEntityOverview("group", "artifact", "1.0.0", "some::path", "some::classifier");
        DepotEntityOverview b = new DepotEntityOverview("group", "artifact", "1.0.0", "some::path", "some::classifier");

        Assertions.assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void testHashCodeDiffers()
    {
        DepotEntityOverview a = new DepotEntityOverview("group", "artifact", "1.0.0", "some::path", "some::classifier");
        DepotEntityOverview b = new DepotEntityOverview("group", "artifact", "2.0.0", "some::path", "some::classifier");

        Assertions.assertNotEquals(a.hashCode(), b.hashCode());
    }
}
