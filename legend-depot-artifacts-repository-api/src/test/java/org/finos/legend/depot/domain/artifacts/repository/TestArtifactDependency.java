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

package org.finos.legend.depot.domain.artifacts.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestArtifactDependency
{
    @Test
    public void testConstructorAndGetters()
    {
        ArtifactDependency dep = new ArtifactDependency("org.finos", "artifact", "1.0.0");
        Assertions.assertEquals("org.finos", dep.getGroupId());
        Assertions.assertEquals("artifact", dep.getArtifactId());
        Assertions.assertEquals("1.0.0", dep.getVersion());
    }

    @Test
    public void testEquality()
    {
        ArtifactDependency a = new ArtifactDependency("org.finos", "artifact", "1.0.0");
        ArtifactDependency b = new ArtifactDependency("org.finos", "artifact", "1.0.0");
        Assertions.assertEquals(a, b);
        Assertions.assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void testNotEqualDifferentVersion()
    {
        ArtifactDependency a = new ArtifactDependency("org.finos", "artifact", "1.0.0");
        ArtifactDependency b = new ArtifactDependency("org.finos", "artifact", "2.0.0");
        Assertions.assertNotEquals(a, b);
    }

    @Test
    public void testNotEqualDifferentGroup()
    {
        ArtifactDependency a = new ArtifactDependency("org.finos", "artifact", "1.0.0");
        ArtifactDependency b = new ArtifactDependency("com.example", "artifact", "1.0.0");
        Assertions.assertNotEquals(a, b);
    }

    @Test
    public void testNotEqualDifferentArtifact()
    {
        ArtifactDependency a = new ArtifactDependency("org.finos", "artifact-a", "1.0.0");
        ArtifactDependency b = new ArtifactDependency("org.finos", "artifact-b", "1.0.0");
        Assertions.assertNotEquals(a, b);
    }
}
