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

package org.finos.legend.depot.domain.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProjectVersionTest
{
    @Test
    public void testGetGav()
    {
        ProjectVersion pv = new ProjectVersion("org.finos", "legend-depot", "1.0.0");
        Assertions.assertEquals("org.finos:legend-depot:1.0.0", pv.getGav());
    }

    @Test
    public void testEquals()
    {
        ProjectVersion pv1 = new ProjectVersion("org.finos", "legend-depot", "1.0.0");
        ProjectVersion pv2 = new ProjectVersion("org.finos", "legend-depot", "1.0.0");
        Assertions.assertEquals(pv1, pv2);
    }

    @Test
    public void testNotEquals()
    {
        ProjectVersion pv1 = new ProjectVersion("org.finos", "legend-depot", "1.0.0");
        ProjectVersion pv2 = new ProjectVersion("org.finos", "legend-depot", "2.0.0");
        Assertions.assertNotEquals(pv1, pv2);
    }

    @Test
    public void testHashCode()
    {
        ProjectVersion pv1 = new ProjectVersion("org.finos", "legend-depot", "1.0.0");
        ProjectVersion pv2 = new ProjectVersion("org.finos", "legend-depot", "1.0.0");
        Assertions.assertEquals(pv1.hashCode(), pv2.hashCode());
    }

    @Test
    public void testConstructorAndGetters()
    {
        ProjectVersion pv = new ProjectVersion("org.finos", "legend-depot", "2.5.0");
        Assertions.assertEquals("org.finos", pv.getGroupId());
        Assertions.assertEquals("legend-depot", pv.getArtifactId());
        Assertions.assertEquals("2.5.0", pv.getVersionId());
    }

    @Test
    public void testDefaultConstructor()
    {
        ProjectVersion pv = new ProjectVersion();
        Assertions.assertNull(pv.getGroupId());
        Assertions.assertNull(pv.getArtifactId());
        Assertions.assertNull(pv.getVersionId());
    }
}
