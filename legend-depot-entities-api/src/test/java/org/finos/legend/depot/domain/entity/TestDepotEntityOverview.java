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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestDepotEntityOverview
{
    @Test
    public void testConstructorAndGetters()
    {
        DepotEntityOverview overview = new DepotEntityOverview("org.finos", "artifact", "1.0.0", "my::entity::Path", "meta::pure::Class");
        Assertions.assertEquals("org.finos", overview.getGroupId());
        Assertions.assertEquals("artifact", overview.getArtifactId());
        Assertions.assertEquals("1.0.0", overview.getVersionId());
        Assertions.assertEquals("my::entity::Path", overview.getPath());
        Assertions.assertEquals("meta::pure::Class", overview.getClassifierPath());
    }

    @Test
    public void testEquality()
    {
        DepotEntityOverview a = new DepotEntityOverview("org.finos", "artifact", "1.0.0", "path", "classifier");
        DepotEntityOverview b = new DepotEntityOverview("org.finos", "artifact", "1.0.0", "path", "classifier");
        Assertions.assertEquals(a, b);
    }

    @Test
    public void testNotEqualDifferentPath()
    {
        DepotEntityOverview a = new DepotEntityOverview("org.finos", "artifact", "1.0.0", "path1", "classifier");
        DepotEntityOverview b = new DepotEntityOverview("org.finos", "artifact", "1.0.0", "path2", "classifier");
        Assertions.assertNotEquals(a, b);
    }
}
